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

	public void setMask(Sprite sprite){
		this.mask = sprite;
	}
	
	public boolean isPositonWalkable(int x, int y){
		int[] res = mask.getColorOfPixel(x, y);
		//System.out.println("rgb:["+res[0]+","+res[1]+","+res[2]+"]");
		return (mask.getColorOfPixel(x, y)[0] == 255);
	}
	
	
}
