package de.majonan.piratenpaddy.valueobjects.listeners;

public interface ClickListener {

	public boolean onMouseDown(int key, int x, int y);
	public boolean onMouseUp(int key,  int x, int y);
	public boolean onMouseClicked(int key, int x, int y);
}