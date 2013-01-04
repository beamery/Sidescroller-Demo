package com.beamery.sidescroller.gfx;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import com.beamery.engine.util.Color;
import com.beamery.engine.util.Point;

public class BackgroundLayer {
	
	private Texture texture;
	private Color color;
	
	private Point[] vertexPositions;
	private Point[] uvPositions;
	
	private Point uvTopLeft;
	private Point uvBottomRight;
	
	public BackgroundLayer(Texture texture, Point uvTopLeft, Point uvBottomRight) {
		this.texture = texture;
		this.uvTopLeft = uvTopLeft;
		this.uvBottomRight = uvBottomRight;
		color = new Color(1, 1, 1, 1);
		vertexPositions = new Point[6];
		uvPositions = new Point[6];
		initVertexPositions();
		initUVs();
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Point[] getUVPositions() {
		return uvPositions;
	}
	
	public Point[] getVertexPositions() {
		return vertexPositions;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void scrollVertically(float amount) {
		uvTopLeft.y += amount;
		uvBottomRight.y += amount;
		initUVs();
	}
	
	public void scrollHorizontally(float amount) {
		uvTopLeft.x += amount;
		uvBottomRight.x += amount;
		initUVs();
	}
	
	private void initUVs() {

		// TopLeft, TopRight, BottomLeft
		uvPositions[0] = new Point(uvTopLeft.x, uvTopLeft.y);
		uvPositions[1] = new Point(uvBottomRight.x, uvTopLeft.y);
		uvPositions[2] = new Point(uvTopLeft.x, uvBottomRight.y);

		// TopRight, BottomRight, BottomLeft
		uvPositions[3] = new Point(uvBottomRight.x, uvTopLeft.y);
		uvPositions[4] = new Point(uvBottomRight.x, uvBottomRight.y);
		uvPositions[5] = new Point(uvTopLeft.x, uvBottomRight.y);
	}
	
	private void initVertexPositions() {

		// TopLeft, TopRight, BottomLeft
		vertexPositions[0] = new Point(-5, Display.getHeight() + 5);
		vertexPositions[1] = new Point(Display.getWidth() + 5, Display.getHeight() + 5);
		vertexPositions[2] = new Point(-5, -5);

		// TopRight, BottomRight, BottomLeft
		vertexPositions[3] = new Point(Display.getWidth() + 5, Display.getHeight() + 5);
		vertexPositions[4] = new Point(Display.getWidth() + 5, -5);
		vertexPositions[5] = new Point(-5, -5);
	}

	public void render() {
		
		texture.bind();
		GL11.glColor4f(color.red, color.green, color.blue, color.alpha);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < uvPositions.length; i++) {
			GL11.glTexCoord2f(uvPositions[i].x, uvPositions[i].y);
			GL11.glVertex2f(vertexPositions[i].x, vertexPositions[i].y);
		}
		GL11.glEnd();
		//renderer.drawBackground(this);
	}
}
