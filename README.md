# 중고거래 사이트 당근팔조

## 주요기능
판매게시글 등록, 게시글 조회, 댓글달기, 좋아요, 구매하기, 구매내역 보기

## ERD
![image](https://user-images.githubusercontent.com/116478121/213187334-462e44b7-8461-4c19-b128-224fbb84acf7.png)

## API 설계
https://www.notion.so/b898a61a16594bf9af23a460289e4215?v=c40292364a6f42e3a690e3b60a95e197

## 트러블 슈팅
1. 게시글 전체 조회 할 때 Spring Data Jpa를 사용하였더니 댓글 Entity를 조회하는 쿼리가 전체 게시글 숫자만큼 실행됨<br>
  -> 해결 : Querydsl fetchjoin 기능을 사용하여 한번에 조회할 수 있도록 함
