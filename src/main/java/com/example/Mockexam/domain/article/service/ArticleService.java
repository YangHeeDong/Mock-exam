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
        return articleRepository.findAllByOrderByIdDesc();
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    public RsData<String> deleteArticle(long id) {
        Optional<Article> article = findById(id);

        if(article.isEmpty()) return RsData.of("200","없는 게시글이다리",id + "");

        articleRepository.delete(article.get());
        return RsData.of("200","삭제되었다리",article.get().getId().toString());
    }

    public RsData<Article> modifyArticle(long id, String title, String body) {
        Optional<Article> article = findById(id);

        if(article.isEmpty()) return RsData.of("200","없는 게시글이다리",null);

        Article getArticle = article.get();
        getArticle.setTitle(title);
        getArticle.setBody(body);
        getArticle.setModifyDate(LocalDateTime.now());

        articleRepository.save(getArticle);

        return RsData.of("200","수정했다리",getArticle);
    }
}
