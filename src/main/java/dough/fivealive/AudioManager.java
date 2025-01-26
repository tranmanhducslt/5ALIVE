/* 
 * Author: Phuong Nguyen [ID]
 * Purpose: Managing sounds in the game
*/

package dough.fivealive;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class AudioManager {
    private static AudioManager instance;
    private static MediaPlayer backgroundMusicPlayer;
    private static double backgroundMusicVolume = 0.5;
    private static double soundEffectsVolume = 0.5;

    // Private constructor for Singleton
    private AudioManager() {
        try {
            // Load the background music file
            Media backgroundMusic = new Media(getClass().getResource("/audio/mainBGM.mp3").toURI().toString());
            backgroundMusicPlayer = new MediaPlayer(backgroundMusic);

            // Set the music to loop indefinitely
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Adjust the volume (default to 50%)
            backgroundMusicPlayer.setVolume(backgroundMusicVolume);
        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("Failed to load background music. Please check the file path.");
        }
    }

    // Thread-safe Singleton implementation
    public static synchronized AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    // Play the background music
    public void playBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            backgroundMusicPlayer.play();
        }
    }

    // Stop the background music
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    // Set the background music volume
    public static void setBackgroundMusicVolume(double volume) {
        backgroundMusicVolume = Math.max(0, Math.min(volume, 1)); // Ensure volume is between 0 and 1
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(backgroundMusicVolume);
        }
    }

    // Get the background music volume
    public static double getBackgroundMusicVolume() {
        return backgroundMusicVolume;
    }

    // Set the sound effects volume
    public static void setSoundEffectsVolume(double volume) {
        soundEffectsVolume = Math.max(0, Math.min(volume, 1)); // Ensure volume is between 0 and 1
    }

    // Get the sound effects volume
    public static double getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    public static void playSoundEffect(String soundPath) {
        Media sound = new Media(AudioManager.class.getResource(soundPath).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(getSoundEffectsVolume());
        mediaPlayer.play();
    }
}
