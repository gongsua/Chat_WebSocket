package org.example.chatproject.common.configs;

import org.example.chatproject.common.auth.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
public class SecurityConfigs {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfigs(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }


    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) //csrf 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)  //HTTP basic 비활성
                //어떤 인증은 허용하고 허용안할건지 지정 특정 url은 허용하지 않고 나머지는 허용하겠다.
                .authorizeHttpRequests(a -> a.requestMatchers("/member/create" , "/member/doLogin").permitAll().anyRequest().authenticated())
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션방식을 사용하지 않겠다. 토큰방식으로 사용할 것
                //jwtAuthFilter에 가서 우리가 발급한 토큰인지 확인할 것이다
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));  // 🔹 Arrays.asList()로 수정
        configuration.setAllowedMethods(Arrays.asList("*")); // 🔹 모든 HTTP 메서드를 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 🔹 모든 헤더를 허용
        configuration.setAllowCredentials(true);  // 🔹 자격 증명을 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 🔹 모든 URL 패턴에 대해 CORS 허용
        return source;
    }

    //패스워드를 암호화하여 저장한다. makepassword라는 메서드이름으로 생성
    @Bean
    public PasswordEncoder makepassword() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
