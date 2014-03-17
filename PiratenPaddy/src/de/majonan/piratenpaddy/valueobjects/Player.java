package de.majonan.piratenpaddy.valueobjects;

import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3f;
import de.majonan.piratenpaddy.domain.GameManager;

public class Player extends Character {

	private Inventory inventory;
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.inventory = new Inventory(16);
		setSpeed(4);
		messagePoint = new Point(0,-450);
	}

	@Override
	public void changeState(int stateId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void talkTo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub

	}
	
	public Inventory getInventory(){
		return this.inventory;
	}

	@Override
	public void setHighlighted(boolean highlighted) {
		super.setHighlighted(false);
	}
	
	
	
	
	

}
