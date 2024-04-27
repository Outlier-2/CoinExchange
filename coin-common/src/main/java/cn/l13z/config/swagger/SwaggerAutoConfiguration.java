package cn.l13z.config.swagger;

import java.util.Collections;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
/**
 * ClassName: swaggerAutoConfiguration.java <br>
 *
 * @author AlfredOrlando <br>
 * <p>
 * Created: 2024-04-27 02:00 <br> Description: SwaggerApi排配置类 <br>
 * <p>
 * Modification History: <br> - 2024/4/27 AlfredOrlando SwaggerApi排配置类 <br>
 */

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    private final SwaggerProperties swaggerProperties;

    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    public Docket docker() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
            .paths(PathSelectors.any())
            .build();
        docket.securitySchemes(securitySchemes())
            .securityContexts(securityContexts());

        return docket;

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().contact(
                new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail())
            )
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .version(swaggerProperties.getVersion())
            .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
            .build();

    }


    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey("Authorization", "Authorization", "Authorization"));
    }


    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(new SecurityContext(
            Collections.singletonList(new SecurityReference("Authorization",
                new AuthorizationScope[]{new AuthorizationScope("global", "accessResource")})),
            PathSelectors.any()
        ));
    }

}



