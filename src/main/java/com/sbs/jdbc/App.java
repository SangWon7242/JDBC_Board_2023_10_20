package com.sbs.jdbc;

import com.sbs.jdbc.container.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  List<Article> articles;
  int articleLastId;

  public App() {
    articles = new ArrayList<>();
    articleLastId = 0;
  }

  public void run() {
    Scanner sc = Container.scanner;

    while(true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      if(cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 등록 ==");

        System.out.printf("제목 : ");
        String title = sc.nextLine();

        System.out.printf("내용 : ");
        String content = sc.nextLine();
        int id = ++articleLastId;

        Article article = new Article(id, title, content);
        articles.add(article);

        System.out.printf("%d번 게시물을 작성하였습니다.\n", article.id);
      }
      else if(cmd.equals("/usr/article/list")) {
        System.out.println("== 게시물 리스트 ==");

        if(articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("번호 / 제목");

        /*
        for(Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }
        */

        for(int i = articles.size() - 1; i >= 0; i--) {
          Article article = articles.get(i);
          System.out.printf("%d / %s\n", article.id, article.title);
        }
      }
      else if(cmd.equals("exit")) {
        System.out.println("프로그램 종료");
        break;
      }
    }

    sc.close();
  }
}
