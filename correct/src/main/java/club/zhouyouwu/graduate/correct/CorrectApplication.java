package club.zhouyouwu.graduate.correct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("club.zhouyouwu.graduate.correct.feign")
@EnableDiscoveryClient
public class CorrectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorrectApplication.class, args);
	}

}
