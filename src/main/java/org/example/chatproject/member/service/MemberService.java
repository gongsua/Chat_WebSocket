package org.example.chatproject.member.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.chatproject.member.domain.Member;
import org.example.chatproject.member.dto.MeberSaveReqDto;
import org.example.chatproject.member.dto.MemberLoginReqDto;
import org.example.chatproject.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member create(MeberSaveReqDto meberSaveReqDto) {
        //이미 가입되어 있는 이메일을 검증한다
        if(memberRepository.findByEmail(meberSaveReqDto.getEmail()).isPresent()) {
            throw  new IllegalArgumentException("Email is already in use");
        }
        Member newMember = Member.builder()
                .name(meberSaveReqDto.getName())
                .email(meberSaveReqDto.getEmail())
                .password(meberSaveReqDto.getPassword())
                .build();

        Member member = memberRepository.save(newMember);

        return member;
    }

    public Member login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail()).orElseThrow()->new EntityNotFoundException("존재하지 않는 이메일 입니다");

    }
}