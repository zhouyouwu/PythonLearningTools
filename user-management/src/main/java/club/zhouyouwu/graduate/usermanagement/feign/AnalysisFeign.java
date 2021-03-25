package club.zhouyouwu.graduate.usermanagement.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "analysis")
public interface AnalysisFeign {

    @GetMapping("analysis/{userId}/knowledgeType")
    long analysisKnowledgeType(@PathVariable long userId);
}
