package com.example.Mockexam.domain.article.controller;

import com.example.Mockexam.domain.article.entity.Article;
import com.example.Mockexam.domain.article.service.ArticleService;
import com.example.Mockexam.global.rsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {

    private final ArticleService articleService;

    // 수정 나중에 DTO를 생각해서 일단 만들어놓자
    @Getter
    @Setter
    public static class ModifyArticleRequestBody{
        private long id;
        private String title;
        private String body;
    }

    @Getter
    public static class ModifyArticleResponseBody{
        Article article;

        public ModifyArticleResponseBody(Article article){
            this.article = article;
        }
    }

    @PutMapping("")
    @ResponseBody
    public RsData<ModifyArticleResponseBody> modifyArticle(@RequestBody ModifyArticleRequestBody body){
        RsData<Article> rsDataArticle = articleService.modifyArticle(body.getId(),body.getTitle(),body.getBody());

        return RsData.of(rsDataArticle.getResultCode(),rsDataArticle.getMsg(),new ModifyArticleResponseBody(rsDataArticle.getData()));
    }

    // 삭제
    @DeleteMapping("{id}")
    @ResponseBody
    public RsData<String> deleteArticle(@PathVariable long id){
        return articleService.deleteArticle(id);
    }

    // 단건 조회
    @Getter
    public static class GetArticleResponseBody{
        private Article article;

        public GetArticleResponseBody(Article article){
            this.article = article;
        }
    }

    @GetMapping("{id}")
    @ResponseBody
    public RsData<GetArticleResponseBody> getArticles(@PathVariable long id){
        return RsData.of("200","단건조회", new GetArticleResponseBody(articleService.findById(id).get()));
    }


    // 다건 조회
    @Getter
    public static class GetArticlesResponseBody{
        private List<Article> articles;

        public GetArticlesResponseBody(List<Article> articles){
            this.articles = articles;
        }
    }

    @GetMapping("")
    @ResponseBody
    public RsData<GetArticlesResponseBody> getArticles(){
        return RsData.of("200","다건조회", new GetArticlesResponseBody(articleService.findAll()));
    }

    // 등록
    @Getter
    @Setter
    public static class WriteArticleRequestBody{
        private String title;
        private String body;
    }

    @Getter
    public static class  WriteArticleResponseBody{
        private Article article;

        public WriteArticleResponseBody(Article article){
            this.article = article;
        }
    }

    @PostMapping("")
    @ResponseBody
    public RsData<WriteArticleResponseBody> saveArticle(@RequestBody WriteArticleRequestBody body){

        RsData<Article> writeData = articleService.write(body.getTitle(), body.getBody());
        return writeData.of(new WriteArticleResponseBody(writeData.getData()));
    }


}
