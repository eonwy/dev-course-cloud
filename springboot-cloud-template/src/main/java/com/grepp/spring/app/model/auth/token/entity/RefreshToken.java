package com.grepp.spring.app.model.auth.token.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
public class RefreshToken {
    private String id = UUID.randomUUID().toString();
    private String atId;
    private String token = UUID.randomUUID().toString();
    private Long ttl = 3600 * 24 * 7L;
    
    public RefreshToken(String atId) {
        this.atId = atId;
    }
}
