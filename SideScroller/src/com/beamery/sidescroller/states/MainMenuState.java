package com.beamery.sidescroller.states;

import com.beamery.engine.core.*;
import com.beamery.engine.gfx.Renderer;
import com.beamery.engine.gfx.TextureManager;
import com.beamery.engine.input.*;
import com.beamery.engine.util.Point;
import com.beamery.sidescroller.Core;
import com.beamery.sidescroller.gfx.BackgroundLayer;
import com.beamery.sidescroller.ui.StateWidget;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Event;

import org.lwjgl.opengl.*;

public class MainMenuState implements GameState {
	
	private StateManager stateManager;
	private StateWidget stateWidget;
	private InputManager inputManager;
	private TextureManager textureManager;
	private Renderer renderer;
	
	private Button startGame;
	private Button settings;
	private Button exit;

	private GameAction action1;
	private GameAction action2;
	private GameAction action3;
	private GameAction action4;
	
	private float green = 0;
	private float blue = 0;
	BackgroundLayer background;
	BackgroundLayer mountains;
	
	public MainMenuState(StateManager stateManager, StateWidget stateWidget, InputManager inputManager, TextureManager textureManager) {
		
		this.stateManager = stateManager;
		this.stateWidget = stateWidget;
		this.inputManager = inputManager;
		this.textureManager = textureManager;
		renderer = new Renderer();
	}
	
	public void init() {
		GL11.glClearColor(0, 0, 0, 1);
		background = new BackgroundLayer(textureManager.get("menu_bg"), new Point(0, 1), new Point(0.92f, 0));
		mountains = new BackgroundLayer(textureManager.get("mountain_bg"), new Point(0, 1), new Point(1, 0));
		stateWidget.setCurrentState(this);
		
		startGame = new Button();
		startGame.setTheme("play-button");
		
		settings = new Button();
		settings.setTheme("cog-button");
		
		exit = new Button();
		exit.setTheme("quit-button");
		
		stateWidget.add(startGame);
		stateWidget.add(settings);
		stateWidget.add(exit);
		
		initButtons();
		initActions();
	}
	
	private void initButtons() {
		
		startGame.addCallback(new Runnable() {
			public void run() {
				//startGame.setEnabled(false);
				stateWidget.removeAllChildren();
				stateManager.changeState("inner_game");
			}
		});
		
		exit.addCallback(new Runnable() {
			public void run() {
				Core.gameOver = true;
			}
		});
	}
	
	private void initActions() {
		action1 = new GameAction("up");
		action2 = new GameAction("down");
		action3 = new GameAction("color_up");
		action4 = new GameAction("color_down");
		inputManager.bindKeyAction(Event.KEY_W, action1);
		inputManager.bindKeyAction(Event.KEY_S, action2);
		inputManager.bindKeyAction(Event.KEY_D, action3);
		inputManager.bindKeyAction(Event.KEY_A, action4);
	}
	
	public void layoutUI() {
		
		startGame.setSize(120, 120);
		settings.setSize(120, 120);
		exit.setSize(120, 120);
		
		int y = Display.getHeight() / 2 + startGame.getHeight() / 2;
		int x = Display.getWidth() / 2 - 3 * startGame.getWidth() / 2;
		
		startGame.setPosition(x, y);
		x += startGame.getWidth();
		settings.setPosition(x, y);
		x += settings.getWidth();
		exit.setPosition(x, y);
		
	}
	
	public void update(double delta) {
		if (action1.isPressed()) {
			green = Math.min(green + 0.01f, 1);
		}
		if (action2.isPressed()) {
			green = Math.max(green - 0.01f, 0);
		}
		if (action3.isPressed()) {
			blue = Math.min(blue + 0.01f, 1);
		}
		if (action4.isPressed()) {
			blue = Math.max(blue - 0.01f, 0);
		}
		GL11.glClearColor(0, green, blue, 1);
		background.scrollHorizontally(0.0002f);
	}

	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		background.render();
		mountains.render();
		renderer.render();
	}

}
