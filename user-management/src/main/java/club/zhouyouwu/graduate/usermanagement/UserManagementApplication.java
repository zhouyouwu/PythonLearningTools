package club.zhouyouwu.graduate.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhou
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("club.zhouyouwu.graduate.usermanagement.feign")
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
