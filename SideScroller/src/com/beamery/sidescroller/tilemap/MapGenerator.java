package com.beamery.sidescroller.tilemap;

import java.io.*;
import java.util.Random;

public class MapGenerator {

	public MapGenerator() {
		
	}
	
	public boolean generateMap(int x, int y, int seed, String name) {
		PrintWriter out;
		try {
			
			out = new PrintWriter(name);
			
		} catch (FileNotFoundException e) {
			
			System.out.println("Unable to create file " + name);
			e.printStackTrace();
			return false;
		}
		Random ran = new Random(seed);
		char[][] map = new char[x][y];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = ' ';
			}
		}
		int prevHeight = y / 2;
		int currHeight = prevHeight;
		for (int i = 0; i < map.length; i++) {
			
			if (ran.nextInt(10) < 2) {
				// amount to change the height of this col from previous col
				int change = ran.nextInt(5) - 2;
				currHeight = prevHeight + change;
			}
			
			for (int j = 0; j < currHeight; j++) {
				if (j == currHeight - 1)
					map[i][j] = 'g';
				else
					map[i][j] = 'd';
			}
			map[i][map[0].length - 1] = 'n'; // top character empty 
			prevHeight = currHeight;
		}
		// fill in water tiles
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length / 4; j++) {
				if (map[i][j] == ' ') {
					if (j == map[0].length - 1)
						map[i][j] = 'W';
					else
						map[i][j] = 'w';
				}
			}
		}
		
		for (int j = map[0].length - 1; j >= 0; j--) {
			for (int i = 0; i < map.length; i++) {
				out.print(map[i][j]);
			}
			out.println();
		}
		
		out.close();
		return true;
	}
}
