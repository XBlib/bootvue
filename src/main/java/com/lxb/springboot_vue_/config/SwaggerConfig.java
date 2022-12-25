//package com.lxb.springboot_vue_.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * @author XBlib
// * @version 1.0
// */
//@Configuration
//@EnableOpenApi
//public class SwaggerConfig {
//
//        @Bean
//        public Docket docket(){
//            return new Docket(DocumentationType.SWAGGER_2)
//                    .apiInfo(apiInfo()).enable(true)
//                    .select()
//                    //添加swagger接口提取范围,修改成指向你的controller包
//                    .apis(RequestHandlerSelectors.basePackage("com.lxb.springboot_vue_.controller"))
//                    .paths(PathSelectors.any())
//                    .build();
//        }
//
//        private ApiInfo apiInfo(){
//            return new ApiInfoBuilder()
//                    .title("后台管理系统管理文档")
//                    .description("这是一个后台管理系统")
//                    .version("1.0")
//                    .build();
//        }
//}
