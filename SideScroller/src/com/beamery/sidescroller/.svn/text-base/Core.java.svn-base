package com.beamery.sidescroller;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.beamery.engine.core.*;
import com.beamery.engine.gfx.*;
import com.beamery.engine.input.InputManager;
import com.beamery.engine.sound.SoundManager;
import com.beamery.sidescroller.states.*;
import com.beamery.sidescroller.ui.StateWidget;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 * The basic game window.  Creates a window, sets it up for 2D graphics, 
 * runs the game loop, and updates input and rendering.
 * 
 * @author Brian Murray
 *
 */
public class Core extends Thread {

	public static boolean gameOver;
	
	protected int width;
	protected int height;

	protected TextureManager textureManager;
	protected StateManager stateManager;
	protected SoundManager soundManager;
	protected InputManager inputManager;
	
	protected LWJGLRenderer renderer;
	protected StateWidget stateWidget;
	protected GUI gui;
	protected ThemeManager themeManager;


	public Core() {
		gameOver = false;
		textureManager = new TextureManager();
		stateManager = new StateManager();
		soundManager = new SoundManager();
		inputManager = new InputManager();
		stateWidget = new StateWidget(inputManager);
	}

	public void run() {

		try {
			initDisplay();

		} catch (LWJGLException e) {
			System.out.println("Error opening window.");
			System.exit(0);
		}

		setup2DGraphics();
		initTextures();
		initSounds();
		initStates();
		initUI();

		// run the game loop (variable timestep)
		gameLoop();

		Display.destroy();
		AL.destroy();
		System.exit(0);
	}

	/**
	 * A fixed timestep game loop.  Framerate is capped at 60 fps, 
	 * but may dip below if necessary.
	 */
	private void gameLoop() {

		long elapsedTime = 0;
		long newTime;
		long oldTime = System.nanoTime();
		long expectedTime = 1000000000 / 60;
		double delta;

		int frames = 0;
		long frameTimer = 0;

		while (!Display.isCloseRequested() && !gameOver) {

			// get new time, find elapsed time, determine delta
			newTime = System.nanoTime();
			elapsedTime = newTime - oldTime;
			delta = (double)elapsedTime / (double)expectedTime;
			oldTime = newTime;

			// update frame count
			frames++;
			frameTimer += elapsedTime;

			// if a second has passed, print the fps
			if (frameTimer >= 1000000000) {
				System.out.println("FPS: " + frames);
				System.out.println(delta);
				frameTimer = Math.max(0, frameTimer - 1000000000);
				frames = 0;
			}

			// update game state
			soundManager.update();
			stateManager.update(delta);
			stateManager.render();
			gui.update();
			Display.update();	

			// sleep until 1/60 of a second has passed
			if (newTime - oldTime < expectedTime) {

				while (newTime - oldTime < expectedTime) {

					newTime = System.nanoTime();

					try {
						Thread.sleep(0);
					} catch (InterruptedException e) { } 

				} 
			}
			else {

				try {
					Thread.sleep(0);
				} catch (InterruptedException e) { }
			}

		}
	}


	/**
	 * Initializes the display window and sets its resolution
	 * 
	 * @throws LWJGLException
	 */
	private void initDisplay() throws LWJGLException {

		Display.setDisplayMode(Display.getDesktopDisplayMode());
		Display.setFullscreen(true);
		width = Display.getDisplayMode().getWidth();
		height = Display.getDisplayMode().getHeight();
		Display.create();
		Display.setVSyncEnabled(true);
	}

	/**
	 * Initializes the texture manager and loads textures
	 */
	private void initTextures() {

		textureManager.loadTexture("PNG", "menu_bg", "res/menu_bg.png");
		textureManager.loadTexture("PNG", "mountain_bg", "res/mountain_bg.png");
		textureManager.loadTexture("PNG", "ball", "res/ball_sm.png");
		textureManager.loadTexture("PNG", "inner_bg1", "res/inner_bg1.png");
		textureManager.loadSheet("stand_still", "res/stand_still.png", 32, 64);
		textureManager.loadSheet("testAnim", "res/testAnim.png", 32, 64);
		textureManager.loadSheet("tileSet", "res/tileset_small.png", 32, 32);
		textureManager.loadSheet("walking", "res/walking.png", 32, 64);
		textureManager.loadSheet("jumping", "res/jumping.png", 32, 64);
		textureManager.loadSheet("slime_still", "res/slime_still_small.png", 32, 32);
	}

	/**
	 * Initializes the sounds for this game
	 */
	private void initSounds() {


	}

	/**
	 * Initializes the state manager, loads game states, and sets the initial state
	 */
	private void initStates() {

		stateManager.add("main_menu", new MainMenuState(stateManager, stateWidget, inputManager, textureManager));
		stateManager.add("inner_game", new InnerGameState(stateManager, stateWidget, inputManager, textureManager));
		stateManager.add("game_over", new GameOverState(stateManager));
		stateManager.changeState("main_menu");
	}
	
	/**
	 * Initializes TWL for this game
	 */
	private void initUI() {
		
		try {
			
			renderer = new LWJGLRenderer();
			gui = new GUI(stateWidget, renderer);
			themeManager = ThemeManager.createThemeManager(StateWidget.class.getResource("gameui.xml"), renderer);
			gui.applyTheme(themeManager);
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up OpenGL to render 2D graphics
	 */
	private void setup2DGraphics() {

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public static void main(String[] args) {
		new Core().start();
	}

}
