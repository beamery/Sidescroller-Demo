package com.beamery.sidescroller;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.SpriteSheet;

import com.beamery.engine.gfx.Animation;
import com.beamery.engine.gfx.Renderer;
import com.beamery.engine.gfx.TextureManager;
import com.beamery.engine.input.GameAction;
import com.beamery.engine.input.InputManager;
import com.beamery.engine.util.Color;
import com.beamery.engine.util.Point;
import com.beamery.sidescroller.entity.*;
import com.beamery.sidescroller.tilemap.TileMap;

import de.matthiasmann.twl.Event;

public class Level {

	private static final float DEFAULT_MOVE_SPEED = 3;
	private static final float DEFAULT_JUMP_SPEED = 12;
	private static final float DEFAULT_GRAVITY = 0.5f;

	private static float moveSpeed = DEFAULT_MOVE_SPEED;
	private static float jumpSpeed = DEFAULT_JUMP_SPEED;
	private static float gravity = DEFAULT_GRAVITY;

	private static final int DOWN = 0;
	private static final int LEFT = 1;
	private static final int RIGHT = 2;
	private static final int UP = 3;

	private InputManager inputManager;

	private TileMap tilemap;
	private Point offset;
	private Player player;
	private ArrayList<Enemy> enemies;

	public boolean gameOver = false;

	GameAction jump;
	GameAction moveLeft;
	GameAction moveDown;
	GameAction moveRight;

	Animation boundary;

	public Level(TextureManager textureManager, InputManager inputManager, String path) {

		this.inputManager = inputManager;

		offset = new Point(0, 0);

		initTileMap(textureManager, path);
		player = new Player(new Point(tilemap.getWidth() / 2, tilemap.getHeight() / 2), textureManager);
		offset = player.getAbsPosition();
		initMobs(textureManager);
		initActions();
	}

	private void initMobs(TextureManager textureManager) {

		enemies = new ArrayList<Enemy>();
		Random ran = new Random();

		for (int i = 0; i < 50; i++) {
			Color color = new Color(
					1 - (float)Math.pow(ran.nextFloat(), 3), 
					1 - (float)Math.pow(ran.nextFloat(), 3),
					1 - (float)Math.pow(ran.nextFloat(), 3), 
					1 - (float)Math.pow(ran.nextFloat(), 5));

			Slime slime = new Slime(color, new Point(ran.nextInt((int)tilemap.getWidth()), 
					400 + ran.nextInt((int)tilemap.getHeight())), textureManager);
			enemies.add(slime);
		}
	}

	private void initTileMap(TextureManager textureManager, String path) {

		tilemap = new TileMap();
		SpriteSheet tileSet = textureManager.getSheet("tileSet");

		tilemap.addTile(new Animation(tileSet, 1, 0, 1).add(0, 1000), TileMap.BOUNDARY);
		tilemap.addTile(new Animation(tileSet, 3, 0, 1).add(0, 1000), TileMap.GRASS_LEFT);
		tilemap.addTile(new Animation(tileSet, 3, 1, 1).add(0, 1000), TileMap.GRASS);
		tilemap.addTile(new Animation(tileSet, 3, 2, 1).add(0, 1000), TileMap.GRASS_RIGHT);
		tilemap.addTile(new Animation(tileSet, 2, 0, 1).add(0, 1000), TileMap.DIRT_LEFT);
		tilemap.addTile(new Animation(tileSet, 2, 1, 1).add(0, 1000), TileMap.DIRT);
		tilemap.addTile(new Animation(tileSet, 2, 2, 1).add(0, 1000), TileMap.DIRT_RIGHT);
		tilemap.addTile(new Animation(tileSet, 3, 3, 1).add(0, 1000), TileMap.WATER_TOP);
		tilemap.addTile(new Animation(tileSet, 2, 3, 1).add(0, 1000), TileMap.WATER);

		tilemap.loadFromFile(path);
	}

