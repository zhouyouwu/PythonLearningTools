package club.zhouyouwu.graduate.knowledgemanagement.mapper;

import club.zhouyouwu.graduate.knowledgemanagement.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    List<Question> getUnsolvedQuestions(@Param("userId") Long userId, @Param("count") Integer count,
                                        @Param("typeId") Long typeId);
}
