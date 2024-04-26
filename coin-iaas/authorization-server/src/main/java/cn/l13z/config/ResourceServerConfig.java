package cn.l13z.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * ClassName: ResourceServerConfig.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-25 19:55 <br> Description: 资源服务器配置控制器 <br>
 * <p>
 * Modification History: <br> - 2024/4/25 AlfredOrlando 资源服务器配置控制器 <br>
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


}
