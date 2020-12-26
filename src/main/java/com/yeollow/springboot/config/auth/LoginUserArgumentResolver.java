package com.yeollow.springboot.config.auth;


import com.yeollow.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;      //parameter에 @LoginUser Annotation이 붙어있는지 확인
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());                   //parameter의 type이 SessionUser인지 확인

//        parameter에 @LoginUser Annotation이 붙어있고, SessionUser 클래스이면 true반환
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        parameter에 전달할 Session객체를 가져옴
        return httpSession.getAttribute("user");
    }
}
