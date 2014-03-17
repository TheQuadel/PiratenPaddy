package de.majonan.piratenpaddy.valueobjects.items;

import java.util.List;
import java.util.Vector;

import de.majonan.piratenpaddy.domain.GameManager;
import de.majonan.piratenpaddy.valueobjects.Item;
import de.majonan.piratenpaddy.valueobjects.Message;
import de.majonan.piratenpaddy.valueobjects.Point;
import de.majonan.piratenpaddy.valueobjects.Sprite;

public class Candle extends Item {

	public Candle(int x, int y){
		super(x, y, 62, 161);
		addSprite("off", (new Sprite(62, 161,0,0)).addFrame(GameManager.IMAGE_PATH+"kerze_aus.png", 5000));
		addSprite("on", (new Sprite(62, 161,0,0)).addFrame(GameManager.IMAGE_PATH+"kerze.png", 500)
												  .addFrame(GameManager.IMAGE_PATH+"kerze2.png", 500));
		changeSprite("on");
		setNearestPoint(new Point(320, 690));
	}
	
	public Candle(int x, int y, int width, int height) {
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
		List<Message> messages = new Vector<Message>();
		messages.add(new Message("Sie brennt schon seit Ewigkeiten", 2000));
		messages.add(new Message("Liegt bestimmt an Klebstoffgasen in der Luft", 2000));
		messages.add(new Message("Seit ich stündlich schnüffel sind die echt gestiegen", 2000));
		
		return messages;

	}

}
