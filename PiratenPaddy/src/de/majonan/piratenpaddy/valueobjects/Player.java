package de.majonan.piratenpaddy.valueobjects;

public class Player extends Character {

	private Inventory inventory;
	
	public Player(int x, int y, String imagePath) {
		super(x, y, imagePath);
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

}
