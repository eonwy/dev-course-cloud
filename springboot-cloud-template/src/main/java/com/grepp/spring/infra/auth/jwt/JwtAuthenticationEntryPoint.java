package com.grepp.spring.infra.auth.jwt;

import com.grepp.spring.infra.error.exceptions.AuthApiException;
import com.grepp.spring.infra.error.exceptions.AuthWebException;
import com.grepp.spring.infra.response.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

// EntryPoint : AuthenticationException 이 발생했을 때 인증을 유도
// form Login 을 사용할 때 사용하는 LoginUrlAuthenticationEntryPoint
// custom EntryPoint 를 등록하면 기존의 EntryPoint 는 무력화 된다.
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    
    private final HandlerExceptionResolver resolver;
    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        
        log.info("request uri : {}", request.getRequestURI());
        
        ResponseCode responseCode = switch (authException){
            case BadCredentialsException bce ->{
                log.warn("{}", bce.getMessage());
                // yield : switch 블록 값을 반환
                yield ResponseCode.BAD_CREDENTIAL;
            }
            case InsufficientAuthenticationException iae -> {
                // 인증이 되지 않은 사용자 (Anonymous) 가 보호되고 있는 리소스에 접근
                log.warn("{}", iae.getMessage());
                yield ResponseCode.UNAUTHORIZED;
            }
            case CompromisedPasswordException cpe -> {
                // 제공된 비밀번호가 손상되었음. 해킹당했을지도!
                log.warn("{}", cpe.getMessage());
                yield ResponseCode.SECURITY_INCIDENT;
            }
            case PreAuthenticatedCredentialsNotFoundException pcne -> {
                log.warn("{}", pcne.getMessage());
                yield ResponseCode.NOT_EXIST_PRE_AUTH_CREDENTIAL;
            }
            default -> {
                log.error(authException.getMessage(), authException);
                yield ResponseCode.BAD_REQUEST;
            }
        };
        
        if (request.getRequestURI().startsWith("/api")) {
            resolver.resolveException(request,
                response, null, new AuthApiException(responseCode));
            return;
        }
        
        resolver.resolveException(request, response, null, new AuthWebException(responseCode));
    }
}