package game; // 재훈님 패키지

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * 메인 메뉴 화면 (JPanel)
 * 'mainpage.png'를 배경으로, '게임 시작'과 '이어하기' 버튼을 표시합니다.
 */
public class MainMenuPanel extends JPanel {

    private Image backgroundImage;
    private JButton startButton;
    private JButton continueButton;

    public MainMenuPanel(ActionListener controller) {
        // [핵심 1] BorderLayout을 사용해 하단(SOUTH)에 버튼을 배치합니다.
        setLayout(new BorderLayout());

        // 1. 배경 이미지 로드 (GamePanel과 동일한 방식)
        // 'mainpage.png'가 'src/images/' 폴더 안에 있어야 합니다.
        ImageIcon icon = ImageLoader.loadIcon("images/mainpage.png", 800, 700); // (창 크기에 맞게 조절)
        if (icon != null) {
            this.backgroundImage = icon.getImage();
        }

        // 2. [핵심 2] 버튼을 중앙에 배치하기 위한 'wrapper' 패널 생성
        // FlowLayout은 기본값이 중앙 정렬입니다.
        JPanel buttonWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonWrapperPanel.setOpaque(false); // 버튼만 보이도록 패널은 투명하게

        // 3. '게임 시작' 버튼 생성
        startButton = new JButton("게임 시작");
        startButton.setActionCommand("startGame"); //  (btnStare)
        startButton.addActionListener(controller); // 컨트롤러 연결
        
        startButton.setFont(new Font("맑은 고딕", Font.BOLD, 30)); // (20은 예시 크기입니다)

        // 4. '이어하기' 버튼 생성
        /*
        continueButton = new JButton("이어하기");
        continueButton.setActionCommand("continueGame"); 
        continueButton.addActionListener(controller);

        continueButton.setFont(new Font("맑은 고딕", Font.BOLD, 30)); // (시작 버튼과 크기를 맞춥니다)
        buttonWrapperPanel.add(continueButton);
         styleButton(continueButton);  
        */
       
        buttonWrapperPanel.add(startButton);    

        
        // 5. [핵심 3] 하단 중앙에 보이도록, wrapper 패널을 SOUTH에 추가
        // 버튼이 너무 바닥에 붙지 않게 바깥쪽 패널에 여백(Border)을 줍니다.
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(buttonWrapperPanel, BorderLayout.CENTER);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0)); // 하단 여백 80px

        add(southPanel, BorderLayout.SOUTH);
    }

    // 6. 패널의 배경으로 이미지를 그리는 메서드 (GamePanel과 동일)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 이미지가 패널 크기에 꽉 차게 그리기
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
 // ... (paintComponent 메소드 끝나는 곳) ...

    /**
     * JButton을 고급스러운 스타일(반투명, 호버 효과)로 변경하는 헬퍼 메소드
     * @param button 스타일을 적용할 버튼
     */
    private void styleButton(JButton button) {
        // 버튼의 기본 색상 (R, G, B, Alpha(투명도))
        Color defaultColor = new Color(80, 80, 80, 200); // 반투명 진한 회색
        // 마우스를 올렸을 때 색상
        Color hoverColor = new Color(110, 110, 110, 220); // 더 밝은 반투명 회색
        
        button.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 폰트 (크기는 여기서 조절)
        button.setForeground(Color.WHITE); // 글자색은 흰색
        button.setBackground(defaultColor); // 기본 배경색
        
        // 1. 버튼의 3D 테두리 제거
        button.setBorderPainted(false);
        // 2. 버튼 클릭 시 생기는 텍스트 주변 점선 제거
        button.setFocusPainted(false);
        // 3. 배경색을 칠하기 위해 Opaque 설정 (반드시 true)
        button.setOpaque(true); 
        button.setContentAreaFilled(true); // 내용 영역 채우기

        // 4. 마우스 호버(Rollover) 효과 추가
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor); // 마우스 올리면 밝은색
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor); // 마우스 떠나면 기본색
            }
        });
    }

} // <-- MainMenuPanel 클래스 닫는 괄호