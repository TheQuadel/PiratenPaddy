package de.majonan.piratenpaddy.valueobjects;

import java.util.List;
import java.util.Vector;

public class Inventory{

	private List<Item> slots;
	private int maxItems;
	private List<InventoryEntity> displayEntities;
	
	
	public void notifyListeners(){
		for(InventoryEntity ie : displayEntities){
			ie.update(slots);
		}
	}
	
	public Inventory(int slotsAmount){
		maxItems = slotsAmount;
		this.slots = new Vector<Item>(slotsAmount < 0 ? 10 : slotsAmount);
		this.displayEntities = new Vector<InventoryEntity>();
	}
	
	public boolean addItem(Item item){
		if(slots.size() < maxItems){
			slots.add(item);
			notifyListeners();
			return true;
		}
		return false;
	}
	
	public Item getItemByIndex(int index){
		return slots.get(index);
	}
	
	public void removeItemAtIndex(int index){
		slots.remove(index);
		notifyListeners();
	}
	
	public void clear(){
		slots = new Vector<Item>(maxItems < 0 ? 10 : maxItems);
		notifyListeners();
	}
	
	public boolean containsItem(Item item){
		for(Item i : slots){
			if(i.equals(item)){
				return true;
			}
		}
		return false;
	}
	
	public void addInventoryListener(InventoryEntity listener){
		this.displayEntities.add(listener);
	}


}
