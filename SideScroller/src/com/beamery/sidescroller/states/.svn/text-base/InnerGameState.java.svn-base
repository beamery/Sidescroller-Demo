package com.beamery.sidescroller.states;

import org.lwjgl.opengl.GL11;

import com.beamery.engine.core.*;
import com.beamery.engine.gfx.*;
import com.beamery.engine.input.GameAction;
import com.beamery.engine.input.InputManager;
import com.beamery.engine.util.Point;
import com.beamery.sidescroller.Level;
import com.beamery.sidescroller.gfx.BackgroundLayer;
import com.beamery.sidescroller.ui.StateWidget;

import de.matthiasmann.twl.Event;

public class InnerGameState implements GameState{

	StateManager stateManager;
	StateWidget gui;
	InputManager inputManager;
	TextureManager textureManager;
	Renderer renderer;
	Level level;
	BackgroundLayer background;
	double timer = 0;
	
	GameAction exit;
	
	public InnerGameState(StateManager stateManager, StateWidget gui, InputManager inputManager, 
		TextureManager textureManager) {
		
		this.stateManager = stateManager;
		this.gui = gui;
		this.inputManager = inputManager;
		this.textureManager = textureManager;
		renderer = new Renderer();
	}

	public void init() {
		gui.setCurrentState(this);
		initActions();
		level = new Level(textureManager, inputManager, "res/genMap.bmap");
		background = new BackgroundLayer(textureManager.get("inner_bg1"), new Point(0, 1), new Point(1, 0));
	}
	
	public void initActions() {
		exit = new GameAction("Exit");
		
		inputManager.bindKeyAction(Event.KEY_ESCAPE, exit);
	}
	
	public void layoutUI() {
		
	}
	
	public void update(double delta) {
		
		if (exit.isPressed() || level.gameOver) {
			stateManager.changeState("game_over");
		}
		
		level.update(delta);
	}

	@Override
	public void render() {
		
		GL11.glClearColor(0, 0, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		background.render();
		level.render(renderer);
		renderer.render();
		
	}
	
	
}
