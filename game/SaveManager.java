package game;

import java.io.*;

public class SaveManager {

    private static final String SAVE_FILE = "savefile.dat"; // 저장 파일 이름

    // 게임 상태 저장
    public void saveGame(GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(gameState); // 객체를 파일로 씀
            System.out.println("게임 저장 완료: " + SAVE_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("게임 저장 실패");
        }
    }

    // 게임 상태 불러오기
    public GameState loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (GameState) ois.readObject(); // 파일에서 객체를 읽음
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("저장된 파일이 없거나 불러올 수 없습니다.");
            return null;
        }
    }
    
    // 저장 파일 존재 여부 확인
    public boolean saveFileExists() {
        File f = new File(SAVE_FILE);
        return f.exists() && !f.isDirectory();
    }
}