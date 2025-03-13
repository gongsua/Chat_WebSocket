package org.example.chatproject.member.controller;


import org.example.chatproject.member.domain.Member;
import org.example.chatproject.member.dto.MeberSaveReqDto;
import org.example.chatproject.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/create")  //HTTP meg랑 회원id을 리턴할 것
    public ResponseEntity<?> memberCreate(@RequestBody MeberSaveReqDto memberSaveReqDto) {
        Member member = memberService.create(memberSaveReqDto);
        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

}
