package org.example.chatproject.member.controller;


import org.example.chatproject.common.auth.JwtTokenProvider;
import org.example.chatproject.member.domain.Member;
import org.example.chatproject.member.dto.MeberSaveReqDto;
import org.example.chatproject.member.dto.MemberLoginReqDto;
import org.example.chatproject.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @PostMapping("/create")  //HTTP meg랑 회원id을 리턴할 것
    public ResponseEntity<?> memberCreate(@RequestBody MeberSaveReqDto memberSaveReqDto) {
        Member member = memberService.create(memberSaveReqDto);
        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

    //엔드포인트 생성, 아이디와 패스워드가 맞으면 토큰을 생성해준다.
    @PostMapping("/doLogin")
    public  ResponseEntity<?> doLogin(@RequestBody MemberLoginReqDto memberLoginReqDto) {
        //데이터베이스를 조회하고 검증하고 문제없을 경우 토큰을 발행한다
        //email. pass 검증
        Member member = memberService.login(memberLoginReqDto);
        //일치할 경우 토큰을 발행한다


        //문자열 토큰발행
        String jwtToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());
        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("token", jwtToken);
        loginInfo.put("id", member.getId());
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);

    }
}
