package com.epam.mentoring.taf.pojos.models.response.articles;

import java.util.List;

public class ArticleResponse {
    private List<Article> articles;
    private int articlesCount;


    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(int articlesCount) {
        this.articlesCount = articlesCount;
    }
}