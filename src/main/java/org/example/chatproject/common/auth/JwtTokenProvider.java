package org.example.chatproject.common.auth;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Component
public class JwtTokenProvider {
    private final String secretkey;
    private final int expiration;
    private final Key SECRETKEY;

    //Value를 사용하여 jwt 밑에 있는 키, 만료일 가져온다
    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretkey, @Value("${jwt.expiration}") int expiration) {
        this.secretkey = secretkey;
        this.expiration = expiration;
        this.SECRETKEY = new SecretKeySpec(java.util.Base64.getDecoder().decode(secretkey), SignatureAlgorithm.HS512.getJcaName());
    }



}
