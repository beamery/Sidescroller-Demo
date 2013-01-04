package com.beamery.engine.core;

public interface GameState {

	public void init();
	public void layoutUI();
	public void update(double delta);
	public void render();
}
