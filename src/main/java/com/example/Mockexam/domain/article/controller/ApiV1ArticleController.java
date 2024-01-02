package com.example.Mockexam.domain.article.controller;

import com.example.Mockexam.domain.article.entity.Article;
import com.example.Mockexam.domain.article.service.ArticleService;
import com.example.Mockexam.global.rsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {

    private final ArticleService articleService;

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
