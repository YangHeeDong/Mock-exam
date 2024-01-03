package com.example.Mockexam.domain.article.entity;

import com.example.Mockexam.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@SuperBuilder(toBuilder = true) 안되어 버려
@SuperBuilder
@AllArgsConstructor
public class Article extends BaseEntity {
    private Long id;
    private String title;
    private String body;
}
