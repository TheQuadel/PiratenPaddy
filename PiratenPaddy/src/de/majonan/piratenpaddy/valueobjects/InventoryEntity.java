package de.majonan.piratenpaddy.valueobjects;

import java.util.List;



public class InventoryEntity extends Entity {

	private int[][] slotPositions;
	private int destY;
	private boolean hidden = false;
	
	public InventoryEntity(int x, int y, int width, int height, int[][] slotPositions) {
		super(x, y, width, height);
		 destY = y;
		this.slotPositions = slotPositions;
	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub

	}

	public void update(List<Item> slots) {
		for(int i=0; i< slotPositions.length && i < slots.size(); i++){
			slots.get(i).setPosition(slotPositions[i][0]+(int)x, slotPositions[i][1]+(int)y);
		}
		
	}
	

	@Override
	public void setHighlighted(boolean highlighted) {
		this.highlighted = false;
	}
	
	public void tick(){
		if(Math.abs(destY-y) > 4){
			y += (int)((destY-y))/10;
		}
	}
	
	public void show(){
		this.destY = 720-150;
		hidden = false;
	}
	
	public void hide(){
		this.destY = 720-5;
		hidden = true;
	}
	
	public boolean isHidden(){
		return hidden;
	}
	
	

}
