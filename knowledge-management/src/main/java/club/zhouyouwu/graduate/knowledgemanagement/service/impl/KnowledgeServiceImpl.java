package club.zhouyouwu.graduate.knowledgemanagement.service.impl;

import club.zhouyouwu.graduate.knowledgemanagement.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.markdownj.MarkdownProcessor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Slf4j
@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    public boolean makeHtml(String dirPath, String fileName, String markdown) throws Exception {
        MarkdownProcessor processor = new MarkdownProcessor();
        String html = processor.markdown(markdown);

        File dir = new File(dirPath);
        if(!dir.exists() && !dir.mkdirs()){
            log.error("创建目录失败......"+dirPath);
            return false;
        }

        FileOutputStream outputStream = new FileOutputStream(dirPath + "/" + fileName + ".html");
        outputStream.write(html.getBytes());
        outputStream.close();

        return true;
    }

}
