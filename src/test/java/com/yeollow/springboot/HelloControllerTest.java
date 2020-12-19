package com.yeollow.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//JUnit에 내장된 실행자 외에 SpringRunner라는 Spring실행자를 실행시킴 - SpringBoot Test와 JUnit사이의 연결자 역할
@RunWith(SpringRunner.class)
//Web에 집중할 수 있는 Annotation. (집중의 의미가 뭐지?) - 선언 시 @Controller 등은 사용할 수 있지만 @Service, @Component, @Repository는 사용불가능함
@WebMvcTest
public class HelloControllerTest {
    //    Spring이 관리하는 Bean을 주입 받음
    @Autowired
    private MockMvc mvc;        //web API(HTTP GET,POST 등)를 테스트할 때 사용 - 내장 WAS(Tomcat)구동 안함

    @Test
    public void returnTestHelloSpring() throws Exception {
        String hello = "hello Spring!";

        mvc.perform(get("/hello").param("hello",hello))                          //MockMvc를 통해 /hello주소로 HTTP GET을 요청
                .andExpect(status().isOk())                 //mvc.perform의 결과를 검증 - GET요청의 HTTP Header의 Status를 200인지 아닌지 확인 (HTTP Status code 반환)
                .andExpect(jsonPath("$.hello",is(hello)));        //mvc.perform의 결과를 검증 - Controller에서 "hello Spring!"return값을 반환하는지 확인
    }

    @Test
    public void returnTestHelloDto() throws Exception {
        String name = "yeollow";
        int amount = 1080;

//        param - API test 시 사용될 요청 parameter를 설정!(String만 허용)
        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
//        jsonPath - JSON 응답값을 field별로 검증할 수 있는 method. $를 기준으로 field명을 명시한다.
    }
}
