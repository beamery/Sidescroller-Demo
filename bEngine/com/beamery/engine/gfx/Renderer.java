package com.beamery.engine.gfx;

import org.lwjgl.opengl.*;

public class Renderer {
	
	Batch batch;
	
	public Renderer() {
		
		batch = new Batch();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void drawSprite(Sprite sprite) {
		
		batch.add(sprite);
	}
	
	public void render() {
		batch.draw();
	}
}
