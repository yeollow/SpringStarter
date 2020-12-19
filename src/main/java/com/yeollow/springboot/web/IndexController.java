package com.yeollow.springboot.web;

import com.yeollow.springboot.service.posts.PostsService;
import com.yeollow.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {              //page에 관련된 Controller

    private final PostsService postsService;

    //    mustache starter dependency 덕분에 문자열을 반환할 때 기본적으로 resources/templates에서 반환하는 문자열(여기서는 index).mustache로 view resolver가 처리함.
    @GetMapping("/")
    public String index(Model model) {
//        Model은 서버 템플릿 엔진(mustache, Thymeleaf)등에서 사용할 수 있는 객체를 저장할 수 있음
        model.addAttribute("posts", postsService.findAllDesc());

//        postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달.
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
