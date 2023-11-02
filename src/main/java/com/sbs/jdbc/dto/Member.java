package com.sbs.jdbc.dto;

import java.util.Map;

public class Member {
  public int id;
  public String regDate;
  public String updateDate;
  public String loginId;
  public String loginPw;
  public String name;

  public String getLoginId() {
    return loginId;
  }

  public String getLoginPw() {
    return loginPw;
  }

  public String getName() {
    return name;
  }

  public Member(int id, String regDate, String updateDate, String loginId, String loginPw, String name) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.loginId = loginId;
    this.loginPw = loginPw;
    this.name = name;
  }

  public Member(Map<String, Object> memberMap) {
    this.id = (int) memberMap.get("id");
    this.regDate = (String) memberMap.get("regDate");
    this.updateDate = (String) memberMap.get("updateDate");
    this.loginId = (String) memberMap.get("loginId");
    this.loginPw = (String) memberMap.get("loginPw");
    this.name = (String) memberMap.get("name");
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", loginId='" + loginId + '\'' +
        ", loginPw='" + loginPw + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
