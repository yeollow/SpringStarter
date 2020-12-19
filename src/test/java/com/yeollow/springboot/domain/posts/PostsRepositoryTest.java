package com.yeollow.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest         //별다른 설정 없이 @SpringBootTest를 사용할 경우 h2 Database를 자동으로 실행해 준다.
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After                  //JUnit에서 단위 테스트가 끝날때마다 수행되는 Method를 지정. @Before도 같은 의미
    public void cleanuUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void loadSavedPosts() {
        String title = "test Title";
        String content = "test Content";

//        Builder Pattern
        postsRepository.save(Posts.builder()                //posts Table에 id값이 있다면 update, 없다면 insert가 실행됨.
                .title(title)
                .content(content)
                .author("yeollow@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();  //posts Table에 있는 모든 데이터를 조회

//       Table에 들어있는 데이터가 하나밖에 없어서 0으로 뽑아오면 됨..
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void registerBaseTimeEntity() {
        LocalDateTime now = LocalDateTime.of(2020, 12, 19, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("Spring")
                .content("testCode")
                .author("yeollow")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