	private void initActions() {
		jump = new GameAction("Move Up", GameAction.DETECT_INITIAL_PRESS_ONLY);
		moveLeft = new GameAction("Move Left");
		moveDown = new GameAction("Move Down");
		moveRight = new GameAction("Move Right");

		inputManager.bindKeyAction(Event.KEY_SPACE, jump);
		inputManager.bindKeyAction(Event.KEY_A, moveLeft);
		inputManager.bindKeyAction(Event.KEY_S, moveDown);
		inputManager.bindKeyAction(Event.KEY_D, moveRight);
	}

	/**
	 *
	 * Scrolls the view of this level by the specified x and y amounts
	 *
	 * @param x The amount to scroll in the x direction
	 * @param y The amount to scroll in the y direction
	 * @return true if scrolling successful, false otherwise
	 **/
	public boolean scroll(float x, float y) {
		if (x < 0) {
			offset.x = Math.max(0, offset.x + x);
			if (offset.x == 0) {
				return false;
			}
		}
		if (x > 0) {
			offset.x = Math.min(tilemap.getWidth() - Display.getWidth(), offset.x + x);
			if (offset.x == tilemap.getWidth() - Display.getWidth()) {
				return false;
			}
		}
		if (y < 0) {
			offset.y = Math.max(0, offset.y + y);
			if (offset.y == 0) {
				return false;
			}
		}
		if (y > 0) {
			offset.y = Math.min(tilemap.getHeight() - Display.getHeight(), offset.y + y);
			if (offset.y == tilemap.getHeight() - Display.getHeight()) {
				return false;
			}
		}
		return true;
	}

	public void update(double delta) {

		updatePlayer(delta);

		for(Mob mob : enemies) {
			updateMob(mob, delta);
		}

		for (int i = 0; i < tilemap.grid.length; i++) {
			for (int j = 0; j < tilemap.grid[0].length; j++) {

				Block tmp = tilemap.grid[i][j];
				if (tmp != null) {
					tmp.setScreenPosition(tmp.getAbsPosition().subtract(offset));
				}
			}
		}
	}

	private void updateMob(Mob mob, double delta) {

		mob.moveTimer += (1.0/60) * delta;
		Random ran = new Random();
		boolean tick = false;
		int oldState = -1;

		// update mob movement state
		if (mob.moveTimer > 1) {
			tick = true;
			oldState = mob.state;
			mob.state = ran.nextInt(3);
		}

		if (tick && oldState != mob.state) {
			mob.moveTimer = 0;
		}

		mob.updateMovements(delta);

		// move absPosition
		mob.move((float)(mob.velocity.x * delta), (float)(mob.velocity.y * delta));	
		mob.setScreenPosition(mob.getAbsPosition().subtract(offset));
		if (mob.inAir) {
			mob.velocity.y = Math.max((float)(mob.velocity.y - mob.gravity * delta), -mob.jumpSpeed * 2);
			mob.setAnimation("jumping");
		}
		updateTileCollisions(mob);
		mob.update(delta);
	}

