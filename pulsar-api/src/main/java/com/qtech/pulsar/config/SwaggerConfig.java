package com.qtech.pulsar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/14 14:30:40
 * desc   :  Swagger Bean 访问：ip:port/swagger-ui.html
 */


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo("Api Documentation",
                                "Api Documentation", "1.0", "urn:tos",
                                new Contact("", "", ""), "Apache 2.0",
                                "http://www.apache.org/licenses/LICENSE-2.0",
                                new ArrayList()
                        )
                );
    }
}
