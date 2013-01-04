package com.beamery.sidescroller.entity;

import java.util.Random;

import com.beamery.engine.gfx.Animation;
import com.beamery.engine.gfx.TextureManager;
import com.beamery.engine.util.Color;
import com.beamery.engine.util.Point;

public class Slime extends Enemy {

	public int lowDamage = 19;
	public int highDamage = 27;

	public Slime(Color color, Point position, TextureManager textureManager) {

		super(position);

		defaultMoveSpeed = 2;
		defaultJumpSpeed = 12;
		defaultGravity = 0.5f;
		
		moveSpeed = defaultMoveSpeed;
		jumpSpeed = defaultJumpSpeed;
		gravity = defaultGravity;
		
		Animation standing = new Animation(textureManager.getSheet("slime_still"), 0, 3);
		standing.add(0, 250);
		standing.add(1, 250);
		standing.add(2, 250);
		standing.add(1, 250);

		Animation walking = new Animation(textureManager.getSheet("slime_still"), 0, 3);
		walking.add(0, 250);
		walking.add(1, 250);
		walking.add(2, 250);
		walking.add(1, 250);

		Animation jumping = new Animation(textureManager.getSheet("slime_still"), 0, 1);
		jumping.add(0, 1000);
		addAnimation("standing", standing);
		addAnimation("jumping", jumping);
		addAnimation("walking", walking);
		setAnimation("standing");
		setColor(color);

	}
	
	public void updateMovements(double delta) {
		
		if (state == MOVING_RIGHT) {
			velocity.x = moveSpeed;
			setAnimation("walking");
			setFlipped(false);
		}
		else if (state == MOVING_LEFT) {
			velocity.x = -moveSpeed;
			setAnimation("walking");
			setFlipped(true);
		}
		else if (state == STANDING){
			velocity.x = 0;
			setAnimation("standing");
		}
		
		Random ran = new Random();
		if (ran.nextInt(100) == 0) {
			if (!inAir) {
				velocity.y = jumpSpeed;
				inAir = true;
				jumping = true;
				setAnimation("jumping");
			}
		}
	}
	
	public int getDamage() {
		Random ran = new Random();
		int damage = lowDamage + ran.nextInt(highDamage - lowDamage);
		System.out.println(damage);
		
		return damage;
	}
}
