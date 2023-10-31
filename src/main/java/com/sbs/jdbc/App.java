package com.sbs.jdbc;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.controller.ArticleController;
import com.sbs.jdbc.controller.MemberController;
import com.sbs.jdbc.util.DBUtil;
import com.sbs.jdbc.util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        action(conn, sc, cmd, rq);

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

  private void action(Connection conn, Scanner sc, String cmd, Rq rq) {
    MemberController memberController = Container.memberController;
    memberController.setConn(conn);
    memberController.setScanner(sc);

    ArticleController articleController = Container.articleController;
    articleController.setConn(conn);
    articleController.setScanner(sc);

    PreparedStatement pstat = null;

    if (rq.getUrlPath().equals("/usr/article/write")) {
      articleController.doWrite();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      articleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      articleController.showDetail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      articleController.doModify(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      articleController.doDelete(rq);
    } else if (cmd.equals("/usr/member/join")) {
      memberController.join();
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    }
  }
}
