package com.yeollow.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexComtrollerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void loadIndexMustache() {
        String body = this.restTemplate.getForObject("/", String.class);        //TestRestTemplate을 통해 "/"를 호출했을 때

        assertThat(body).contains("hello");            //hello 문자열을 포함하고 있냐!
    }
}
