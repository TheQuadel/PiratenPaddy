package de.majonan.piratenpaddy.valueobjects;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import de.majonan.piratenpaddy.domain.GameManager;

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
		
		this.anchorX = anchorX;
		this.anchorY = anchorY;
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
		draw(x,y,1);
	}
	public void draw(int x, int y, float resizeFactor){
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
		int w = (int) (resizeFactor * frames.get(currentFrame).getTextureWidth());
		int h = (int) (resizeFactor * frames.get(currentFrame).getTextureHeight());
		
		glLoadIdentity();
		glTranslatef(x-anchorX*resizeFactor, y-anchorY*resizeFactor, 0f);
		
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
		
		
		if(GameManager.DEBUG){
			glDisable(GL_TEXTURE_2D);
			glEnable(GL_COLOR_MATERIAL);
				
			
			glLineWidth(2); 
			glColor3f(0, 0, 1);
			w = (int) (resizeFactor * frames.get(currentFrame).getImageWidth());
			h = (int) (resizeFactor * frames.get(currentFrame).getImageHeight());
			glBegin(GL_LINES);
			glVertex3f(1, 1, 0);
			glVertex3f(w-1, 1, 0);
			
			glVertex3f(w-1, 1, 0);
			glVertex3f(w-1, h-1, 0);
			
			glVertex3f(w-1, h-1, 0);
			glVertex3f(1, h-1, 0);
			
			glVertex3f(1, h-1, 0);
			glVertex3f(1, 1, 0);
			
			glColor3f(1, 0, 0);
			w = (int) frames.get(currentFrame).getTextureWidth();
			h = (int) frames.get(currentFrame).getTextureHeight();
			
			glVertex3f(0, 0, 0);
			glVertex3f(w, 0, 0);
			
			glVertex3f(w, 0, 0);
			glVertex3f(w, h, 0);
			
			glVertex3f(w, h, 0);
			glVertex3f(0, h, 0);
			
			glVertex3f(0, h, 0);
			glVertex3f(0, 0, 0);
			
			
			glColor3f(0, 1, 0);
			glVertex3f(anchorX-4, anchorY-4, 0);
			glVertex3f(anchorX+4, anchorY+4, 0);
			
			glVertex3f(anchorX+4, anchorY-4, 0);
			glVertex3f(anchorX-4, anchorY+4, 0);
			glColor3f(0, 1, 1);
			glVertex3f(resizeFactor*anchorX-4, resizeFactor*anchorY-4, 0);
			glVertex3f(resizeFactor*anchorX+4, resizeFactor*anchorY+4, 0);
			
			glVertex3f(resizeFactor*anchorX+4, resizeFactor*anchorY-4, 0);
			glVertex3f(resizeFactor*anchorX-4, resizeFactor*anchorY+4, 0);
			glEnd();
		}
			
		glLoadIdentity();
		
	}
	
	public void destroy(){
		for(Texture tex : frames){
			tex.release();
		}
	}
}


