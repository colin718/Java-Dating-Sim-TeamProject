package game;

import javax.swing.*;
import java.awt.*;

/**
 * 상태 창 (JPanel) (기획서 9페이지 참고)
 */
public class StatusPanel extends JPanel {

    private JButton backButton; 
    private JLabel currentDayLabel;
    
    // 캐릭터별 호감도 레이블
    private JLabel affectionLabelMinji;
    private JLabel affectionLabelGaeul;

    public StatusPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. 뒤로가기 버튼
        backButton = new JButton("게임으로 돌아가기");
        add(backButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // 2. 현재 상태
        currentDayLabel = new JLabel("현재 N일차");
        currentDayLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(currentDayLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // 3. 캐릭터 정보 (기획서 9페이지 시안)
        // 캐릭터 1 (김민지)
        add(createCharacterProfile(
            "images/human1.png", 
            "김민지, 20세",
            "affectionLabelMinji"
        ));
        
        add(Box.createRigidArea(new Dimension(0, 15)));

        // 캐릭터 2 (이가을)
        add(createCharacterProfile(
            "images/human2.png", 
            "이가을, 21세", 
            "affectionLabelGaeul"
        ));
    }

    private JPanel createCharacterProfile(String imgPath, String name, String labelName) {
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        profilePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // 이미지 (9페이지 시안처럼 작게)
        ImageIcon icon = ImageLoader.loadIcon(imgPath, 100, 100);
        if(icon != null) {
            profilePanel.add(new JLabel(icon));
        }

        // 정보
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel(name));
        
        JLabel affectionLabel = new JLabel("호감도: 0");
        if (labelName.equals("affectionLabelMinji")) {
            affectionLabelMinji = affectionLabel;
        } else {
            affectionLabelGaeul = affectionLabel;
        }
        infoPanel.add(affectionLabel);

        profilePanel.add(infoPanel);
        return profilePanel;
    }

    // --- Public 메서드 (컨트롤러가 호출) ---

    public JButton getBackButton() {
        return backButton;
    }

    /**
     * 컨트롤러가 모델의 데이터를 가져와서 UI를 업데이트할 때 호출
     */
    public void updateStatus(int day, int affMinji, int affGaeul) {
        currentDayLabel.setText("현재 " + day + "일차");
        affectionLabelMinji.setText("호감도: " + affMinji);
        affectionLabelGaeul.setText("호감도: " + affGaeul);
    }
}