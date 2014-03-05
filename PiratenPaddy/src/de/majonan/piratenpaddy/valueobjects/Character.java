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
	
	private int footX;
	private int footY;
	private int footZ;
	
	public Character(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.destinationX = x;
		this.destinationY = y;
		this.startX = x;
		this.startY = y;
		this.footZ =(int)((y/720f)*80+20);
		this.startWidth = width;
		this.startHeight = height;
		// TODO Auto-generated constructor stub
	}
	private Vector<CharacterState> states;
	
	public void moveTo(int x, int y){
		this.destinationX = x;
		int nfootZ = (int)((y/720f)*80+20);
		this.destinationY = y-footY*footZ/100;
		this.startX = x;
		this.startY = y;
		System.out.println("moveTo"+x+","+y+", fz"+footZ+", nfz:"+nfootZ);
	}
	
	public void tick(){
	
		if(Math.abs(destinationX-x) > 2){
			x += Math.signum(destinationX-x)*speed;
			//System.out.println("moved");
		}
		if(Math.abs(destinationY-y) > 2){
			y += Math.signum(destinationY-y)*speed;
		}
		footZ = (int)((y/720f)*80+20);

		
		width = (int) (startWidth*footZ/100f);
		height = (int) (startHeight*footZ/100f);
		
	}
	
	public abstract void changeState(int stateId);
	public abstract void talkTo();

	
	public void setFootPosition(int fx, int fy){
		this.footX = fx;
		this.footY = fy;
	}
}
