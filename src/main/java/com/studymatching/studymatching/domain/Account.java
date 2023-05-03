package com.studymatching.studymatching.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//순환 참조, 무한루프 때문에 id만 사용하는 것이 좋다.
@EqualsAndHashCode(of = "id") //아이디만 사용
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    //아이디를 두 방향으로 쓰겠다!
    @Column(unique = true)
    private  String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;

    //프로필과 관련된 정보들
    private String bio;

    private String url;

    private  String occupation;

    private String location; //varchar(255)

    @Lob //텍스트 타입의 매핑
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    //알림 설정
    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdateByEmail;

    private boolean studyUpdateByWeb;
}
