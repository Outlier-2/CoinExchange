package cn.l13z.filter;

import com.alibaba.fastjson.JSONObject;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ClassName: JwtCheckFilter.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-26 05:20 <br> Description: 检查Jwt <br>
 * <p>
 * Modification History: <br> - 2024/4/26 AlfredOrlando 检查Jwt <br>
 */
@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${no.require.token.uris:/admin/}")
    private Set<String> noRequireTokenUris;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1.判断API是否需要Token才能访问
        if (!isRequireToken(exchange)) {
            return chain.filter(exchange);
        }
        // 2. 获取用户的token
        String token = getUserToken(exchange);
        if (StringUtils.isEmpty(token)) {
            return buildeNoAuthorizationResult(exchange);
        }
        Boolean hasKey = redisTemplate.hasKey(token);
        if (hasKey != null & hasKey) {

            return chain.filter(exchange);

        }

        return buildeNoAuthorizationResult(exchange);
    }

    private Mono<Void> buildeNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "No Authorization");
        jsonObject.put("error_message", "Token is NUll or Error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));
    }

    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        return token == null ? null : token.replace("bearer", "");
    }

    private boolean isRequireToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return !noRequireTokenUris.contains(path);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
