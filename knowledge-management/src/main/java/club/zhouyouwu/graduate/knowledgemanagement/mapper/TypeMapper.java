package club.zhouyouwu.graduate.knowledgemanagement.mapper;

import club.zhouyouwu.graduate.knowledgemanagement.model.params.SetTypeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TypeMapper {

    void setChapter(SetTypeParam param);

    void mdfChapter(SetTypeParam typeParam);

    void setChapterOnOff(@Param("typeId") int typeId, @Param("op") String op);
}
