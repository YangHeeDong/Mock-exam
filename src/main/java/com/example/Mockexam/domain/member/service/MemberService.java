package com.example.Mockexam.domain.member.service;

import com.example.Mockexam.domain.member.entity.Member;
import com.example.Mockexam.domain.member.repository.MemberRepository;
import com.example.Mockexam.global.exceptions.GlobalException;
import com.example.Mockexam.global.rsData.RsData;
import com.example.Mockexam.global.security.SecurityUser;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;

    public Optional<Member> getMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    public RsData<String> joinMember(String loginId, String password, String email) {
        Member member = Member.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .email(email)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();

        memberRepository.save(member);
        return RsData.of("200","회원가입되었다!",null);
    }

    public SecurityUser getUserByAccessToken(String accessToken) {

        Claims claims = authTokenService.decode(accessToken);

        long id = Long.parseLong(claims.get("id").toString());
        String username = claims.get("username").toString();

        List<? extends GrantedAuthority> authorities = ((List<String>)claims.get("authorities"))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new SecurityUser(id,username,"",authorities);

    }


    // 로그인 시 Token 발급
    @Getter
    @Setter
    public static class AuthAndMakeTokensResponseBody {
        private String accessToken;
        private String refreshToken;
        private Member member;

        public AuthAndMakeTokensResponseBody(String accessToken, String refreshToken, Member member) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.member = member;
        }
    }

    public RsData<AuthAndMakeTokensResponseBody> loginMember(String loginId, String password) {

        Optional<Member> member = memberRepository.findByLoginId(loginId);

        if(member.isEmpty()) throw new GlobalException("417","해당 아이디로 가입되지 않았습니다.");

        if(!passwordEncoder.matches(password, member.get().getPassword())) throw new GlobalException("417","비밀번호가 달라요~.");

        String refreshToken = authTokenService.genRefreshToken(member.get());
        String accessToken = authTokenService.genAccessToken(member.get());

        return RsData.of("200","로그인 드가자~",new AuthAndMakeTokensResponseBody(accessToken,refreshToken, member.get()));
    }
}
