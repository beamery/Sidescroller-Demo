package com.beamery.engine.input;

import de.matthiasmann.twl.Event;


/**
 * Handles input for the game, as well as the keybindings for specific game actions
 * 
 * <p> Key Action codes found in twl.Event </p>
 * <p> Mouse Action codes found here </p>
 * 
 * @author Brian Murray
 *
 */
public class InputManager {

	private final int NUM_MOUSE_ACTIONS = 6;
	
	public static final int MOUSE_LEFT = 0;
	public static final int MOUSE_RIGHT = 1;
	public static final int MOUSE_MIDDLE = 2;
	public static final int MOUSE_SCROLL_DOWN = 3;
	public static final int MOUSE_SCROLL_UP = 5;
	
	private GameAction[] keyActions = new GameAction[500];
	private GameAction[] mouseActions = new GameAction[NUM_MOUSE_ACTIONS];
	
	
	/**
	 * Binds a game action to a particular mouse button, or the scroll wheel
	 * 
	 * <p> Mouse codes are defined in the InputHandler class </p>
	 */
	public void bindMouseAction(int mouseCode, GameAction action) {
		mouseActions[mouseCode] = action;
	}
	
	/**
	 * Binds a game action to a particular key on the keyboard.
	 * 
	 * <p> Key codes are defined in the Event class</p>
	 * 
	 */
	public void bindKeyAction(int keyCode, GameAction action) {
		keyActions[keyCode] = action;
	}
	
	/**
	 * Resets the status of all game actions
	 */
	public void resetAllGameActions() {
		for (int i = 0; i < mouseActions.length; i++) {
			mouseActions[i].reset();
		}
		for (int i = 0; i < keyActions.length; i++) {
			keyActions[i].reset();
		}
	}

	/**
	 * Clears the bindings of the specified GameAction
	 */
	public void clearBinding(GameAction action) {
		for (int i = 0; i < mouseActions.length; i++) {
			if (mouseActions[i] == action) {
				mouseActions[i] = null;
			}
		}
		for (int i = 0; i < keyActions.length; i++) {
			if (keyActions[i] == action) {
				keyActions[i] = null;
			}
		}
	}
	
	/**
	 * @return the GameAction specified at the specified keyCode.  Null if invalid
	 */
	public GameAction getKeyAction(int keyCode) {
		if (keyCode <= keyActions.length) {
			return keyActions[keyCode];
		}
		return null;
	}
	
	/**
	 * @return the GameAction specified at the specified mouseCode.  Null if invalid
	 */
	public GameAction getMouseAction(int mouseCode) {
		if (mouseCode <= mouseActions.length) {
			return mouseActions[mouseCode];
		}
		return null;
	}
	
	/**
	 * Handles mouse input other than the scroll wheel
	 */
	public void handleMouseEvent(Event e) {
		GameAction action = getMouseAction(e.getMouseButton());
		if (action != null) {
			action.tap();
		}
		
	}

	/**
	 * Handles mouse scroll wheel input
	 */
	public void handleMouseWheelEvent(Event e) {
		int mouseWheelDelta;
		
		if (e.getMouseWheelDelta() > 0)
			mouseWheelDelta = 1;
		else
			mouseWheelDelta = -1;
		
		GameAction action = getMouseAction(mouseWheelDelta + 4);
		if (action != null) {
			action.tap();
		}
		
	}

	/**
	 * Handles keyboard input
	 */
	public void handleKeyEvent(Event e) {
		GameAction action = getKeyAction(e.getKeyCode());
		if (action != null) {
			if (e.getType().equals(Event.Type.KEY_PRESSED)) {
				action.press();
			}
			else {
				action.release();
			}
		}
		
	}
		
}
