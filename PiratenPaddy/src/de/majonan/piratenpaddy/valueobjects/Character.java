package de.majonan.piratenpaddy.valueobjects;

import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.Font;
import java.util.Vector;

import org.newdawn.slick.TrueTypeFont;

import de.majonan.piratenpaddy.domain.GameManager;

public abstract class Character extends Entity {

	private TrueTypeFont font;
	private String speech;
	private long speechStart;
	private long speechDuration;
	protected Point speechPoint;
	
	private float speed = 2;
	
	private float destinationX;
	private float destinationY;
	private float startX;
	private float startY;
	private float startWidth;
	private float startHeight;
	private boolean isMoving = false;
	private Callback movingCallback;
	private Vector<CharacterState> states;
	
	private float z;
	
	public Character(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.destinationX = x;
		this.destinationY = y;
		this.startX = x;
		this.startY = y;
		this.z =((y/720f)*80+20);
		this.startWidth = width;
		this.startHeight = height;
		this.speechPoint = new Point(0,0);
		
		this.font = new TrueTypeFont(new Font("Times New Roman", Font.PLAIN, 24), true);
		// TODO Auto-generated constructor stub
	}
	
	
	public void say(String what, long duration){
		this.speech = what;
		this.speechDuration = duration;
		this.speechStart = System.currentTimeMillis();
	}
	
	public void moveTo(int x, int y, Callback callback){
		isMoving = true;
		this.destinationX = x;
		int nfootZ = (int)((y/720f)*80+20);
		this.destinationY = y;
		this.startX = this.x;
		this.startY = this.y;
		this.movingCallback = callback;
	}
	
	public void moveTo(int x, int y){
		moveTo(x, y, null);
	}
	
	public void tick(){
		
		if(System.currentTimeMillis() > speechStart+speechDuration){
			speech = null;
		}
	
//		isMoving = false;
//		if(Math.abs(destinationX-x) > 2){
//			x += Math.signum(destinationX-x)*speed;
//			isMoving = true;
//			//System.out.println("moved");
//		}
//		if(Math.abs(destinationY-y) > 2){
//			y += Math.signum(destinationY-y)*speed;
//			isMoving = true;
//		}
		
		isMoving = false;
		if(Math.abs(destinationY-y) > speed || Math.abs(destinationX-x) > speed){
			
			//double directionX = Math.acos((destinationX-x)/Math.sqrt((destinationX-x)*(destinationX-x)+(destinationY-y)*(destinationY-y)));
			//double directionY = Math.asin((destinationY-y)/Math.sqrt((destinationX-x)*(destinationX-x)+(destinationY-y)*(destinationY-y)));
			double direction = Math.atan2((destinationY-y),(destinationX-x));
			x += Math.cos(direction)*speed;
			y += Math.sin(direction)*speed;
			//System.out.println("Dirs: x:"+directionX+" y:"+directionY);
			
			isMoving = true;
		}
		
		if(!isMoving && movingCallback != null){
			movingCallback.execute();
			movingCallback = null;
		}
		
		z = ((y/720f)*80+20);
		//System.out.println("z:"+z);

		
		width = (int) (startWidth*z/100f);
		height = (int) (startHeight*z/100f);
		
	}
	
	public abstract void changeState(int stateId);
	public abstract void talkTo();

	
	public boolean isMoving(){
		return isMoving;
	}
	
	@Override
	public void draw(){
		if(currentSprite != null){
			currentSprite.draw(Math.round(x), Math.round(y), z/100f);//width/(float)startWidth);
			
			
		}else{
			System.err.println("EntityError: Please change currentSprite!");
		}
		
		if(speech != null){
			int dx = font.getWidth(speech)/2;
			int dy = font.getHeight(speech)/2;
			font.drawString(x+speechPoint.x-dx, y+speechPoint.y*(z/100f)-dy, speech);
		}
		
		if(GameManager.DEBUG){
			glDisable(GL_TEXTURE_2D);
			glEnable(GL_COLOR_MATERIAL);
				
			glLineWidth(2); 
			glColor3f(.2f, .2f, .3f);
			glVertex3f(startX, startY, 0);
			glVertex3f(destinationX, destinationY, 0);
			glColor3f(.4f, .4f, .6f);
			glBegin(GL_LINES);
			glVertex3f(x, y, 0);
			glVertex3f(destinationX, destinationY, 0);
			
			
			glEnd();
			

		}
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public TextTransmitter getTextTransmitter(){
		final Character me = this;
		return new TextTransmitter() {
			
			@Override
			public void say(String what, long duration) {
				me.say(what, duration);
				
			}
		};
	}

}
