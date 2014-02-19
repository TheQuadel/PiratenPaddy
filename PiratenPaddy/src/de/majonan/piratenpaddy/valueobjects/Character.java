package de.majonan.piratenpaddy.valueobjects;

import java.util.Vector;

public abstract class Character extends Entity {

	private int speed = 2;
	
	private int destinationX;
	private int destinationY;
	private int startX;
	private int startY;
	
	private int footX;
	private int footY;
	
	public Character(int x, int y, String imagePath) {
		super(x, y, imagePath);
		this.destinationX = x;
		this.destinationY = y;
		this.startX = x;
		this.startY = y;
		// TODO Auto-generated constructor stub
	}
	private Vector<CharacterState> states;
	
	public void moveTo(int x, int y){
		this.destinationX = x-footX;
		this.destinationY = y-footY;
		this.startX = x;
		this.startY = y;
		System.out.println("moveTo"+x+","+y);
	}
	
	public void tick(){
	
		if(Math.abs(destinationX-x) > 2){
			x += Math.signum(destinationX-x)*speed;
			System.out.println("moved");
		}
		if(Math.abs(destinationY-y) > 2){
			y += Math.signum(destinationY-y)*speed;
		}
	}
	
	public abstract void changeState(int stateId);
	public abstract void talkTo();

	
	public void setFootPosition(int fx, int fy){
		this.footX = fx;
		this.footY = fy;
	}
}
