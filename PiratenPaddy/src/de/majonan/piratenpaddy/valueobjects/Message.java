package de.majonan.piratenpaddy.valueobjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Message {
	
	public static int ALIGN_H_LEFT = 0;
	public static int ALIGN_H_CENTER = 1;
	public static int ALIGN_H_RIGHT = 2;
	
	public static int ALIGN_V_TOP = 3;
	public static int ALIGN_V_CENTER = 4;
	public static int ALIGN_V_BOTTOM = 5;

	private String text;
	private long duration;
	private long startTime;
	private boolean started = false;
	
	private Color color;
	private int hAlign;
	private int vAlign;
	
	
	public Message(String text, long duration){
		this.text = text;
		this.duration = duration;
	}
	
	public Message(String text, long duration, int hAlign, int vAlign){
		this.text = text;
		this.duration = duration;
		this.vAlign = vAlign;
		this.hAlign = hAlign;
	}
	
	
	public void draw(float x, float y, TrueTypeFont font){
		this.draw(x, y, font, this.color);
	}
	public void draw(float x, float y, TrueTypeFont font, Color color){
		color = (color == null) ? this.color : color;
		color = (color == null) ? Color.white : color;
		if(!started){
			startTime = System.currentTimeMillis();
			started = true;
		}
		float dx = x;
		float dy = y;
		
		
		if(hAlign != ALIGN_H_LEFT){
			float w = getWidth(font);
			dx -= (hAlign == ALIGN_H_CENTER) ? w/2 : w;
		}
		
		if(vAlign != ALIGN_V_TOP){
			float h = getHeight(font);
			dy -= (vAlign == ALIGN_V_CENTER ? h/2 : h);
		}
		font.drawString(dx, dy, text, color);
	}

	
	public boolean isExpired(){
		return started && System.currentTimeMillis() > (startTime + duration);
	}
	
	public float getWidth(TrueTypeFont font){
		return font.getWidth(text);
	}
	
	public float getHeight(TrueTypeFont font){
		return font.getHeight(text);
	}

	public int gethAlign() {
		return hAlign;
	}

	public void sethAlign(int hAlign) {
		this.hAlign = hAlign;
	}

	public int getvAlign() {
		return vAlign;
	}

	public void setvAlign(int vAlign) {
		this.vAlign = vAlign;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isStarted() {
		return started;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	
}
