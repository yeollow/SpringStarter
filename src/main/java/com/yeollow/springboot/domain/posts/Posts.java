package com.yeollow.springboot.domain.posts;

import com.yeollow.springboot.domain.BaseTimeEntity;
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
public class Posts extends BaseTimeEntity  {        //BaseTimeEntity를 상속받음
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

//    update쿼리 필요없이 Entity의 수정할 field를 매개변수로 받아 this에 저장
    /*
    * Controller로 update에 관한 req가 오면, Controller에서 Service를 통해 update method를 부름
    * Service에 update method에서는 update에 관해 정의된 DTO를 통해 update할 항목들을 Posts Entity class의 instance를 제공받아 Posts의 update method를 실행 -> Dirty Checking
    * */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
