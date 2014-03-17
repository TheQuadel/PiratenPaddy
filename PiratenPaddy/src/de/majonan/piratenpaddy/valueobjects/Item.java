package de.majonan.piratenpaddy.valueobjects;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public abstract class Item extends Entity {
	
	private Point nearestPoint;

	public Item(int x, int y, int width, int height) {
		super(x, y, width, height);
//		try {
//			defaultTexture =   TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(imagePath));
//			collectedTexture =   TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(imagePathCollected));
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void setCollected(boolean col){
		if(col){
			this.changeSprite("collected");
		}else{
			this.changeSprite("default");
		}
		
	}
	
	public abstract void pull();
	public abstract void push();
	public abstract void open();
	public abstract void close();
	public abstract List<Message> lookAt();
	

	public Point getNearestPoint() {
		return nearestPoint;
	}

	public void setNearestPoint(Point nearestPoint) {
		this.nearestPoint = nearestPoint;
	}
	


}
