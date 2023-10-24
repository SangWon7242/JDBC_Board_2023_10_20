package com.sbs.jdbc;

import com.sbs.jdbc.container.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  List<Article> articles;
  int articleLastId;

  public App() {
    articles = new ArrayList<>();
    articleLastId = 0;
  }

  public void run() {
    Scanner sc = Container.scanner;

    while(true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      if(cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 등록 ==");

        System.out.printf("제목 : ");
        String title = sc.nextLine();

        System.out.printf("내용 : ");
        String content = sc.nextLine();
        int id = ++articleLastId;

        // JDBC 드라이버 클래스 이름
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";

        // 데이터베이스 연결 정보
        String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String username = "sbsst";
        String password = "sbs123414";

        Connection conn = null;
        PreparedStatement pstat = null;

        String sql = "INSERT INTO article";
        sql += " SET regDate = NOW()";
        sql += ", updateDate = NOW()";
        sql += ", title = \"" + title + "\"";
        sql += ", content = \"" + content + "\"";

        System.out.println("sql : " + sql);

        try {
          // JDBC 드라이버 로드
          Class.forName(jdbcDriver);

          // 데이터베이스에 연결
          conn = DriverManager.getConnection(url, username, password);

          pstat = conn.prepareStatement(sql);

          pstat.executeUpdate();
          int affectedRows = pstat.executeUpdate();
          System.out.printf("affectedRows : " + affectedRows);

        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");
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
          try {
            if(pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }

        Article article = new Article(id, title, content);
        articles.add(article);

        System.out.printf("%d번 게시물을 작성하였습니다.\n", article.id);
      }
      else if(cmd.equals("/usr/article/list")) {
        System.out.println("== 게시물 리스트 ==");

        if(articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("번호 / 제목");

        /*
        for(Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }
        */

        for(int i = articles.size() - 1; i >= 0; i--) {
          Article article = articles.get(i);
          System.out.printf("%d / %s\n", article.id, article.title);
        }
      }
      else if(cmd.equals("exit")) {
        System.out.println("프로그램 종료");
        break;
      }
    }

    sc.close();
  }
}
