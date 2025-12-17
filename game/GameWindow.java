package game;

import javax.swing.*;
import java.awt.*;

/**
 * 게임의 메인 JFrame (View) 
 */
public class GameWindow extends JFrame {

       private JToolBar toolBar; // 
       private JButton settingsButton, volumeUpButton, volumeDownButton, saveButton, loadButton, profileButton;
   
       private JPanel mainPanel;
       private CardLayout cardLayout; // 
   
       private GamePanel gamePanel;
       private StatusPanel statusPanel;
       private JTextArea logArea; // 
       private MainMenuPanel mainMenuPanel;
       private SmartphonePanel smartphonePanel;
   
       public GameWindow() {
           setTitle("그녀를 '자바'라");
           setSize(800, 700); // Page 6의 800x600
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setLocationRelativeTo(null);
           setLayout(new BorderLayout());
   
           // 1. 툴바 생성 
           createToolBar();
           add(toolBar, BorderLayout.NORTH);
   
           // 2. 메인 패널 (CardLayout) 생성
           cardLayout = new CardLayout();
           mainPanel = new JPanel(cardLayout);
   
           gamePanel = new GamePanel();
           statusPanel = new StatusPanel();
   
           mainPanel.add(gamePanel, "GAME"); // "GAME" 화면 등록
           mainPanel.add(statusPanel, "STATUS"); // "STATUS" 화면 등록
   
           add(mainPanel, BorderLayout.CENTER);    
   
           // 3. 하단 대화 로그 영역 생성 
           logArea = new JTextArea(5, 0);
           logArea.setEditable(false);
           logArea.setLineWrap(true);
           logArea.setBackground(Color.DARK_GRAY);
           logArea.setForeground(Color.WHITE);
           JScrollPane logScrollPane = new JScrollPane(logArea);
           // add(logScrollPane, BorderLayout.SOUTH); 하단 상황설명 
           setVisible(true);
       }
    
       public void addMainMenuPanel(MainMenuPanel mainMenuPanel) {
           this.mainMenuPanel = mainMenuPanel;
           mainPanel.add(mainMenuPanel, "MAIN_MENU"); 
       }
       
       public void addSmartphonePanel(SmartphonePanel panel) {
           this.smartphonePanel = panel;   // 저장만
           mainPanel.add(panel, "SMARTPHONE");
       }
       
       // <-- 3. Getter 추가
       public MainMenuPanel getMainMenuPanel() { 
           return mainMenuPanel;
       }

       private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // 기획서 및 이미지 기반 버튼 생성
        settingsButton = new JButton("설정(⚙️)"); // Page 6
        volumeUpButton = new JButton("🔊++"); 
        volumeDownButton = new JButton("🔉--"); 
        saveButton = new JButton("Save");
        loadButton = new JButton("Load"); 
        profileButton = new JButton("상태(👩)"); // Page 5

        toolBar.add(settingsButton);
        toolBar.addSeparator();
        toolBar.add(volumeUpButton);
        toolBar.add(volumeDownButton);
        toolBar.addSeparator();
        toolBar.add(saveButton);
        toolBar.add(loadButton);
        toolBar.addSeparator();
        toolBar.add(profileButton);
    }

    // 컨트롤러가 접근할 수 있도록 Getter 제공
       public CardLayout getCardLayout() { return cardLayout; }
       public JPanel getMainPanel() { return mainPanel; }
       public GamePanel getGamePanel() { return gamePanel; }
       public StatusPanel getStatusPanel() { return statusPanel; }
       public JTextArea getLogArea() { return logArea; }
       public JButton getSettingsButton() { return settingsButton; }
       public JButton getVolumeUpButton() { return volumeUpButton; }
       public JButton getVolumeDownButton() { return volumeDownButton; }
       public JButton getSaveButton() { return saveButton; }
       public JButton getLoadButton() { return loadButton; }
       public JButton getProfileButton() { return profileButton; }
       public SmartphonePanel getSmartphonePanel() {return smartphonePanel;}
   }
	
