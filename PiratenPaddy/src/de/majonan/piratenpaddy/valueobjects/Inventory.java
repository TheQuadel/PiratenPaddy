package de.majonan.piratenpaddy.valueobjects;

import java.util.List;
import java.util.Vector;

public class Inventory {

	private List<Item> slots;
	
	public Inventory(int slotsAmount){
		this.slots = new Vector<Item>(slotsAmount);
	}
}
