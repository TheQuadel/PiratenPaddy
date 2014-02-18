package de.majonan.piratenpaddy.valueobjects;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

public class InventoryEntity extends Entity {

	private List<Vector2f> slotPositions;
	
	public InventoryEntity(int x, int y, String imagePath, List<Vector2f> slotPositions) {
		super(x, y, imagePath);
		this.slotPositions = slotPositions;
	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub

	}

	public void update(List<Item> slots) {
		for(int i=0; i< slotPositions.size(); i++){
			slots.get(i).setPosition((int)slotPositions.get(i).x, (int)slotPositions.get(i).y);
		}
		
	}

}
