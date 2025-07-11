package com.example.city_engine.middleware;

import java.util.Optional;
import java.time.Duration;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class JWTService {
    private final WebClient apiAccess;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    
    public JWTService()
    {
        apiAccess = WebClient.create(Optional.ofNullable(System.getenv("JWT_SERVICE")).orElse("http://jwt_service:7000"));
    }

    public record JWTResult(boolean success, Long user_id, String newToken){}

    public JWTResult check(String token)
    {
        return apiAccess
            .get()
            .uri("/check/{token}", token)
            .retrieve()
            .bodyToMono(JWTResult.class)
            // don't expect this one to happend: the service should return a success false, with a message set into token.
            .switchIfEmpty(Mono.create(_ -> new JWTResult(false, Long.valueOf(0), "")))
            // this is only added in case of full service failure (4xx,5xx)
            .onErrorReturn(new JWTResult(false, Long.valueOf(0), "Failed to contact JWT Provider Service(4xx,5xx)"))
            .block(REQUEST_TIMEOUT);
    }

}
