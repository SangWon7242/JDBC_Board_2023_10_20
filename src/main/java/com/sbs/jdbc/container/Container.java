package com.sbs.jdbc.container;

import com.sbs.jdbc.controller.ArticleController;
import com.sbs.jdbc.controller.MemberController;
import com.sbs.jdbc.repository.ArticleRepository;
import com.sbs.jdbc.repository.MemberRepository;
import com.sbs.jdbc.service.ArticleService;
import com.sbs.jdbc.service.MemberService;
import com.sbs.jdbc.session.Session;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
  public static Scanner scanner;
  public static Session session;
  public static MemberRepository memberRepository;
  public static ArticleRepository articleRepository;

  public static MemberService memberService;
  public static ArticleService articleService;

  public static MemberController memberController;
  public static ArticleController articleController;

  public static Connection conn;

  static {
    scanner = new Scanner(System.in);
    session = new Session();

    memberRepository = new MemberRepository();
    articleRepository = new ArticleRepository();

    memberService = new MemberService();
    articleService = new ArticleService();

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
