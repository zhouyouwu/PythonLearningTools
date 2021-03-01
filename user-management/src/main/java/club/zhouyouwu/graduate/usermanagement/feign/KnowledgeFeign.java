package club.zhouyouwu.graduate.usermanagement.feign;

import club.zhouyouwu.graduate.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("knowledge-management")
public interface KnowledgeFeign {

    @GetMapping("/knowledge/unsolved")
    Result getUnsolvedQuestions(long userId, int count);

    @GetMapping("/knowledge/unsolved")
    Result getUnsolvedQuestions(long userId, int count, long typeId);

    @GetMapping("/knowledge/{quesId}")
    Object getQuestion(@PathVariable long quesId);
}
