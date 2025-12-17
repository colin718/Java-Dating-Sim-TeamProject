package game;

import java.io.Serializable;


public class GameState implements Serializable {
    
    private static final long serialVersionUID = 1L; // Serializable ID

    private String currentSceneId; 
    private int currentDay;
    
    // 기획서(Page 9)의 캐릭터 기반
    private int affectionMinji;
    private int affectionGaeul;
    // (세 번째 캐릭터 호감도...)

    // --- Getter 및 Setter ---
    
    public String getCurrentSceneId() {
        return currentSceneId;
    }

    public void setCurrentSceneId(String currentSceneId) {
        this.currentSceneId = currentSceneId;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public int getAffectionMinji() {
        return affectionMinji;
    }

    public void setAffectionMinji(int affectionMinji) {
        this.affectionMinji = affectionMinji;
    }

    public int getAffectionGaeul() {
        return affectionGaeul;
    }

    public void setAffectionGaeul(int affectionGaeul) {
        this.affectionGaeul = affectionGaeul;
    }
}