package club.zhouyouwu.graduate.knowledgemanagement.service;

public interface KnowledgeService {

    void makeHtml(String dirPath, String fileName, String markdown) throws Exception;

    long setHtml(String path, int typeId);

    void delHtml(Integer typeId, Long knowId);
}
