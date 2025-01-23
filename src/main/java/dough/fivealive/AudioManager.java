package dough.fivealive;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;

public class AudioManager {
    private static AudioManager instance;
    private MediaPlayer backgroundMusicPlayer;

    private AudioManager() {
        try {
            // Load the background music file
            Media backgroundMusic = new Media(getClass().getResource("/audio/testbgm.mp3").toURI().toString());
            backgroundMusicPlayer = new MediaPlayer(backgroundMusic);

            // Set the music to loop
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Adjust the volume (default to 50%)
            backgroundMusicPlayer.setVolume(0.5);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            backgroundMusicPlayer.play();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    public void setVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(volume);
        }
    }
}
