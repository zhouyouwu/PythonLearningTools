package club.zhouyouwu.graduate.knowledgemanagement.service.impl;

import club.zhouyouwu.graduate.knowledgemanagement.constant.FilePath;
import club.zhouyouwu.graduate.knowledgemanagement.mapper.KnowledgeMapper;
import club.zhouyouwu.graduate.knowledgemanagement.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.markdownj.MarkdownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    private KnowledgeMapper knowledgeMapper;

    public void makeHtml(String dirPath, String fileName, String markdown) throws Exception {
        MarkdownProcessor processor = new MarkdownProcessor();
        String html = processor.markdown(markdown);

        File dir = new File(dirPath);
        if(!dir.exists() && !dir.mkdirs()){
            log.error("创建目录失败......"+dirPath);
            return;
        }

        FileOutputStream outputStream = new FileOutputStream(dirPath + "/" + fileName + ".html");
        outputStream.write(html.getBytes());
        outputStream.close();

    }

    @Override
    public long setHtml(String path, int typeId) {

        return knowledgeMapper.setKnowledge(path, typeId);
    }

    @Override
    public void delHtml(Integer typeId, Long knowId) {

        File file = new File(FilePath.KNOWLEDGE_FILE_PATH+typeId+"/"+knowId+".html");
        if(file.exists()){
            if(file.delete()) {
                knowledgeMapper.delKnowledge(knowId);
            }
        }
    }
}
