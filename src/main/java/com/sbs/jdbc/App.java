package com.sbs.jdbc;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.controller.ArticleController;
import com.sbs.jdbc.controller.MemberController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

        Container.conn = conn;

        action(rq);

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

  private void action(Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      Container.articleController.doWrite();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      Container.articleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      Container.articleController.showDetail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      Container.articleController.doModify(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      Container.articleController.doDelete(rq);
    } else if (rq.getUrlPath().equals("/usr/member/join")) {
      Container.memberController.join();
    } else if (rq.getUrlPath().equals("/usr/member/login")) {
      Container.memberController.login();
    } else if (rq.getUrlPath().equals("/usr/member/logout")) {
      Container.memberController.logout();
    } else if (rq.getUrlPath().equals("/usr/member/whoami")) {
      Container.memberController.whoami();
    } else if (rq.getUrlPath().equals("/usr/member/findLoginPw")) {
      Container.memberController.findLoginPw();
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    }
  }
}
