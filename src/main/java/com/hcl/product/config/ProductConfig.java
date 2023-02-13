package com.hcl.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class ProductConfig {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("ecommerce").apiInfo(apiInfo()).select()
                .paths(regex("/productmanagement.*")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Product Service")
                .description("Sample Documentation Generateed Using SWAGGER2 for our product Rest API")
                .termsOfServiceUrl("https://www.hcl.com/channel/IL")
                .license("HCL License")
                .licenseUrl("https://www.hcl.com/channel/IL").version("1.0").build();
    }
}
