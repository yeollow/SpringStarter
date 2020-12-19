package com.yeollow.springboot.service.posts;

import com.yeollow.springboot.domain.posts.Posts;
import com.yeollow.springboot.domain.posts.PostsRepository;
import com.yeollow.springboot.web.dto.PostsResponseDto;
import com.yeollow.springboot.web.dto.PostsSaveRequestDto;
import com.yeollow.springboot.web.dto.PostsUpdateRequestDto;
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

//    @Transactional이 추가되면 이 클래스에 Transaction기능이 적용된 Proxy 객체가 생성되어 Transaction 정상 여부에 따라 Commit or Rollback한다.
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
//    JPA 영속성 context(Entity를 영구 저장하는 환경)때문에 쿼리를 날릴 필요가 없음. 즉, Entity객체의 값만 변경하면 별도로 update query를 날릴 필요가 없음. -> Dirty Checking이라고 함.
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
