package org.example.chatproject.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MemberLoginReqDto {
    //로그인을 할때 이메일 패스워드 받을 수 있는 클래스
    private String email;
    private String password;
}
