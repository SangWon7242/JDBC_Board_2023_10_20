package com.sbs.jdbc.repository;

import com.sbs.jdbc.container.Container;
import com.sbs.jdbc.util.DBUtil;
import com.sbs.jdbc.util.SecSql;

import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(String title, String content) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);

    int id = DBUtil.insert(Container.conn, sql);

    return id;
  }

  public List<Map<String, Object>> getArticlesListMap() {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    return DBUtil.selectRows(Container.conn, sql);
  }

  public Map<String, Object> getArticleMap(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRow(Container.conn, sql);
  }

  public int getArticleCount(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowIntValue(Container.conn, sql);
  }

  public void update(int id, String title, String content) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("set updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `content` = ?", content);
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append(" WHERE id = ?", id);

    DBUtil.delete(Container.conn, sql);
  }
}
