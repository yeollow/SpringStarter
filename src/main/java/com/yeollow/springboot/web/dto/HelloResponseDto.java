package com.yeollow.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//선언된 모든 field의 getter를 생성해줌
@Getter
//선언된 모든 final field가 포함된 생성자를 생성해줌. final field가 없는 경우 생성자에 포함되지 않음
@RequiredArgsConstructor
//선언된 모든 field에 대한 생성자를 생성해줌
public class HelloResponseDto {
    private final String name;
    private final int amount;
//    testCode실행 시 default constructor에서 초기화되지 않은 field라고 name, amount가 뜬다.. @RequiredArgsConstructor가 문제인가?
}
