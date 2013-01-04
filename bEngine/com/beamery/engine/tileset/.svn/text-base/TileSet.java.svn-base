package com.beamery.engine.tileset;

import org.newdawn.slick.opengl.Texture;

import com.beamery.engine.util.Point;

public class TileSet {

	private Texture texture;
	private float tileWidth;
	private float tileHeight;
	private Tile[][] tiles;
	
	/**
	 * Constructs a tileset from the given image.  The dimensions of the image
	 * must be multiples of tileWidth and tileHeight to be constructed properly
	 * @param image The image to be converted into a tileset
	 * @param tileWidth The width of an individual tile
	 * @param tileHeight The height of an individual tile
	 */
	public TileSet(Texture texture, int numTilesX, int numTilesY) {
		
		this.texture = texture;
		tileWidth = texture.getTextureWidth() / numTilesX;
		tileHeight = texture.getTextureHeight() / numTilesY;
		
		initTiles(numTilesX, numTilesY);
	}
	
	private void initTiles(int numTilesX, int numTilesY) {
		
		tiles = new Tile[numTilesX][numTilesY];
		float scaledTileWidth = tileWidth / texture.getTextureWidth();
		float scaledTileHeight = tileHeight / texture.getTextureHeight();
		
		
		// Go through each tile and set their UVs
		for (int i = 0; i < numTilesX; i++) {
			for (int j = 0; j < numTilesY; j++) {
				
				Point topLeft = new Point(i * scaledTileWidth + 1 / (tileWidth * numTilesX), (numTilesY - j) * scaledTileHeight - 1 / (tileHeight * numTilesY));
				Point topRight = new Point(topLeft.x + scaledTileWidth - 2 / (tileWidth * numTilesX), topLeft.y);
				Point bottomRight = new Point(topRight.x, topLeft.y - scaledTileHeight + 2 / (tileHeight * numTilesY));
				Point bottomLeft = new Point(topLeft.x, bottomRight.y);
				
				tiles[i][j] = new Tile(texture, tileWidth, tileHeight, topLeft, topRight, bottomLeft, bottomRight);
			}
		}
	}

	/**
	 * @param x The x coordinate of the tile on the grid
	 * @param y The y coordinate of the tile on the grid
	 * @return  The tile at the grid location given by the x and y parameters.
	 */
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	/**
	 * @return The image associated with this tileset
	 */
	public Texture getTexture() {
		return this.texture;
	}
		
}
