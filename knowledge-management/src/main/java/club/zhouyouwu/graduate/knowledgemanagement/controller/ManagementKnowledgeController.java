package club.zhouyouwu.graduate.knowledgemanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.knowledgemanagement.constant.FilePath;
import club.zhouyouwu.graduate.knowledgemanagement.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Knowledge")
public class ManagementKnowledgeController {
    @Autowired
    private KnowledgeService knowledgeService;

    @PostMapping("/{typeId}/{knowId}")
    public Result createKnowledgePage(@PathVariable int typeId, @PathVariable long knowId, String pageData) throws Exception {

        knowledgeService.makeHtml(FilePath.KNOWLEDGE_FILE_PATH+typeId, Long.toString(knowId), pageData);
        return Result.ok();
    }

}
