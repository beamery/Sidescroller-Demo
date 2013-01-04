package com.beamery.engine.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.*;
import org.newdawn.slick.SlickException;

import com.beamery.engine.gfx.*;
import com.beamery.engine.sound.SoundManager;

/**
 * The basic game window.  Creates a window, sets it up for 2D graphics, 
 * runs the game loop, and updates input and rendering.
 * 
 * @author Brian Murray
 *
 */
public class GameCore extends Thread {

	protected int width;
	protected int height;

	protected TextureManager textureManager;
	protected StateManager stateManager;
	protected SoundManager soundManager;



	public GameCore() {
		textureManager = new TextureManager();
		stateManager = new StateManager();
		soundManager = new SoundManager();
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

		while (!Display.isCloseRequested()) {

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
			updateInput();
			soundManager.update();
			stateManager.update(delta);
			stateManager.render();	
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

		Display.setDisplayMode(Display.getAvailableDisplayModes()[0]);
		width = Display.getDisplayMode().getWidth();
		height = Display.getDisplayMode().getHeight();
		//Display.setFullscreen(true);
		Display.create();
	}

	/**
	 * Initializes the texture manager and loads textures
	 */
	private void initTextures() {

		textureManager.loadTexture("TGA", "ball", "res/bullet.tga");
		textureManager.loadSheet("tester", "res/tester.png", 32, 64);
		textureManager.loadSheet("testAnim", "res/testAnim.png", 32, 64);
	}

	/**
	 * Initializes the sounds for this game
	 */
	private void initSounds() {

		soundManager.loadSong("testSong", "res/testSong.ogg");
		soundManager.loadSong("song4", "res/song4.ogg");
		soundManager.loadSound("applause", "res/applause.ogg");
		new Thread() {
			public void run() {
				soundManager.loadSong("song2", "res/song2.ogg");
				soundManager.loadSong("song3", "res/song3.ogg");
			}
		}.start();
	}

	/**
	 * Initializes the state manager, loads game states, and sets the initial state
	 */
	private void initStates() {

		stateManager.add("test", new TestState(textureManager, soundManager));
		stateManager.changeState("test");
	}

	/**
	 * Updates keyboard and mouse input
	 */
	public void updateInput() {

		if (Mouse.isButtonDown(0)) {
			System.out.println(
					"Mouse Pressed at X: " + Mouse.getX() + " Y: " + Mouse.getY());
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			System.out.println("Pressed: Spacebar");
		}

		while (Keyboard.next()) {

			if (Keyboard.getEventKeyState() == true) {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("Pressed: A");
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("Pressed: S");
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("Pressed: D");
				}
			}
			else {
				if (Keyboard.getEventKey() == Keyboard.KEY_A) {
					System.out.println("Released: A");
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					System.out.println("Released: S");
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_D) {
					System.out.println("Released: D");
				}
			}
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
	}

	public static void main(String[] args) {
		new GameCore().start();
	}

}
