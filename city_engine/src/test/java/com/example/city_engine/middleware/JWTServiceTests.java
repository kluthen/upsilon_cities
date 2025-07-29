package com.example.city_engine.middleware;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.city_engine.middleware.JWTService.JWTResult;

@SpringBootTest
public class JWTServiceTests {

    @BeforeAll
    static public void Setup()
    {
        
    }

    @TestConfiguration
    public static class TestConfig {  
        @RestController
        static public class JWTServiceTokenProviderController 
        {
            @GetMapping("/check/{token}")
            public JWTResult check(@RequestParam String token)
            {
                if(token.equals("ValidToken"))
                    return new JWTResult(true, 1L, "NewToken");
                else
                    return new JWTResult(false, 0L, "InvalidToken");
            }
        }
    }

    @Test
    public void CheckTokenOk()
    {
        var service = new JWTService("http://localhost:8080");
        JWTResult res = service.check("ValidToken");
        assertEquals(res.success(), true, "Request Failed");
        assertEquals(res.userId(), 1L, "Wrong user id returned");
        assertEquals(res.newToken(), "NewToken", "Wrong token returned");
    }
    
}
