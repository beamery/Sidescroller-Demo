package com.beamery.engine.gfx;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class Batch {

	protected int maxVertexNumber = 1000;
	protected FloatBuffer vertexPositions;
	protected FloatBuffer uvPositions;
	protected FloatBuffer colorPositions;
	protected int batchSize = 0;

	protected final int vertexDimensions = 2;
	protected final int colorDimensions = 4;
	protected final int uvDimensions = 2;

	protected Sprite currentSprite;

	public Batch() {
		vertexPositions = BufferUtils.createFloatBuffer(vertexDimensions * maxVertexNumber);
		uvPositions = BufferUtils.createFloatBuffer(uvDimensions * maxVertexNumber);
		colorPositions = BufferUtils.createFloatBuffer(colorDimensions * maxVertexNumber);

		currentSprite = new Sprite();
	}

	public void add(Sprite sprite) {
		
		
		if (batchSize + sprite.getVertexPositions().length > maxVertexNumber) {
			draw();
		}

		// if the current animation is different from ones in the batch, 
		// draw the batch and bind the new animation before adding it to the batch
		if (currentSprite.getCurrentAnimation() == null || !currentSprite.getCurrentAnimation().equals(sprite.getCurrentAnimation())) {
			draw();
			currentSprite = sprite;
			currentSprite.getCurrentAnimation().spriteSheet.bind();
		}

		for (int i = 0; i < sprite.getVertexPositions().length; i++) {
			vertexPositions.put(
					sprite.getVertexPositions()[i].x).put(sprite.getVertexPositions()[i].y);

			uvPositions.put(sprite.getUVPositions()[i].x).put(sprite.getUVPositions()[i].y);

			colorPositions
			.put(sprite.getColor().red)
			.put(sprite.getColor().green)
			.put(sprite.getColor().blue)
			.put(sprite.getColor().alpha);
		}
		batchSize += sprite.getVertexPositions().length;

	}

	private void setupPointers() {
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		vertexPositions.flip();
		uvPositions.flip();
		colorPositions.flip();

		glVertexPointer(2, 0, vertexPositions);
		glTexCoordPointer(2, 0, uvPositions);
		glColorPointer(4, 0, colorPositions);
	}

	public void draw() {
		
		// don't draw an empty batch
		if (batchSize == 0) {
			return;
		}

		// Setup OpenGL
		setupPointers();
		
		glDrawArrays(GL_TRIANGLES, 0, batchSize);
		batchSize = 0;
		vertexPositions.clear();
		uvPositions.clear();
		colorPositions.clear();

		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	}

}
