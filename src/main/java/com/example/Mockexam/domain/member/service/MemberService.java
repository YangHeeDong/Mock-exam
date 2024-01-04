package com.example.Mockexam.domain.member.service;

import com.example.Mockexam.domain.member.entity.Member;
import com.example.Mockexam.domain.member.repository.MemberRepository;
import com.example.Mockexam.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
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


    // 로그인 시 Token 발급
    @Getter
    @Setter
    public static class AuthAndMakeTokensResponseBody {
        private String accessToken;
        private String refreshToken;

        public AuthAndMakeTokensResponseBody(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    public RsData<AuthAndMakeTokensResponseBody> loginMember(String loginId, String password) {

        Optional<Member> member = memberRepository.findByLoginId(loginId);

        if(member.isEmpty()) return RsData.of("417","해당 아이디로 가입되지 않았어용",null);

        if(!passwordEncoder.matches(password, member.get().getPassword())){
            return RsData.of("417","비밀번호가 일치하지 않습니다.",null);
        }

        String refreshToken = "refreshToken";
        String accessToken = "accessToken";

        return RsData.of("200","로그인 드가자~",new AuthAndMakeTokensResponseBody(accessToken,refreshToken));
    }
}
