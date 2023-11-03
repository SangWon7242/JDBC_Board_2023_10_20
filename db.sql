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
	loginId CHAR(20) NOT NULL UNIQUE, # 로그인 아이디는 중복되면 안된다.
	loginPw CHAR(100) NOT NULL,
	`name` CHAR(100) NOT NULL
);

# 게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

# 게시물에 hit 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT(10) UNSIGNED NOT NULL AFTER `content`;

# 테스트 게시물 데이터
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = '제목1',
content = '내용1',
hit = 3;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = '제목2',
content = '내용2',
hit = 8;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
title = '제목3',
content = '내용3',
hit = 20;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
title = '제목4',
content = '내용4',
hit = 12;

DESC `member`;

# 멤버 테이블에 email 칼럼 추가
ALTER TABLE `member` ADD COLUMN email CHAR(50) NOT NULL AFTER `name`;

# 임시 회원
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = '1234',
`name` = '홍길동',
email = 'user1@test.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = '1234',테니스 후드티
`name` = '홍길순',
email = 'user2@test.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user3',
loginPw = '1234',
`name` = '임꺽정',
email = 'user3@test.com';

# email 칼럼에 unique 추가
ALTER TABLE `member` MODIFY COLUMN email CHAR(50) NOT NULL UNIQUE;

