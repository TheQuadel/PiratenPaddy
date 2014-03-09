package de.majonan.piratenpaddy.valueobjects;

public class Area extends Entity {

	private Sprite mask;
	
	public Area(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub

	}

	public void addMask(Sprite sprite){
		this.mask = sprite;
	}
}
