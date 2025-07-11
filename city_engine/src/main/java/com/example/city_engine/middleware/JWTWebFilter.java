package com.example.city_engine.middleware;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.city_engine.util.Error;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter
public class JWTWebFilter implements Filter {
    private final ObjectMapper mapper;

    private final JWTService service;
    public JWTWebFilter()
    {
        service = new JWTService();
        mapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = "";
        var jwt = service.check(token);
        
        if(jwt.success())
        {
            // add User id to the request.
            var req = (HttpServletRequest) request;
            req.setAttribute(JWTUser.Attr, jwt.user_id());

            // call next filter or request handler.
            chain.doFilter(request, response);
        } else {
            var err = new Error("Authentication", "Denied", jwt.newToken());
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);           
            httpResponse.setContentType("application/json");
            mapper.writeValue(httpResponse.getWriter(), err);
            httpResponse.flushBuffer();
        }

    }
}
