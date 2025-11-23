# 🍙 삼각김밥 레이싱 게임 백엔드 레포지토리

Spring Boot 기반의 간단한 레이싱 게임 서버 구현 프로젝트입니다.

게임의 라운드 진행, 카드 분배 및 제출, 라운드 판정, 최종 결과 집계 기능을 구현했습니다.


### 프로젝트 개요
프로젝트 구조는 **MVC 패턴**을 기반으로 구성했습니다.

이 프로젝트는 프론트엔드와 연동되는 레이싱 게임 백엔드 서버 구현을 목표로 합니다.


### 핵심 기능

    1. 레이스 생성

    2. 플레이어와 봇 카드 분배

    3. 플레이어 보유 카드 목록 조회
    
    4. 라운드별 카드 제출 및 승패 판정
    
    5. 현재 경기 상태 조회


### [API 목록](https://yura-can-do-it.notion.site/api-2b357f19c169804fa990c2ae2801ca29)

| 기능               | 메서드  | 엔드포인트                                           |
|------------------| ---- | ----------------------------------------------- |
| 레이스 생성           | POST | `/api/races/start`                              |
| 카드 배분            | POST | `/api/races/{raceId}/distribute`                |
| 플레이어 카드(Hand) 조회 | GET  | `/api/races/{raceId}/player/cards`              |
| 카드 제출            | POST | `/api/races/{raceId}/cards?round=`              |
| 라운드 판정           | POST | `/api/races/{raceId}/rounds/{roundNumber}/judge` |
| 경기 상태 조회         | GET  | `/api/races/{raceId}/status`                    |

### 테스트 관련

JUnit5 기반 단위 테스트를 작성했습니다.

- 주요 흐름 테스트:
  - 레이스 생성 -> 카드 분배 -> 상태 조회

- 오류 테스트:
  - 존재하지 않는 레이스 ID 요청 시 `INVALID_RACE` 발생 여부 확인