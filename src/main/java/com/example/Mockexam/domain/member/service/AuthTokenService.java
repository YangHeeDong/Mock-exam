package com.example.Mockexam.domain.member.service;

import com.example.Mockexam.domain.member.entity.Member;
import com.example.Mockexam.global.app.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    public String genToken(Member member, long expireSeconds){
        Claims claims = Jwts
                .claims()
                .add("id",member.getId())
                .add("username",member.getLoginId())
                .add("authorities",member.getAuthoritiesAsStringList())
                .build();

        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000 * expireSeconds);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .signWith(SignatureAlgorithm.HS256, AppConfig.getJwtSecretKey())
                .compact();
    }

    public Claims decode(String apiKey) {
        return Jwts
                .parser()
                .setSigningKey(AppConfig.getJwtSecretKey())
                .build()
                .parseClaimsJws(apiKey)
                .getPayload();
    }

    public String genRefreshToken(Member member) {
        return genToken(member,60*60*24*365);
    }

    public String genAccessToken(Member member) {
        return genToken(member,60*10);
    }
}
