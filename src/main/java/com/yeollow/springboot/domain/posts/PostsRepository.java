package com.yeollow.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//DB Layer 접근자. JPA에서는 Repository라고 부르며 Interface로 생성한다.
public interface PostsRepository extends JpaRepository<Posts,Long> {                //JpaRepository<Entity,PK>를 상속하면 기본적인 CRUD Method가 자동으로 생성된다
//    Entity와 Entity Repository는 같은 package에 있어야함. -> domain package에서 함께 관리함
}
