package club.zhouyouwu.graduate.gateway.feign;

import club.zhouyouwu.graduate.gateway.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-management")
public interface UserFeign {

    @GetMapping("/user/checkTokenResult")
    Result checkToken(@RequestParam String token);
}
