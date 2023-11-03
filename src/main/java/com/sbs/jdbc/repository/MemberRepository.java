package com.sbs.jdbc.repository;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.dto.Member;
import com.sbs.jdbc.util.DBUtil;
import com.sbs.jdbc.util.SecSql;

import java.util.Map;

public class MemberRepository {
  public boolean isLoginDup(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);
  }

  public void join(String loginId, String loginPw, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);

    DBUtil.insert(Container.conn, sql);
  }

  public Member getMemberByLoginId(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

    if(memberMap.isEmpty()) {
      return null;
    }

    return new Member(memberMap);
  }

  public Member getMemberByEmail(String email) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM `member`");
    sql.append("WHERE email = ?", email);

    Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

    if(memberMap.isEmpty()) {
      return null;
    }

    return new Member(memberMap);
  }

  public void findLoginPw(String loginId, String email, String loginPw) {
    SecSql sql = new SecSql();
    sql.append("UPDATE `member`");
    sql.append("SET loginPw = ?", loginPw);
    sql.append("WHERE loginId = ?", loginId);
    sql.append("AND email = ?", email);

    DBUtil.update(Container.conn, sql);
  }

  public int checkLoginIdEmailMatch(String loginId, String email) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*)");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);
    sql.append("AND email = ?", email);

    return DBUtil.selectRowIntValue(Container.conn, sql);
  }
}
