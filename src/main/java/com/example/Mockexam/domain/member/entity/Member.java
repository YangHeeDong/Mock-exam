package com.example.Mockexam.domain.member.entity;

import com.example.Mockexam.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Member extends BaseEntity {

    @Column(unique=true)
    private String loginId;

    private String password;
    private String email;

    public List<String> getAuthoritiesAsStringList() {
        return List.of("ROLE_MEMBER");
    }
}
