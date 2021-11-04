package samueldev.projects.products.docs;

import org.springframework.context.annotation.Configuration;
import samueldev.projects.core.docs.BaseSwaggerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class
SwaggerConfig extends BaseSwaggerConfig {
    public SwaggerConfig() {
        super("samueldev.projects.products.controller", true
        );
    }

}
