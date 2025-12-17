package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SmartphonePanel extends JPanel {

    private Image smartphoneFrame;
    private JScrollPane scrollPane;
    private JPanel chatContainer;
    private JPanel replyButtonPanel;
    private JLabel contactNameLabel;
    private JButton backButton;

    // 배경색 등 설정
    private static final Color BG_FILL = new Color(0xF5F5F7);
    private static final Color MY_BUBBLE = new Color(0xAEE6FF); // 나(하늘색)
    private static final Color OTHER_BUBBLE = Color.WHITE;      // 상대(흰색)
    private static final Font MSG_FONT = new Font("맑은 고딕", Font.PLAIN, 14);

    // 프레임 여백 설정
    private static final int SCREEN_LEFT = 70;
    private static final int SCREEN_TOP = 110;
    private static final int SCREEN_RIGHT = 70;
    private static final int SCREEN_BOTTOM = 110;

    public SmartphonePanel(ActionListener controller) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // [수정 1] 경로 맨 앞 '/' 제거
        ImageIcon icon = ImageLoader.loadIcon("images/smartphone.png", 800, 500);
        if (icon != null) {
            smartphoneFrame = icon.getImage();
        }

        // 상단 헤더
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        backButton = new JButton("◀");
        backButton.setActionCommand("returnToGame");
        backButton.addActionListener(controller);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));

        contactNameLabel = new JLabel("대화 상대", SwingConstants.CENTER);
        contactNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        contactNameLabel.setForeground(Color.BLACK);

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(contactNameLabel, BorderLayout.CENTER);
        topBar.setBorder(new EmptyBorder(10, 10 + SCREEN_LEFT, 0, 10 + SCREEN_RIGHT));

        add(topBar, BorderLayout.NORTH);

        // 채팅 컨테이너
        chatContainer = new JPanel();
        chatContainer.setLayout(new BoxLayout(chatContainer, BoxLayout.Y_AXIS));
        chatContainer.setBackground(new Color(0,0,0,0));

        JPanel screenWrapper = new JPanel(new BorderLayout());
        screenWrapper.setOpaque(false);
        screenWrapper.setBorder(new EmptyBorder(SCREEN_TOP, SCREEN_LEFT, SCREEN_BOTTOM, SCREEN_RIGHT));
        
        scrollPane = new JScrollPane(chatContainer);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        screenWrapper.add(scrollPane, BorderLayout.CENTER);

        add(screenWrapper, BorderLayout.CENTER);

        // 답장 버튼 패널
        replyButtonPanel = new JPanel();
        replyButtonPanel.setLayout(new BoxLayout(replyButtonPanel, BoxLayout.Y_AXIS));
        replyButtonPanel.setOpaque(false);
        replyButtonPanel.setBorder(new EmptyBorder(10, SCREEN_LEFT, 120, SCREEN_RIGHT));
        add(replyButtonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BG_FILL);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (smartphoneFrame != null) {
            // (이미지 그리는 로직 유지)
            int imgW = smartphoneFrame.getWidth(this);
            int imgH = smartphoneFrame.getHeight(this);
            if (imgW > 0 && imgH > 0) {
                double panelRatio = (double) getWidth() / getHeight();
                double imgRatio = (double) imgW / imgH;
                int drawW, drawH;
                if (imgRatio > panelRatio) {
                    drawW = getWidth();
                    drawH = (int) (getWidth() / imgRatio);
                } else {
                    drawH = getHeight();
                    drawW = (int) (getHeight() * imgRatio);
                }
                int x = (getWidth() - drawW) / 2;
                int y = (getHeight() - drawH) / 2;
                g.drawImage(smartphoneFrame, x, y, drawW, drawH, this);
            }
        }
    }

    public void setupChatRoom(String contactName) {
        contactNameLabel.setText(contactName);
        chatContainer.removeAll();
        chatContainer.revalidate();
        chatContainer.repaint();
    }

    public void addChatMessage(String sender, String message) {
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BorderLayout());

        JTextArea bubble = new JTextArea(message);
        bubble.setFont(MSG_FONT);
        bubble.setLineWrap(true);
        bubble.setWrapStyleWord(true);
        bubble.setEditable(false);
        bubble.setOpaque(true);
        
        // [수정 2] 여백(Padding) 값 정상화 (상, 좌, 하, 우)
        bubble.setBorder(new EmptyBorder(8, 12, 8, 12)); 
        bubble.setMaximumSize(new Dimension(200, Integer.MAX_VALUE)); // 말풍선 최대 폭 제한

        JPanel bubbleWrap = new JPanel();
        bubbleWrap.setLayout(new BoxLayout(bubbleWrap, BoxLayout.X_AXIS));
        bubbleWrap.setOpaque(false);

        // [수정 3] '풍선껌' 오타 수정 및 레이아웃 정리
        if ("me".equals(sender)) {
            bubble.setBackground(MY_BUBBLE);
            bubble.setForeground(Color.BLACK);
            bubbleWrap.add(Box.createHorizontalGlue()); // 왼쪽을 밀어서
            bubbleWrap.add(bubble); // 오른쪽에 배치
        } else {
            bubble.setBackground(OTHER_BUBBLE);
            bubble.setForeground(Color.BLACK);
            bubbleWrap.add(bubble); // 왼쪽에 배치
            bubbleWrap.add(Box.createHorizontalGlue()); // 오른쪽을 밈
        }
        
        row.add(bubbleWrap, BorderLayout.CENTER);

        chatContainer.add(row);
        chatContainer.add(Box.createVerticalStrut(8));
        chatContainer.revalidate();
        chatContainer.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scrollPane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

    public void setReplies(ActionListener controller, String... replies) {
        replyButtonPanel.removeAll();
        if (replies != null && replies.length > 0) {
            for (String r : replies) {
                JButton btn = new JButton(r);
                btn.setFocusPainted(false);
                btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setActionCommand(r);
                btn.addActionListener(controller);
                
                btn.setBackground(new Color(255, 255, 240, 220));
                btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

                replyButtonPanel.add(btn);
                replyButtonPanel.add(Box.createVerticalStrut(8));
            }
        }
        replyButtonPanel.revalidate();
        replyButtonPanel.repaint();
    }
}