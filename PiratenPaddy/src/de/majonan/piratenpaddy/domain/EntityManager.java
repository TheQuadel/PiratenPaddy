package de.majonan.piratenpaddy.domain;

import java.util.List;
import java.util.Vector;

import org.lwjgl.input.Mouse;

import de.majonan.piratenpaddy.valueobjects.Entity;

public class EntityManager {

	private List<Entity> entities;
	private Entity clickedEntity;
	
	private List<EntityHoverListener> hoverListeners;
	private List<EntityClickListener> clickListeners;
	
	public EntityManager(){
		entities = new Vector<Entity>();
		hoverListeners = new Vector<EntityHoverListener>();
		clickListeners = new Vector<EntityClickListener>();
	}
	
	public void addEntity(Entity en){
		entities.add(en);
	}
	
	public List<Entity> getEntitiesAtPosition(int x, int y){
		List<Entity> found = new Vector<Entity>();
		for(Entity e : entities){
			if(e.isAtPosition(x, y)){
				found.add(e);
			}
		}
		return found;
	}
	
	public Entity getEntityAtPosition(int x, int y){
		for(Entity e : entities){
			if(e.isAtPosition(x, y)){
				return e;
			}
		}
		return null;
	}
	
	public void deHighlightAll(){
		for(Entity e : entities){
			e.setHighlighted(false);
		}
	}
	
	
	public void draw(){
		for(Entity e : entities){
			e.draw();
		}
	}
	
	public void destroy(){
		for(Entity e : entities){
			e.destroy();
		}
	}

	public void update(int mouseX, int mouseY) {
		
		Entity e = getEntityAtPosition(mouseX, mouseY);
		if(e != null){
			for(EntityHoverListener l : hoverListeners){
				l.onHover(e);
			}
			if(Mouse.isButtonDown(0)){
				clickedEntity = e;
			}else{
				for(EntityClickListener l : clickListeners){
					l.onClicked(e);
				}
			}
		}
		
		
		
	}
	
	
	public void addHoverListener(EntityHoverListener listener){
		this.hoverListeners.add(listener);
	}
	
	public void addClickListener(EntityClickListener listener) {
		this.clickListeners.add(listener);
	}
}
