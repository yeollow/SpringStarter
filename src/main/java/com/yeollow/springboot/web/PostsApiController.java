package com.yeollow.springboot.web;

import com.yeollow.springboot.service.posts.PostsService;
import com.yeollow.springboot.web.dto.PostsResponseDto;
import com.yeollow.springboot.web.dto.PostsSaveRequestDto;
import com.yeollow.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

//    등록/수정/조회 API를 생성하기 위해 총 3개의 Class가 필요함.
//    1. API요청을 받을 Controller
//    2. Request Data를 받을 DTO
//    3. Transaction, Domain간 순서를 보장하는 Service

//    @PutMapping - 해당 URI에 대한 HTTP Method PUT의 요청을 받을 수 있는 API를 만들어 줌 : GET,POST,DELETE,PUT,PATCH Mapping이 있음
    @PutMapping("/api/v1/posts")
//    @RequestBody - Client가 전송하는 HTTP요청의 Body내용을 Java Object로 변환시켜주는 역할. 즉 Body가 존재하지 않는 GET방식에는 유효하지 않음
//    @RequestBody는 JSON이나 XML형태의 데이터를 Jackson등의 MessageConverter를 이용하여 Java Object로 변환한다.
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts{id}")
    public Long update(@PathVariable Long id,@RequestBody PostsUpdateRequestDto requestDto) {
//        @PathVariable - URI 일부를 변수로 전달할 수 있음. 위의 @PutMapping의 parameter와 @PathVariable의 값(여기서는 id)이 동일해야함

        return postsService.update(id, requestDto);
    }

    @GetMapping(value = "/api/v1/posts{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
