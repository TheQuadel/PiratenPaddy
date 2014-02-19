package de.majonan.piratenpaddy.valueobjects;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public abstract class Item extends Entity {
	
	private Texture defaultTexture;
	private Texture collectedTexture;

	public Item(int x, int y, String imagePath, String imagePathCollected) {
		super(x, y, imagePath);
		try {
			defaultTexture =   TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(imagePath));
			collectedTexture =   TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(imagePathCollected));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCollected(boolean col){
		if(col){
			texture = collectedTexture;
		}else{
			texture = defaultTexture;
		}
		width = texture.getImageWidth();
		height = texture.getImageHeight();
	}
	
	public abstract void pull();
	public abstract void push();
	public abstract void open();
	public abstract void close();

}
