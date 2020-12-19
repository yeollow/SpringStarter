package com.yeollow.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)          //Auditing - 시간에 대해 자동으로 값을 넣어주는 것
//모든 Entity의 상위 클래스가 되어 Entity들의 createDate, modifiedDate를 관리하는 역할
public class BaseTimeEntity {

    @CreatedDate        //Entity가 생성되어 저장될 때 시간이 자동으로 저장됨.
    private LocalDateTime createdDate;

    @LastModifiedDate       //조회한 Entity의 값을 변경할 때 시간이 자동으로 저장됨.
    private LocalDateTime modifiedDate;
}
