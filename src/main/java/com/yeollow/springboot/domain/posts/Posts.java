package com.yeollow.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//lombok Annotation
@Getter
@NoArgsConstructor

//JPA Annotation
@Entity             //Table과 Link될 클래스임을 나타냄. Entity클래스를 기준으로 Table이 생성되고 Schema가 변경됨.

//쿼리를 날리지 않고 Posts라는 Entity Class를 수정하여 DB에 질의
public class Posts {
    @Id             //해당 테이블의 pk를 나타냄
    @GeneratedValue //pk생성 규칙을 나타냄
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    //Table의 Column을 나타냄. 선언하지 않더라도 모든 domain Class의 field는 Column이 됨.
    private String content;
    private String author;

//    해당 Class의 Builder패턴 Class를 생성
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
