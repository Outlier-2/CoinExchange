package cn.l13z.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

/**
 * ClassName: AuthorizationConfig.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-25 01:45 <br> Description: 授权配置类 <br>
 * <p>
 * Modification History: <br> - 2024/4/25 AlfredOrlando 授权配置类 <br>
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    // 依赖注入密码编码器
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 依赖注入认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    // 依赖注入用户详情服务
    @Autowired
    private UserDetailsService userDetailsService;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置客户端信息，包括客户端ID、客户端密码、客户端访问范围、授权类型、访问令牌有效期、刷新令牌有效期等。
     *
     * @param clients clients
     * @throws Exception 配置客户端信息异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置内存中的客户端信息，用于授权服务器验证客户端
        clients.inMemory()
            // 客户端ID
            .withClient("coin-api")
            // 客户端密码，并使用密码编码器加密
            .secret(passwordEncoder.encode("coin-secret"))
            // 客户端的访问范围
            .scopes("all")
            // 授权类型，允许使用密码和刷新令牌进行授权
            .authorizedGrantTypes("password", "refresh")
            // 访问令牌的有效期，单位为秒
            .accessTokenValiditySeconds(24 * 7200)
            // 刷新令牌的有效期，单位为秒
            .refreshTokenValiditySeconds(7 * 24 * 7200);
    }

    /**
     * 配置授权服务器端点，包括令牌存储、认证管理器、用户详情服务等。
     *
     * @param endpoints endpoints
     * @throws Exception 配置授权服务器端点异常
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new InMemoryTokenStore())
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
            //存储JWT令牌
            .tokenStore(jwtTokenStore())
            .tokenEnhancer(jwtAccessTokenConverter());

        super.configure(endpoints);
    }

    public  TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter() ;
        // 读取classpath 下面的密钥文件
        ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
        // 获取KeyStoreFactory
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,"coinexchange".toCharArray()) ;
        // 给JwtAccessTokenConverter 设置一个密钥对
        tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinexchange","coinexchange".toCharArray()));
        return  tokenConverter ;
    }


//    public TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
