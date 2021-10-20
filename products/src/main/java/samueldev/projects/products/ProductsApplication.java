package samueldev.projects.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import samueldev.projects.core.property.JwtConfiguration;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"samueldev.projects.core.domain"})
@EnableJpaRepositories({"samueldev.projects.core.repository"})
@EnableConfigurationProperties(value = JwtConfiguration.class)
@ComponentScan("samueldev.projects")
public class ProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

}

