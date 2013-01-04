package com.beamery.sidescroller.ui;

import com.beamery.engine.core.GameState;
import com.beamery.engine.input.InputManager;

import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.Widget;

public class StateWidget extends Widget {

	private GameState currentState;
	private InputManager inputHandler;

	public StateWidget(InputManager inputHandler) {
		this.inputHandler = inputHandler;
	}

	public void layout() {
		currentState.layoutUI();
	}

	public void setCurrentState(GameState state) {
		currentState = state;
	}

	protected boolean handleEvent(Event evt) {
		
		boolean guiHandled = super.handleEvent(evt);
		// if GUI doesn't handle event, send it to the input handler
		// to be handled
		if (!guiHandled) {

			if (evt.isMouseEventNoWheel() && !evt.getType().equals(Event.Type.MOUSE_ENTERED) 
					&& !evt.getType().equals(Event.Type.MOUSE_MOVED) 
					&& !evt.getType().equals(Event.Type.MOUSE_EXITED)) {
				
				inputHandler.handleMouseEvent(evt);
			}
			else if (evt.isMouseEvent() && !evt.getType().equals(Event.Type.MOUSE_ENTERED) 
					&& !evt.getType().equals(Event.Type.MOUSE_MOVED) 
					&& !evt.getType().equals(Event.Type.MOUSE_EXITED)) {
				
				inputHandler.handleMouseWheelEvent(evt);
			}
			else if (evt.isKeyEvent()) {
				inputHandler.handleKeyEvent(evt);
			}
		}
		return guiHandled;
	}

}

