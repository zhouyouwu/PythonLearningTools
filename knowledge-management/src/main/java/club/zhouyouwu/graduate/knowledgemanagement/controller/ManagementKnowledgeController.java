package club.zhouyouwu.graduate.knowledgemanagement.controller;

import club.zhouyouwu.graduate.common.entity.Result;
import club.zhouyouwu.graduate.knowledgemanagement.constant.FilePath;
import club.zhouyouwu.graduate.knowledgemanagement.model.params.Question;
import club.zhouyouwu.graduate.knowledgemanagement.model.params.SetTypeParam;
import club.zhouyouwu.graduate.knowledgemanagement.service.KnowledgeService;
import club.zhouyouwu.graduate.knowledgemanagement.service.QuestionService;
import club.zhouyouwu.graduate.knowledgemanagement.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Knowledge")
public class ManagementKnowledgeController {
    @Autowired
    private KnowledgeService knowledgeService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TypeService typeService;

    @PostMapping("/{typeId}/knowledge")
    public Result createKnowledgePage(@PathVariable int typeId, String pageData) throws Exception {
        String path = FilePath.KNOWLEDGE_FILE_PATH + typeId;
        long knowId = knowledgeService.setHtml(path, typeId);

        knowledgeService.makeHtml(path, Long.toString(knowId), pageData);

        return Result.ok("添加成功");
    }

    @PutMapping("/{typeId}/{knowId}")
    public Result mdfKnowledgePage(@PathVariable int typeId, @PathVariable Long knowId, String pageData) throws Exception {
        String path = FilePath.KNOWLEDGE_FILE_PATH + typeId;

        knowledgeService.makeHtml(path, Long.toString(knowId), pageData);
        return Result.ok("修改成功");
    }

    @DeleteMapping("/{typeId}/{knowId}")
    public Result delKnowledgePage(@PathVariable int typeId, @PathVariable Long knowId) {

        knowledgeService.delHtml(typeId, knowId);
        return Result.ok("删除成功");
    }

    /**
     * 创建问题
     * @param model normal即普通题目，program编程题
     * @param typeId 没用占位，题目所属章节
     * @param pageData 页面信息，md数据
     * @return
     * @throws Exception
     */
    @PostMapping("{model}/{typeId}/question")
    public Result createQuestionPage(@PathVariable String model, @PathVariable int typeId, @RequestBody Question pageData) throws Exception {

        questionService.setQuestion(model, pageData);
        return Result.ok();
    }

    @DeleteMapping("{model}/{typeId}/{quesId}")
    public Result delQuestionPage(@PathVariable String model, @PathVariable int typeId, @PathVariable Long quesId) {

        questionService.delQuestion(model, quesId);
        return Result.ok("删除成功");
    }

    @PutMapping("{model}/{typeId}/{quesId}")
    public Result mdfQuestionPage(@PathVariable String model, @PathVariable int typeId, @RequestBody Question pageData) throws Exception {

        questionService.mdfQuestion(model, pageData);
        return Result.ok();
    }

    @PostMapping("/type")
    public Result createChapter(@RequestBody SetTypeParam typeParam) throws Exception {

        typeService.setChapter(typeParam);
        return Result.ok("处理成功");
    }

    /**
     * 章节删除是假删除，不启用
     * @param typeId
     * @param op 1开启，0关闭
     * @return
     */
    @PutMapping("/type/{typeId}/{op}")
    public Result setChapterOnOff(@PathVariable int typeId, @PathVariable String op) {

        typeService.setChapterOnOff(typeId, op);
        return Result.ok("删除成功");
    }



    /**
     *
     * @param typeId
     * @return
     * @throws Exception
     */
    @PutMapping("/type/{typeId}")
    public Result mdfChapter(@RequestBody SetTypeParam typeParam, @PathVariable Integer typeId) throws Exception {

        typeService.mdfChapter(typeParam);
        return Result.ok();
    }
}
