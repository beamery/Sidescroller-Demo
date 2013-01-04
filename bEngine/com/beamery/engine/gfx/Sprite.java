package com.beamery.engine.gfx;

import java.awt.Rectangle;
import java.util.HashMap;

import com.beamery.engine.util.*;

import org.lwjgl.opengl.*;

public class Sprite {

	protected HashMap<String, Animation> animations;
	protected Animation currentAnim;
	protected Point screenPosition;
	protected Point absPosition;
	protected Color color;
	protected boolean flipped;
	
	public Sprite() {
		animations = new HashMap<String, Animation>();
		screenPosition = new Point(Display.getWidth() / 2, Display.getHeight() / 2);
		absPosition = new Point(Display.getWidth() / 2, Display.getHeight() / 2);
		color = new Color(1, 1, 1, 1);
		flipped = false;
	}
	
	/**
	 * Creates a Sprite with an animation and position
	 * Animation is stored with name "default"
	 * @param anim
	 * @param position
	 */
	public Sprite (Animation anim, Point position) {
		this();
		addAnimation("default", anim);
		setAnimation("default");
		screenPosition = position;
		absPosition = position;
	}
	
	/**
	 * @return The position of the center of this sprite
	 */
	public Point getScreenPosition() {
		return this.screenPosition;
	}
	
	/**
	 * @return The position of this sprite in the game world
	 */
	public Point getAbsPosition() {
		return this.absPosition;
	}
	
	/**
	 * @return the UV positions of this sprite
	 */
	public Point[] getVertexPositions() {
		return currentAnim.getVertexPositions();
	}
	
	/**
	 * @return the vertex positions of this sprite
	 */
	public Point[] getUVPositions() {
		return currentAnim.getUVPositions();
	}
	
	/**
	 * @return The color of this sprite
	 */
	public Color getColor() {
		return this.color;
	}
	
	public float getWidth() {
		return currentAnim.getWidth();
	}
	
	public float getHeight() {
		return currentAnim.getHeight();
	}

	/**
	 * @return True if flipped, false otherwise
	 */
	public boolean isFlipped() {
		return currentAnim.isFlipped();
	}
	
	/**
	 * Sets the flipped state of this sprite's current animation
	 */
	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
		currentAnim.setFlipped(flipped);
	}
	
	/**
	 * Sets the center position of this sprite's current animation
	 */
	public void setScreenPosition(Point position) {
		this.screenPosition = position;
		currentAnim.setPosition(position);
	}
	
	public void setAbsPosition(Point position) {
		this.absPosition = position;
	}
	
	/**
	 * Sets the color of this sprite's current animation
	 */
	public void setColor(Color color) {
		this.color = color;
		currentAnim.setColor(color);
	}
	
	/**
	 * Sets the current animation and updates its color, position, and flipped status
	 * 
	 * @param animID The string used to access the animation from this sprite's library
	 */
	public void setAnimation(String animID) {
		currentAnim = animations.get(animID);
		currentAnim.setColor(color);
		currentAnim.setPosition(screenPosition);
		currentAnim.setFlipped(flipped);
	}
	
	/**
	 * Adds an animation to this sprite's library
	 * 
	 * @param id The string used to access the animation
	 * @param anim The animation to be added
	 */
	public void addAnimation(String id, Animation anim) {
		animations.put(id, anim);
	}
	
	public Animation getCurrentAnimation() {
		return currentAnim;
	}
	
	public void update(double delta) {
		
		this.currentAnim.update(delta);
	}
	
public Rectangle getBounds() {
		
		Rectangle rect = new Rectangle(
				(int)(screenPosition.x - getWidth() / 2), 
				(int)(screenPosition.y - getHeight() / 2), 
				(int)getWidth(), 
				(int)getHeight());
		
		return rect;
	}
	
	public void drawBounds() {
		Rectangle bounds = getBounds();
		
		GL11.glColor3f(1, 0, 0);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		{
			GL11.glVertex2d(bounds.getMinX(), bounds.getMinY());
			GL11.glVertex2d(bounds.getMinX(), bounds.getMaxY());
			GL11.glVertex2d(bounds.getMaxX(), bounds.getMaxY());
			GL11.glVertex2d(bounds.getMaxX(), bounds.getMinY());
		}
		GL11.glEnd();
	}
		
}
