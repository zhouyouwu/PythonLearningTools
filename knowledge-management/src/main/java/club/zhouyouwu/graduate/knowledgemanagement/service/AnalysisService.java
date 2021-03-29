package club.zhouyouwu.graduate.knowledgemanagement.service;

import java.util.Map;

public interface AnalysisService {
    Map<String, Object> getUserTrainingData(Long userId);

    Object getUserTrainingDataByDate(Long userId, String startDate, String endDate);
}
