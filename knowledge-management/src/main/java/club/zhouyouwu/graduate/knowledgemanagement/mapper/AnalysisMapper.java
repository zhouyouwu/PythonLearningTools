package club.zhouyouwu.graduate.knowledgemanagement.mapper;

import club.zhouyouwu.graduate.knowledgemanagement.model.entity.UserSolve;
import club.zhouyouwu.graduate.knowledgemanagement.model.vo.TrainingDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnalysisMapper {
    List<UserSolve> getUserTrainingData(Long userId);

    List<TrainingDetail> getUserTrainingByDate(@Param("userId") Long userId, @Param("dateList") List<String> dateList);
}
