package com.sbs.jdbc.session;

import com.sbs.jdbc.dto.Member;

public class Session {
  public Member loginedMember;

  public boolean isLogined() {
    return loginedMember != null;
  }

  public void login(Member member) {
    loginedMember = member;
  }

  public void logout() {
    loginedMember = null;
  }
}
