package com.yeollow.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)          //Annotation이 생성될 수 있는 위치를 지정 -> PARAMETER로 설정했으니 method의 parameter로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { }
//@LoginUser Annotation 생성
