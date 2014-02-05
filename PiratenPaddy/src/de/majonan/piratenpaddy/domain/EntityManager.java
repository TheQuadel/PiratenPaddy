package de.majonan.piratenpaddy.domain;

import java.util.List;
import java.util.Vector;

import de.majonan.piratenpaddy.valueobjects.Entity;

public class EntityManager {

	private List<Entity> entities;
	
	public EntityManager(){
		entities = new Vector<Entity>();
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
}
