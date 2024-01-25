package ru.adaliza.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JwtToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_at")
    private long expiresAt;
}
