package com.Nhom19.shopQuanAo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig1 implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("avatar/**","image/**","public/**")
                .addResourceLocations("file:///D:/shopbanquanao/src/assets/avatar/")
                .addResourceLocations("file:///D:/shopbanquanao/src/assets/image/")
                .addResourceLocations("file:///D:/shopbanquanao/src/assets/image/")
                .addResourceLocations("file:///D:/shopbanquanao/public/");
    }
}