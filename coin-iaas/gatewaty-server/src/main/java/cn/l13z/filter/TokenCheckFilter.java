package cn.l13z.filter;

import com.alibaba.fastjson.JSONObject;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ClassName: TokenCheckFilter.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-25 22:53 <br> Description: 检查Jwt <br>
 * <p>
 * Modification History: <br> - 2024/4/25 AlfredOrlando 检查Jwt <br>
 */
@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {

    @Value("${no.token.access.urls:/admin/login,/admin/validate/code}")
    private Set<String> noTokenAccessUrls;
//    private Redis

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 实现判断用户是否携带token ，或token 错误的功能
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 不需要token 就能访问
        if (allowNoTokenAccess(exchange)) {
            return chain.filter(exchange);
        }
        // 获取用户的token
        String token = getToken(exchange);

        if (StringUtils.isEmpty(token)) { // token 为 Empty
            return buildUNAuthorizedResult(exchange);
        }
        if (false) {
            return buildUNAuthorizedResult(exchange);
        }

        return chain.filter(exchange);
    }

    private boolean allowNoTokenAccess(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (noTokenAccessUrls.contains(path)) {
            return true;
        }
        return false;
    }

    /**
     * 从头里面获取
     *
     * @param exchange
     * @return
     */
    private String getToken(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorization) || authorization.trim().isEmpty()) {
            return null;
        }
        return authorization.replace("bearer ", "");
    }

    private Mono<Void> buildUNAuthorizedResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set("Content-Type", "application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "unauthorized");
        jsonObject.put("error_description", "invalid_token");
        DataBuffer dataBuffer = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(dataBuffer));
    }
}

