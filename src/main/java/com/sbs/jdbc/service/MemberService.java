package com.sbs.jdbc.service;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.repository.MemberRepository;

public class MemberService {
  public MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public boolean isLoginDup(String loginId) {
    return memberRepository.isLoginDup(loginId);
  }

  public void join(String loginId, String loginPw, String name) {
    memberRepository.join(loginId, loginPw, name);
  }
}
