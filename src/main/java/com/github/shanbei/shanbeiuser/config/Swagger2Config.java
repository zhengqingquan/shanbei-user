//package com.github.shanbei.shanbeiuser.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * 中定义Swagger接口文档定义
// */
//@Configuration
//@Enable
//// Profile表示想要在哪个环境下加载这个Bean，也可以传入数组，限定配置只在部分环境开启
//@Profile("dev")
//public class Swagger2Config {
//
//    @Bean
//    public Docket createRestApi() {
//        // 这里指定Swapper的版本为DocumentationType.SWAGGER_2。
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                // 扫描路径，获取controller层的接口
//                // 根据配置，编写自定义Swapper配置类。
//                // 定义需要生成代码配置的位置。
//                // 线上环境不要把接口全部暴露出去。
//                .apis(RequestHandlerSelectors.basePackage("com/github/shanbei/shanbeiuser/controller"))
//                // 这里是筛选路径。
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    /**
//     * Api信息
//     * 使用了构造器模式
//     *
//     * @return
//     */
//    public ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                // 标题
//                .title("shanbei-user")
//                // 简介
//                .description("shanbei-user接口文档")
//                // 服务团队的地址
//                .termsOfServiceUrl("https://github.com/zhengqingquan")
//                // 作者、网址http:localhost:8088/doc.html(这里注意端口号要与项目一致，如果你的端口号后面还加了前缀，就需要把前缀加上)、邮箱
//                .contact(new Contact("zqq", "http:localhost:8088/doc.html", "zhengqq@qq.com"))
//                // 版本
//                .version("1.0")
//                .build();
//    }
//}
//
