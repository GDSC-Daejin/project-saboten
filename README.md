# 선인장 (선택장애 인간들을 위한 장) 프로젝트

## Table of Contents
- [Architectures](#1)
  - [Project Architecture Overall](#1-1)
  - [Common Client Side Architecture Overall](#1-2)
- [Git Rules](#2)

<a name="1"/>

## Architectures

<a name="1-1"/>

### Project Architecture Overall

#### Diagram

![Project Architecture](images/Saboten.png)

#### Concept
##### Kotlin Multiplatform
- Supports android, ios, web, server (jvm)

##### Shared Multiplatform Module
- `common` module 은 클라이언트와 서버 둘 다 사용할법한 API 데이터 Response / Body Request 등의 모델, 유틸 (logger 와 같은) 을 서로 공유합니다.
- `common-client` module 은 클라이언트만에서 사용할만한 API Fetch, Repository, UseCase 를 포함하여 Presentation (ViewModel) 같은 비지니스 로직을 공유합니다.

<a name="1-2"/>

### Common Client Side Architecture Overall

#### Diagram

![Project Architecture](images/Saboten%20Client%20Architecture.png)

#### Concept
##### Clean Architecture Pattern
- data, domain, presentation (viewmodel) layer 를 `common-client` package 에 구분합니다.

##### Uni-Directional Architecture (UDA) 구조
- [UDA 란?](https://proandroiddev.com/unidirectional-data-flow-on-android-the-blog-post-part-1-cadcf88c72f5)
- State, Event, Effect 구조를 ViewModel 에 적용합니다.
- Event 를 사용하여 ViewModel 에 특정 이벤트를 전송합니다.
- Event 등에 따라 각 UseCase 에서 받은 데이터를 Reducer 개념을 사용하여 하나의 State 로 Merge 한 후에 UI 에 전달합니다.

##### ViewModel 로 같은 UI 렌더링 로직 공유
- 클라이언트는 화면별로 viewmodel 을 갖고 있으며, 각 플랫폼은 모두 같은 viewmodel 로직을 공유합니다.


<a name="2"/>

## Git Rules

### Branch Create
- 브랜치 생성시 "feature/{포지션 : BE, FE, AND}/SBT-{이슈번호}-{비고(Optional)}" 로 생성합니다.
  - 예 : "feature/BE/SBT-120-user-api"
