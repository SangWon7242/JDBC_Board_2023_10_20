package com.sbs.jdbc.controller;

import com.sbs.jdbc.Article;
import com.sbs.jdbc.Rq;
import com.sbs.jdbc.util.DBUtil;
import com.sbs.jdbc.util.SecSql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleController extends Controller {

  public void doWrite() {
    System.out.println("== 게시물 등록 ==");

    System.out.printf("제목 : ");
    String title = sc.nextLine();

    System.out.printf("내용 : ");
    String content = sc.nextLine();

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);

    int id = DBUtil.insert(conn, sql);

    System.out.printf("%d번 게시물을 작성하였습니다.\n", id);
  }

  public void showList() {
    ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articlesListMap = DBUtil.selectRows(conn, sql);

    for (Map<String, Object> articleMap : articlesListMap) {
      articles.add(new Article(articleMap));
    }

    System.out.println("== 게시물 리스트 ==");

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("번호 / 제목");

    for (Article article : articles) {
      System.out.printf("%d / %s\n", article.id, article.title);
    }
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 상세보기 ==");

    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

    if (articleMap.isEmpty()) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    Article article = new Article(articleMap);

    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("작성날짜 : %s\n", article.regDate);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.content);
  }

  public void doModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 수정 ==");

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    int articleCount = DBUtil.selectRowIntValue(conn, sql);

    if (articleCount == 0) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    String title = sc.nextLine();
    System.out.printf("새 내용 : ");
    String content = sc.nextLine();

    sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("set updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);
    sql.append("WHERE id = ?", id);

    DBUtil.update(conn, sql);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    System.out.println("== 게시물 삭제 ==");

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    int articleCount = DBUtil.selectRowIntValue(conn, sql);

    if (articleCount == 0) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append(" WHERE id = ?", id);

    DBUtil.delete(conn, sql);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
