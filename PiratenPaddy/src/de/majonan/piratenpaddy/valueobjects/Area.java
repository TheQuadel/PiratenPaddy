package de.majonan.piratenpaddy.valueobjects;

import static org.lwjgl.opengl.GL11.*;


import java.util.List;
import java.util.Vector;

import de.majonan.piratenpaddy.domain.GameManager;

public class Area extends Entity {

	private Sprite mask;
	private List<Point> polygon;
	
	public Area(int x, int y, int width, int height) {
		super(x, y, width, height);
		polygon = new Vector<Point>();
	}

	@Override
	public void lookAt() {
		// TODO Auto-generated method stub

	}

	public void setMask(Sprite sprite){
		this.mask = sprite;
	}
	
	public boolean isPositonWalkable(int x, int y){
		//Old version, slow as hell
		//int[] res = mask.getColorOfPixel(x, y);
		//System.out.println("rgb:["+res[0]+","+res[1]+","+res[2]+"]");
		//return (mask.getColorOfPixel(x, y)[0] == 255);
		
//		Point toTest = new Point(x,y);
//		Point zeroPoint = new Point(0,0);
//		int count = 0;
//		for(int i=0; i<polygon.size(); i++){
//			Point p1 = polygon.get(i);
//			Point p2 = polygon.get((i == polygon.size()-1) ? 0 : i+1);
//			System.out.println("Test Line from ["+zeroPoint.x+","+zeroPoint.y+"] to ["+toTest.x+","+toTest.y+"] and ["+p1.x+","+p1.y+"] to ["+p2.x+","+p2.y+"]");
//			if(Point.doIntersect(zeroPoint, toTest, p1, p2)){
//				count++;
//				System.out.println("true");
//			}else{
//				System.out.println("false");
//			}
//			break;
//		}
//		System.out.println("x:"+x+" y:"+y+" count: "+count);
//		return (count % 2 != 0);
		
		return (new Point(x,y)).isInPolygon(polygon);
	}
	
	
	public void addPointToWalkablePolygon(Point newPoint){
		polygon.add(newPoint);
	}
	public void addPointsToWalkablePolygon(Point... newPoints){
		for(Point p : newPoints){
			polygon.add(p);
		}
	}
	
	
	public Point getWalkablePointNextTo(Point p){
		int nearestId = 0;
		double distance = p.distanceTo(polygon.get(0));
		for(int i=1; i<polygon.size(); i++){
			double newdist = p.distanceTo(polygon.get(i));
			if(newdist < distance){
				nearestId = i;
				newdist = distance;
			}
		}
	
		Point p1,p2,p3;
		p1 = polygon.get(nearestId==0 ? polygon.size()-1 : nearestId-1);
		p2 = polygon.get(nearestId);
		p3 = polygon.get((nearestId==polygon.size()-1) ? 0 : nearestId+1);
		
		Point result = new Point((int) (0.5*p2.x+0.25*p1.x+0.25*p3.x),(int) (0.5*p2.y+0.25*p1.y+0.25*p3.y));
		
		return result;
	
	}
	
	@Override
	public void draw(){
		super.draw();
		if(GameManager.DEBUG){
			glDisable(GL_TEXTURE_2D);
			glEnable(GL_COLOR_MATERIAL);
				
			
			glLineWidth(2); 
			glColor3f(.8f, .8f, 0f);
			glBegin(GL_LINES);
			for(int i=0; i<polygon.size(); i++){
				Point p1 = polygon.get(i);
				Point p2 = polygon.get((i == polygon.size()-1) ? 0 : i+1);
				glVertex3f(p1.x, p1.y, 0);
				glVertex3f(p2.x, p2.y, 0);
				
			}
			
			glEnd();
			

		}
	}
	
	
}
