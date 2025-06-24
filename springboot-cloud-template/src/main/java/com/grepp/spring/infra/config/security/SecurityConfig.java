package com.grepp.spring.infra.config.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.grepp.spring.app.model.auth.code.AuthToken;
import com.grepp.spring.app.model.auth.token.entity.RefreshToken;
import com.grepp.spring.infra.auth.jwt.JwtExceptionFilter;
import com.grepp.spring.infra.auth.jwt.JwtAuthenticationFilter;
import com.grepp.spring.infra.auth.jwt.JwtTokenProvider;
import com.grepp.spring.infra.auth.jwt.TokenCookieFactory;
import com.grepp.spring.infra.auth.jwt.dto.AccessTokenDto;
import com.grepp.spring.infra.auth.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.grepp.spring.infra.auth.oauth.OAuthSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    
    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
                
                boolean isAdmin = authentication.getAuthorities()
                                      .stream()
                                      .anyMatch(authority ->
                                                    authority.getAuthority().equals("ROLE_ADMIN"));
                
                if(isAdmin){
                    response.sendRedirect("/admin");
                    return;
                }
                
                response.sendRedirect("/");
            }
        };
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // * : 1depth 아래 모든 경로
        // ** : 모든 depth 의 모든 경로
        // Security Config 에는 인증과 관련된 설정만 지정 (PermitAll or Authenticated)
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .logout(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                (requests) -> requests
                                  .requestMatchers(GET, "/", "/error", "/favicon.ico", "/img/**", "/js/**","/css/**","/download/**").permitAll()
                                  .requestMatchers(GET, "/book/list").permitAll()
                                  .requestMatchers(GET, "/api/book/list").permitAll()
                                  .requestMatchers(GET, "/api/member/exists/*").permitAll()
                                  .requestMatchers(GET, "/member/signup").permitAll()
                                  .requestMatchers("/member/signin", "/member/signup", "/login","/logout").permitAll()
                                  .requestMatchers("/book/**").permitAll()
                                  .requestMatchers("/auth/**").permitAll()
                                  .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    

    


}
