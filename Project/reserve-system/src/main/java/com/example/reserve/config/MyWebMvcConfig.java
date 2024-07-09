package com.example.reserve.config;

import com.example.reserve.interceptors.LoginInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MyWebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LoginInterceptor loginInterceptor = new LoginInterceptor();
    registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/system/login", "/easyui/**", "/h-ui/**");
  }
}
