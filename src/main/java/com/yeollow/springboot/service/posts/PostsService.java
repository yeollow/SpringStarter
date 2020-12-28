package com.yeollow.springboot.service.posts;

import com.yeollow.springboot.domain.posts.Posts;
import com.yeollow.springboot.domain.posts.PostsRepository;
import com.yeollow.springboot.web.dto.PostsListResponseDto;
import com.yeollow.springboot.web.dto.PostsResponseDto;
import com.yeollow.springboot.web.dto.PostsSaveRequestDto;
import com.yeollow.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
                new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));            //id에 대한 Entity 객체를 얻음

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다.id=" + id));
//        postsRepository.deleteById(id)로 해당 id를 바로 삭제할 수 있으나, 해당 id에 대한 Entity가 존재하는지 확인 후 삭제

        postsRepository.delete(posts);              //save(해당 id를 가진 객체가 없으면 insert, 있으면 update 맞나..?)와 delete같은 경우는.. Entity 객체에 수정할 것 없이 DB에서 삭제, 삽입하면 되므로 Repository에서 처리하는 듯
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc() {
//        postsRepository의 @Query를 통해 결과로 넘어온 Posts의 Stream을 map을통해 PostsListResponseDto로 변환하여 list에 삽입하여 반환
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

}
