package com.yeollow.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//      DB Layer 접근자. JPA에서는 Repository라고 부르며 Interface로 생성한다.
//      Entity와 Entity Repository는 같은 package에 있어야함. -> domain package에서 함께 관리함
public interface PostsRepository extends JpaRepository<Posts,Long> {                //JpaRepository<Entity,PK>를 상속하면 기본적인 CRUD Method가 자동으로 생성된다
//    JPA의 기본 메소드만으로도 해결 가능. but @Query로도 가능하며 가독성이 더 좋은듯..?
    @Query("SELECT P FROM Posts P ORDER BY P.id DESC")
    List<Posts> findAllDesc();              //service에서 재정의해야 함.

//    규모가 큰 프로젝트에서는 Entity 클래스만으로 조회가 어려워 조회용 프레임워크를 사용하기도 함.
//    querydsl, jooq, MyBatis등이 있음. 국내에서는 querydsl을 적극활용중
}
