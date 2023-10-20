package com.sbs.jdbc.container;

import java.util.Scanner;

public class Container {
  public static Scanner scanner;

  static {
    scanner = new Scanner(System.in);
  }
}
