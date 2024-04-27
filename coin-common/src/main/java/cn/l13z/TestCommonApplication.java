package cn.l13z;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ClassName: TestCommonApplication.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 06:59 <br> Description: 测试启动类 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando 测试启动类 <br>
 */
@SuppressWarnings("unused")
@SpringBootApplication
@EnableDiscoveryClient
public class TestCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestCommonApplication.class, args);
    }
}
