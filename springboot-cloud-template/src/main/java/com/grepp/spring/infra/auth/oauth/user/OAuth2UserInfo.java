package com.grepp.spring.infra.auth.oauth.user;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfo {
    String getProviderId(); // OAuth 제공자별 고유 ID (예: Google의 sub, GitHub의 id)
    String getProvider();   // OAuth 제공자 이름 (예: "google", "github")
    String getName();
    String getPicture(); // 프로필 이미지 URL (선택 사항)
    
    static OAuth2UserInfo createUserInfo(String path, OAuth2User user){
        Map<String, OAuth2UserInfo> map = Map.of(
            "/login/oauth2/code/grepp", new GreppOAuth2UserInfo(user.getAttributes()),
            "/login/oauth2/code/github", new GitOAuth2UserInfo(user.getAttributes())
        );
        
        return map.get(path);
    }
}
