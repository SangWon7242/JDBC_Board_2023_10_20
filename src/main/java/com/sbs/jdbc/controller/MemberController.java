package com.sbs.jdbc.controller;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.util.DBUtil;
import com.sbs.jdbc.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController extends Controller {

  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("== 회원 가입 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = sc.nextLine().trim();

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) > 0");
      sql.append("FROM `member`");
      sql.append("WHERE loginId = ?", loginId);

      boolean isLoginDup = DBUtil.selectRowBooleanValue(conn, sql);

      if (isLoginDup) {
        System.out.printf("\"%s\"(은)는 이미 사용중인 아이디입니다.\n", loginId);
        continue;
      }

      if (loginId.length() == 0) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      break;
    }

    // 로그인 비번 입력
    while (true) {
      System.out.printf("로그인 비번 : ");
      loginPw = sc.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비번을 입력해주세요.");
        continue;
      }

      boolean loginPwConfirmIsSame = true;

      while (true) {
        System.out.printf("로그인 비번확인 : ");
        loginPwConfirm = sc.nextLine().trim();

        if (loginPwConfirm.length() == 0) {
          System.out.println("로그인 비번확인을 입력해주세요.");
          continue;
        }

        if (loginPw.equals(loginPwConfirm) == false) {
          System.out.println("로그인 비번이 일치하지 않습니다. 다시 입력해주세요.");
          loginPwConfirmIsSame = false;
          break;
        }

        break;
      }

      // 로그인 비번과 비번확인이 일치한다면 제대로 입력된 것으로 간주한다.
      if (loginPwConfirmIsSame) {
        break;
      }
    }

    // 이름 입력
    while (true) {
      System.out.printf("이름 : ");
      name = sc.nextLine().trim();

      if (name.length() == 0) {
        System.out.println("이름를 입력해주세요.");
        continue;
      }

      break;
    }

    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);

    DBUtil.insert(conn, sql);

    System.out.printf("\"%s\"님 회원 가입을 환영합니다.\n", name);
  }
}
