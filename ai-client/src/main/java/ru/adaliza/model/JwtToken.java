package ru.adaliza.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_at")
    private long expiresAt;
}
