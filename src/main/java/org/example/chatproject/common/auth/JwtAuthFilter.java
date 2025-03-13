package org.example.chatproject.common.auth;


import jakarta.servlet.GenericFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


//사용자의 토큰을 까봐서 그 토큰이 우리 서버에서 생성한 토큰인지를 확인하는 코드
@Component
public class JwtAuthFilter extends GenericFilter {

}
