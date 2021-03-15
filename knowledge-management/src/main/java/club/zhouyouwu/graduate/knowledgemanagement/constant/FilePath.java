package club.zhouyouwu.graduate.knowledgemanagement.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:FilePath.properties")
public class FilePath {
    @Value("{KNOWLEDGE_PATH}")
    public static String KNOWLEDGE_FILE_PATH;

    @Value("{IMAGE_PATH}")
    public static String IMAGE_PATH;
}
