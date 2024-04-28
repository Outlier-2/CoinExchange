package cn.l13z;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ClassName: AdminServiceApplication.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 21:43 <br> Description: admin-service 启动类 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando admin-service 启动类 <br>
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }
}
