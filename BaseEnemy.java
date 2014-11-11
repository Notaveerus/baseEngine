package baseEngine;

import java.awt.Point;
import java.awt.Polygon;



public class BaseEnemy extends CharacterObject{
	protected boolean isAlive;
	boolean playerSeen;
	Point[]path;	
	int moveType;
	Polygon vision;
	Polygon curPath;
	
	public BaseEnemy(int moveType, Point[] path){
		//0 = stationary, 1 = straight patrol, 2  = path
		this.moveType = moveType;
		if(moveType == 2 ){
			this.path = path;
		}
		boolean isAlive = true;
		boolean playerSeen = false;
		
	}
	
	boolean isAlive(){
		return isAlive;
	}
	
	void findPath(Point move){
		if(moveType != 2){
			
			Polygon curPath = new Polygon(new int[]{position.x , move.x},new int[]{position.y,move.y},2);
			if(checkPathIntersection(curPath)){
				
			}
		}
	}

	boolean checkPathIntersection(Polygon intersect) {
		Area pathArea = new Area(curPath); 
		if(curPath.intersects(intersect)){
			
		}
		return false;
		
	}
	
	boolean isPlayerSeen(){
		return playerSeen;
	}
}
