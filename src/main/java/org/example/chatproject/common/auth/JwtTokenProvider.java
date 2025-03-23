package org.example.chatproject.common.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtTokenProvider {
    private final String secretkey;
    private final int expiration;
    //여기서 새로 생성한 KEY를 선언한다.
    private final Key SECRETKEY;

    //Value를 사용하여 jwt 밑에 있는 키, 만료일 가져온다
    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretkey, @Value("${jwt.expiration}") int expiration) {
        this.secretkey = secretkey;
        this.expiration = expiration;
        this.SECRETKEY = new SecretKeySpec(java.util.Base64.getDecoder().decode(secretkey), SignatureAlgorithm.HS512.getJcaName());
    }

    public String createToken(String email, String role) {
        //patload부분이다 setsubject부분에는 셋팅하고 싶은 data를 넣으면 된다
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+expiration*60*1000L))
                .signWith(SECRETKEY)
                .compact();
        return token;

    }



}
