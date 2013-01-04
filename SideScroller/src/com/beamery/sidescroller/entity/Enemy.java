package com.beamery.sidescroller.entity;

import com.beamery.engine.util.Point;

public class Enemy extends Mob {

	public Enemy(Point position) {
		super(position);
	}
	
	public int getDamage() {
		return 0;
	}
}
