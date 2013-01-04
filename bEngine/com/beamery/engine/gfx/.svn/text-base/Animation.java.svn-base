package com.beamery.engine.gfx;

import org.lwjgl.opengl.*;
import org.newdawn.slick.SpriteSheet;

import com.beamery.engine.util.*;

import java.util.LinkedList;

/**
 * The Animation class holds an image containing different animation
 * states for a sprite.  It is used to render animations for 2D sprites
 * 
 * @author Brian Murray
 *
 */
public class Animation implements Cloneable {

	public SpriteSheet spriteSheet;
	private int row;
	private int frameCount;
	private int startCol;
	private LinkedList<Frame> frames;
	private int currentFrame;
	private int framePointer;
	private double frameTimer;

	private boolean flipped;
	private Point[] vertexPositions;
	private Point[] UVPositions;

	private Point position;
	private float width;
	private float height;
	private Color color;

	public Animation(SpriteSheet sheet, int row, int startCol, int frameCount) {

		this.spriteSheet = sheet;
		this.row = row;
		this.frameCount = frameCount;
		this.startCol = startCol;
		frames = new LinkedList<Frame>();
		currentFrame = 0;
		framePointer = 0;
		frameTimer = 0;
		flipped = false;
		vertexPositions = new Point[6];
		UVPositions = new Point[6];

		position = new Point(Display.getWidth() / 2, Display.getHeight() / 2);
		width = spriteSheet.getWidth() / spriteSheet.getHorizontalCount();
		height = spriteSheet.getHeight() / (float)spriteSheet.getVerticalCount();
		this.color = new Color(1, 1, 1, 1);

		initVertexPositions(
				position, 
				spriteSheet.getWidth() / spriteSheet.getHorizontalCount(), 
				spriteSheet.getHeight() / (float)spriteSheet.getVerticalCount());

		updateUVs(currentFrame);
	}
	
	public Animation(SpriteSheet sheet, int row, int frameCount) {
		this(sheet, row, 0, frameCount);
	}

	/**
	 * Adds a frame to this animation as well as a duration for the 
	 * frame to be rendered.
	 * 
	 * @param frameIndex the index on the sprite sheet of the frame
	 * @param duration the length of time for the frame to be rendered
	 */
	public Animation add(int frameIndex, int duration) {

		frames.add(new Frame(frameIndex, duration));
		return this;
	}

	public Point[] getVertexPositions() {
		return vertexPositions;
	}

	public Point[] getUVPositions() {
		return UVPositions;
	}

	/**
	 * Sets whether the animation is flipped across the x axis or not
	 * 
	 * @param flipped true to flip, false otherwise
	 */
	public void setFlipped(boolean flipped) {
		
		if (flipped != this.flipped) {
			this.flipped = flipped;
			frameTimer = frames.get(currentFrame).duration;
		}
	}

	/**
	 * 
	 * @return whether or not this animation is flipped
	 */
	public boolean isFlipped() {
		return flipped;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	/**
	 * Sets the color of this animation
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the position of this animation
	 */
	public void setPosition(Point position) {
		initVertexPositions(position, width, height);
	}

	private void initVertexPositions(Point position, float width, float height) {

		this.position = position;
		this.width = width;
		this.height = height;

		float halfWidth = width / 2;
		float halfHeight = height / 2;

		// TopLeft, TopRight, BottomLeft
		vertexPositions[0] = new Point(position.x - halfWidth, position.y + halfHeight);
		vertexPositions[1] = new Point(position.x + halfWidth, position.y + halfHeight);
		vertexPositions[2] = new Point(position.x - halfWidth, position.y - halfHeight);

		// TopRight, BottomRight, BottomLeft
		vertexPositions[3] = new Point(position.x + halfWidth, position.y + halfHeight);
		vertexPositions[4] = new Point(position.x + halfWidth, position.y - halfHeight);
		vertexPositions[5] = new Point(position.x - halfWidth, position.y - halfHeight);
	}

	private void updateUVs(int currentFrame) {

		float u = (startCol + currentFrame) / (float)spriteSheet.getHorizontalCount();
		float v = row / (float)spriteSheet.getVerticalCount();
		float uOffset = 1.0f / spriteSheet.getHorizontalCount();
		float vOffset = 1.0f / spriteSheet.getVerticalCount();

		/*
		 * Offsets the texture displayed by one pixel to increase the accuracy of the
		 * displayed images
		 */
		// TopLeft, TopRight, BottomLeft
		UVPositions[0] = new Point(u + 1/(float)spriteSheet.getWidth(), v + vOffset - 1/(float)spriteSheet.getHeight());
		UVPositions[1] = new Point(u + uOffset - 1/(float)spriteSheet.getWidth(), v + vOffset - 1/(float)spriteSheet.getHeight());
		UVPositions[2] = new Point(u + 1/(float)spriteSheet.getWidth(), v + 1/(float)spriteSheet.getHeight());

		// TopRight, BottomRight, BottomLeft
		UVPositions[3] = new Point(u + uOffset - 1/(float)spriteSheet.getWidth(), v + vOffset - 1/(float)spriteSheet.getHeight());
		UVPositions[4] = new Point(u + uOffset - 1/(float)spriteSheet.getWidth(), v + 1/(float)spriteSheet.getHeight());
		UVPositions[5] = new Point(u + 1/(float)spriteSheet.getWidth(), v + 1/(float)spriteSheet.getHeight());
	}

	private void flip(int currentFrame) {

		float u = currentFrame / (float)spriteSheet.getHorizontalCount();
		float v = row / (float)spriteSheet.getVerticalCount();
		float uOffset = 1.0f / spriteSheet.getHorizontalCount();
		float vOffset = 1.0f / spriteSheet.getVerticalCount();

		// TopRight, TopLeft, BottomRight
		UVPositions[0] = new Point(u + uOffset, v + vOffset);
		UVPositions[1] = new Point(u, v + vOffset);
		UVPositions[2] = new Point(u + uOffset, v);

		// TopLeft, BottomLeft, BottomRight
		UVPositions[3] = new Point(u, v + vOffset);
		UVPositions[4] = new Point(u, v);
		UVPositions[5] = new Point(u + uOffset, v);
	}

	public Animation clone() {
		return new Animation(spriteSheet, row, startCol, frameCount);
	}
	
	/**
	 * Updates the animation, changing frames if necessary
	 * 
	 * @param delta ratio between actual frame time and ideal frame time
	 */
	public void update(double delta) {

		frameTimer += delta * (1.0 / 60) * 1000; // adds the update time in milliseconds

		if (frameTimer >= frames.get(currentFrame).duration) {

			frameTimer = 0;
			// wraps around to the first frame after the last one
			framePointer = (framePointer + 1) % frameCount; 
			currentFrame = frames.get(framePointer).index;
			if (flipped) {
				flip(currentFrame);
			}
			else {
				updateUVs(currentFrame);
			}
		}
	}

	/**
	 * Renders this animation.  
	 */
	public void render() {

		spriteSheet.bind();
		GL11.glColor4f(color.red, color.green, color.blue, color.alpha);
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
		{

			for (int i = 0; i < vertexPositions.length; i++) {
				GL11.glTexCoord2f(UVPositions[i].x, UVPositions[i].y);
				GL11.glVertex2f(vertexPositions[i].x, vertexPositions[i].y);
			}

		}
		GL11.glEnd();
	}

	private class Frame {
		int index;
		int duration; // in milliseconds

		private Frame(int index, int duration) {
			this.index = index;
			this.duration = duration;
		}
	}
}