	private void updatePlayer(double delta) {

		boolean scrolledX = false; // indicator variables in place of an if-else chain
		boolean scrolledY = false;

		// update velocities
		if (player.canMove) {
			if (jump.isPressed() && canMove(UP)) {
				if (!player.jumping) {
					player.velocity.y = player.jumpSpeed;
					player.inAir = true;
					player.jumping = true;
					player.setAnimation("jumping");
				}	
			}
			if (moveRight.isPressed() && moveLeft.isPressed()) {
				player.velocity.x = 0;
				player.setAnimation("standing");
			}
			else if (moveRight.isPressed() && canMove(RIGHT)) {
				player.setFlipped(false);
				player.velocity.x = player.moveSpeed;
				player.setAnimation("walking");

			}
			else if (moveLeft.isPressed() && canMove(LEFT)) {
				player.setFlipped(true);
				player.velocity.x = -player.moveSpeed;
				player.setAnimation("walking");
			}
			else {
				player.velocity.x = 0;
				player.setAnimation("standing");
			}
		}
		// move absPosition
		player.move((float)(player.velocity.x * delta), (float)(player.velocity.y * delta));	

		// update screenPositions
		if (player.getScreenPosition().y > Display.getHeight() * 3/4 && player.velocity.y > 0) {

			scrolledY = true;
			if (scroll(0, (float)(player.velocity.y * delta)) == false) {
				player.moveOnScreen(0, (float)(player.velocity.y * delta));
			}
		}
		if (player.getScreenPosition().x > Display.getWidth() * 3/4 && player.velocity.x > 0) {

			scrolledX = true;
			if (scroll((float)(player.velocity.x * delta), 0) == false) {
				player.moveOnScreen((float)(player.velocity.x * delta), 0);
			}
		}
		if (player.getScreenPosition().y < Display.getHeight() * 1/4 && player.velocity.y < 0) {

			scrolledY = true;
			if (scroll(0, (float)(player.velocity.y * delta)) == false) {
				player.moveOnScreen(0, (float)(player.velocity.y * delta));
			}
		}
		if (player.getScreenPosition().x < Display.getWidth() * 1/4 && player.velocity.x < 0) {

			scrolledX = true;
			if (scroll((float)(player.velocity.x * delta), 0) == false) {
				player.moveOnScreen((float)(player.velocity.x * delta), 0);
			}
		}
		// if the player hasn't scrolled the screen, move them within the screen
		if (!scrolledX) {
			player.moveOnScreen((float)(player.velocity.x * delta), 0);
		}
		if (!scrolledY) {
			player.moveOnScreen(0, (float)(player.velocity.y * delta));
		}

		//check for collisions
		updateTileCollisions(player);
		if (!player.isInvulnerable && !player.isDead) {
			updateMobCollisions();
		}

		if (player.inAir) {
			player.velocity.y = Math.max((float)(player.velocity.y - player.gravity * delta), -player.jumpSpeed);
			player.setAnimation("jumping");
		}
		if (!canMove(DOWN)) {
			player.velocity.y = 0;
			player.inAir = false;
			player.jumping = false;
		}
		if (player.knockedBack) {
			player.knockbackTimer -= (1.0/60) * delta;
			if (player.velocity.x > 0) {
				player.velocity.x -= (1.0/60) * delta * player.moveSpeed * 2;
			}
			else {
				player.velocity.x += (1.0/60) * delta * player.moveSpeed * 2;
			}
			if (player.knockbackTimer <= 0) {
				player.canMove = true;
				player.knockedBack = false;
				// Player dead
				if (player.hp <= 0) {
					player.isDead = true;
					player.deathTimer = 2;
					player.canMove = false;
				}
			}
		}
		if (player.isInvulnerable) {
			player.invulnerabilityTimer -= (1.0/60) * delta;
			if ((int)(player.invulnerabilityTimer * 20) % 2 == 0) {
				player.setColor(new Color(1, 0.5f, 0.5f, 1));
			}
			else {
				player.setColor(new Color(1, 1, 1, 1));
			}
			if (player.invulnerabilityTimer <= 0) {
				player.setColor(new Color(1, 1, 1, 1));
				player.isInvulnerable = false;
			}
		}
		if (player.isDead) {
			player.deathTimer -= (1.0/60) * delta;
			float playerColor = (float)(player.deathTimer / 2);
			player.setColor(new Color(playerColor, playerColor, playerColor, 1));
			if (player.deathTimer <= 0) {
				gameOver = true;
			}
		}
		player.update(delta);
	}

