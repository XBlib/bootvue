package com.lxb.springboot_vue_.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author XBlib
 * @version 1.0
 */

/**
 * 跨域问题是一种安全策略(同源策略)
 * 同源：协议，域名，端口号全部相同否则导致跨域问题
 */
@Configuration
public class CorsConfig {
    //跨域请求默认最大访问时间为一天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8081");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }
}
