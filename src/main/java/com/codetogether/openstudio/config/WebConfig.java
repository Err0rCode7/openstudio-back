package com.codetogether.openstudio.config;

import com.codetogether.openstudio.config.auth.LoginUserArgumentResolver;
import com.codetogether.openstudio.exception.advice.MyDefaultHandlerExceptionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;
//    private final MyDefaultHandlerExceptionResolver myDefaultHandlerExceptionResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolverList) {
        argumentResolverList.add(loginUserArgumentResolver);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        HandlerExceptionResolver handlerExceptionResolver = resolvers.stream().filter(ex -> ex instanceof DefaultHandlerExceptionResolver)
                .findAny().get();

        // 빈을 등록해서 사용하려고 했으나 Component Scan이 안됐음
        // 왜 그럴까?
        MyDefaultHandlerExceptionResolver myDefaultHandlerExceptionResolver = new MyDefaultHandlerExceptionResolver();
        int index = resolvers.indexOf(handlerExceptionResolver);
        resolvers.add(index, myDefaultHandlerExceptionResolver);
        WebMvcConfigurer.super.extendHandlerExceptionResolvers(resolvers);
    }
}