package com.beamery.engine.gfx;

import org.newdawn.slick.*;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.ResourceLoader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Contains a library of textures, accessible by their ID string.
 * 
 * @author Brian Murray
 *
 */
public class TextureManager {

	private HashMap<String, Texture> textureLibrary;
	private HashMap<String, SpriteSheet> sheetLibrary;

	public TextureManager() {

		textureLibrary = new HashMap<String, Texture>();
		sheetLibrary = new HashMap<String, SpriteSheet>();
	}

	/**
	 * Gets the texture from the texture library specified by the ID string
	 * 
	 * @param texID the texture's ID
	 * 
	 * @return The texture referenced by the ID
	 */
	public Texture get(String texID) {
		return textureLibrary.get(texID);
	}

	/**
	 * Loads a texture from a file path into the texture manager
	 * 
	 * @param format the file format of the file (ie. "PNG", "TGA", etc.)
	 * @param texID the String used to access the texture
	 * @param path the path to the file containing the image
	 */
	public void loadTexture(String format, String texID, String path) {
		try {
			Texture texture = TextureLoader.getTexture(format, 
					ResourceLoader.getResourceAsStream(path), true);
			textureLibrary.put(texID, texture);


		} catch (IOException e) {
			System.out.println("Error loading texture.");
		}
	}

	public void loadSheet(String key, String path, int tw, int th) {

		Image img;
		try {
			img = new Image(path, true);
			SpriteSheet sheet = new SpriteSheet(img, tw, th);

			sheetLibrary.put(key, sheet);
		} catch (SlickException e) {
			System.out.println("Error loading sprite sheet");
		}
	}

	public SpriteSheet getSheet(String sheetID) {
		return sheetLibrary.get(sheetID);
	}

}
