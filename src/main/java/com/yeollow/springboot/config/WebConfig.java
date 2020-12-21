package com.yeollow.springboot.config;

import com.yeollow.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @LoginUser Annotation을 사용하기 위한 환경을 구성한 LoginUserArgumentResolver가 Spring에서 인식될 수 있도록 WebMvcConfigurer에 추가
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolver()를 통해 등록해야 함
//        loginUserArgumentResolver는 HandlerMethodArgumentResolver의 구현객체

        argumentResolvers.add(loginUserArgumentResolver);
    }
}
