package de.majonan.piratenpaddy.valueobjects;

import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Sprite {
	
	private List<Texture> frames;
	private List<Long> deltas;
	
	private int currentFrame = -1;
	private long lastFrameTime = 0;
	
	private int width, height, anchorX, anchorY;
	
	public Sprite(int width, int height, int anchorX, int anchorY){
		this.width = width;
		this.height = height;
		this.frames = new Vector<Texture>();
		this.deltas = new Vector<Long>();
		this.lastFrameTime = System.currentTimeMillis();
	}
	
	public Sprite addFrame(Texture texture, long delta){
		frames.add(texture);
		deltas.add(delta);
		if(currentFrame == -1){
			currentFrame = 0;
		}
		return this;
	}

	public Sprite addFrame(String imagePath, long delta){
		try {
			Texture tex = TextureLoader.getTexture("PNG", new FileInputStream(new File(imagePath)));
			addFrame(tex, delta);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void draw(int x, int y){
		if(currentFrame == -1 || frames.size() == 0){
			System.err.println("SpriteError: Please add frames before drawing!");
			return;
		}
		if(lastFrameTime + deltas.get(currentFrame) <= System.currentTimeMillis()){
//			frames.get(currentFrame).release();
			currentFrame = (currentFrame == frames.size()-1) ? 0 : currentFrame+1;
			lastFrameTime = System.currentTimeMillis();
		}
		
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_COLOR_MATERIAL);
		frames.get(currentFrame).bind();
		
		glColor3d(1.0, 1.0, 1.0);
		
		int w = frames.get(currentFrame).getImageWidth();
		int h = frames.get(currentFrame).getImageHeight();
		
		glLoadIdentity();
		glTranslatef(x-anchorX, y-anchorY, 0f);
		
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2i(0, 0);
		glTexCoord2f(1, 0);
		glVertex2i(w, 0);
		glTexCoord2f(1, 1);
		glVertex2i(w, h);
		glTexCoord2f(0, 1);
		glVertex2i(0, h);
		glEnd();
		glLoadIdentity();
		
	}
	
	public void destroy(){
		for(Texture tex : frames){
			tex.release();
		}
	}
}


