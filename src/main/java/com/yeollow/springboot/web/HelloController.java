package com.yeollow.springboot.web;

import com.yeollow.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController - Controller를 JSON으로 반환하는 Controller로 만들어 줌
@RestController
public class HelloController {
//    @GetMapping - 해당 URI에 대한 HTTP Method GET의 요청을 받을 수 있는 API를 만들어 줌
//    @RequestMapping(method = RequestMethod.GET)과 같음
    @GetMapping("/hello")
    public String hello() {
        return "hello Spring!";
    }

    @GetMapping("/hello/dto")
//    @RequestParam - 외부에서 API로 넘긴 parameter를 가져오는 Annotation. name이란 이름으로 외부에서 넘긴 parameter를 String name에 저장하겠다는 의미.
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
       return new HelloResponseDto(name, amount);
//       GET방식에서 queryString으로 name과 amount에 대한 값을 제공해줘야 함.
    }

}
