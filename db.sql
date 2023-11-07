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
loginPw = '1234',
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

# 제목% : '제목' 이라는 단어로 시작하는
# %제목% : '제목' 이라는 단어를 포함하는
# %제목 : '제목' 이라는 단어로 끝나는
SELECT A.*, M.name AS extra__writerName
FROM article AS A
INNER JOIN `member` AS M
ON A.memberId = M.id
WHERE A.title LIKE CONCAT('%', '제목', '%')
ORDER BY id DESC;

# 게시물에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

# board 테이블 생성
CREATE TABLE board (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	`name` CHAR(200) NOT NULL UNIQUE
);

# board 데이터 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`name` = '자유';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`name` = '공지';

# 테스트 게시물 데이터
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
boardId = 1,
title = '제목1',
content = '내용1',
hit = 3;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
boardId = 1,
title = '제목2',
content = '내용2',
hit = 8;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
boardId = 2,
title = '제목3',
content = '내용3',
hit = 20;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
boardId = 2,
title = '제목4',
content = '내용4',
hit = 12;

# 랜덤하게 테스트 데이터 생성
INSERT INTO article(regDate, updateDate, memberId, boardId, title, content, hit)
SELECT NOW(), NOW(),
FLOOR(1 + RAND() * 10),
FLOOR(RAND() * 2 + 1),
CONCAT('제목-', FLOOR(RAND() * 100)),
CONCAT('내용-', FLOOR(RAND() * 100)),
FLOOR(RAND() * 10)
FROM article;
