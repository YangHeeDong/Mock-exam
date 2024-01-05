package com.example.Mockexam.domain.member.controller;

import com.example.Mockexam.domain.member.dto.MemberDto;
import com.example.Mockexam.domain.member.entity.Member;
import com.example.Mockexam.domain.member.service.MemberService;
import com.example.Mockexam.global.exceptions.GlobalException;
import com.example.Mockexam.global.rq.Rq;
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
    private final Rq rq;

    // 로그인
    @Getter
    public static class loginMemberRequestBody{
        private String loginId;
        private String password;
    }


    @AllArgsConstructor
    @Getter
    public static class loginMemberResponseBody{
        private MemberDto memberDto;
    }

    @PostMapping("/login")
    @ResponseBody
    public RsData<loginMemberResponseBody> loginMember(@RequestBody loginMemberRequestBody body){
        RsData<MemberService.AuthAndMakeTokensResponseBody> rsData = memberService.loginMember(body.loginId, body.password);

        rq.setCrossDomainCookie("accessToken", rsData.getData().getAccessToken());
        rq.setCrossDomainCookie("refreshToken", rsData.getData().getRefreshToken());

        return RsData.of(rsData.getResultCode(),
                        rsData.getMsg(),
                        new loginMemberResponseBody(
                            new MemberDto(rsData.getData().getMember()))
                        );
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
            throw new GlobalException("417","비밀번호와 비밀번호 확인이 달라요~");

        // Id 중복체크
        Optional<Member> member = memberService.getMemberByLoginId(body.loginId);
        if (!member.isEmpty()) throw new GlobalException("417","해당 아이디의 회원이 이미 있어요~");

        // 저장
        return memberService.joinMember(body.loginId, body.password, body.email);
    }

}
