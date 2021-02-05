package club.zhouyouwu.graduate.usermanagement.service;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient
public interface OpenFeign {
    List<Long> getUnsolvedQuestion(long userId);

    // getQuestion(long quesId);
}
