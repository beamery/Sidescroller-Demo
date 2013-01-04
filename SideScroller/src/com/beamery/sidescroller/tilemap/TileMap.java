package com.beamery.sidescroller.tilemap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.beamery.engine.gfx.Animation;
import com.beamery.engine.tileset.Tile;
import com.beamery.engine.util.Point;
import com.beamery.sidescroller.entity.Block;

public class TileMap {
	
	public static final int BOUNDARY = 0;
	public static final int GRASS_LEFT = 1;	
	public static final int GRASS = 2;
	public static final int GRASS_RIGHT = 3;
	public static final int DIRT_LEFT = 4;
	public static final int DIRT = 5;
	public static final int DIRT_RIGHT = 6;
	public static final int WATER = 7;
	public static final int WATER_TOP = 8;
	
	private Animation[] tiles;
	private float width;
	private float height;
	public Block[][] grid;
	
	public TileMap() {
		tiles = new Animation[100];
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void loadFromFile(String path) {
		try {
			
			Scanner in = new Scanner(new File(path));
			ArrayList<String> lines = getLines(in);
			int width = getLength(lines);
			int height = lines.size();
			grid = new Block[width][height];
			
			this.width = width * tiles[GRASS].getWidth();
			this.height = height * tiles[GRASS].getHeight();
			
			
			for (int j = 0; j < lines.size(); j++) {
				for (int i = 0; i < lines.get(j).length(); i++) {
					String line = lines.get(j);
					
					Point position = new Point(
							tiles[GRASS].getWidth() / 2 + i * tiles[GRASS].getWidth(), 
							((height - j) * tiles[GRASS].getHeight()) - tiles[GRASS].getHeight() / 2);
					
					switch (line.charAt(i)) {
					
					case 'g':
						grid[i][j] = new Block(tiles[GRASS].clone(), position, GRASS); break;
					case 'f':
						grid[i][j] = new Block(tiles[GRASS_LEFT].clone(), position, GRASS); break;
					case 'h':
						grid[i][j] = new Block(tiles[GRASS_RIGHT].clone(), position, GRASS); break;
					case 'd':
						grid[i][j] = new Block(tiles[DIRT].clone(), position, DIRT); break;
					case 's':
						grid[i][j] = new Block(tiles[DIRT_LEFT].clone(), position, DIRT); break;
					case 'D':
						grid[i][j] = new Block(tiles[DIRT_RIGHT].clone(), position, DIRT); break;
					case 'w':
						grid[i][j] = new Block(tiles[WATER].clone(), position, WATER); break;
					case 'W':
						grid[i][j] = new Block(tiles[WATER_TOP].clone(), position, WATER); break;
					case 'b':
						grid[i][j] = new Block(tiles[BOUNDARY].clone(), position, BOUNDARY); break;
					default:
						grid[i][j] = null; break;
					}
				}
			}
			
		} catch (IOException e) {
			System.out.println("Error loading tilemap.");
		}
	}

	private ArrayList<String> getLines(Scanner in) {
		ArrayList<String> lines = new ArrayList<String>();
		String tmp;
		do {
		tmp = in.nextLine();
		} while (tmp.charAt(0) == '#');
		
		lines.add(tmp);
		while (in.hasNextLine()) {
			lines.add(in.nextLine());
		}
		return lines;
	}
	
	private int getLength(ArrayList<String> lines) {
		int max = 0;
		for (String line : lines) {
			if (line.length() > max)
				max = line.length();
		}
		return max;
	}
	
	/**
	 * Adds a tile to this tilemap's library of tiles.  Indices are named as static
	 * variables in the TileMap class
	 * 
	 * @param tile The tile to add to the library
	 * @param id The id by which to add it
	 */
	public void addTile(Animation tile, int id) {
		tiles[id] = tile;
	}
}
