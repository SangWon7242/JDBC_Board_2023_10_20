package com.sbs.jdbc.container;

import com.sbs.jdbc.Article;
import com.sbs.jdbc.controller.ArticleController;
import com.sbs.jdbc.controller.MemberController;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
  public static Scanner scanner;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    scanner = new Scanner(System.in);

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
