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
import java.util.List;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import de.majonan.piratenpaddy.domain.GameManager;

public abstract class Character extends Entity {

	private TrueTypeFont font;
	
	protected Point messagePoint;
	protected List<Message> messageQueue;
	
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
		this.messagePoint = new Point(0,0);
		
		this.font = new TrueTypeFont(new Font("Times New Roman", Font.PLAIN, 24), true);
		this.messageQueue = new Vector<Message>();
		// TODO Auto-generated constructor stub
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
		
		System.out.println("Player #messages:"+messageQueue.size());
		if(!messageQueue.isEmpty()){
			if(!messageQueue.get(0).isExpired()){
				messageQueue.get(0).draw(messagePoint.x, messagePoint.y, font, Color.white);
			}else{
				messageQueue.remove(0);
			}
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
	
	public int say(String text, long duration){
		Message m = new Message(text, duration, Message.ALIGN_H_CENTER, Message.ALIGN_V_CENTER);
		m.setColor(Color.white);
		this.messageQueue.add(m);
		return messageQueue.indexOf(m);
	}
	
	public void beQuite(){
		messageQueue.clear();
	}
	
	
	public void pull(Item item){
		item.pull();
	}
	
	public void push(Item item){
		item.push();
	}
	
	public void open(Item item){
		item.open();
	}
	
	public void close(Item item){
		item.close();
	}
	
	public void lookAt(Item item){
		List<Message> messages = item.lookAt();
		for(Message m : messages){
			m.setColor(Color.white);
			m.sethAlign(Message.ALIGN_H_CENTER);
			m.setvAlign(Message.ALIGN_V_CENTER);
			messageQueue.add(m);
		}
		System.out.println("LookAt - #messages:"+messages.size());
	}

	public void lookAt(Character character){
		character.lookAt();
	}
	
	public abstract void lookAt();
}
