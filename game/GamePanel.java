package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {

    private Image backgroundImage;

    private JTextArea dialogueArea;
    private RoundedPanel dialogueBlockPanel;

    private JPanel choiceCenterPanel;
    private JPanel choiceButtonPanel;

    private Runnable nextDialogueCallback = null;
    
    private Thread typingThread;
    
    // ★ [추가된 변수] 선택지가 세팅되었지만 아직 화면에 안 나온 상태인지 체크
    private boolean isChoicePending = false;

    private static final Color SHARED_BG_COLOR = new Color(255, 255, 240, 220); 
    private static final Color TEXT_COLOR = Color.BLACK; 
    

    public GamePanel() {
        setLayout(new BorderLayout());

        // 0. 배경 이미지 로딩
        ImageIcon bg = ImageLoader.loadIcon("images/hansung_background.png", 800, 500);
        if (bg != null) backgroundImage = bg.getImage();

        // 1. 대화창 영역
        dialogueBlockPanel = new RoundedPanel(15);
        dialogueBlockPanel.setLayout(new BorderLayout(10, 10));
        dialogueBlockPanel.setBackground(SHARED_BG_COLOR); 
        dialogueBlockPanel.setOpaque(false); 
        dialogueBlockPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        dialogueArea = new JTextArea("게임을 시작합니다...\n\n\n\n", 4, 0);
        dialogueArea.setEditable(false);
        dialogueArea.setLineWrap(true);
        dialogueArea.setWrapStyleWord(true);
        dialogueArea.setOpaque(false);
        dialogueArea.setForeground(TEXT_COLOR); 
        dialogueArea.setFont(new Font("맑은 고딕", Font.PLAIN, 18)); 

        JScrollPane scroll = new JScrollPane(dialogueArea);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleScreenClick();
            }
        });

        dialogueBlockPanel.add(scroll, BorderLayout.CENTER);
        add(dialogueBlockPanel, BorderLayout.SOUTH);


        // 2. 중앙 선택지 패널
        choiceCenterPanel = new JPanel(new GridBagLayout());
        choiceCenterPanel.setOpaque(false);
        choiceCenterPanel.setVisible(false);

        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setOpaque(false);
        choiceButtonPanel.setLayout(new BoxLayout(choiceButtonPanel, BoxLayout.Y_AXIS));

        choiceCenterPanel.add(choiceButtonPanel);
        add(choiceCenterPanel, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleScreenClick();
            }
        });
    }

    // =============================================================
    // ★ [핵심 로직] 화면 클릭 시 동작을 제어하는 메서드
    // =============================================================
    private void handleScreenClick() {
        // 1. 이미 선택지 패널이 보이고 있다면? -> 아무것도 안 함 (버튼을 눌러야 하므로)
        if (choiceCenterPanel.isVisible()) {
            return;
        }

        // 2. 숨겨진 선택지가 있는가? ([선택지 등장] 단계)
        if (isChoicePending) {
            choiceCenterPanel.setVisible(true); // 선택지 짠! 하고 보여줌
            isChoicePending = false;            // 대기 상태 해제
            revalidate();
            repaint();
        } 
        // 3. 숨겨진 선택지도 없다면? ([다음 대사] 단계)
        else {
            if (nextDialogueCallback != null) {
                nextDialogueCallback.run();
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null)
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void setCharacterImage(ImageIcon icon) {
        if (icon != null) {
            backgroundImage = icon.getImage();
            repaint();
        }
    }

    public void setDialogue(String text) { 
        dialogueArea.setText(text);
        dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());
    }

    public void appendDialogue(String text) { 
        dialogueArea.append("\n" + text);
        dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());
    }

    public void setNextDialogueCallback(Runnable callback) {
        this.nextDialogueCallback = callback;
    }

    /** * ★ [수정] 선택지 설정 로직 
     * 선택지를 만들되, 바로 보여주지 않고 isChoicePending 상태로 만듭니다.
     */
    public void setChoices(ActionListener controller, String... choices) {
        choiceButtonPanel.removeAll();
        
        // 일단 기본적으로 안 보이게 설정
        choiceCenterPanel.setVisible(false); 
        isChoicePending = false;

        // [다음] 버튼 하나뿐인 경우 (단순 대사 넘김)
        if (choices != null && choices.length == 1 && choices[0].contains("[다음]")) {
            // 이 경우는 클릭 한 번에 바로 넘어가야 하므로
            // 선택지 패널을 아예 안 띄우고, isChoicePending도 false로 둡니다.
            // -> handleScreenClick()의 3번 로직(nextDialogueCallback)이 실행됨
            
        } else if (choices != null && choices.length > 0) {
            // 진짜 선택지가 있는 경우 (A 또는 B)
            for (String choice : choices) {
                JButton btn = new JButton(choice);
                btn.addActionListener(controller);

                // 스타일 적용
                btn.setBackground(SHARED_BG_COLOR); 
                btn.setForeground(TEXT_COLOR); 
                btn.setOpaque(true);
                btn.setFont(new Font("맑은 고딕", Font.BOLD, 17));
                btn.setFocusPainted(false);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); 
                
                choiceButtonPanel.add(btn);
                choiceButtonPanel.add(Box.createVerticalStrut(12)); 
            }
            
            // ★ 핵심: 버튼은 다 만들었지만, 화면에는 바로 띄우지 않고 "대기" 상태로 둠
            // 사용자가 화면을 클릭해야 handleScreenClick()에서 visible true로 바꿈
            isChoicePending = true; 
        }

        revalidate();
        repaint();
    }

    public void clearChoices() {
        choiceCenterPanel.setVisible(false);
        choiceButtonPanel.removeAll();
        isChoicePending = false; // 초기화
    }
    
    public void showTextWithEffect(String text) {
        // 만약 이전에 글자를 치던 스레드가 있다면 중단시킴 (빠르게 넘길 때 겹침 방지)
        if (typingThread != null && typingThread.isAlive()) {
            typingThread.interrupt();
        }

        // 텍스트 영역 초기화
        dialogueArea.setText(""); 

        // 새로운 스레드 생성 (보조 일꾼 고용)
        typingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < text.length(); i++) {
                        // 한 글자 가져오기
                        String charToAdd = String.valueOf(text.charAt(i));
                        
                        // 화면 업데이트 (스윙 규칙상 화면 건드리는 건 invokeLater 안에)
                        SwingUtilities.invokeLater(() -> {
                            dialogueArea.append(charToAdd);
                            dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());
                        });

                        // 0.05초 잠들기 (타자 속도 조절)
                        Thread.sleep(50); 
                    }
                } catch (InterruptedException e) {
                    // 스레드가 중단되면(대사 스킵 등) 남은 텍스트를 한 번에 다 보여줌
                    SwingUtilities.invokeLater(() -> dialogueArea.setText(text));
                }
            }
        });

        // 일꾼 투입! (스레드 시작)
        typingThread.start();
    }

    
    // 둥근 모서리 패널 클래스 (기존 유지)
    class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}