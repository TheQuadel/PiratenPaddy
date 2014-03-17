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

import java.util.HashMap;

public abstract class Entity implements Comparable<Entity> {
	protected float x, y, width, height;
	protected int zIndex;
	protected HashMap<String, Sprite> sprites;
	protected Sprite currentSprite;
	

	protected boolean highlighted;
	
	
	public Entity(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprites = new HashMap<String, Sprite>();
//		try {
//			//texture =   TextureLoader.getTexture("PNG", new FileInputStream(new File(imagePath)));
//			//texture =   TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(imagePath));
//			width = texture.getImageWidth();
//			height = texture.getImageHeight();
//			System.out.println("w:"+width+"h:"+height);
//			
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public void draw(){
//		glLoadIdentity();
//		glTranslatef(x, y, 0f);
//		if(highlighted){
//			glDisable(GL_TEXTURE_2D);
//			glEnable(GL_COLOR_MATERIAL);
//			glColor3d(1.0, 0.5, 0);
//			glBegin(GL_QUADS);
//			glVertex2i(-2, -2);
//			glVertex2i(width+2, -2);
//			glVertex2i(width+2, height+2);
//			glVertex2i(-2, height+2);
//			glEnd();
//		}
		
//		glEnable(GL_TEXTURE_2D);
//		glDisable(GL_COLOR_MATERIAL);
//		
//		glColor3d(1.0, 1.0, 1.0);
//		
//		glBegin(GL_QUADS);
//		glTexCoord2f(0, 0);
//		glVertex2i(0, 0);
//		glTexCoord2f(1, 0);
//		glVertex2i(width, 0);
//		glTexCoord2f(1, 1);
//		glVertex2i(width, height);
//		glTexCoord2f(0, 1);
//		glVertex2i(0, height);
//		glEnd();
//		glLoadIdentity();

		if(currentSprite != null){
			currentSprite.draw((int)x, (int)y);
		}else{
			System.err.println("EntityError: Please change currentSprite!");
		}
		
	}
	
	public boolean isAtPosition(int px, int py){
		return (px > x && px < x+width && py > y && py < y+height);
	}
	
	public void destroy(){
		for(String name : sprites.keySet()){
			sprites.get(name).destroy();
		}
	}
	
	public void addSprite(String name, Sprite sprite){
		this.sprites.put(name, sprite);
	}
	
	public void changeSprite(String name){
		currentSprite = sprites.get(name);
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getZIndex(){
		return this.zIndex;
	}
	
	public void setZIndex(int zIndex){
		this.zIndex = zIndex;
	}
	
	@Override
	public int compareTo(Entity other) {
		if(this.zIndex < other.getZIndex() ){
			return -1;
		}
		if(this.zIndex > other.getZIndex()){
			return 1;
		}
		return 0;
	}

	public float getX() {
		return x;
	}


	public float getY() {
		return y;
	}


	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}
	
}
