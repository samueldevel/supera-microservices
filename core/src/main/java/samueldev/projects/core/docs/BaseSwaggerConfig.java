package samueldev.projects.core.docs;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;


public class BaseSwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final String basePackages;
    private final Boolean haveAuthorization;

    public BaseSwaggerConfig(String basePackages, Boolean haveAuthorization) {
        this.basePackages = basePackages;
        this.haveAuthorization = haveAuthorization;
    }

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackages))
                .build()
                .apiInfo(metaData());

        if (Boolean.TRUE.equals(haveAuthorization)) {
            docket.securityContexts(List.of(securityContext()));
            docket.securitySchemes(List.of(apiKey()));
        }

        return docket;
    }

    private ApiInfo metaData() {

        return new ApiInfoBuilder()
                .title("Documentation of Superagames application microservices")
                .description("Refer to this documentation to be able to analyze the project")
                .version("1.0")
                .contact(new Contact("Samuel Elias", "https://github.com/muelthebest/", "samuel.elias.dev@gmail.com"))
                .license("Copyright samueldev")
                .licenseUrl("https://www.linkedin.com/in/samueleliasdev/")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return List.of(new SecurityReference("JWT", authorizationScopes));
    }
}
