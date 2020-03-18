-- 회원 테이블 생성
create table member(
id varchar(50) not null primary key, -- 아이디
passwd varchar(16) not null, -- 비밀번호
name varchar(10) not null, -- 이름
reg_date datetime not null, -- 가입일자
tel varchar(20) not null, -- 전화번호
address varchar(100) not null -- 주소 
) default character set utf8 collate utf8_general_ci;



-- 관리자 테이블 생성
create table manager(
managerId varchar(50) not null primary key, -- 매니저 아이디
managerPasswd varchar(16) not null -- 매니저 비밀번호
) default character set utf8 collate utf8_general_ci;



insert into manager(managerId, managerPasswd) values ('bookmaster','1111');



-- 도서 테이블
create table book(
book_id int not null primary key auto_increment, -- 책의 등록번호(자동으로 부여됨)
book_kind varchar(3) not null, -- 책의 종류
book_title varchar(100) not null, -- 책의 이름
book_price int not null, -- 책의 가격
book_count smallint not null, -- 책의 재고 수량 -- int보다 작은 단위일때 smallint사용(oracle에는 없고 mysql에서 씀)
author varchar(30) not null, -- 저자
publishing_com varchar(30) not null, -- 출판사
publishing_date varchar(15) not null, -- 출판일
book_image varchar(16) default 'nothing.jpg', -- 책의 이미지명
book_content text not null, -- 책의 내용
discount_rate tinyint default 10, -- 책의 할인률 -- smallint보다 작은 단위일때 tinyint사용(oracle에는 없고 mysql에서 씀)
reg_date datetime not null -- 책의 등록일자
) default character set utf8 collate utf8_general_ci;



-- 은행 테이블
create table bank(
account varchar(30) not null, -- 은행 계좌번호
bank varchar(10) not null, -- 은행명
name varchar(10) not null -- 계좌 소유주명
) default character set utf8 collate utf8_general_ci;

insert into bank(account, bank, name) values('11111-111-11111', '오늘은행', '김오늘');
insert into bank(account, bank, name) values('22222-222-22222', '내일은행', '오내일');



-- 장바구니 테이블
create table cart(
cart_id int not null primary key auto_increment, -- 장바구니 아이디
buyer varchar(50) not null, -- 구매자
book_id int not null, -- 장바구니에 담긴 책의 아이디
book_title varchar(100) not null, -- 장바구니에 담긴 책제목
buy_price int not null, -- 판매가
buy_count tinyint not null, -- 판매수량
book_image varchar(16) default 'nothig.jpg' -- 책의 이미지명 
) default character set utf8 collate utf8_general_ci;



-- 구매 테이블
create table buy(
buy_id bigint not null, -- 구매자 아이디 -- int보다 큰 단위일때 bigint사용(oracle에는 없고 mysql에서 씀)
buyer varchar(50) not null, -- 구매자
book_id varchar(12) not null, -- 구매된 책 아이디
book_title varchar(100) not null, -- 구매된 책 제목
buy_price int not null, -- 판매가
buy_count tinyint not null, -- 판매수량
book_image varchar(16) default 'nothig.jpg', -- 책의 이미지명
buy_date datetime not null, -- 구매일자
account varchar(50) not null, -- 결제 계좌
deliveryName varchar(10) not null, -- 배송받는 사람
deliveryTel varchar(20) not null, -- 배송지 전화번호
deliveryAddress varchar(100) not null, -- 배송지 주소
sanction varchar(10) default '상품준비중' -- 배송상황
) default character set utf8 collate utf8_general_ci;


