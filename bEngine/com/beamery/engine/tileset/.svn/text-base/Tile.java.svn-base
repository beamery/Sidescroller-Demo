package com.beamery.engine.tileset;

import org.newdawn.slick.opengl.Texture;

import com.beamery.engine.util.Point;

public class Tile {
		
		// UVs
		public Point topLeft;
		public Point topRight;
		public Point bottomRight;
		public Point bottomLeft;
		
		private Texture texture;
		private float width;
		private float height;
		
		/**
		 * Constructs a Tile with the given UV coordinates used to map it to a texture
		 * for rendering.
		 */
		Tile(Texture texture, float tileWidth, float tileHeight, Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) {
			
			this.texture = texture;
			this.width = tileWidth;
			this.height = tileHeight;
			
			this.topLeft = topLeft;
			this.topRight = topRight;
			this.bottomLeft = bottomLeft;
			this.bottomRight = bottomRight;
		}
		
		/**
		 * @return The image associated with this tile
		 */
		public Texture getTexture() {
			return texture;
		}
		
		/**
		 * @return The width of this tile in pixels
		 */
		public float getWidth() {
			return width;
		}
		
		
		/**
		 * @return The height of this tile in pixels
		 */
		public float getHeight() {
			return height;
		}
	}

