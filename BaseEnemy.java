package baseEngine;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;



public class BaseEnemy extends CharacterObject{
	protected boolean isAlive;
	boolean playerSeen;
	public ArrayList<Point> path;	
	int moveType;
	Polygon vision;
	Line2D curPath;
	ArrayList<WorldObject> nearby;
	int pathPoint;
	
	@SuppressWarnings("unused")
	public BaseEnemy(int moveType, ArrayList<Point> path, int x,int y){
		//0 = stationary, 1 = straight patrol, 2  = path
		this.moveType = moveType;
		if(moveType == 2 ){
			this.path = path;
		}
		position = new Point(x,y);
		boolean isAlive = true;
		boolean playerSeen = false;
		nearby = new ArrayList<WorldObject>();	
		pathPoint = 0;
	}
	
	
	boolean isAlive(){
		return isAlive;
	}
	
	
	public void findPath(Point move){
		if(moveType != 2){			
			curPath = new Line2D.Double(position, move);
			
			
			for(int i = 0; i<nearby.size();i++){
				
				if(checkPathIntersection(nearby.get(i).collisionBox)){
					getIntersectAngle(curPath, nearby.get(i).collisionBox);
				}
			}
		}
	}
	

	boolean checkPathIntersection(Rectangle collisionBox) {		
		if(curPath.intersects(collisionBox)){			
			return true;
		}		
		return false;		
	}
	
	
	void getNearby(ArrayList<WorldObject> object){
		int x = Math.max(0, position.x-300);
		int y = Math.max(0, position.y-300);		
		
		
		Rectangle objectDetection = new Rectangle(x,y,700,700);
		for(int i = 0;i<object.size();i++){
			if(objectDetection.intersects(object.get(i).collisionBox) || objectDetection.contains(object.get(i).collisionBox)){
				nearby.add(object.get(i));				
			}
		}
	}
	
	
	public void getIntersectAngle(Line2D curPath, Rectangle collisionBox){
		double x1 = curPath.getX1();
		double y1 = curPath.getY1();
		double x2 = curPath.getX2();
		double y2 = curPath.getY2();
		
		
		double xDistance = x1 - x2;		
		double yDistance = y1 - y2;
		double direction =(Math.atan2(yDistance, xDistance));
		
		double distance = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		System.out.println(1);
		for(int i = 0; i <distance; i +=10){
			x2 = (int) (x2+10*Math.cos(direction));
			y2 = (int) (y2+10*Math.sin(direction));
			Point endPoint = new Point((int)x2,(int)y2);
			if(collisionBox.contains(endPoint)){
				int newX = (int) (x2+10*Math.cos(direction+Math.toRadians(90)));
				int newY = (int) (y2+10*Math.sin(direction+Math.toRadians(90)));
				
				Point newPoint = new Point(newX,newY);
				System.out.println(newPoint);
				if(collisionBox.contains(newPoint)){
					newX = (int) (x2-10*Math.cos(direction+Math.toRadians(90)));
					newY = (int) (y2-10*Math.sin(direction+Math.toRadians(90)));
					path.add(newPoint);
					
				}
				break;
			}
		}
		
	}
	
	
	public void followPath(ArrayList<WorldObject> worldObjects){
		findPath(path.get(pathPoint));
		
		double x1 = curPath.getX1();
		double y1 = curPath.getY1();
		double x2 = curPath.getX2();
		double y2 = curPath.getY2();
		
		
		double xDistance = x2 - x1;		
		double yDistance = y2 - y1;
		double direction =(Math.atan2(yDistance, xDistance));
		position.x += (int) (x2+10*Math.cos(direction));
		position.y += (int) (y2+10*Math.sin(direction));
		getNearby(worldObjects);
		if(path.get(pathPoint) == position){
			pathPoint++;
			
		};
		
		
		
		
	}
	
	public void move(ArrayList<WorldObject> worldObjects){
		followPath(worldObjects);
	}
	
	
	
	boolean isPlayerSeen(){
		return playerSeen;
	}
}
