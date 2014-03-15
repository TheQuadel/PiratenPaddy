package de.majonan.piratenpaddy.valueobjects;

import de.majonan.piratenpaddy.domain.GameManager;

public class Cursor extends Entity{

	public Cursor(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		this.zIndex = GameManager.ZINDEX_CURSOR;
		
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub
		
	}
}
