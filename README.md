# Java-Dating-Sim-TeamProject
ava Swing 기반의 연애 시뮬레이션 게임 프로젝트. MVC 패턴 설계, 멀티스레딩 렌더링, 직렬화(Serialization) 저장 시스템을 구현했습니다.

# 그녀를 '자바'라 (Catch Her / Java)

### 프로젝트 소개 (Introduction)
**"자바(Java)로 만든, 그녀를 사로잡는(자바라) 게임"**

대학 캠퍼스를 배경으로 한 **비주얼 노벨 형식의 연애 시뮬레이션 게임**입니다.
단순한 텍스트 출력을 넘어, `Java Swing`을 활용하여 직관적인 GUI와 인터랙티브한 요소를 구현했습니다.

객체지향 설계를 기반으로 **MVC 패턴**을 적용해 구조를 체계화했고, `Multi-threading`과 `Serialization` 등 자바의 핵심 기술을 실무 수준으로 활용하여 시스템 안정성을 높였습니다.

### 기술 스택 (Tech Stack)
* **Language:** `Java (JDK 8+)`
* **GUI Framework:** `Swing`, `AWT`
* **Architecture:** `MVC Pattern` (Model-View-Controller)
* **Data Persistence:** `Java Serialization` (Save/Load)
* **IDE:** `VS Code` / `Eclipse`

### 핵심 기술 및 구현 기능 (Key Features)
이 프로젝트는 단순한 로직 구현을 넘어 **견고한 소프트웨어 아키텍처**를 구축하는 데 집중했습니다.

1.  **MVC 디자인 패턴 적용**
    * **Model:** `GameState` (플레이어 상태, 호감도, 씬 데이터 관리)
    * **View:** `GameWindow`, `GamePanel` (CardLayout 화면 전환, UI 렌더링)
    * **Controller:** `GameController` (사용자 입력 처리, 로직 제어)

2.  **멀티스레딩 (Multi-threading) 렌더링**
    * 대사 출력 시 별도의 `Thread`를 생성하여 **타자기 효과(Typewriter Effect)** 구현
    * UI 스레드(EDT)가 멈추는 **Freezing 현상 방지** 및 비동기 로직 처리

3.  **데이터 영속성 (Data Persistence)**
    * `Serializable` 인터페이스를 활용하여 객체 직렬화 구현
    * 게임 진행 상황을 바이너리 파일(`savefile.dat`)로 저장 및 로드하는 시스템 구축

4.  **리소스 최적화 (Resource Management)**
    * `HashMap`을 활용한 **이미지 캐싱(Caching) 시스템** 구현
    * `ImageLoader`를 통해 리소스를 미리 로딩(Pre-loading)하여 장면 전환 시 렉(Lag) 제거

### 팀 구성 및 기여도 (Contribution)
**Role:** Lead Developer

* **My Contribution (개발 총괄):**
    * 전체 프로젝트 아키텍처 설계 (`MVC Pattern` 도입)
    * 핵심 게임 엔진 개발 (이벤트 처리, 씬 매니저, 세이브/로드
    * `Swing` 컴포넌트 커스터마이징 및 UX 개선
    * 리소스 관리 시스템 및 멀티스레드 로직 구현
* **Team Contribution (기획 및 리소스):**
    * 게임 시나리오 및 캐릭터 대사 작성
    * 이미지 및 사운드 리소스 수집

### 실행 화면 (Screenshots)
<img width="796" height="646" alt="스크린샷 2025-12-17 14 27 16" src="https://github.com/user-attachments/assets/af917390-1763-4d2a-90ac-d19ac9ca9e42" />
<img width="798" height="691" alt="스크린샷 2025-12-17 14 28 05" src="https://github.com/user-attachments/assets/6a9d1742-d4cd-418e-9266-86539b0d45d6" />


### 설치 및 실행 방법 (How to Run)
1.  이 레포지토리를 Clone 합니다.
2.  `src/game/GameLauncher.java` 파일을 컴파일 및 실행합니다.
3.  프로젝트 루트 경로에 `images`와 `sounds` 폴더가 위치해야 합니다.
