package de.majonan.piratenpaddy.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.lwjgl.input.Mouse;

import de.majonan.piratenpaddy.valueobjects.Entity;

public class EntityManager {

	private List<Entity> entities;
	private Entity clickedEntity;
	private Comparator<Entity> entityComparator;
	
	private List<EntityHoverListener> hoverListeners;
	private List<EntityClickListener> clickListeners;
	
	public EntityManager(){
		entities = new Vector<Entity>();
		hoverListeners = new Vector<EntityHoverListener>();
		clickListeners = new Vector<EntityClickListener>();
		entityComparator = new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				return e1.compareTo(e2);
			}
		};
	}
	
	public void addEntity(Entity en){
		entities.add(en);
		Collections.sort(entities,entityComparator);
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
		
		for(int i= entities.size()-1; i >= 0; i--){
			if(entities.get(i).isAtPosition(x, y)){
				return entities.get(i);
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

	public void mouseMove(int mouseX, int mouseY) {
		
		Entity e = getEntityAtPosition(mouseX, mouseY);
		if(e != null){
			for(EntityHoverListener l : hoverListeners){
				l.onHover(e);
			}
//			if(Mouse.isButtonDown(0)){
//				clickedEntity = e;
//			}else{
//				if(clickedEntity != null && e != null && clickedEntity.equals(e)){
//					clickedEntity = null;
//					for(EntityClickListener l : clickListeners){
//						l.onClicked(e);
//					}
//				}
//			}
		}
		
	}
	
public void clicked(int mouseX, int mouseY) {
		
		Entity e = getEntityAtPosition(mouseX, mouseY);
		if(e != null){
			for(EntityClickListener l : clickListeners){
				l.onClicked(e);
			}
		}
		
	}
	
	
	public void addHoverListener(EntityHoverListener listener){
		this.hoverListeners.add(listener);
	}
	
	public void addClickListener(EntityClickListener listener) {
		this.clickListeners.add(listener);
	}

	public void resort() {
		Collections.sort(entities,entityComparator);
		
	}
}
