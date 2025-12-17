package game;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameController implements ActionListener {

    private GameWindow view;
    private GameState model;
    private SmartphonePanel smartphonePanel;
    
    // 매니저 클래스
    private SaveManager saveManager;
    private AudioManager audioManager;

    // 이미지를 메모리에 저장해두는 저장소 (캐시)
    private Map<String, ImageIcon> imageCache = new HashMap<>();

    public GameController(GameWindow view, GameState model) {
        this.view = view;
        this.model = model;

        // 매니저 초기화
        this.saveManager = new SaveManager();
        this.audioManager = new AudioManager();

        // [수정 1] 이미지 미리 로딩 활성화 (주석 해제!)
        preloadImages();

        addListeners();

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        view.addMainMenuPanel(mainMenuPanel);

        this.smartphonePanel = new SmartphonePanel(this);
        view.addSmartphonePanel(smartphonePanel);

        showMainMenu();
    }

    // [수정 2] 이미지 경로의 맨 앞 슬래시(/) 제거
    private void preloadImages() {
        try {
            // 배경
            imageCache.put("hansung_bg", ImageLoader.loadIcon("images/hansung_background.png", 800, 500));          
            imageCache.put("promotion_club", ImageLoader.loadIcon("images/promotion_club.png", 800, 500));
            imageCache.put("blackout_club", ImageLoader.loadIcon("images/blackout_club.png", 800, 500));
            imageCache.put("home", ImageLoader.loadIcon("images/home.png", 800, 500));
            imageCache.put("ricecakestore", ImageLoader.loadIcon("images/ricecakestore.png", 800, 500));
            imageCache.put("classroom", ImageLoader.loadIcon("images/classroom.png", 800, 500));
            imageCache.put("morning_bus", ImageLoader.loadIcon("images/morning_bus.png", 800, 500));

            // 민지
            imageCache.put("minji_default", ImageLoader.loadIcon("images/human1.png", 800, 500));
            imageCache.put("minji_smile", ImageLoader.loadIcon("images/smile_minji.png", 800, 500));
            imageCache.put("minji_restaurant", ImageLoader.loadIcon("images/restaurant_minji.png", 800, 500));
            imageCache.put("minji_normal", ImageLoader.loadIcon("images/normal_minji.png", 800, 500));
            imageCache.put("minji_choice", ImageLoader.loadIcon("images/choice_minji.png", 800, 500));
            imageCache.put("library_smileminji", ImageLoader.loadIcon("images/library_smileminji.png", 800, 500));
            imageCache.put("minji_library_sad", ImageLoader.loadIcon("images/library_sadminji.png", 800, 500));
            imageCache.put("minji_suggest", ImageLoader.loadIcon("images/suggest_minji.png", 800, 500));

            //2주차
            imageCache.put("minji_bench", ImageLoader.loadIcon("images/minji_bench.png", 800, 500));
            imageCache.put("minji_library_thinking", ImageLoader.loadIcon("images/minji_library_thinking.png", 800, 500));
            imageCache.put("minji_library_smile", ImageLoader.loadIcon("images/minji_library_smile.png", 800, 500));
            imageCache.put("minji_walking", ImageLoader.loadIcon("images/minji_walking.png", 800, 500));
            imageCache.put("minji_bye", ImageLoader.loadIcon("images/minji_bye.png", 800, 500));
            //3주차
            imageCache.put("minji_day3_smile", ImageLoader.loadIcon("images/minji_day3_smile.png", 800, 500));
            imageCache.put("minji_day3_shy", ImageLoader.loadIcon("images/minji_day3_shy.png", 800, 500));
            imageCache.put("minji_cafeteria", ImageLoader.loadIcon("images/minji_cafeteria.png", 800, 500));

            // 가을
            imageCache.put("gaeul_first", ImageLoader.loadIcon("images/fitst_gaeul.png", 800, 500)); 
            imageCache.put("gaeul_smile", ImageLoader.loadIcon("images/gaeul_smile.png", 800, 500));
            // (이미지가 없는 경우를 대비해 기본값 사용 가능)
            imageCache.put("gaeul_eating", ImageLoader.loadIcon("images/human2.png", 800, 500));
            //2주차
            imageCache.put("gaeul_piano", ImageLoader.loadIcon("images/piano.png", 800, 500));
            imageCache.put("gaeul_ricecake", ImageLoader.loadIcon("images/gaeul_ricecake.png", 800, 500));
            imageCache.put("gaeul_ricecake_just", ImageLoader.loadIcon("images/gaeul_ricecake_just.png", 800, 500));
            imageCache.put("gaeul_ricecake_smile", ImageLoader.loadIcon("images/gaeul_ricecake_smile.png", 800, 500));
            imageCache.put("gaeul_practiceroom", ImageLoader.loadIcon("images/gaeul_practiceroom.png", 800, 500));
            imageCache.put("gaeul_surprising", ImageLoader.loadIcon("images/gaeul_surprising.png", 800, 500));
            imageCache.put("gaeul_close", ImageLoader.loadIcon("images/gaeul_close.png", 800, 500));
            imageCache.put("gaeul_staring", ImageLoader.loadIcon("images/gaeul_staring.png", 800, 500));
            //3주차
            imageCache.put("gaeul_day3_close", ImageLoader.loadIcon("images/gaeul_day3_close.png", 800, 500)); 
            imageCache.put("gaeul_day3_smile", ImageLoader.loadIcon("images/gaeul_day3_smile.png", 800, 500));
            imageCache.put("practice_day3", ImageLoader.loadIcon("images/practice_day3.png", 800, 500));
            imageCache.put("gaeul_laughing", ImageLoader.loadIcon("images/gaeul_laughing.png", 800, 500));


        } catch (Exception e) {
            System.err.println("이미지 로딩 중 오류 발생: " + e.getMessage());
            // 오류가 나도 게임이 꺼지지 않도록 처리
        }
    }

    private void addListeners() {
        view.getSettingsButton().addActionListener(this);
        view.getVolumeUpButton().addActionListener(this);
        view.getVolumeDownButton().addActionListener(this);
        view.getSaveButton().addActionListener(this);
        view.getLoadButton().addActionListener(this);
        view.getProfileButton().addActionListener(this);
        view.getStatusPanel().getBackButton().addActionListener(this);
    }

    private void showMainMenu() {
        view.getCardLayout().show(view.getMainPanel(), "MAIN_MENU");
    }


    private void setupInitialGame() {
        model.setCurrentDay(1);
        model.setAffectionMinji(10);
        model.setAffectionGaeul(5);

        // 화면 클릭 시 [다음] 버튼 기능 수행
        view.getGamePanel().setNextDialogueCallback(new Runnable() {
            @Override
            public void run() {
                handleGameChoice("[다음]");
            }
        });
        
        audioManager.playBGM("backgroundsound.wav");
        loadScene("SCENE_1_INTRO");
    }

    private void loadScene(String sceneId) {
        model.setCurrentSceneId(sceneId);

        CardLayout cl = view.getCardLayout();
        JPanel mainPanel = view.getMainPanel();

        view.getGamePanel().clearChoices();
        
        ImageIcon minjiIcon = imageCache.get("minji_default");
        ImageIcon gaeulIcon = imageCache.get("gaeul_first");
        ImageIcon minjiSmileIcon = imageCache.get("minji_smile");
        ImageIcon hansung_background = imageCache.get("hansung_bg");
        ImageIcon library_sadminji = imageCache.get("minji_library_sad");
        ImageIcon library_smileminji = imageCache.get("library_smileminji");
        ImageIcon home = imageCache.get("home");

        switch (sceneId) {
            case "SCENE_1_INTRO":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.get("hansung_bg"));
                view.getGamePanel().showTextWithEffect("묘사: 두 번째 학기가 시작되었다. ... 누군가 등 뒤에서 망설이듯 나를 부른다.");
                view.getGamePanel().setChoices(this, "[뒤를 돌아본다]");
                break;

            case "SCENE_1_MINJI_INTRO":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_default"));
                view.getGamePanel().showTextWithEffect("민지: 저기... 실례지만... 혹시... 한성이... 맞지? 나 기억나?  한성초등학교 같이 다녔던 김민지라고 하는데...");
                view.getGamePanel().setChoices(this,
                    "어! 김민지? 진짜 오랜만이다. 여기서 보네?",
                    "글쎄... 잘 기억이 안 나는데. (장난스럽게)",
                    "...(어색하게)... 그런 것 같기도 하고."
                );
                break;

            case "SCENE_1_MINJI_REACTION":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_smile"));
                view.getGamePanel().showTextWithEffect("민지: (표정이 환해지며) 다행이다! 알아봤네. 나 못 알아볼까 봐 걱정했어. 너도 이 수업 들어? 나도 컴공이라... 같은 수업 듣나 봐!");
                view.getGamePanel().setChoices(this, "[같이 수업을 듣고나니 점심시간이 되었다.]");
                break;

            case "SCENE_2_LUNCH_1":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_default"));
                view.getGamePanel().showTextWithEffect("묘사: 수업이 끝나고 민지가 점심을 같이 먹자고 제안했다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "SCENE_2_LUNCH_2":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_normal"));
                view.getGamePanel().showTextWithEffect("민지:초등학교 때 너 엄청 조용했었는데. 대학 오니 어때? 나는 아직도 좀 어색하네...");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "SCENE_2_LUNCH_3":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_normal"));
                view.getGamePanel().showTextWithEffect("주인공: 그래도 2학년이나 됐는데... 뭔가 새로운 거라도 해봐야 하나 싶기도 하고");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
            
            case "SCENE_2_LUNCH_4":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_normal"));
                view.getGamePanel().showTextWithEffect("민지:새로운 거? 예를 들면?");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "SCENE_2_LUNCH_5":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_restaurant"));
                view.getGamePanel().showTextWithEffect("민지: 동아리? 오, 좋은 생각이다!");
                view.getGamePanel().setChoices(this, "[동아리 부스 쪽을 살펴본다.]");
                break;

            case "SCENE_2_OUTSIDE_1":
                view.getGamePanel().setCharacterImage(imageCache.get("promotion_club"));
                view.getGamePanel().showTextWithEffect("묘사: 시끄러운 확성기 소리가 들려왔다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "SCENE_2_OUTSIDE_2":
                view.getGamePanel().setCharacterImage(imageCache.get("blackout_club"));
                view.getGamePanel().showTextWithEffect("묘사: '신입생 상시 모집!' 중앙 밴드부 '블랙아웃'의 포스터가 눈에 들어왔다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "SCENE_3_POSTER":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_choice"));
                view.getGamePanel().showTextWithEffect("민지: 와, 사람 많다... 한성아, 우리 이제 도서관으로 갈까?");
                view.getGamePanel().setChoices(this,
                    "[밴드부 부스를 가리킨다.] 민지야, 미안한데 나 저기 잠깐만 들렀다 가도 될까?",
                    "[도서관 방향을 가리킨다.] 아, 그래. 레포트. 가자."
                );
                break;

            case "SCENE_4A_GAEUL_INTRO_1":
            	audioManager.playBGM("practiceroom.wav");
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_first"));
                view.getGamePanel().showTextWithEffect("이가을: 어? 신입생? 구경하러 왔어? 일단 들어와. 먼지 많으니까 문 닫고.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "SCENE_4A_GAEUL_INTRO_2":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_first"));
                view.getGamePanel().showTextWithEffect("이가을: 그래서, 무슨 파트 관심 있는데?");
                view.getGamePanel().setChoices(this,
                    "저... 혹시 건반(키보드) 자리 비어있나요? 2학년인데... 지원 가능할까요?",
                    "아... 그냥 한번 둘러보려고 한 건데... 바쁘시면 다음에 올게요."
                );
                break;

            case "SCENE_4A_GAEUL_REACTION":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_smile"));
                view.getGamePanel().showTextWithEffect("이가을: 너 완전 환영이야! 앞으로 잘해보자, 후배님.");
                view.getGamePanel().setChoices(this, "[저녁이 되었다.]");
                break;

            case "SCENE_4B_MINJI_TEAMUP_1":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_suggest"));
                view.getGamePanel().showTextWithEffect("민지: '객체지향언어2' 레포트 주제...");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "SCENE_4B_MINJI_TEAMUP_2":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_suggest"));
                view.getGamePanel().showTextWithEffect("민지: 괜찮으면 나랑 같이 팀플 할래?");
                view.getGamePanel().setChoices(this,
                    "좋아. 잘 부탁해, 민지야. 지금 바로 시작할까?",
                    "음... 조금만 더 생각해 보고 알려줘도 될까?"
                );
                break;

            case "SCENE_4B_MINJI_REACTION":
                view.getGamePanel().setCharacterImage(imageCache.get("library_smileminji"));
                view.getGamePanel().showTextWithEffect("민지: 정말? 고마워!");
                view.getGamePanel().setChoices(this, "[저녁이 되었다.]");
                break;

            case "SCENE_4B_MINJI_REACTION_SAD":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_library_sad"));
                view.getGamePanel().showTextWithEffect("민지: 알겠어... 그럼... 나중에 봐.");
                view.getGamePanel().setChoices(this, "[저녁이 되었다.]");
                break;

            case "SCENE_5A_MSG_GAEUL":
                cl.show(mainPanel, "SMARTPHONE");
                
                smartphonePanel.setupChatRoom("이가을");
                smartphonePanel.addChatMessage("이가을", "신입 부원 환영! 내일 저녁 6시 합주. 늦지 마.");
                smartphonePanel.setReplies(this, "[확인했다. (무서운 건지 재밌는 건지...)]");
                break;

            case "SCENE_5B_MSG_MINJI":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("김민지");
                smartphonePanel.addChatMessage("김민지", "한성아! 내일 공강에 도서관에서 볼래?");
                smartphonePanel.setReplies(this, "[좋아. 내일 보자.]");
                break;

            case "SCENE_6_END_DAY_A":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.get("home"));
                view.getGamePanel().showTextWithEffect("주인공: (이가을 선배... 내일 합주 기대된다.)");
                view.getGamePanel().setChoices(this, "[1일차 종료. 잠자리에 든다.]");
                break;

            case "SCENE_6_END_DAY_B":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.get("home"));
                view.getGamePanel().showTextWithEffect("주인공: (민지랑 팀플... 잘 되겠지.)");
                view.getGamePanel().setChoices(this, "[1일차 종료. 잠자리에 든다.]");
                break;

            // 2일차
            case "DAY2_OPENING":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.get("classroom"));
                view.getGamePanel().showTextWithEffect("공통 오프닝: 교수님이 '상속'을 설명 중...");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY2_SCENE1_CLASS":
                // 배경을 학교 전경이나 강의실로 설정
                view.getGamePanel().setCharacterImage(imageCache.get("lecture_hall"));
                view.getGamePanel().showTextWithEffect("수업이 모두 끝났다. 다음 일정까지 시간이 좀 남는데... 어디로 가볼까?");
                view.getGamePanel().setChoices(this, 
                    "가을 선배가 있는 합주실로 가볼까?",
                    "민지가 있는 도서관으로 가볼까?"
                );
                break;

            case "DAY2_SCENE2A_PRACTICE":
            	audioManager.playBGM("practiceroom.wav");
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_practiceroom"));
                view.getGamePanel().showTextWithEffect("약속한 6시. 합주실 도착.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY2_SCENE2A_ARRIVAL":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_staring"));
                view.getGamePanel().showTextWithEffect("철문을 열고 들어가자, 베이스를 튜닝하던 가을 선배가 고개를 돌린다.");
                view.getGamePanel().setChoices(this, "[다음]"); 
                break;

            // 2. 선배의 핀잔 (대사 -> 다음 버튼)
            case "DAY2_SCENE2A_GREETING":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_staring")); // 팔짱 낀 이미지
                view.getGamePanel().showTextWithEffect("이가을: ...왔냐? 1분 늦었어. 1학년이 벌써부터 빠져가지고. 얼른 세팅해.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            // 3. 연주 도전 (중요 선택지 2개)
            case "DAY2_SCENE2A_CHALLENGE":
            	view.getGamePanel().showTextWithEffect("이가을: 악보는 다 외워 왔겠지? 어디, 우리 후배님 실력 좀 볼까?");
                view.getGamePanel().setChoices(this,
                    "기가 죽지 않게 화려한 기교를 섞어 연주한다.",  // -> SHOWOFF 이동
                    "선배가 알려준 대로 정석에 맞춰 연주한다."       // -> STANDARD 이동
                );
                break;

            // ---------------- [분기 결과] ----------------

            // 4-1. 화려한 연주 결과 (칭찬 루트)
            case "DAY2_SCENE2A_RESULT_SHOWOFF":
            	audioManager.playBGM("backgroundsound.wav");
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_surprising")); // 놀란 표정
                view.getGamePanel().showTextWithEffect("이가을: ...하, 건방지네? 근데... 좀 하는데? 선배로서 인정. 합격.");
                view.getGamePanel().setChoices(this, "[분식집으로 이동]");
                break;

            // 4-2. 정석 연주 결과 (스킨십 루트)
            case "DAY2_SCENE2A_RESULT_STANDARD":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_close")); // 클로즈업
                view.getGamePanel().showTextWithEffect("답답하다는 듯 다가온 선배가 내 손을 덥석 잡았다. 훅 끼쳐오는 샴푸 향기.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY2_SCENE2A_RESULT_STANDARD_2":
                // 위에서 [다음] 누르면 이어지는 대사
            	view.getGamePanel().showTextWithEffect("이가을: 아냐, 거기선 힘을 빼야지. 손 줘봐. ...이렇게 잡는 거라고.");
                view.getGamePanel().setChoices(this, "[분식집으로 이동]");
                break;

                /* * DAY 2: 분식집 데이트 (이미지 3장 활용)
                 * 흐름: 먹방(just) -> 눈맞춤(basic) -> 미소(smile)
                 */

                // 1. [먹방] 열심히 먹는 선배 (gaeul_ricecake_just.png)
            case "DAY2_SCENE3A_SNACK_EATING":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_ricecake_just"));
                view.getGamePanel().showTextWithEffect("무대 위에서의 카리스마는 온데간데없다. 선배는 볼이 빵빵해질 정도로 떡볶이를 오물거리고 있다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

                
            case "DAY2_SCENE3A_SNACK_EYECONTACT":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_ricecake"));
                view.getGamePanel().showTextWithEffect("이가을: ...뭘 봐? 내 얼굴에 뭐 묻었냐? 안 먹고 왜 그렇게 빤히 쳐다봐.");
                view.getGamePanel().setChoices(this,
                    "입가에 소스 묻었어요. (직접 닦아준다)",   // -> 설렘 폭발 루트
                    "그냥... 먹는 모습이 복스럽고 예뻐서요."      // -> 직구 칭찬 루트
                );
                break;

                // ---------------- [선택지에 따른 반응] ----------------

                // 3-1. [미소] 행동/칭찬에 대한 반응 (gaeul_ricecake_smile.png)
            case "DAY2_SCENE3A_SNACK_SMILE":
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_ricecake_smile"));
                view.getGamePanel().showTextWithEffect("이가을: 푸흡... 뭐야, 너. 1학년 주제에 제법 사람 설레게 하는 멘트도 날릴 줄 아네?");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

                // 3-2. [마무리] 분위기 정리
           case "DAY2_SCENE3A_SNACK_END":
                    // 다시 차분한 표정이나 웃는 표정 유지
                view.getGamePanel().setCharacterImage(imageCache.get("gaeul_ricecake_smile")); 
                view.getGamePanel().showTextWithEffect("이가을: 기분이다. 튀김도 더 시켜. 오늘은 선배가 풀코스로 쏜다.");
                view.getGamePanel().setChoices(this, "[집으로 이동]"); // -> 밤 문자 씬으로 연결
                break;


            case "DAY2_SCENE3A_SNACK_TALK":
            	view.getGamePanel().showTextWithEffect("이가을: 아 맵다... 야, 너 안 먹고 뭐 하냐? 선배가 사주는 거니까 남기지 마라.");
                view.getGamePanel().setChoices(this, "[집으로 귀가]");
                break;

            // ---------------- [밤 문자] ----------------

            // 6. 밤 문자 (마지막 선택지)
            case "DAY2_NIGHT_MSG_GAEUL":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("이가을 선배");
                
                smartphonePanel.addChatMessage("이가을 선배", "집엔 잘 들어갔냐?");
                smartphonePanel.addChatMessage("이가을 선배", "아까 떡볶이.. 잘 먹었어.");
                smartphonePanel.addChatMessage("이가을 선배", "오늘 보니까 제법이던데?ㅋ 내일 봐.");
                
                // 호감도 결정 선택지
                smartphonePanel.setReplies(this, 
                    "선배도 푹 쉬어요! 내일 봬요~", 
                    "꿈에서 만나요 선배 ㅎㅎ"
                );
                break;


            case "DAY2_SCENE2B_LIBRARY":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_library_thinking")); // 혹은 thinking
                view.getGamePanel().showTextWithEffect("민지: (한숨) 하아... 상속받았는데 왜 부모 생성자가 안 보이지? 벌써 한 시간째야...");
                view.getGamePanel().setChoices(this,
                    "노트북 마우스를 잡으며: \"이건 부모 클래스에 기본 생성자가 없어서 그래. super()를 써봐.\"",
                    "아, 이거 수업 때 교수님이 엄청 강조하신 건데. 그때 잤어?",
                    "음... 나도 잘 모르겠는데. 그냥 챗GPT한테 물어볼까?"
                );
                break;

            // [NEW] 2. 도서관: 해결 후 반응 (자연스러운 이동 제안)
            case "DAY2_SCENE2B_LIBRARY_SOLVED":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_library_smile"));
                view.getGamePanel().showTextWithEffect("민지: 와! 에러 사라졌다! 대박... 너 진짜 많이 늘었구나? 나 완전 감동했어.");
                view.getGamePanel().setChoices(this, "[머리도 식힐 겸 잠깐 밖으로 나간다.]");
                break;

            // [NEW] 3. 벤치: 대화의 시작 (맥락 부여)
            case "DAY2_SCENE3B_BENCH_TALK":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_bench")); // 혹은 minji_bench_thinking
                view.getGamePanel().showTextWithEffect("민지: 으아~ 따듯하다. \n야, 아까 너 알려주는 거 보니까 옛날 생각나더라. 너 초등학교 때 구구단 못 외워서 나머지 공부하던 거 기억나? ㅋㅋ");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            // 4. 벤치: 선택지 (이제 선택지가 자연스러워짐)
            case "DAY2_SCENE3B_BENCH":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_bench"));
                view.getGamePanel().showTextWithEffect("민지: 그때 진짜 엉엉 울었었잖아. 귀여웠는데.");
                view.getGamePanel().setChoices(this,
                    "아, 제발 그 흑역사는 잊어줘. 지금은 아니잖아!",
                    "너야말로 그때 칠판 지우개 털다가 얼굴 하얗게 떴던 거 기억나?",
                    "그래도 그 덕분에 네가 남아서 같이 챙겨줬었지."
                );
                break;
                
            case "DAY2_SCENE3B_BENCH_1":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_bench"));
                view.getGamePanel().showTextWithEffect("민지: (옅은 미소를 띠며)과제도 끝났겠다 이제 집으로 가야겠다!");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "DAY2_SCENE3B_BENCH_2":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_bench"));
                view.getGamePanel().showTextWithEffect("주인공: 그래 같이 걸어가자");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "DAY2_SCENE3B_BENCH_3":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_walking"));
                view.getGamePanel().showTextWithEffect("민지: 오늘 날씨 진짜 좋다~!");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "DAY2_SCENE3B_BENCH_4":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_walking"));
                view.getGamePanel().showTextWithEffect("주인공: (알 수 없는 설렘에 대답하지 못 한다.)");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "DAY2_SCENE3B_BENCH_5":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_bye"));
                view.getGamePanel().showTextWithEffect("민지: 아쉽지만 나 이제 가볼게 내일 보자!");
                view.getGamePanel().setChoices(this, "[다음]");
                break;
                
            case "DAY2_SCENE3B_BENCH_6":
                view.getGamePanel().setCharacterImage(imageCache.get("minji_bye"));
                view.getGamePanel().showTextWithEffect("묘사: 손을 흔들며 민지를 배웅해준다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;


            case "DAY2_NIGHT_MSG_MINJI_HIGH":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("김민지");
                smartphonePanel.addChatMessage("김민지", "오늘 알려줘서 고마워! 커피 내가 살게.");
                smartphonePanel.setReplies(this, "[좋아]");
                break;

            // [기존 코드] 민지 호감도 낮을 때
            case "DAY2_NIGHT_MSG_MINJI_LOW":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("김민지");
                smartphonePanel.addChatMessage("김민지", "오늘 고마웠어…^^ 푹 쉬어.");
                smartphonePanel.setReplies(this, "[굿나잇]");
                break;

            case "DAY2_END_NIGHT":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.get("home"));
                view.getGamePanel().showTextWithEffect("밤: 하루를 정리하며 침대에 눕는다.");
                view.getGamePanel().setChoices(this, "[하루 끝내기]");
                break;
            
            case "DAY3_OPENING":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.getOrDefault("morning_bus", imageCache.get("morning_bus")));
                view.getGamePanel().showTextWithEffect("3일차 아침. 한성대입구역 앞에서 버스를 기다리고 있다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_SCENE0_CLASS":
                view.getGamePanel().setCharacterImage(imageCache.get("classroom"));
                view.getGamePanel().showTextWithEffect("수업이 끝나자, 스마트폰이 진동한다.");

                if (model.getAffectionGaeul() >= model.getAffectionMinji()) {
                    view.getGamePanel().setChoices(this, "[가을 선배에게서 온 메시지를 확인한다]");
                } else {
                    view.getGamePanel().setChoices(this, "[민지에게서 온 메시지를 확인한다]");
                }
                break;

            case "DAY3_ROUTE_GAEUL_MSG":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("이가을 선배");
                smartphonePanel.addChatMessage("이가을 선배", "수업 끝났냐?");
                smartphonePanel.addChatMessage("이가을 선배", "점심 시간 괜찮으면 얼굴 좀 보자.");
                smartphonePanel.setReplies(this, "[좋아요! 어디서 볼까요?]");
                break;

            case "DAY3_ROUTE_MINJI_MSG":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("김민지");
                smartphonePanel.addChatMessage("김민지", "한성아! 점심 먹었어?");
                smartphonePanel.addChatMessage("김민지", "잠깐 얘기할 수 있을까?");
                smartphonePanel.setReplies(this, "[좋아! 식당에서 보자.]");
                break;

            /* ⭐⭐⭐ 3일차 가을 루트 ⭐⭐⭐ */
            case "DAY3_GAEUL_MEET":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.getOrDefault("practice_day3", imageCache.get("practice_day3")));
                view.getGamePanel().showTextWithEffect("합주실 문을 열자, 가을 선배가 손을 흔들며 반겨준다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_GAEUL_TALK1":
                view.getGamePanel().setCharacterImage(imageCache.getOrDefault("gaeul_day3_smile", imageCache.get("gaeul_day3_smile")));
                view.getGamePanel().showTextWithEffect("이가을: 어제 연주 말인데… 생각보다 괜찮더라?");
                view.getGamePanel().setChoices(this,
                    "선배 칭찬 들으니까 기분 좋은데요?",
                    "아… 그냥 운이 좋았던 거예요."
                );
                break;

            case "DAY3_GAEUL_TALK2":
            	view.getGamePanel().setCharacterImage(imageCache.getOrDefault("gaeul_laughing", imageCache.get("gaeul_laughing")));
                view.getGamePanel().showTextWithEffect("이가을: 하, 너 참…");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_GAEUL_EVENT":
                view.getGamePanel().setCharacterImage(imageCache.getOrDefault("gaeul_day3_close", imageCache.get("gaeul_day3_close")));
                view.getGamePanel().showTextWithEffect("갑자기 가까이 다가온 가을 선배. 손끝이 스칠 듯 말 듯.");
                view.getGamePanel().setChoices(this,
                    "…선배, 너무 가까워요.",
                    "그대로 마주본다."
                );
                break;

            case "DAY3_GAEUL_EVENT_RESULT":
                view.getGamePanel().showTextWithEffect("주인공: (심장이 빨리 뛰고 아무 소리도 들리지 않는다.)");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_GAEUL_END":
                view.getGamePanel().setCharacterImage(imageCache.get("home"));
                view.getGamePanel().showTextWithEffect("집에 도착하자 선배에게 메시지가 왔다.");
                view.getGamePanel().setChoices(this, "[가을 선배 문자 확인]");
                break;

            case "DAY3_GAEUL_NIGHT_MSG":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("이가을 선배");
                smartphonePanel.addChatMessage("이가을 선배", "오늘 뭐... 나쁘진 않았어.");
                smartphonePanel.addChatMessage("이가을 선배", "내일도 합주실 올 거지?");
                smartphonePanel.setReplies(this, "네 선배! 내일 뵈어요.");
                break;

            /* ⭐⭐⭐ 3일차 민지 루트 ⭐⭐⭐ */
            case "DAY3_MINJI_MEET":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.getOrDefault("cafeteria", imageCache.get("minji_cafeteria")));
                view.getGamePanel().showTextWithEffect("학생식당에서 민지가 나를 기다리고 있다.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_MINJI_TALK1":
                view.getGamePanel().setCharacterImage(imageCache.getOrDefault("minji_day3_smile", imageCache.get("minji_day3_smile")));
                view.getGamePanel().showTextWithEffect("민지: 어제… 집 가는 길에 생각했는데…");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_MINJI_TALK2":
                view.getGamePanel().setCharacterImage(imageCache.getOrDefault("minji_day3_shy", imageCache.get("minji_day3_shy")));
                view.getGamePanel().showTextWithEffect("민지: 음… 그냥. 고마웠다는 말 하고 싶었어.");
                view.getGamePanel().setChoices(this,
                    "나도 즐거웠어, 민지야.",
                    "고작 그 말하려고 부른 거야? ㅋㅋ"
                );
                break;

            case "DAY3_MINJI_TALK3":
                view.getGamePanel().showTextWithEffect("민지: …아, 진짜. 놀리지 마.");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_MINJI_EVENT":
                view.getGamePanel().showTextWithEffect("민지의 손등이 살짝 스친다. 서로 눈이 마주친다.");
                view.getGamePanel().setChoices(this,
                    "손을 살짝 잡아본다.",
                    "아무 일 없던 듯 식판을 든다."
                );
                break;

            case "DAY3_MINJI_EVENT_RESULT":
                view.getGamePanel().showTextWithEffect("민지: (작게 웃음) 바보…");
                view.getGamePanel().setChoices(this, "[다음]");
                break;

            case "DAY3_MINJI_END":
                view.getGamePanel().setCharacterImage(imageCache.get("home"));
                view.getGamePanel().showTextWithEffect("집에 도착하자 민지에게 메시지가 도착했다.");
                view.getGamePanel().setChoices(this, "[민지 문자 확인]");
                break;

            case "DAY3_MINJI_NIGHT_MSG":
                cl.show(mainPanel, "SMARTPHONE");
                smartphonePanel.setupChatRoom("김민지");
                smartphonePanel.addChatMessage("김민지", "오늘도 고마웠어!");
                smartphonePanel.addChatMessage("김민지", "내일 보자 :)");
                smartphonePanel.setReplies(this, "그래! 내일 보자 민지야.");
                break;

            case "DAY3_END":
                cl.show(mainPanel, "GAME");
                view.getGamePanel().setCharacterImage(imageCache.get("home"));
                view.getGamePanel().showTextWithEffect("3일차의 끝. 오늘도 꽤 많은 일이 있었다.");
                view.getGamePanel().setChoices(this, "[잠자리에 든다]");
                break;
            default:
                showMainMenu();
                break;
        }
    }

    public void adjustAffection(String characterName, int amount) {
        if ("minji".equals(characterName)) {
            model.setAffectionMinji(model.getAffectionMinji() + amount);
        } else if ("gaeul".equals(characterName)) {
            model.setAffectionGaeul(model.getAffectionGaeul() + amount);
        }
    }

    private void handleGameChoice(String command) {
        String currentScene = model.getCurrentSceneId();

        // [수정 3] 스마트폰 답장 완료 처리 (reply: 접두사 제거)
        // SmartphonePanel에서 "reply:" 없이 텍스트만 보내므로 바로 비교
        if (command.startsWith("[확인했다")) { // 가을 1일차 답장
             smartphonePanel.addChatMessage("이가을", "좋아. 내일 보자.");
             loadScene("SCENE_6_END_DAY_A");
             return;
        } else if (command.startsWith("[좋아. 내일")) { // 민지 1일차 답장
            smartphonePanel.addChatMessage("김민지", "오케이! 내일 봐~");
            loadScene("SCENE_6_END_DAY_B");
            return;
       // ★ 여기를 수정했습니다 (버튼 텍스트와 일치하도록 변경)
       } else if (command.contains("선배도 푹 쉬어요") || command.contains("꿈에서 만나요")) { 
            smartphonePanel.addChatMessage("이가을", "그래, 내일 보자."); // 가을이의 마지막 답장 추가
            loadScene("DAY2_END_NIGHT"); // 2일차 종료 씬으로 이동
            return;
       } else if (command.equals("[좋아]") || command.equals("[굿나잇]")) {
             smartphonePanel.addChatMessage("김민지", "내일 학교에서 봐~");
             loadScene("DAY2_END_NIGHT");
             
       } else if (command.contains("어디서 볼까요")) { // 가을 점심 약속
           loadScene("DAY3_GAEUL_MEET");
           return;
       } else if (command.contains("식당에서 보자")) { // 민지 점심 약속
           loadScene("DAY3_MINJI_MEET");
           return;
       } else if (command.contains("내일 뵈어요")) { // 가을 밤 문자 답장
           loadScene("DAY3_END");
           return;
       } else if (command.contains("내일 보자 민지야")) { // 민지 밤 문자 답장
           loadScene("DAY3_END");
           return;
       }

        switch (currentScene) {
            // 1일차 흐름
            case "SCENE_1_INTRO":
                loadScene("SCENE_1_MINJI_INTRO");
                break;

            case "SCENE_1_MINJI_INTRO":
                if (command.equals("어! 김민지? 진짜 오랜만이다. 여기서 보네?")) {
                    adjustAffection("minji", 5);
                } else if (command.equals("글쎄... 잘 기억이 안 나는데. (장난스럽게)")) {
                    // no change
                } else {
                    adjustAffection("minji", -3);
                }
                loadScene("SCENE_1_MINJI_REACTION");
                break;

            case "SCENE_1_MINJI_REACTION":
                loadScene("SCENE_2_LUNCH_1");
                break;

            case "SCENE_2_LUNCH_1":
                loadScene("SCENE_2_LUNCH_2");
                break;

            case "SCENE_2_LUNCH_2":
                loadScene("SCENE_2_LUNCH_3");
                break;

            case "SCENE_2_LUNCH_3":
                loadScene("SCENE_2_LUNCH_4");
                break;
                
            case "SCENE_2_LUNCH_4":
                loadScene("SCENE_2_LUNCH_5");
                break;
                
            case "SCENE_2_LUNCH_5":
                loadScene("SCENE_2_OUTSIDE_1");
                break;
                
            case "SCENE_2_OUTSIDE_1":
                loadScene("SCENE_2_OUTSIDE_2");
                break;

            case "SCENE_2_OUTSIDE_2":
                loadScene("SCENE_3_POSTER");
                break;

            case "SCENE_3_POSTER":
                if (command.contains("밴드부 부스를 가리킨다")) {
                    loadScene("SCENE_4A_GAEUL_INTRO_1");
                } else {
                    loadScene("SCENE_4B_MINJI_TEAMUP_1");
                }
                break;

            case "SCENE_4A_GAEUL_INTRO_1":
                loadScene("SCENE_4A_GAEUL_INTRO_2");
                break;

            case "SCENE_4A_GAEUL_INTRO_2":
                if (command.contains("건반")) {
                    adjustAffection("gaeul", 10);
                    loadScene("SCENE_4A_GAEUL_REACTION");
                } else {
                    adjustAffection("gaeul", -5);
                    loadScene("SCENE_5A_MSG_GAEUL");
                }
                break;

            case "SCENE_4A_GAEUL_REACTION":
                loadScene("SCENE_5A_MSG_GAEUL");
                break;

            case "SCENE_4B_MINJI_TEAMUP_1":
                loadScene("SCENE_4B_MINJI_TEAMUP_2");
                break;

            case "SCENE_4B_MINJI_TEAMUP_2":
                if (command.contains("좋아")) {
                    adjustAffection("minji", 10);
                    loadScene("SCENE_4B_MINJI_REACTION");
                } else {
                    adjustAffection("minji", -5);
                    loadScene("SCENE_4B_MINJI_REACTION_SAD");
                }
                break;

            case "SCENE_4B_MINJI_REACTION":
            case "SCENE_4B_MINJI_REACTION_SAD":
                loadScene("SCENE_5B_MSG_MINJI");
                break;

            case "SCENE_6_END_DAY_A":
            case "SCENE_6_END_DAY_B":
                if (command.equals("[1일차 종료. 잠자리에 든다.]")) {
                    NextDay();
                }
                break;

            // 2일차
             // ... (1일차 코드 생략) ...

                // =============================================================
                // [2일차 공통 및 분기 시작]
                // =============================================================
                case "DAY2_OPENING":
                    loadScene("DAY2_SCENE1_CLASS");
                    break;

                case "DAY2_SCENE1_CLASS":
                    // 선택지에 따라 분기 이동
                    if (command.contains("합주실")) {
                        loadScene("DAY2_SCENE2A_PRACTICE"); // 이가을 루트 진입
                    } else {
                        loadScene("DAY2_SCENE2B_LIBRARY");  // 김민지 루트 진입
                    }
                    break;

                // =============================================================
                // [2일차 이가을 루트] - 수정된 스토리 흐름 반영
                // =============================================================
                
                // 1. 합주실 도착 -> 문 열고 들어감
                case "DAY2_SCENE2A_PRACTICE":
                    loadScene("DAY2_SCENE2A_ARRIVAL");
                    break;

                // 2. 선배 발견 -> 핀잔 듣기
                case "DAY2_SCENE2A_ARRIVAL":
                    loadScene("DAY2_SCENE2A_GREETING");
                    break;

                // 3. 핀잔 -> 연주 도전 선택지
                case "DAY2_SCENE2A_GREETING":
                    loadScene("DAY2_SCENE2A_CHALLENGE");
                    break;

                // 4. 연주 선택지 결과 처리
                case "DAY2_SCENE2A_CHALLENGE":
                    if (command.contains("화려한")) {
                        adjustAffection("gaeul", 10); // 대박 호감도
                        loadScene("DAY2_SCENE2A_RESULT_SHOWOFF"); // 칭찬 루트
                    } else {
                        adjustAffection("gaeul", 5);  // 기본 호감도
                        loadScene("DAY2_SCENE2A_RESULT_STANDARD"); // 스킨십 루트
                    }
                    break;

                // 5-1. 화려한 연주 결과 -> 바로 분식집(먹방)으로
                case "DAY2_SCENE2A_RESULT_SHOWOFF":
                    loadScene("DAY2_SCENE3A_SNACK_EATING");
                    break;

                // 5-2. 정석 연주 결과 (스킨십 1) -> 스킨십 2로
                case "DAY2_SCENE2A_RESULT_STANDARD":
                    loadScene("DAY2_SCENE2A_RESULT_STANDARD_2");
                    break;

                // 5-3. 정석 연주 결과 (스킨십 2) -> 분식집(먹방)으로
                case "DAY2_SCENE2A_RESULT_STANDARD_2":
                    loadScene("DAY2_SCENE3A_SNACK_EATING");
                    break;

                // 6. 분식집: 먹방 -> 눈맞춤
                case "DAY2_SCENE3A_SNACK_EATING":
                    loadScene("DAY2_SCENE3A_SNACK_EYECONTACT");
                    break;

                // 7. 분식집: 눈맞춤 선택지 처리
                case "DAY2_SCENE3A_SNACK_EYECONTACT":
                    if (command.contains("소스")) { // 닦아준다
                        adjustAffection("gaeul", 10);
                    } else { // 예쁘다 칭찬
                        adjustAffection("gaeul", 5);
                    }
                    loadScene("DAY2_SCENE3A_SNACK_SMILE"); // 미소 짓는 장면
                    break;

                // 8. 분식집: 미소 -> 마무리 멘트
                case "DAY2_SCENE3A_SNACK_SMILE":
                    loadScene("DAY2_SCENE3A_SNACK_END");
                    break;

                // 9. 분식집 종료 -> 밤 문자 메시지 화면으로 이동
                case "DAY2_SCENE3A_SNACK_END":
                    loadScene("DAY2_NIGHT_MSG_GAEUL");
                    break;

                case "DAY2_SCENE2B_LIBRARY":
                    if (command.startsWith("노트북 마우스를 잡으며")) {
                        adjustAffection("minji", 10);
                        loadScene("DAY2_SCENE2B_LIBRARY_SOLVED"); // 해결 씬 연결
                    } else if (command.contains("교수님이 강조하신")) {
                        adjustAffection("minji", 5);
                        loadScene("DAY2_SCENE2B_LIBRARY_SOLVED");
                    } else {
                        adjustAffection("minji", -5);
                        loadScene("DAY2_SCENE3B_BENCH_TALK"); // 바로 대화로 이동
                    }
                    break;

                case "DAY2_SCENE2B_LIBRARY_SOLVED":
                    loadScene("DAY2_SCENE3B_BENCH_TALK");
                    break;

                case "DAY2_SCENE3B_BENCH_TALK":
                    loadScene("DAY2_SCENE3B_BENCH");
                    break;

                case "DAY2_SCENE3B_BENCH":
                    // 선택지에 따른 호감도 반영
                    if (command.equals("그래도 그 덕분에 네가 챙겨줬었지.")) {
                        adjustAffection("minji", 5);
                    }
                    // (기존에는 여기서 바로 문자로 갔지만, 이제 추가된 대화 장면으로 이동합니다)
                    loadScene("DAY2_SCENE3B_BENCH_1");
                    break;

                // [추가] 과제 끝, 집으로 가자
                case "DAY2_SCENE3B_BENCH_1":
                    loadScene("DAY2_SCENE3B_BENCH_2");
                    break;
                    
                case "DAY2_SCENE3B_BENCH_2":
                    loadScene("DAY2_SCENE3B_BENCH_3");
                    break;
                    
                case "DAY2_SCENE3B_BENCH_3":
                    loadScene("DAY2_SCENE3B_BENCH_4");
                    break;
                    
                case "DAY2_SCENE3B_BENCH_4":
                    loadScene("DAY2_SCENE3B_BENCH_5");
                    break;
                    
                case "DAY2_SCENE3B_BENCH_5":
                    loadScene("DAY2_SCENE3B_BENCH_6");
                    break;
                    
                // [추가] 작별 인사 -> 이후 밤 문자 씬으로 이동
                case "DAY2_SCENE3B_BENCH_6":
                    // 모든 대화가 끝났으니 이제 호감도에 따라 문자 메시지를 결정합니다.
                    if (model.getAffectionMinji() >= 15) {
                        loadScene("DAY2_NIGHT_MSG_MINJI_HIGH");
                    } else {
                        loadScene("DAY2_NIGHT_MSG_MINJI_LOW");
                    }
                    break;

                case "DAY2_END_NIGHT":
                    if (command.equals("[하루 끝내기]")) {
                        NextDay();
                    }
                    break;
                    
                    /* 3일차 */
                case "DAY3_OPENING":
                    loadScene("DAY3_SCENE0_CLASS");
                    break;

                case "DAY3_SCENE0_CLASS":
                    if (command.contains("가을"))
                        loadScene("DAY3_ROUTE_GAEUL_MSG");
                    else
                        loadScene("DAY3_ROUTE_MINJI_MSG");
                    break;

                case "DAY3_GAEUL_MEET":
                    loadScene("DAY3_GAEUL_TALK1");
                    break;

                case "DAY3_GAEUL_TALK1":
                    loadScene("DAY3_GAEUL_TALK2");
                    break;

                case "DAY3_GAEUL_TALK2":
                    loadScene("DAY3_GAEUL_EVENT");
                    break;

                case "DAY3_GAEUL_EVENT":
                    loadScene("DAY3_GAEUL_EVENT_RESULT");
                    break;

                case "DAY3_GAEUL_EVENT_RESULT":
                    loadScene("DAY3_GAEUL_END");
                    break;

                case "DAY3_GAEUL_END":
                    loadScene("DAY3_GAEUL_NIGHT_MSG");
                    break;

                case "DAY3_MINJI_MEET":
                    loadScene("DAY3_MINJI_TALK1");
                    break;

                case "DAY3_MINJI_TALK1":
                    loadScene("DAY3_MINJI_TALK2");
                    break;

                case "DAY3_MINJI_TALK2":
                    loadScene("DAY3_MINJI_TALK3");
                    break;

                case "DAY3_MINJI_TALK3":
                    loadScene("DAY3_MINJI_EVENT");
                    break;

                case "DAY3_MINJI_EVENT":
                    loadScene("DAY3_MINJI_EVENT_RESULT");
                    break;

                case "DAY3_MINJI_EVENT_RESULT":
                    loadScene("DAY3_MINJI_END");
                    break;

                case "DAY3_MINJI_END":
                    loadScene("DAY3_MINJI_NIGHT_MSG");
                    break;

                case "DAY3_END":
                    if (command.equals("[잠자리에 든다]"))
                        NextDay();
                    break;
        }
    }

    public void NextDay() {
        model.setCurrentDay(model.getCurrentDay() + 1);
        int day = model.getCurrentDay();
        
        if (day == 2) {
            loadScene("DAY2_OPENING");
        } 
        // ★ [추가된 부분] 3일차일 경우 3일차 오프닝을 로드합니다.
        else if (day == 3) { 
            loadScene("DAY3_OPENING");
        }
        else {
            JOptionPane.showMessageDialog(view, day + "일차가 시작됩니다. (스토리 미구현)");
            showMainMenu();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String command = e.getActionCommand();

        CardLayout cl = view.getCardLayout();
        JPanel mainPanel = view.getMainPanel();

        // 1. 게임 시작
        if ("startGame".equals(command)) {
            setupInitialGame();
            return;
        }

        // 2. 상태창 보기
        else if (source == view.getProfileButton()) {
            view.getStatusPanel().updateStatus(
                model.getCurrentDay(),
                model.getAffectionMinji(),
                model.getAffectionGaeul()
            );
            cl.show(mainPanel, "STATUS");
            return;
        } 
        
        // 3. 뒤로가기 (상태창 -> 게임)
        else if (source == view.getStatusPanel().getBackButton()) {
            cl.show(mainPanel, "GAME");
            return;
        } 
        
        // 4. 스마트폰 뒤로가기
        else if ("returnToGame".equals(command)) {
            cl.show(mainPanel, "GAME");
            return;
        }
        
        // 5. 저장 (Save)
        else if (source == view.getSaveButton()) {
            saveManager.saveGame(model);
            JOptionPane.showMessageDialog(view, 
                "게임이 저장되었습니다.\n(현재 씬: " + model.getCurrentSceneId() + ")");
            return;
        } 
        
        // 6. 불러오기 (Load)
        else if (source == view.getLoadButton()) {
            if (saveManager.saveFileExists()) {
                GameState loadedState = saveManager.loadGame();
                if (loadedState != null) {
                    this.model = loadedState;
                    loadScene(model.getCurrentSceneId());
                    JOptionPane.showMessageDialog(view, "게임을 불러왔습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "저장된 파일이 없습니다.");
            }
            return;
        } 
        
        // 7. 설정 (Settings)
        else if (source == view.getSettingsButton()) {
            JOptionPane.showMessageDialog(view, 
                "<html><h2>환경 설정</h2><br>버전: 1.0<br>제작: 상속자들</html>", 
                "설정", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // 8. 볼륨 조절
        else if (source == view.getVolumeUpButton()) {
            audioManager.volumeUp();
            JOptionPane.showMessageDialog(view, "볼륨: " + audioManager.getVolume() + "%");
            return;
        } 
        else if (source == view.getVolumeDownButton()) {
            audioManager.volumeDown();
            JOptionPane.showMessageDialog(view, "볼륨: " + audioManager.getVolume() + "%");
            return;
        }
        

        // 9. 그 외 일반 게임 선택지 (스마트폰 답장 포함)
        handleGameChoice(command);
    }
}