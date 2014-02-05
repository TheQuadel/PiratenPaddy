package de.majonan.piratenpaddy.valueobjects;

import java.util.Vector;

public abstract class Character extends Entity {

	public Character(int x, int y, String imagePath) {
		super(x, y, imagePath);
		// TODO Auto-generated constructor stub
	}
	private Vector<CharacterState> states;
	
	public void moveTo(){
		//TODO implement
	}
	
	public abstract void changeState(int stateId);
	public abstract void talkTo();

}
