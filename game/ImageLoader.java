package game;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

/**
 * ImageIcon을 로드하고 크기를 조절하는 유틸리티 클래스
 */
public class ImageLoader {

    /**
     * 파일 경로에서 ImageIcon을 로드하고 지정된 크기로 조절합니다.
     * @param path 이미지 파일 경로 (예: "images/character1.png")
     * @param width 조절할 너비
     * @param height 조절할 높이
     * @return 크기가 조절된 ImageIcon, 실패 시 null
     */
    public static ImageIcon loadIcon(String path, int width, int height) {
        try {
            // getResource()는 클래스패스(src 폴더 등) 기준으로 리소스를 찾습니다.
            URL imgUrl = ImageLoader.class.getClassLoader().getResource(path);
            if (imgUrl == null) {
                System.err.println("이미지를 찾을 수 없습니다: " + path);
                return null;
            }
            
            ImageIcon icon = new ImageIcon(imgUrl);
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}