package com.beamery.engine.core;

import java.util.HashMap;

/**
 * The StateManager holds a collection of all of the game states, 
 * allowing the user to switch between them as necessary.  
 * <p>Used in the game loop for updating and rendering the current state<p>
 * 
 * @author Brian Murray
 *
 */
public class StateManager {

	private HashMap<String, GameState> stateLibrary;
	private GameState currentState;
	
	public StateManager() {
		stateLibrary = new HashMap<String, GameState>();
		currentState = null;
	}
	
	/**
	 * Updates the state manager's current state
	 * 
	 * @param delta A multiple of the expected time, used to 
	 * 			create equally timed animations on all systems
	 */
	public void update(double delta) {
		
		currentState.update(delta);
	}
	
	/**
	 * Does the OpenGL rendering of the current state
	 */
	public void render() {
		
		currentState.render();
	}
	
	/**
	 * Adds a state to the state manager's library
	 * 
	 * @param stateID The string used to access this state
	 * @param state The state to be added
	 */
	public void add(String stateID, GameState state) {
		
		stateLibrary.put(stateID, state);
	}
	
	/**
	 * Changes the current state of this state manager
	 * 
	 * @param stateID The state to be changed to
	 */
	public void changeState(String stateID) {
		
		currentState = stateLibrary.get(stateID);
		currentState.init();
	}
	
	/**
	 * @return the current state
	 */
	public GameState getCurrent() {
		return currentState;
	}
}
