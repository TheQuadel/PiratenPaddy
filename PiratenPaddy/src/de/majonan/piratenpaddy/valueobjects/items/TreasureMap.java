package de.majonan.piratenpaddy.valueobjects.items;

import java.util.List;

import de.majonan.piratenpaddy.valueobjects.Item;
import de.majonan.piratenpaddy.valueobjects.Message;

public class TreasureMap extends Item {

	public TreasureMap(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void pull() {
		// TODO Auto-generated method stub

	}

	@Override
	public void push() {
		// TODO Auto-generated method stub

	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Message> lookAt() {
		// TODO Auto-generated method stub
	System.out.print("Papa hat 'Mein Schatz' draufgeschrieben. Sie ist zu schmutzig, um mehr zu erkennen");
		return null;
	}

}
