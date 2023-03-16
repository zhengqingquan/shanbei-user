// package com.github.shanbei.shanbeiuser.common;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
// import org.springframework.security.web.SecurityFilterChain;
//
// import static org.springframework.security.config.Customizer.withDefaults;
//
// /**
//  * 鉴权模块
//  * <a href="https://blog.csdn.net/OLinOne/article/details/128100367">...</a>
//  */
// @Configuration
// public class SecurityConfiguration {
//
//     /**
//      * 创建SecurityFilterChain的Bean来配置HttpSecurity的功能。
//      *
//      * @param http
//      * @return
//      * @throws Exception
//      */
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.authorizeHttpRequests(
//                 (authz) -> authz.anyRequest().authenticated()
//         ).httpBasic(withDefaults());
//         return http.build();
//     }
//
//     /**
//      * 引入了WebSecurityCustomizer。
//      * WebSecurityCustomizer是一个回调接口，可以用来定制WebSecurity。
//      * 忽略匹配/ignore1或/ignore2的请求
//      * 如果你正在配置WebSecurity来忽略请求，建议你改为在HttpSecurity#authorizeHttpRequests内使用permitAll。
//      * 想了解更多请参考configure Javadoc。
//      *
//      * @return
//      */
//     @Bean
//     public WebSecurityCustomizer webSecurityCustomizer() {
//         return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//     }
//
// }