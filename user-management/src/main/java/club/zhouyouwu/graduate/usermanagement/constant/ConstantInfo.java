package club.zhouyouwu.graduate.usermanagement.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:microservice.properties")
public class ConstantInfo {
    @Value("${USER_ID_NOT_EXIST}")
    public static long USER_ID_NOT_EXIST;

    @Value("${WORKER_ID}")
    public static long WORKER_ID;

    @Value("${DATACENTER_ID}")
    public static long DATACENTER_ID;

    @Value("${DATACENTER_ID_FOR_IMAGE}")
    public static long DATACENTER_ID_FOR_IMAGE;

    @Value("${PASSWORD_ENCODE_ROUNDS}")
    public static int LOG_ROUNDS;

    @Value("${TEMP_FILE_PATH}")
    public static String TEMP_FILE_PATH;

    @Value("${IMAGE_PATH}")
    public static String IMAGE_PATH;


}
