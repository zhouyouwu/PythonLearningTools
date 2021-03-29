package club.zhouyouwu.graduate.knowledgemanagement.service.impl;

import club.zhouyouwu.graduate.knowledgemanagement.mapper.TypeMapper;
import club.zhouyouwu.graduate.knowledgemanagement.model.params.SetTypeParam;
import club.zhouyouwu.graduate.knowledgemanagement.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public void setChapter(SetTypeParam param) {

        typeMapper.setChapter(param);
    }

    @Override
    public void setChapterOnOff(int typeId, String op) {
        typeMapper.setChapterOnOff(typeId, op);
    }

    @Override
    public void mdfChapter(SetTypeParam typeParam) {
        typeMapper.mdfChapter(typeParam);
    }
}
