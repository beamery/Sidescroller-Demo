package com.beamery.sidescroller.entity;

import com.beamery.engine.gfx.Animation;
import com.beamery.engine.gfx.TextureManager;
import com.beamery.engine.util.Point;

public class Player extends Mob {

	
	public Player(Point position, TextureManager textureManager) {
		super(position);
		
		defaultMoveSpeed = 3;
		defaultJumpSpeed = 12;
		defaultGravity = 0.5f;
		
		moveSpeed = defaultMoveSpeed;
		jumpSpeed = defaultJumpSpeed;
		gravity = defaultGravity;
		hp = 1000;
		
		Animation standing = new Animation(textureManager.getSheet("stand_still"), 0, 4);
		standing.add(0, 1000);
		standing.add(1, 50);
		standing.add(2, 100);
		standing.add(1, 50);

		Animation walking = new Animation(textureManager.getSheet("walking"), 0, 7);
		walking.add(6, 65);
		walking.add(5, 65);
		walking.add(4, 65);
		walking.add(3, 65);
		walking.add(2, 65);
		walking.add(1, 65);
		walking.add(0, 65);

		Animation jumping = new Animation(textureManager.getSheet("jumping"), 0, 1);
		jumping.add(0, 1000);
		
		addAnimation("standing", standing);
		addAnimation("walking", walking);
		addAnimation("jumping", jumping);
		setAnimation("standing");
	}
	
}
