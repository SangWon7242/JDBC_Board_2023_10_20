package com.sbs.jdbc.controller;

import com.sbs.jdbc.Rq;
import com.sbs.jdbc.container.Container;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Connection conn;
  protected Scanner scanner;

}
