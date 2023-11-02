package com.sbs.jdbc.repository;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.dto.Article;
import com.sbs.jdbc.util.DBUtil;
import com.sbs.jdbc.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(int memberId, String title, String content) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);

    int id = DBUtil.insert(Container.conn, sql);

    return id;
  }

  public List<Article> getArticles() {
    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("ORDER BY id DESC");

    List<Article> articles = new ArrayList<>();

    List<Map<String, Object>> articlesMap = DBUtil.selectRows(Container.conn, sql);

    for(Map<String, Object> articleMap : articlesMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

  public int getArticleCount(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowIntValue(Container.conn, sql);
  }

  public void update(int id, String title, String content) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("set updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append(" WHERE id = ?", id);

    DBUtil.delete(Container.conn, sql);
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    return new Article(articleMap);
  }
}
