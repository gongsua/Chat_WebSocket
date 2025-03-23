package org.example.chatproject.member.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.chatproject.member.domain.Member;
import org.example.chatproject.member.dto.MeberSaveReqDto;
import org.example.chatproject.member.dto.MemberLoginReqDto;
import org.example.chatproject.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.chatproject.common.configs.SecurityConfigs;


@Service
@Transactional

public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member create(MeberSaveReqDto meberSaveReqDto) {
        //이미 가입되어 있는 이메일을 검증한다
        if(memberRepository.findByEmail(meberSaveReqDto.getEmail()).isPresent()) {
            throw  new IllegalArgumentException("Email is already in use");
        }
        Member newMember = Member.builder()
                .name(meberSaveReqDto.getName())
                .email(meberSaveReqDto.getEmail())
                //비밀번호를 암호화하여 저장할 것이다.
                .password(passwordEncoder.encode(meberSaveReqDto.getPassword()) )
                .build();

        Member member = memberRepository.save(newMember);

        return member;
    }

    //로그인 기능
    public Member login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이메일입니다"));

        // 비밀번호 검증 (비교 로직은 실제 상황에 따라 암호화 등을 고려해야 함)
        if (passwordEncoder.matches(memberLoginReqDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");

        }
        return member;
    }

}
