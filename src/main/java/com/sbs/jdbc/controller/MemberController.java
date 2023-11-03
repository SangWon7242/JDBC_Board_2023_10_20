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
    String email;

    System.out.println("== 회원 가입 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = scanner.nextLine().trim();

      boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

      if (isLoginIdDup) {
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
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    // 이름 입력
    while (true) {
      System.out.printf("이메일 : ");
      email = scanner.nextLine().trim();

      if (email.length() == 0) {
        System.out.println("이메일을 입력해주세요.");
        continue;
      }

      boolean isLoginEmailDup = memberService.isLoginEmailDup(email);

      if (isLoginEmailDup) {
        System.out.printf("\"%s\"(은)는 이미 사용중인 이메일입니다.\n", email);
        continue;
      }

      break;
    }

    memberService.join(loginId, loginPw, name, email);

    System.out.printf("\"%s\"님 회원 가입을 환영합니다.\n", name);
  }

  public void login() {
    String loginId;
    String loginPw;

    if(Container.session.isLogined()) {
      System.out.println("현재 로그인 되어 있습니다.");
      return;
    }

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

    int loginPwTryMaxCount = 3;
    int loginPwTryCount = 0;

    // 로그인 비번 입력
    while (true) {
      if(loginPwTryCount >= loginPwTryMaxCount) {
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
        loginPwTryCount++;
        continue;
      }

      System.out.printf("\"%s\"님 로그인 되었습니다.\n", member.getName());
      Container.session.login(member);

      break;
    }
  }

  public void whoami() {
    if(Container.session.loginedMember == null) {
      System.out.println("로그인 상태가 아닙니다.");
    } else {
      String loginId = Container.session.loginedMember.getLoginId();
      System.out.printf("현재 로그인 회원은 \"%s\" 입니다.\n", loginId);
    }
  }

  public void logout() {
    if (Container.session.isLogined() == false) {
      System.out.println("이미 로그아웃 상태입니다.");
      return;
    }

    Container.session.logout();
    System.out.println("로그아웃 되었습니다.");
  }

  public void findLoginPw() {

    if(Container.session.isLogined()) {
      System.out.println("현재 로그인 되어 있습니다.");
      return;
    }

    System.out.println("== 비밀번호 찾기 ==");

    System.out.printf("로그인 아이디 : ");
    String loginId = scanner.nextLine().trim();

    if (loginId.length() == 0) {
      System.out.println("로그인 아이디를 입력해주세요.");
      return;
    }

    Member member = memberService.getMemberByLoginId(loginId);

    if (member == null) {
      System.out.println("입력하신 로그인 아이디는 존재하지 않습니다.");
      return;
    }

    System.out.printf("이메일 : ");
    String email = scanner.nextLine().trim();

    if (email.length() == 0) {
      System.out.println("이메일을 입력해주세요.");
      return;
    }

    member = memberService.getMemberByEmail(email);

    if (member == null) {
      System.out.println("입력하신 이메일은 존재하지 않습니다.");
      return;
    }

    boolean isMatch = memberService.checkLoginIdEmailMatch(loginId, email) == 1;

    if (isMatch == false) {
      System.out.println("입력한 로그인 아이디와 이메일은 일치하지 않습니다.");
      return;
    }

    System.out.printf("새 비밀번호 : ");
    String loginPw = scanner.nextLine().trim();

    if (loginPw.length() == 0) {
      System.out.println("새 비밀번호를 입력해주세요.");
      return;
    }

    System.out.printf("새 비밀번호 확인 : ");
    String loginPwConfirm = scanner.nextLine().trim();

    if (loginPwConfirm.length() == 0) {
      System.out.println("새 비밀번호 확인을 입력해주세요.");
      return;
    }

    if (loginPwConfirm.equals(loginPw) == false) {
      System.out.println("비밀번호가 일치하지 않습니다.");
      return;
    }

    memberService.findLoginPw(loginId, email, loginPw);

    System.out.println("비밀번호가 변경되었습니다.");

  }
}
