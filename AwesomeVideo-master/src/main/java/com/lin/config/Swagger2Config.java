package com.lin.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        ArrayList paramList = new ArrayList();
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select().apis(RequestHandlerSelectors.basePackage((String)"com.lin.controller")).paths(PathSelectors.any()).build().globalOperationParameters(paramList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("\u503e\u5fc3\u77ed\u89c6\u9891\u540e\u7aefAPI\u63a5\u53e3\u6587\u6863").contact(new Contact("lkmc2", "https://github.com/lkmc2", "lkmc2@163.com")).description("\u6b22\u8fce\u8bbf\u95ee\u77ed\u89c6\u9891\u63a5\u53e3\u6587\u6863").version("1.0").build();
    }
}
