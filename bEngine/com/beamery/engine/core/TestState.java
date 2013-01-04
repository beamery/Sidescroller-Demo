package com.beamery.engine.core;

import org.lwjgl.opengl.*;

import com.beamery.engine.gfx.*;
import com.beamery.engine.sound.SoundManager;

public class TestState implements GameState {

	float brightness = 0;
	boolean increasing = true;
	Animation anim;
	Sprite sprite;
	Renderer renderer;
	SoundManager soundManager;

	public TestState(TextureManager textureManager, SoundManager soundManager) {
		renderer = new Renderer();
		this.soundManager = soundManager;
		
		anim = new Animation(textureManager.getSheet("tester"), 0, 4);
		anim.add(0, 1000);
		anim.add(1, 50);
		anim.add(2, 100);
		anim.add(3, 50);
		anim.setFlipped(true);
		
		sprite = new Sprite();
		sprite.addAnimation("test", anim);
		sprite.setAnimation("test");
		soundManager.playSong("testSong", 1, 1, false);
		soundManager.play("applause", 1, 1, false);
	}
	
	public void init() {
		
	}
	
	public void layoutUI() {
		
	}

	@Override
	public void update(double delta) {

		if (increasing) {
			brightness = Math.min(brightness + (float)delta / 60, 1);
			if (brightness == 1) {
				increasing = false;
			}
		}
		else {
			brightness = Math.max(brightness - (float)delta / 60, 0);
			if (brightness == 0) {
				increasing = true;
			}
		}
		anim.update(delta);

	}

	@Override
	public void render() {

		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		for (int i = 0; i < 100000; i++) {
			renderer.drawSprite(sprite);
		}
		renderer.render();
	}

}
