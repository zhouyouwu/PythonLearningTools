package club.zhouyouwu.graduate.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-management")
public interface UserFeign {

}
