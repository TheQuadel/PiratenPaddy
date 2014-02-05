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
	public void draw(){
		texture.bind();
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
	
	public void destroy(){
		texture.release();
	}
	
}
