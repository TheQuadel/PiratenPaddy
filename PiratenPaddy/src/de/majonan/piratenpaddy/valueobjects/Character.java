package de.majonan.piratenpaddy.valueobjects;

import java.util.Vector;

public abstract class Character extends Entity {

	private Vector<CharacterState> states;
	
	public void moveTo(){
		//TODO implement
	}
	
	public abstract void changeState(int stateId);
	public abstract void talkTo();

}
