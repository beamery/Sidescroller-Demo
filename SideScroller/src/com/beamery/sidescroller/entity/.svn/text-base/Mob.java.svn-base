package com.beamery.sidescroller.entity;

import java.awt.Rectangle;

import com.beamery.engine.gfx.Sprite;
import com.beamery.engine.util.Point;

import org.lwjgl.opengl.*;

public class Mob extends Sprite implements Entity {
	
	public static final int STANDING = 0;
	public static final int MOVING_LEFT = 1;
	public static final int MOVING_RIGHT = 2;
	public static final int JUMPING = 3;
	
	public float defaultMoveSpeed;
	public float defaultJumpSpeed;
	public float defaultGravity;
	
	public Point velocity;
	public boolean inAir;
	public boolean inWater = false;
	public boolean jumping;
	public boolean knockedBack = false;
	public boolean isInvulnerable = false;
	public boolean canMove = true;
	public boolean isDead = false;
	public float moveSpeed;
	public float jumpSpeed;
	public float gravity;
	public int hp;
	public double moveTimer = 0;
	public double knockbackTimer = 0;
	public double invulnerabilityTimer = 0;
	public double deathTimer = 0;
	public int state;
	
	public Mob() {
		
		super();	
		
		velocity = new Point(0, 0);

	}	
	
	public Mob(Point position) {
		this();
		absPosition = position;
		screenPosition = position;
	}

	public void move(float x, float y) {
	
		setAbsPosition(new Point(absPosition.x + x, absPosition.y + y));
	}

	public void moveOnScreen(float x, float y) {
		
		setScreenPosition(new Point(screenPosition.x + x, screenPosition.y + y));
	}

	public void updateMovements(double delta) {
		
	}
}
