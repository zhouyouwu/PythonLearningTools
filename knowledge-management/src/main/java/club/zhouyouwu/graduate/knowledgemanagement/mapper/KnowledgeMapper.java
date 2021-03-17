package club.zhouyouwu.graduate.knowledgemanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface KnowledgeMapper {

    long setKnowledge(@Param("path") String path, @Param("typeId") Integer typeId);

    void delKnowledge(Long knowId);
}
