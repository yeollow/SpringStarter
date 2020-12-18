package com.yeollow.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication을 통해 SpringBoot의 자동설정, Bean읽기 및 생성 등 자동으로 설정 됨. but Annotation위치부터 설정을 읽어가기 때문에 항상 Project 최 상단에 위치해야함.
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        SpringBoot의 MainClass
        SpringApplication.run(Application.class, args);
//        SpringApplication.run으로 내장 WAS(Tomcat)을 실행
    }
}
