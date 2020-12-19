package com.yeollow.springboot.web;

import com.yeollow.springboot.domain.posts.Posts;
import com.yeollow.springboot.domain.posts.PostsRepository;
import com.yeollow.springboot.web.dto.PostsSaveRequestDto;
import com.yeollow.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;              //mockMvc Test와 다른 Test 제공

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void registerPosts() throws Exception {
        String title = "title";
        String content = "content";
        String author = "author";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void updatePosts() throws Exception {
//        postsTable에 title, content, author 저장
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updatedId = savePosts.getId();
        String updatedTitle = "updatedTitle";
        String updatedContent = "updatedContent";

//        posts Table에 update
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts" + updatedId;

//        Http req/res Entity정보를 나타냄.
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

//        ResponseEntity는 HttpEntity를 상속받음으로 HTTP header와 body를 가질 수 있다.
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);        //ResponseEntity는 상태코드를 가지고 있어 반환할 수 있다.
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updatedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updatedContent);
    }
}
