package game;

import javax.swing.SwingUtilities;

/**
 * 게임을 실행하는 메인 클래스
 */
public class GameLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. 모델(데이터) 생성
            GameState model = new GameState(); 

            // 2. 뷰(화면) 생성
            GameWindow view = new GameWindow(); 

            // 3. 컨트롤러 생성 (뷰와 모델 연결)
            new GameController(view, model); 

            // 4. 화면 표시
            view.setVisible(true);
        });
    }
}