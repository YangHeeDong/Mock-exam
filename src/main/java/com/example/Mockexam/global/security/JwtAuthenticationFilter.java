package com.example.Mockexam.global.security;

import com.example.Mockexam.domain.member.service.MemberService;
import com.example.Mockexam.global.rq.Rq;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final Rq rq;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = rq.getCookieValue("accessToken",null);

        if (accessToken != null){
            SecurityUser user = memberService.getUserByAccessToken(accessToken);
            rq.setLogin(user);
        }

        filterChain.doFilter(request,response);

    }
}
