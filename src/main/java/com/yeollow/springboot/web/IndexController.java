package com.yeollow.springboot.web;

import com.yeollow.springboot.config.auth.LoginUser;
import com.yeollow.springboot.config.auth.dto.SessionUser;
import com.yeollow.springboot.domain.user.User;
import com.yeollow.springboot.service.posts.PostsService;
import com.yeollow.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {              //page에 관련된 Controller
    private final PostsService postsService;
    private final HttpSession httpSession;

    //    mustache starter dependency 덕분에 문자열을 반환할 때 기본적으로 resources/templates에서 반환하는 문자열(여기서는 index).mustache로 view resolver가 처리함.
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {     //@LoginUser Annotation을 정으히ㅏ고 LoginUserArgumentResolver로 기능을 구현, WebConfig로 등록
//        이제 @LoginUser Parameter Annotation을 적용하여 언제든지 Session정보를 가져올 수 있게 됨 - 기본적으로 Session은 WAS의 메모리에 저장되고 호출됨.
//        즉, 내장 Tomcat처럼 Applicaion 실행 시 WAS가 실행되는 구조에서는 항상 Applicaion을 실행시키고 있어야 Session이 유지가 되고 재실행 시 초기화 됨.
//          ->  톰캣마다 Session 동기화를 진행해야함
//                1.Tomcat Session을 이용 : 기본적으로 선택되며 2대 이상의 WAS가 구동되는 환경에서는 Tomcat들간의 Session공유를 위한 추가 설정 필요
//                2.Session 저장 데이터베이스를 사용 : 공용 Session을 사용하는 가장 쉬운 방법 but 성능적인 Issue가 있을 수 있음 => 본 프로젝트에서는 Session 저장소를 활용.
//                3.Redis와 같은 메모리 DB를 세션 저장소로 사용 : 실제로 서비스를 하기 위해서는 Embedded Redis와 같은 방식이 아닌 외부 메모리 서버가 필요.
        
//        Model은 서버 템플릿 엔진(mustache, Thymeleaf)등에서 사용할 수 있는 객체를 저장할 수 있음
        model.addAttribute("posts", postsService.findAllDesc());

//        CustomOAuth2UserService에서 로그인 성공 시 Session에 SessionUser를 저장하도록 함. 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있음.
        if (user != null)
            model.addAttribute("userName", user.getName());

//        postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달.
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("post", postsService.findById(id));

        return "posts-update";
    }
}
