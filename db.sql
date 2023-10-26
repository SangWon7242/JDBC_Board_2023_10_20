# DB 생성
DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;

# DB 선택
USE text_board

# 게시물 테이블 생성
CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title CHAR(100) NOT NULL,
	`content` TEXT NOT NULL
);

# 게시물 생성 테스트 데이터
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', RAND()),
`content` = CONCAT('내용', RAND());

# 회원 테이블 생성
CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(20) NOT NULL UNIQUE, # 로그인 아이디는 중복되면 안된다. 그래서 UNIQUE를 걸어줘야함
	loginPw CHAR(100) NOT NULL,
	`name` CHAR(100) NOT NULL
);
