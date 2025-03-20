package org.example.chatproject.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;


    //enum의 값을 슷자값으로 넣겠다.
    @Enumerated(EnumType.STRING)
    @Builder.Default
    //입력을 안하면 Role,User 로 사용하겠다.
    private Role role = Role.USER;



}
