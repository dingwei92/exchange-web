package com.topsci.qh.webmanagement.Business;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("登录校验")//name表示名称，description表示描述
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).defaultValue("Bearer ").build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())  //显示所有类
                .apis(RequestHandlerSelectors.basePackage("com.topsci.qh.webmanagement.Business")) //指定提供接口所在的基包
                .build()
                .globalOperationParameters(pars)//************把消息头添加
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IOT-API")  //粗标题
                .description("HTTP对外开放接口")   //描述
                .version("1.0.0")   //api version
                .build();
    }

}