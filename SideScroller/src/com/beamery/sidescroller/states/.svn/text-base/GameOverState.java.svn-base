package com.beamery.sidescroller.states;

import org.lwjgl.opengl.GL11;

import com.beamery.engine.core.*;

public class GameOverState implements GameState {

	private StateManager stateManager;
	private double timer = 0;
	
	public GameOverState(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public void init() {
		
	}
	
	public void layoutUI() {
		
	}

	public void update(double delta) {
		timer += delta * (1.0 / 60);
		if (timer > 2) {
			timer = 0;
			stateManager.changeState("main_menu");
		}
		
	}

	public void render() {
		
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
}
