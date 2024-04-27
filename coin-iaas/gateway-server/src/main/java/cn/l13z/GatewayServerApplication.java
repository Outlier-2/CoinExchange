package cn.l13z;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ClassName: GatewayServerApplication.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-24 22:57 <br> Description: 网关服务启动类 <br>
 * <p>
 * Modification History: <br> - 2024/4/24 AlfredOrlando 网关服务启动类 <br>
 */
@SpringBootApplication
@EnableDiscoveryClient

public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
