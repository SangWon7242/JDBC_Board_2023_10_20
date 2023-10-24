package com.sbs.jdbc;

import com.sbs.jdbc.container.Container;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  public void run() {
    Scanner sc = Container.scanner;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      // DB 연결 시작
      Connection conn = null;

      try {
        // JDBC 드라이버 로드
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.out.println("예외 : MySQL 드라이버 클래스가 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      // 데이터베이스 연결 정보
      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try {
        // 데이터베이스에 연결
        conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

        int actionResult = doAction(conn, sc, cmd, rq);

        if (actionResult == -1) {
          continue;
        } else {
          break;
        }

      } catch (SQLException e) {
        System.out.println("에러 : " + e);
      } finally {
        try {
          if (conn != null && !conn.isClosed()) {
            // 연결 닫기
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // DB 연결 끝
    }

    sc.close();
  }

  private int doAction(Connection conn, Scanner sc, String cmd, Rq rq) {
    int articleLastId = 0;
    PreparedStatement pstat = null;

    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 등록 ==");

      System.out.printf("제목 : ");
      String title = sc.nextLine();

      System.out.printf("내용 : ");
      String content = sc.nextLine();
      int id = ++articleLastId;

      String sql = "INSERT INTO article";
      sql += " SET regDate = NOW()";
      sql += ", updateDate = NOW()";
      sql += ", title = \"" + title + "\"";
      sql += ", content = \"" + content + "\"";

      System.out.println("sql : " + sql);

      try {
        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();
      } catch (SQLException e) {
        System.out.println("에러 : " + e);
      } finally {
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      System.out.printf("%d번 게시물을 작성하였습니다.\n", id);

    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      ResultSet rs = null;

      List<Article> articles = new ArrayList<>();

      String sql = "SELECT *";
      sql += " FROM article";
      sql += " ORDER BY id DESC";

      System.out.println("sql : " + sql);

      try {
        pstat = conn.prepareStatement(sql);
        rs = pstat.executeQuery();

        while (rs.next()) {
          int id = rs.getInt("id");
          String regDate = rs.getString("regDate");
          String updateDate = rs.getString("updateDate");
          String title = rs.getString("title");
          String content = rs.getString("content");

          Article article = new Article(id, regDate, updateDate, title, content);
          articles.add(article);
        }

      } catch (SQLException e) {
        System.out.println("에러 : " + e);
      } finally {
        try {
          if (rs != null && !rs.isClosed()) {
            rs.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.println("== 게시물 리스트 ==");

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return -1;
      }

      System.out.println("번호 / 제목");

      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      System.out.println("== 게시물 등록 ==");

      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String content = sc.nextLine();

      String sql = "UPDATE article";
      sql += " SET updateDate = NOW()";
      sql += ", title = \"" + title + "\"";
      sql += ", content = \"" + content + "\"";
      sql += " WHERE id = " + id;

      try {
        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();
      } catch (SQLException e) {
        System.out.println("에러 : " + e);
      } finally {
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    }

    return -1;
  }
}
