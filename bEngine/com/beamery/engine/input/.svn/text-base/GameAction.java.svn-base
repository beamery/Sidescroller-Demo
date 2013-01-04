package com.beamery.engine.input;

/**
 * Specifies certain actions in-game.  Bound in the input manager
 * to keys to create keymappings
 * 
 * @author Brian Murray
 *
 */
public class GameAction {

	// Continues pushing the action until the button is released
	public static final int NORMAL = 0;
	
	// Detects initial press and then ignores until released
	public static final int DETECT_INITIAL_PRESS_ONLY = 1;
	
	// Action States
	public static final int STATE_RELEASED = 0;
	public static final int STATE_PRESSED = 1;
	public static final int STATE_WAITING_FOR_RELEASE = 2;
	
	
	private String name;
	private int behavior;
	private int state;
	private boolean pressed;
	private boolean tapped;
	
	public GameAction(String name) {
		this(name, NORMAL);
	}
	
	public GameAction(String name, int behavior) {
		this.name = name;
		this.behavior = behavior;
		state = STATE_RELEASED;
		pressed = false;
		tapped = false;
	}
	
	/**
	 * Press this action
	 */
	public void press() {
		
		if (state != STATE_WAITING_FOR_RELEASE) {
			
			pressed = true;
			
			if (behavior == NORMAL) {
				state = STATE_PRESSED;
			}
			else {
				state = STATE_WAITING_FOR_RELEASE;

			}
		}
	}
	
	/**
	 * Release this action
	 */
	public void release() {
		state = STATE_RELEASED;
	}
	
	/**
	 * Presses the action for one frame and then releases
	 */
	public void tap() {
		press();
		state = STATE_WAITING_FOR_RELEASE;
		tapped = true;
	}
	
	/**
	 * Reset the state of this action to default
	 */
	public void reset() {
		pressed = false;
		state = STATE_RELEASED;
	}
	
	
	/**
	 * @return Whether or not this action is pressed in the current frame
	 */
	public boolean isPressed() {
		
		if (state == STATE_RELEASED) {
			pressed = false;
			return pressed;
		}
		if (state == STATE_WAITING_FOR_RELEASE) {
			boolean tmp = pressed;
			pressed = false;
			
			if (tapped) {
				state = STATE_RELEASED;
				tapped = false;
			}
			return tmp;
		}
		return true;
	}
	
	/**
	 * @return The name of this action
	 */
	public String getName() {
		return name;
	}
	
}
