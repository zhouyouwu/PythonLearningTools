package club.zhouyouwu.graduate.gateway.config;

import club.zhouyouwu.graduate.gateway.dto.Resp;
import club.zhouyouwu.graduate.gateway.dto.Result;
import club.zhouyouwu.graduate.gateway.feign.UserFeign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties("org.my.jwt")
@Setter
@Getter
@Slf4j
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private String[] skipAuthUrls;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserFeign userFeign;

    /**
     * 过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();

        //跳过不需要验证的路径
        if (null != skipAuthUrls && Arrays.asList(skipAuthUrls).contains(url)) {
            return chain.filter(exchange);
        }

        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        ServerHttpResponse resp = exchange.getResponse();
        if (StringUtils.isBlank(token)) {
            //没有token
            return authErro(resp, "请登陆");
        } else {
            //有token
            Result result = userFeign.checkToken(token);
            if (result.getCode() == 0) {
                return chain.filter(exchange);
            } else {
                log.error(result.getMsg());
                if (result.getMsg().contains("Allowed clock skew")) {
                    return authErro(resp, "认证过期");
                } else {
                    return authErro(resp, "认证失败");
                }
            }
        }
    }

    /**
     * 认证错误输出
     *
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp, String mess) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Resp<String> returnData = new Resp<>(org.apache.http.HttpStatus.SC_UNAUTHORIZED, mess, mess);
        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(returnData);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

}
