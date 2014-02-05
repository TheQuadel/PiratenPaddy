package de.majonan.piratenpaddy.valueobjects;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public abstract class Entity {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Texture texture;
	protected boolean highlighted;
	
	public abstract void lookAt();
	
	public Entity(int x, int y, String imagePath){
		try {
			texture =   TextureLoader.getTexture("PNG", new FileInputStream(new File(imagePath)));
			width = texture.getImageWidth();
			height = texture.getImageHeight();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public void draw(){
		texture.bind();
		if(highlighted){
			glColor3d(1.0, 0.5, 0);
			glBegin(GL_QUADS);
			glVertex2i(-2, -2);
			glVertex2i(width+2, -2);
			glVertex2i(width+2, height+2);
			glVertex2i(-2, height+2);
			glEnd();
		}
		glColor3d(1.0, 1.0, 1.0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2i(0, 0);
		glTexCoord2f(1, 0);
		glVertex2i(width, 0);
		glTexCoord2f(1, 1);
		glVertex2i(width, height);
		glTexCoord2f(0, 1);
		glVertex2i(0, height);
		glEnd();
	}
	
	public boolean isAtPosition(int px, int py){
		return (px > x && px < x+width && py > y && py < y+height);
	}
	
	public void destroy(){
		texture.release();
	}
	
}
