package club.zhouyouwu.graduate.usermanagement.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //其它
        shiroFilter.setLoginUrl("/view/user/toLogin");
        shiroFilter.setUnauthorizedUrl("/view/user/toLogin");
        Map<String, String> filterMap = new LinkedHashMap<>();
        //静态资源
        filterMap.put("/css/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/scss/**", "anon");
        filterMap.put("/vendor/**", "anon");
        //免认证的请求
        filterMap.put("/view/user/toLogin", "anon");
        filterMap.put("/api/user/login", "anon");
        //test
        filterMap.put("/view/part/details", "anon");
        filterMap.put("/view/test", "anon");
        //swagger
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");

        //登出
        filterMap.put("/view/part/logoutSystem", "logout");
        //需要认证的url
        filterMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCachingEnabled(true);
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        return userRealm;
    }
}
