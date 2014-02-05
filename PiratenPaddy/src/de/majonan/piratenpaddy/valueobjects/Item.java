package de.majonan.piratenpaddy.valueobjects;

import java.awt.Image;


public abstract class Item extends Entity {

	public Item(int x, int y, String imagePath) {
		super(x, y, imagePath);
		// TODO Auto-generated constructor stub
	}
	private Image imageCollected;
	
	public abstract void pull();
	public abstract void push();
	public abstract void open();
	public abstract void close();

}
