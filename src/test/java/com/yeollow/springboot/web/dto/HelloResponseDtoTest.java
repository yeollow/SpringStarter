package com.yeollow.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class HelloResponseDtoTest {

    @Test
    public void lombokTest() {
        String name = "testLombok";
        int amount = 2020;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

//        assertj 테스트 검증 라이브러리의 검증 메소드
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
