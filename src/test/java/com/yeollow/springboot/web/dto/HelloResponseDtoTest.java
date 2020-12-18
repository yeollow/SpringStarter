package com.yeollow.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class HelloResponseDtoTest {

    @Test
    public void lombokTest() {
        String name = "testLombok";
        int amount = 2020;

//        dto객체를 생성하여 생성자로 name과 amount를 전달
        HelloResponseDto dto = new HelloResponseDto(name, amount);

//        assertj 테스트 검증 라이브러리의 검증 메소드
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
//        같을 수 밖에 없는 것들임. 그냥 lombok에서 @Getter랑 @RequiredArgsConstructor를 확인하기 위한 코드인 듯
    }
}
