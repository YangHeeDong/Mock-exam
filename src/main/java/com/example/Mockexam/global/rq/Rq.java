package com.example.Mockexam.global.rq;

import com.example.Mockexam.domain.member.entity.Member;
import com.example.Mockexam.domain.member.service.MemberService;
import com.example.Mockexam.global.security.SecurityUser;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final MemberService memberService;
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final EntityManager entityManager;
    private Member member;
    private SecurityUser securityUser;

    // 일반? 무슨뜻이여
    public boolean isAjax(){
        if("application/json".equals(req.getHeader("Accept"))) return true;

        return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
    }

    // 쿠키 관련
    public void setCookie(String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/"); // 이건뭐여
        resp.addCookie(cookie);
    }

    public void setCrossDomainCookie(String name, String value){
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();

        resp.addHeader("Set-Cookie", cookie.toString());
    }

    public Cookie getCookie(String name){
        Cookie[] cookies = req.getCookies();

        if(cookies == null){
            return null;
        }

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(name)){
                return cookie;
            }
        }

        return null;
    }

    public String getCookieValue(String name, String defaultValue){
        Cookie cookie = getCookie(name);

        if( cookie == null){
            return defaultValue;
        }
        return cookie.getValue();
    }

    public long getCookieAsLong(String name, int defaultValue){
        String value = getCookieValue(name, null);

        if(value == null){
            return defaultValue;
        }

        return Long.parseLong(value);
    }

    public void removeCookie(String name){
        Cookie cookie = getCookie(name);

        if(cookie == null){
            return;
        }

        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        ResponseCookie responseCookie = ResponseCookie.from(name, null)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();

        resp.addHeader("Set-Cookie", responseCookie.toString());
    }

    public Member getMember(){
        if(isLogout()) return null;

        if(member == null){
            member = entityManager.getReference(Member.class, getSecurityUser().getId());
        }
        return member;
    }

    private boolean isLogout(){
        return !isLogin();
    }

    private boolean isLogin(){
        return getSecurityUser() != null;
    }

    private SecurityUser getSecurityUser(){

        if(securityUser == null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if(auth == null) return null;

            Object principal = auth.getPrincipal();

            if(principal == null) return null;

            securityUser = (SecurityUser) principal;
        }

        return securityUser;
    }

    public void setLogin(SecurityUser securityUser){

        Authentication auth = new UsernamePasswordAuthenticationToken(
                securityUser,
                securityUser.getPassword(),
                securityUser.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

    }

}
