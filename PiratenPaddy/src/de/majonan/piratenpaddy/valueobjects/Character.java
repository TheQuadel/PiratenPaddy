package de.majonan.piratenpaddy.valueobjects;

import java.util.Vector;

public abstract class Character extends Entity {

	private int speed = 2;
	
	private int destinationX;
	private int destinationY;
	private int startX;
	private int startY;
	private int startWidth;
	private int startHeight;
	
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
		// TODO Auto-generated constructor stub
	}
	private Vector<CharacterState> states;
	
	public void moveTo(int x, int y){
		this.destinationX = x;
		int nfootZ = (int)((y/720f)*80+20);
		this.destinationY = y;
		this.startX = this.x;
		this.startY = this.y;
	}
	
	public void tick(){
	
		if(Math.abs(destinationX-x) > 2){
			x += Math.signum(destinationX-x)*speed;
			//System.out.println("moved");
		}
		if(Math.abs(destinationY-y) > 2){
			y += Math.signum(destinationY-y)*speed;
		}
		z = ((y/720f)*80+20);
		System.out.println("z:"+z);

		
		width = (int) (startWidth*z/100f);
		height = (int) (startHeight*z/100f);
		
	}
	
	public abstract void changeState(int stateId);
	public abstract void talkTo();

	
	@Override
	public void draw(){
		if(currentSprite != null){
			currentSprite.draw(x, y, z/100f);//width/(float)startWidth);
			
		}else{
			System.err.println("EntityError: Please change currentSprite!");
		}
	}
}
