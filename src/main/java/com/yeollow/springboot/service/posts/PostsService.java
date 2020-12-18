package com.yeollow.springboot.service.posts;

import com.yeollow.springboot.domain.posts.PostsRepository;
import com.yeollow.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//Spring에서는 @Autowired, setter, constructor를 통해 Bean을 주입할 수 있다. 이 중 Constructor로 주입하는 방식을 가장 권장하며
//RequiredArgsConstructor가 final이 선언된 모든 field를 인자로 하는 Constructor를 생성해준다.
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
