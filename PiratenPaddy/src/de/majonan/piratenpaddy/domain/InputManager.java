package de.majonan.piratenpaddy.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import de.majonan.piratenpaddy.valueobjects.listeners.ClickListener;
import de.majonan.piratenpaddy.valueobjects.listeners.KeyboardListener;
import de.majonan.piratenpaddy.valueobjects.listeners.MouseMoveListener;

public class InputManager {

	
	int mouseX;
	int mouseY;
	
	List<Integer> pressedKeys;
	List<Integer> pressedMouseButtons;

	
	List<ClickListener> clickListener;
	List<MouseMoveListener> mouseMoveListener;
	List<KeyboardListener> keyboardListener;
	
	public InputManager(){
		clickListener = new Vector<ClickListener>();
		mouseMoveListener = new Vector<MouseMoveListener>();
		keyboardListener = new Vector<KeyboardListener>();
		
		pressedKeys = new Vector<Integer>();
		pressedMouseButtons = new Vector<Integer>();
		
	}
	
	public void tick(){
//		lastMouseX = mouseX;
//		lastMouseY = mouseY;
//		mouseX = Mouse.getX();
//		mouseY = GameManager.GAME_HEIGHT-Mouse.getY();
//		
//		if(mouseX != lastMouseX || mouseY != lastMouseY){
//			mouseSpeed = Math.sqrt(Math.pow(mouseX-lastMouseX, 2) + Math.pow(mouseY-lastMouseY, 2));
//			for(MouseMoveListener listener : mouseMoveListener){
//				if (listener.onMouseMove(mouseX, mouseY, mouseSpeed)){
//					break;
//				};
//				
//			}
//		}
		
		while(Keyboard.next()){
			int key = Keyboard.getEventKey();
			boolean pressed = Keyboard.getEventKeyState();
			if(pressed && !isKeyPressed(key)){
				pressedKeys.add(key);
				for(KeyboardListener listener : keyboardListener){
					if (listener.onKeyDown(key)){
						break;
					};
					
				}
			}else{
				if(!pressed && isKeyPressed(key)){
					pressedKeys.remove(new Integer(key));
					for(KeyboardListener listener : keyboardListener){
						if (listener.onKeyUp(key)){
							break;
						};
						
					}
				}
			}
			
		}
		for(int key : pressedKeys){
			for(KeyboardListener listener : keyboardListener){
				if (listener.onKeyPressed(key)){
					break;
				};
				
			}
		}
		
		
		while(Mouse.next()){
			int x = Mouse.getEventX();
			int y = GameManager.GAME_HEIGHT-Mouse.getEventY();
			int dx = Mouse.getEventDX();
			int dy = Mouse.getEventDY();
			int button = Mouse.getEventButton();
			boolean pressed = Mouse.getEventButtonState();
			
			if(dx != 0 || dy != 0){
				mouseX = x;
				mouseY = y;
				for(MouseMoveListener listener : mouseMoveListener){
					if (listener.onMouseMove(x,y,dx,dy)){
						break;
					};
				}
			}
			
			if(pressed && !isMouseButtonPressed(button)){
				pressedMouseButtons.add(button);
				for(ClickListener listener : clickListener){
					if (listener.onMouseDown(button, x, y)){
						break;
					};
				}
			}
			if(!pressed && isMouseButtonPressed(button)){
				pressedMouseButtons.add(button);
				for(ClickListener listener : clickListener){
					if (listener.onMouseUp(button, x, y)){
						break;
					};
				}
			}
		}
		
	}
	
	
	private boolean isMouseButtonPressed(int button){
		return pressedMouseButtons.contains(button);
	}
	
	private boolean isKeyPressed(int key){
		return pressedKeys.contains(key);
	}
	
	public void addMouseMoveListener(MouseMoveListener l){
		mouseMoveListener.add(l);
	}
	
	public void addKeyBoardListener(KeyboardListener l){
		keyboardListener.add(l);
	}
	
	public void addClickListener(ClickListener l){
		clickListener.add(l);
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	
}
