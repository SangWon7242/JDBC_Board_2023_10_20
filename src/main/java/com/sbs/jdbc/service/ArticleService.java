package com.sbs.jdbc.service;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.repository.ArticleRepository;

import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public int write(String title, String content) {
    return articleRepository.write(title, content);
  }

  public List<Map<String, Object>> getArticlesListMap() {
    return articleRepository.getArticlesListMap();
  }

  public Map<String, Object> getArticleMap(int id) {
    return articleRepository.getArticleMap(id);
  }

  public int getArticleCount(int id) {
    return  articleRepository.getArticleCount(id);
  }

  public void update(int id, String title, String content) {
    articleRepository.update(id, title, content);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }
}
