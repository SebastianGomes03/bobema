package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

    public static int MENU_MUSIC = 0;
    public static int LEVEL_MUSIC = 1;

    public static int MOVING = 0;
    public static int PLACE_BOMB = 1;
    public static int BOMB_SOUND = 2;
    public static int BOMB_EXPLOSION = 3;
    public static int DYING = 4;
    public static int POWERUP = 5;

    private Clip[] songs, effects;
    private int currentSongId;
    private float volume = 1f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    public AudioPlayer(){
        loadSongs();
        loadEffects();
        playSong(MENU_MUSIC);
    }

    private void loadSongs() {
        String[] names = {"menu", "level"};
        songs = new Clip[names.length];
        for(int i = 0; i < songs.length; i++)
            songs[i] = getClip(names[i]);
    }

    private void loadEffects() {
        String[] effectsNames = {"placebomb", "bomb_sound", "bomb_explosion", "dying"};
        effects = new Clip[effectsNames.length];
        for(int i = 0; i < effects.length; i++)
            effects[i] = getClip(effectsNames[i]);
    }

    private Clip getClip(String name){
        URL url = getClass().getResource("/audio/" + name + ".wav");
        AudioInputStream audioIn;

        try {
            audioIn = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audioIn);
            return c;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }
    
    public void stopSong() {
        if (songs[currentSongId].isActive())
            songs[currentSongId].stop();
    }

    public void setLevelSong(int lvlIndex) {
        playSong(LEVEL_MUSIC);
    }

    public void playEffect(int effect) {
        if (effects[effect].getMicrosecondPosition() > 0)
            effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

	public void playSong(int song) {
		stopSong();

		currentSongId = song;
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}

    private void updateSongVolume() {

		FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);

	}

	private void updateEffectsVolume() {
		for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}

}
