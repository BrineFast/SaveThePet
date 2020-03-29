package com.savethepet.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Alexey Klimov
 * Swagger Configuration
  */
@Configuration
@EnableSwagger2
@NoArgsConstructor
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return (new Docket(DocumentationType.SWAGGER_2)).select().apis(RequestHandlerSelectors.basePackage(""))
                .build().useDefaultResponseMessages(false);
    }
}
