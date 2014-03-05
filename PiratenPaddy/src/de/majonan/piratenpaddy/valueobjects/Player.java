package de.majonan.piratenpaddy.valueobjects;

public class Player extends Character {

	private Inventory inventory;
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.inventory = new Inventory(16);
	}

	@Override
	public void changeState(int stateId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void talkTo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub

	}
	
	public Inventory getInventory(){
		return this.inventory;
	}

	@Override
	public void setHighlighted(boolean highlighted) {
		super.setHighlighted(false);
	}
	
	
	

}
