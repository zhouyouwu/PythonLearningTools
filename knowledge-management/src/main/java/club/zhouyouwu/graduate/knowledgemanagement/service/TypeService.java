package club.zhouyouwu.graduate.knowledgemanagement.service;

import club.zhouyouwu.graduate.knowledgemanagement.model.params.SetTypeParam;

public interface TypeService {

    void setChapter(SetTypeParam param);

    /**
     * 将此章节禁用、开启，非完全删除
     * @param typeId
     * @param op 1 on|0 off
     */
    void setChapterOnOff(int typeId, String op);

    void mdfChapter(SetTypeParam typeParam);


}
