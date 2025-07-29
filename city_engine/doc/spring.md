# TECH DOC SPRING

Mostly, a hodgepodge of bits and stuff. Mostly should be a web server, handling http requests. But does much more, in a very magical way.

## BEAN, COMPONENT, SERVICE, CONTROLLER, REPOSITORY & AUTOWIRED

That is #1 key concept in Spring. The world is filled with magic.

Every @Bean, @Component, @Controller, @Service, @Filter, etc is a singleton instantiated at startup automagically by Spring (@Configure => as included by @SpringBootApplication)

@Autowired will seek such beans and try to fill it in.

```java


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class JWTService {
    @Autowired
    private final WebClient apiAccess;
    
    public JWTService()
    {
    }

    public record JWTResult(boolean success, Long uid, String newToken){}

    public JWTResult check(String token)
    {
        JWTResult res = new JWTResult(false, Long.valueOf(0), "");

        return res;
    }

}

....


@Bean
public WebClient apiAccess() {
    return WebClient.create("http://localhost:8080/api/v3");
}


```

Should apiAccess be declared as a Bean somewhere, Autowired will ensure it's correctly instantiated.

### ORM

