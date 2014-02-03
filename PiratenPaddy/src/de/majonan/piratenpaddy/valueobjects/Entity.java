package de.majonan.piratenpaddy.valueobjects;

import java.awt.Image;

public abstract class Entity {
	private int x;
	private int y;
	private int width;
	private int height;
	private Image image;
	
	public abstract void lookAt();
	
}