	private void updateTileCollisions(Mob mob) {

		// update mob position relative to the edges of the screen and map
		if (mob instanceof Player) {
			mob.setScreenPosition(
					new Point(
							Math.max(mob.getScreenPosition().x, mob.getWidth() / 2), 
							Math.max(mob.getScreenPosition().y, mob.getHeight() / 2)));
			mob.setScreenPosition(
					new Point(
							Math.min(mob.getScreenPosition().x, Display.getWidth() - mob.getWidth() / 2), 
							Math.min(mob.getScreenPosition().y, Display.getHeight() - mob.getHeight() / 2)));

			mob.setAbsPosition(
					new Point(
							Math.max(mob.getAbsPosition().x, mob.getWidth() / 2), 
							Math.max(mob.getAbsPosition().y, mob.getHeight() / 2)));
			mob.setAbsPosition(
					new Point(
							Math.min(mob.getAbsPosition().x, tilemap.getWidth() - mob.getWidth() / 2), 
							Math.min(mob.getAbsPosition().y, tilemap.getHeight()) - mob.getHeight() / 2));
		}

		Point center = mob.getScreenPosition().add(offset);

		boolean hitGround = false; // used to check for vertical collisions and determine 
		// whether or not the mob is in the air
		boolean hitWater = false;

		//update mob collisions with tiles
		for (int i = 0; i < tilemap.grid.length; i++) {
			for (int j = 0; j < tilemap.grid[0].length; j++) {

				if (tilemap.grid[i][j] != null &&
						tilemap.grid[i][j].getAbsPosition().x > center.x - (250) &&
						tilemap.grid[i][j].getAbsPosition().x < center.x + (250) &&
						tilemap.grid[i][j].getAbsPosition().y > center.y - (250) &&
						tilemap.grid[i][j].getAbsPosition().y < center.y + (250)) {

					Rectangle mobRect = mob.getBounds();
					Rectangle tileRect = tilemap.grid[i][j].getBounds();

					// if mob intersects a tile
					if (mobRect.intersects(tileRect)) {

						// get the intersection rectangle
						Rectangle rect = mobRect.intersection(tileRect);

						// if the mob intersects a water tile, adjust its movement accordingly
						if (tilemap.grid[i][j].type == TileMap.WATER) { 

							mob.gravity = mob.defaultGravity * 0.25f;
							mob.jumpSpeed = mob.defaultJumpSpeed * 0.35f;
							mob.moveSpeed = mob.defaultMoveSpeed * 0.7f;

							if (!mob.inWater) { 
								mob.velocity.x *= 0.2;
								mob.velocity.y *= 0.2;
								mob.inWater = true;		
							}
							hitWater = true;
							mob.jumping = false;

						}
						else {

							// if intersection is vertical (and underneath player)
							if (rect.getHeight() < rect.getWidth()) {

								// make sure the intersection isn't really horizontal
								if (j + 1 < tilemap.grid[0].length && 
										(tilemap.grid[i][j+1] == null || 
										!mobRect.intersects(tilemap.grid[i][j+1].getBounds()))) {

									// only when the mob is falling
									if (mob.velocity.y <= 0) {
										mob.velocity.y = 0;
										mob.setAbsPosition(new Point(
												mob.getAbsPosition().x, 
												(float)(mob.getAbsPosition().y + rect.getHeight() - 1)));
										mob.setScreenPosition(new Point(
												mob.getScreenPosition().x, 
												(float)(mob.getScreenPosition().y + rect.getHeight() - 1)));
										mob.inAir = false;	
										mob.jumping = false;
									}
								}
							}
							// if intersection is horizontal
							else {
								mob.velocity.x = 0;

								if (mobRect.getCenterX() < tileRect.getCenterX()) {
									mob.setAbsPosition(new Point(
											(float)(mob.getAbsPosition().x - rect.getWidth()), 
											mob.getAbsPosition().y));
									mob.setScreenPosition(new Point(
											(float)(mob.getScreenPosition().x - rect.getWidth()), 
											mob.getScreenPosition().y));
								}
								else {
									mob.setAbsPosition(new Point(
											(float)(mob.getAbsPosition().x + rect.getWidth()), 
											mob.getAbsPosition().y));
									mob.setScreenPosition(new Point(
											(float)(mob.getScreenPosition().x + rect.getWidth()), 
											mob.getScreenPosition().y));
								}
							}

							float xToCenter = Math.abs((float)(mobRect.getCenterX() - tileRect.getCenterX()));
							float yToCenter = Math.abs((float)(mobRect.getCenterY() - tileRect.getCenterY())); 

							// Check under the mob to see if it touches a tile.  If so, hitGround = true
							if (yToCenter <= (mobRect.getHeight() / 2 + tileRect.getHeight() / 2) && 
									xToCenter <= (mobRect.getWidth() / 2 + tileRect.getWidth() / 2))
								hitGround = true;						
						}

					}
				}
			}
		}
		// if mob doesn't intersect a tile vertically, they are in the air
		if (!hitGround) {
			mob.inAir = true;
		}
		// if the mob is not in the water, restore its default movement parameters
		if (!hitWater) {
			mob.gravity = mob.defaultGravity;
			mob.moveSpeed = mob.defaultMoveSpeed;
			mob.jumpSpeed = mob.defaultJumpSpeed;
			mob.inWater = false;
		}
	}

