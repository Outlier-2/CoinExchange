package cn.l13z.config.resource;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

/**
 * ClassName: ResourceServerConfig.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 02:23 <br> Description: 资源服务器配置 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando 资源服务器配置 <br>
 */
@SuppressWarnings("unused")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置HTTP安全性
        http.csrf()
            .disable()  // 禁用跨站请求伪造
            .sessionManagement().disable()  // 禁用Session管理
            .authorizeRequests()  // 授权请求
            .antMatchers(
                "/users/setPassword",
                "/users/register",
                "/sms/sendTo",
                "/gt/register",
                "/login",
                "/v2/api-docs",
                "/swagger-resources/configuration/ui",  // 用来获取支持的动作
                "/swagger-resources",  // 用来获取api-docs的URI
                "/swagger-resources/configuration/security",  // 安全选项
                "/webjars/**",
                "/swagger-ui.html"
            ).permitAll()  // 指定路径允许所有用户访问
            .antMatchers("/**").authenticated()  // 其他路径需要认证
            .and().headers().cacheControl();  // 设置响应头缓存控制
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore());

    }

    private TokenStore jwtTokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
        return jwtTokenStore;
    }

    @Bean // 放在ioc容器的
    public JwtAccessTokenConverter accessTokenConverter() {
        //resource 验证token（公钥） authorization 产生 token （私钥）
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        String s = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("coinexchange.txt");
            byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
            s = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tokenConverter.setVerifierKey(s);
        return tokenConverter;
    }
}
