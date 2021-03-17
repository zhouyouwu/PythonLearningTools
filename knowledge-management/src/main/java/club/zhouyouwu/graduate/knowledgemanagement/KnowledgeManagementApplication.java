package club.zhouyouwu.graduate.knowledgemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class KnowledgeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeManagementApplication.class, args);
	}

}
