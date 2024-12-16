package com.my.springboot.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

//接口说明文档配置类
@Configuration
@EnableSwagger2  //启动配置类
public class Swagger2Config {
    @Value("${swagger2.basepackage}")
    private String basePackage;

    // 注意如果配置文件yml中配置了  add-mappings: false 需要写个配置文件 本项目配置文件类在 webmvcconfig中
//    同时如果加了拦截器 需要放行访问地址 本项目在SecurityConfig0中放行
//    http://localhost:你的端口/swagger-ui.html
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
//      测试token 10年有效期  eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSMjE0NLQwMjQ1dygs1EvOz1XSUSotTi0Kys9JNTIyAioI8vdxjXd08fX00wEzQ4Ndg4CKUisKlKwMLc2NDCzMzU3MawGxSOicUAAAAA.YD7zNl7TkVVhTqaKrFFU9h1KwCD-PlNT2krsNCYYXNwk4Z0dCZGafPW_2LADCPdZVPDFtm_9NmYj_yRNxxr4Fw
        ticketPar.name("Authorization").description("Bearer token")//Token 以及Authorization 为自定义的参数，session保存的名字是哪个就可以写成那个
                .defaultValue("Authorization-Bearer eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ." +
                        "H4sIAAAAAAAAAKtWKi5NUrJSMjE0NLQwMjQ1dygs1EvOz1XSUSot" +
                        "Ti0Kys9JNTIyAioI8vdxjXd08fX00wEzQ4Ndg4CKUisKlKwMLc2N" +
                        "DCzMzU3MawGxSOicUAAAAA.YD7zNl7TkVVhTqaKrFFU9h1KwCD-P" +
                        "lNT2krsNCYYXNwk4Z0dCZGafPW_2LADCPdZVPDFtm_9NmYj_yRNxxr4Fw")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("我的标题")
                .description("我的描述")
                .termsOfServiceUrl("http://xxx")
                .version("1.0")
                .build();
    }

}