	private void updateMobCollisions() {

		Rectangle playerBounds = player.getBounds();
		for (Enemy mob : enemies) {
			Rectangle mobBounds = mob.getBounds();

			if (mobBounds.intersectsLine(
					playerBounds.getCenterX(), 
					playerBounds.getMinY(), 
					playerBounds.getCenterX(), 
					playerBounds.getMaxY())) {

				player.knockedBack = true;
				player.canMove = false;
				player.isInvulnerable = true;
				player.knockbackTimer = 0.5;
				player.invulnerabilityTimer = 1.5;
				if (mobBounds.getCenterX() > playerBounds.getCenterX()) {
					player.velocity.x = -player.moveSpeed;
				}
				else {
					player.velocity.x = player.moveSpeed;
				}
				player.velocity.y = player.jumpSpeed / 2;
				player.hp -= mob.getDamage();

				break;
			}
		}
	}
	private boolean canMove(int direction) {
		switch (direction) {
		case UP:
			if (player.getScreenPosition().y < Display.getHeight() - player.getHeight() / 2)
				return true;
			break;
		case DOWN:
			if (player.getScreenPosition().y > player.getHeight() / 2)
				return true;
			break;
		case LEFT:
			if (player.getScreenPosition().x > player.getWidth() / 2) 
				return true;
			break;
		case RIGHT:
			if (player.getScreenPosition().x < Display.getWidth() - player.getWidth() /2)
				return true;
		}
		return false;
	}

	public void render(Renderer renderer) {

		for(Mob mob : enemies) {
			renderer.drawSprite(mob);
			mob.drawBounds();
		}
		renderer.drawSprite(player);
		player.drawBounds();

		Point center = new Point(Display.getWidth() / 2 + offset.x, Display.getHeight() / 2 + offset.y);

		for (int i = 0; i < tilemap.grid.length; i++) {
			for (int j = 0; j < tilemap.grid[0].length; j++) {

				Block tmp = tilemap.grid[i][j];

				if (tmp != null &&
						tmp.getAbsPosition().x > center.x - (Display.getWidth() / 2 + 100) &&
						tmp.getAbsPosition().x < center.x + (Display.getWidth() / 2 + 100) &&
						tmp.getAbsPosition().y > center.y - (Display.getWidth() / 2 + 100) &&
						tmp.getAbsPosition().y < center.y + (Display.getWidth() / 2 + 100)) {

					renderer.drawSprite(tmp);
					//tmp.drawBounds();
				}
			}
		}
		/*player.drawBounds();
		for (Mob mob : enemies) {
			mob.drawBounds();
		}*/
	}
}
