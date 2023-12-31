package com.sbs.jdbc.service;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.dto.Member;
import com.sbs.jdbc.repository.MemberRepository;

public class MemberService {
  public MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public boolean isLoginIdDup(String loginId) {
    return memberRepository.isLoginIdDup(loginId);
  }

  public boolean isLoginEmailDup(String email) {
    return memberRepository.isLoginEmailDup(email);
  }

  public void join(String loginId, String loginPw, String name, String email) {
    memberRepository.join(loginId, loginPw, name, email);
  }

  public Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }

  public Member getMemberByEmail(String email) {
    return memberRepository.getMemberByEmail(email);
  }

  public void findLoginPw(String loginId, String email, String loginPw) {
    memberRepository.findLoginPw(loginId, email, loginPw);
  }

  public int checkLoginIdEmailMatch(String loginId, String email) {
    return memberRepository.checkLoginIdEmailMatch(loginId, email);
  }

}
