# RecruitSKKU_bot

<img src="https://img.shields.io/badge/Telegram-%40RecruitSKKU__bot-blue">

> 취업/이직을 꿈꾸지만 일일이 찾을 여유가 없다면? 매일 알림으로 받아보자!

🤖평일 오후 1시에 취업공고를 받아볼 수 있어요. 

원하는 키워드를 포함하는 공고만 받아보거나, 
원하지 않는 키워드를 제외하는 공고만 받아볼 수 있어요.

<p float="left">
  <img src = "https://github.com/Gummybearr/KTfiles/blob/master/searchDemo.gif?raw=false" width="250"/>
  <img src = "https://github.com/Gummybearr/KTfiles/blob/master/blackListDemo.gif?raw=false" width="250"/>
  <img src = "https://github.com/Gummybearr/KTfiles/blob/master/whitelistDemo.gif?raw=false" width="250"/>
</p>

## 서버 구조
<img src = "https://github.com/Gummybearr/KTfiles/blob/master/Architecture.png?raw=false">

## 사용 기술
* Java 1.8
* Spring boot
* Spring Data JPA
* MySQL
* Docker-compose

## 개발 로그
* [📨 메시징 속도 최적화](https://velog.io/@gyunghoe/%ED%85%94%EB%A0%88%EA%B7%B8%EB%9E%A8-%EB%B4%87-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94%ED%95%98%EA%B8%B0)
* [🚀 채용정보 알리미 2.0 업데이트 로그](https://velog.io/@gyunghoe/%EC%B1%84%EC%9A%A9%EC%A0%95%EB%B3%B4-%EC%95%8C%EB%A6%AC%EB%AF%B8-2.0-%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8)

## 업데이트 로그

```
v1.0   - 기본 기능 구현
v1.0.1 - gcp클라우드 자동 실행
v1.0.2 - gke젠킨스 연동
v1.0.3 - 도커 연동

v1.1.0 - 공고 마감일 추가, 데이터베이스 추가, 사용자 메시지 동기화 기능 추가
v1.1.1 - 유저 쿼리, 업데이트 기능 추가
v1.1.2 - 차단 유저 대응 추가

v1.2.0 - 잡코리아 채용정보 Top 100 추가

v2.0.0 - 메시지 속도 개선, 사용자 추이 조회 쿼리 추가(관리자), 서버 구조 개선
v2.0.1 - 검색, 화이트 리스팅, 블랙 리스팅 기능 추가

```
