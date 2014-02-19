package de.majonan.piratenpaddy.valueobjects;

import java.util.List;



public class InventoryEntity extends Entity {

	private int[][] slotPositions;
	
	public InventoryEntity(int x, int y, String imagePath, int[][] slotPositions) {
		super(x, y, imagePath);
		this.slotPositions = slotPositions;
	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub

	}

	public void update(List<Item> slots) {
		for(int i=0; i< slotPositions.length && i < slots.size(); i++){
			slots.get(i).setPosition(slotPositions[i][0]+x, slotPositions[i][1]+y);
		}
		
	}

	@Override
	public void setHighlighted(boolean highlighted) {
		this.highlighted = false;
	}
	
	

}
