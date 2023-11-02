package com.sbs.jdbc.dto;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Article {
  private int id;
  private  String regDate;
  private String updateDate;
  private int memberId;
  private String title;
  private String content;
  private String extra__writerName;

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.title = (String) articleMap.get("title");
    this.content = (String) articleMap.get("content");

    if(articleMap.get("extra__writerName") != null) {
      this.extra__writerName = (String) articleMap.get("extra__writerName");
    }

  }
}
