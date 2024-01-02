package com.example.Mockexam.domain.article.service;

import com.example.Mockexam.domain.article.entity.Article;
import com.example.Mockexam.domain.article.repository.ArticleRepository;
import com.example.Mockexam.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public RsData<Article> write(String title, String body){
        Article article =Article.builder()
                .modifyDate(LocalDateTime.now())
                .createDate(LocalDateTime.now())
                .title(title)
                .body(body)
                .build();

        articleRepository.save(article);
        return RsData.of("200","저장되었다리",article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }
}
