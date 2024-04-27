package cn.l13z.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ClassName: OAuth2FeignConfig.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 03:10 <br> Description: 认证配置 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando 认证配置 <br>
 */
@SuppressWarnings("unused")
@Slf4j
public class OAuth2FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // 1 我们可以从request的上下文环境里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.info("没有请求的上下文,故无法进行token的传递");
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取我们请求上下文的头里面的AUTHORIZATION
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(header)) {
            template.header(HttpHeaders.AUTHORIZATION, header);
            log.info("本次token传递成功,token的值为:{}", header);
        }
    }
}
