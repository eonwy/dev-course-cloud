package com.grepp.spring.infra.auth.oauth;

import com.grepp.spring.app.model.auth.AuthService;
import com.grepp.spring.app.model.auth.code.AuthToken;
import com.grepp.spring.app.model.auth.dto.TokenDto;
import com.grepp.spring.app.model.auth.token.UserBlackListRepository;
import com.grepp.spring.app.model.auth.token.entity.RefreshToken;
import com.grepp.spring.infra.auth.jwt.JwtTokenProvider;
import com.grepp.spring.infra.auth.jwt.TokenCookieFactory;
import com.grepp.spring.infra.auth.jwt.dto.AccessTokenDto;
import com.grepp.spring.infra.auth.oauth.user.OAuth2UserInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        OAuth2UserInfo userInfo = OAuth2UserInfo.createUserInfo(request.getRequestURI(), user);
        
        TokenDto tokenDto = authService.processTokenSignin(userInfo.getName());
        ResponseCookie accessTokenCookie = TokenCookieFactory.create(AuthToken.ACCESS_TOKEN.name(),
            tokenDto.getAccessToken(), jwtTokenProvider.getAccessTokenExpiration());
        
        ResponseCookie refreshTokenCookie = TokenCookieFactory.create(
            AuthToken.REFRESH_TOKEN.name(),
            tokenDto.getRefreshToken(), tokenDto.getRefreshExpiresIn());
        
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        getRedirectStrategy().sendRedirect(request, response, "/");
    }
}
