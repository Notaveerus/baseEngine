package baseEngine;

import java.awt.Rectangle;

public class WorldObject {

	public Rectangle collisionBox;
	
	public WorldObject(int x, int y, int width, int height){
		collisionBox = new Rectangle(x,y,width,height);
	}

}
