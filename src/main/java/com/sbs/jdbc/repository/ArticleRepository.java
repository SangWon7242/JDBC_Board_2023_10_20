package com.sbs.jdbc.repository;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.dto.Article;
import com.sbs.jdbc.util.DBUtil;
import com.sbs.jdbc.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(int memberId, int boardId, String title, String content) {
    int defaultHitCount = 0;

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", boardId = ?", boardId);
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);
    sql.append(", hit = ?", defaultHitCount);

    int id = DBUtil.insert(Container.conn, sql);

    return id;
  }

  public List<Article> getForPrintArticles(Map<String, Object> args) {
    String searchKeyword = "";
    String searchKeywordTypeCode = "";

    if(args.containsKey("searchKeyword")) {
      searchKeyword = (String) args.get("searchKeyword");
    }

    if(args.containsKey("searchKeywordTypeCode")) {
      searchKeywordTypeCode = (String) args.get("searchKeywordTypeCode");
    }

    int limitFrom = -1;
    int limitTake = -1;

    if(args.containsKey("limitFrom")) {
      limitFrom = (int) args.get("limitFrom");
    }

    if(args.containsKey("limitTake")) {
      limitTake = (int) args.get("limitTake");
    }

    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    if(searchKeyword.length() > 0) {
      switch (searchKeywordTypeCode) {
        case "title,content":
          sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
          sql.append("OR A.content LIKE CONCAT('%', ?, '%')", searchKeyword);
          break;
        case "title":
          sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
          break;
        case "content":
          sql.append("WHERE A.content LIKE CONCAT('%', ?, '%')", searchKeyword);
          break;
      }
    }
    sql.append("ORDER BY id DESC");

    if(limitFrom != -1) {
      sql.append("LIMIT ?, ?", limitFrom, limitTake);
    }

    List<Article> articles = new ArrayList<>();

    List<Map<String, Object>> articlesMap = DBUtil.selectRows(Container.conn, sql);

    for(Map<String, Object> articleMap : articlesMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

  public List<Article> getForPrintArticlesByBoard(Map<String, Object> args) {
    String searchKeyword = "";
    String searchKeywordTypeCode = "";
    int boardId = -1;

    if(args.containsKey("boardId")) {
      boardId = (int) args.get("boardId");
    }

    if(args.containsKey("searchKeyword")) {
      searchKeyword = (String) args.get("searchKeyword");
    }

    if(args.containsKey("searchKeywordTypeCode")) {
      searchKeywordTypeCode = (String) args.get("searchKeywordTypeCode");
    }

    int limitFrom = -1;
    int limitTake = -1;

    if(args.containsKey("limitFrom")) {
      limitFrom = (int) args.get("limitFrom");
    }

    if(args.containsKey("limitTake")) {
      limitTake = (int) args.get("limitTake");
    }

    SecSql sql = new SecSql();
    sql.append("SELECT A.*, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");

    if(boardId != -1) {
      sql.append("INNER JOIN board AS B");
      sql.append("ON A.boardId = B.id");
    }

    if(searchKeyword.length() > 0) {
      switch (searchKeywordTypeCode) {
        case "title,content":
          sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
          sql.append("OR A.content LIKE CONCAT('%', ?, '%')", searchKeyword);
          break;
        case "title":
          sql.append("WHERE A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
          break;
        case "content":
          sql.append("WHERE A.content LIKE CONCAT('%', ?, '%')", searchKeyword);
          break;
      }
    }

    if(boardId != -1) {
      sql.append("WHERE A.boardId = ?", boardId);
    }

    sql.append("ORDER BY id DESC");

    if(limitFrom != -1) {
      sql.append("LIMIT ?, ?", limitFrom, limitTake);
    }

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
    sql.append("SELECT A.*");
    sql.append(", M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    return new Article(articleMap);
  }

  public void increaseHit(int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET hit = hit + 1");
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }
}
