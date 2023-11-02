package com.sbs.jdbc.controller;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.dto.Member;
import com.sbs.jdbc.service.MemberService;

public class MemberController extends Controller {

  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
    scanner = Container.scanner;
  }

  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("== 회원 가입 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = scanner.nextLine().trim();

      boolean isLoginDup = memberService.isLoginDup(loginId);

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
      loginPw = scanner.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비번을 입력해주세요.");
        continue;
      }

      boolean loginPwConfirmIsSame = true;

      while (true) {
        System.out.printf("로그인 비번확인 : ");
        loginPwConfirm = scanner.nextLine().trim();

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
      name = scanner.nextLine().trim();

      if (name.length() == 0) {
        System.out.println("이름를 입력해주세요.");
        continue;
      }

      break;
    }

    memberService.join(loginId, loginPw, name);

    System.out.printf("\"%s\"님 회원 가입을 환영합니다.\n", name);
  }

  public void login() {
    String loginId;
    String loginPw;

    System.out.println("== 로그인 ==");

    System.out.printf("로그인 아이디 : ");
    loginId = scanner.nextLine().trim();

    if (loginId.length() == 0) {
      System.out.println("로그인 아이디를 입력해주세요.");
      return;
    }

    Member member = memberService.getMemberByLoginId(loginId);

    if (member == null) {
      System.out.println("입력하신 로그인 아이디는 존재하지 않습니다.");
      return;
    }

    int tryMaxCount = 3;
    int tryCount = 0;

    // 로그인 비번 입력
    while (true) {
      if(tryCount >= tryMaxCount) {
        System.out.println("비밀번호 확인 후 다음에 다시 시도해주세요.");
        break;
      }

      System.out.printf("로그인 비번 : ");
      loginPw = scanner.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비번을 입력해주세요.");
        continue;
      }

      if (member.getLoginPw().equals(loginPw) == false) {
        System.out.println("비밀번호가 일치하지 않습니다.");
        tryCount++;
        continue;
      }

      System.out.printf("\"%s\"님 로그인 되었습니다.\n", member.getName());
      break;
    }
  }
}
