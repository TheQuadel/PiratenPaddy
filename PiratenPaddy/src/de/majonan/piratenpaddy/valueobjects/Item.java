package de.majonan.piratenpaddy.valueobjects;

import java.awt.Image;


public abstract class Item extends Entity {

	private Image imageCollected;
	
	public abstract void pull();
	public abstract void push();
	public abstract void open();
	public abstract void close();

}
