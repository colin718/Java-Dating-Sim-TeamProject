package game;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class AudioManager {
    
    private Clip currentClip; // 현재 재생 중인 음악
    private int volume = 70;  // 기본 볼륨 (0 ~ 100)
    private FloatControl gainControl; // 볼륨 컨트롤러

    // ★ [스레드 적용] 배경음악 재생 기능
    public void playBGM(String fileName) {
        // 음악 재생은 파일 I/O가 발생하므로 별도 스레드에서 처리하는 것이 정석입니다.
        new Thread(() -> {
            try {
                // 1. 이미 재생 중인 음악이 있다면 정지
                stop();

                // 2. 파일 로드 (src/sounds 폴더 기준)
                InputStream audioSrc = getClass().getClassLoader().getResourceAsStream("sounds/" + fileName);
                if (audioSrc == null) {
                    System.err.println("오디오 파일을 찾을 수 없습니다: " + fileName);
                    return;
                }
                
                // 버퍼 기능을 추가해 끊김 방지
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

                // 3. 클립 생성 및 열기
                currentClip = AudioSystem.getClip();
                currentClip.open(audioStream);

                // 4. 볼륨 컨트롤러 연결
                if (currentClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
                    updateVolume(); // 현재 설정된 볼륨 적용
                }

                // 5. 무한 반복 재생 및 시작
                currentClip.loop(Clip.LOOP_CONTINUOUSLY); 
                currentClip.start();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start(); // 스레드 시작
    }

    // 음악 정지
    public void stop() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }

    // 볼륨 증가
    public void volumeUp() {
        if (volume < 100) {
            volume += 10;
            if (volume > 100) volume = 100;
            System.out.println("볼륨 증가: " + volume);
            updateVolume(); // 소리 크기 즉시 반영
        }
    }

    // 볼륨 감소
    public void volumeDown() {
        if (volume > 0) {
            volume -= 10;
            if (volume < 0) volume = 0;
            System.out.println("볼륨 감소: " + volume);
            updateVolume(); // 소리 크기 즉시 반영
        }
    }
    
    public int getVolume() {
        return volume;
    }

    // [내부 로직] 0~100의 값을 데시벨(dB)로 변환하여 적용
    private void updateVolume() {
        if (gainControl == null) return;

        // 데시벨 변환 공식 (선형적인 0~100을 로그 스케일로 변환)
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume / 100.0f) + gainControl.getMinimum();
        
        // 소리가 너무 작으면 아예 Mute 처리를 위해 최소값 고정
        if (volume == 0) {
            gain = gainControl.getMinimum();
        }
        
        gainControl.setValue(gain);
    }
}