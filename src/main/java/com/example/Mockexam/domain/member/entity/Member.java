package com.example.Mockexam.domain.member.entity;

import com.example.Mockexam.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

}
