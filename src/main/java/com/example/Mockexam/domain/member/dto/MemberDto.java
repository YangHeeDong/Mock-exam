package com.example.Mockexam.domain.member.dto;

import com.example.Mockexam.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@NoArgsConstructor(access = PROTECTED)
@Getter
public class MemberDto {

    private long id;

    private String loginId;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    private List<String> AuthoritiesAsStringList;

    public MemberDto(Member member){
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.email = member.getEmail();
        this.AuthoritiesAsStringList = member.getAuthoritiesAsStringList();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
    }


}
