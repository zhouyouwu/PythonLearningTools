package club.zhouyouwu.graduate.correct.feign;

import club.zhouyouwu.graduate.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "KnowledgeManagement")
public interface KnowledgeFeign {

    @GetMapping("knowledge")
    Result getQuestions(@RequestParam List<Long> quesIds);
}
