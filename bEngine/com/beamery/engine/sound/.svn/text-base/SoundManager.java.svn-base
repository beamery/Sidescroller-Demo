package com.beamery.engine.sound;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.*;

public class SoundManager {

	private HashMap<String, Sound> soundLibrary;
	private HashMap<String, Music> songLibrary;
	private Music currentMusic;
	private LinkedList<Sound> currentSounds;

	public SoundManager() {

		soundLibrary = new HashMap<String, Sound>();
		songLibrary = new HashMap<String, Music>();
		currentSounds = new LinkedList<Sound>();
	}

	public void loadSound(String soundId, String path) {

		try {
			soundLibrary.put(soundId, new Sound(path));

		} catch (SlickException e) {
			System.out.println("error loading sound " + soundId);
		}
	}

	public void loadSong(String soundId, String path) {

		try {
			Music tmp = new Music(path, true);
			songLibrary.put(soundId, tmp);
		} catch (SlickException e) {
			System.out.println("Error loading song " + soundId);
		}
	}

	public Sound get(String soundId) {
		return soundLibrary.get(soundId);
	}

	public void play(String soundId, float pitch, float volume, boolean loop) {
		if (loop) {
			soundLibrary.get(soundId).loop(pitch, volume);
		}
		else {
			soundLibrary.get(soundId).play(pitch, volume);
		}
		currentSounds.add(soundLibrary.get(soundId));
	}

	public void playSong(String soundId, float pitch, float volume, boolean loop) {
		if (loop) {
			songLibrary.get(soundId).loop(pitch, volume);
		}
		else {
			songLibrary.get(soundId).play(pitch, volume);
		}
		currentMusic = songLibrary.get(soundId);
	}

	public void stopMusic() {
		soundLibrary.get(currentMusic).stop();
	}
	
	public void update() {
		AudioLoader.update();
		
		for (int i = currentSounds.size() - 1; i >= 0; i--) {
			if (!currentSounds.get(i).playing()) {
				currentSounds.remove(i);
			}
		}
	}
}
