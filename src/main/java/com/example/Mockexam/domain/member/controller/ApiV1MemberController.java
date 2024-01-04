package com.example.Mockexam.domain.member.controller;

import com.example.Mockexam.domain.member.entity.Member;
import com.example.Mockexam.domain.member.service.MemberService;
import com.example.Mockexam.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class ApiV1MemberController {

    private final MemberService memberService;

    // 로그인
    @Getter
    @Setter
    public static class loginMemberRequestBody{
        private String loginId;
        private String password;
    }


    @AllArgsConstructor
    public static class loginMemberResponseBody{
        private String accessToken;
        private String refreshToken;
    }

    @PostMapping("/login")
    @ResponseBody
    public RsData<loginMemberResponseBody> loginMember(@RequestBody loginMemberRequestBody body){
        RsData<MemberService.AuthAndMakeTokensResponseBody> rsData = memberService.loginMember(body.loginId, body.password);
        return RsData.of(rsData.getResultCode(), rsData.getMsg(), new loginMemberResponseBody(rsData.getData().getAccessToken(), rsData.getData().getRefreshToken()));
    }


    // 회원가입
    @Getter
    @Setter
    public static class joinMemberRequestBody {
        private String loginId;
        private String password;
        private String passwordConfirm;
        private String email;
    }

    @PostMapping("/join")
    @ResponseBody
    public RsData<String> joinMember(@RequestBody joinMemberRequestBody body) {

        // password 일치여부
        if (!body.password.trim().equals(body.passwordConfirm.trim()))
            return RsData.of("417", "비밀번호와 비빌번호 확인이 일치하지 않습니다.", null);

        // Id 중복체크
        Optional<Member> member = memberService.getMemberByLoginId(body.loginId);
        if (!member.isEmpty()) return RsData.of("417", "중복된 아이디가 있습니다.", null);

        // 저장
        return memberService.joinMember(body.loginId, body.password, body.email);
    }

}
