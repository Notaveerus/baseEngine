package baseEngine;


import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player extends CharacterObject{	
	double rotationAngle;
	AffineTransform identity;
	InputHandler input;
	int windowWidth;
	int windowHeight;
	Point mousePosition;
	int imgNum;	
	float xDistance;
	float yDistance;
	Point rotatePoint;
	
	public Player(int windowWidth, int windowHeight, InputHandler input){		
		this.sprite=defaultImg;
		try {					
			defaultImg.add(ImageIO.read(new File("./res/images/w1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rotatePoint = position;
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.input = input;
		this.identity = new AffineTransform();		
		
	}
	
	
	
	public void update(Point mousePosition, float interpolation){		
		@SuppressWarnings("unused")
		String facing = getFacing(mousePosition);
		//use as anim key once implemented
		if(input.isKeyDown(KeyEvent.VK_D)){			
			position.x += 5*interpolation;
			sprite = imgLoader.getAnimation("w");
			
		}		
		else if(input.isKeyDown(KeyEvent.VK_A)){
			position.x -= 5*interpolation;
			sprite =imgLoader.getAnimation("w");
		}
		else if(input.isKeyDown(KeyEvent.VK_W)){			
			position.y -= 5*interpolation;
			sprite =imgLoader.getAnimation("w");
		}
		else if(input.isKeyDown(KeyEvent.VK_S)){			
			position.y += 5*interpolation;
			sprite =imgLoader.getAnimation("w");
		}
		else{
			sprite = defaultImg;
		}		
		//NEW SHIT RIGHTE HERE
		int xCentre = position.x + (width/2);
		int yCentre = position.y + (height/2);
		double pythag = (width*width + (height/2)*(height/2))/8;
		double radius = Math.sqrt(pythag);
		
		double angle = calcDirection(mousePosition);
		
		xHitbox[0] = xCentre - (int) (Math.cos(angle + Math.toRadians(45))* radius);
		xHitbox[1] = xCentre + (int) (Math.sin(angle + Math.toRadians(45))* radius);
		xHitbox[2] = xCentre + (int) (Math.cos(angle + Math.toRadians(45))* radius);
		xHitbox[3] = xCentre - (int) (Math.sin(angle + Math.toRadians(45))* radius);
		yHitbox[0] = yCentre - (int) (Math.sin(angle + Math.toRadians(45)) * radius);
		yHitbox[1] = yCentre - (int) (Math.cos(angle + Math.toRadians(45)) * radius);
		yHitbox[2] = yCentre + (int) (Math.sin(angle + Math.toRadians(45)) * radius);
		yHitbox[3] = yCentre + (int) (Math.cos(angle + Math.toRadians(45)) * radius);
		hitbox = new Polygon(xHitbox, yHitbox, 4);
		
		this.mousePosition = mousePosition;
		
		//int x =  (int) (position.x + (Math.cos((angle)) * (rotatePoint.x - position.x)- Math.sin((angle)) * (rotatePoint.y - position.y)));
		//int y =  (int) (position.y + (Math.sin((angle)) * (rotatePoint.x - position.x)- Math.cos((angle)) * (rotatePoint.y - position.y)));
		//rotatePoint = new Point(x,y);
		System.out.println(angle);
	}
	
	
	

	

	public double calcDirection(Point mousePosition) {
		int mouseX = 0;
		int mouseY = 0;
		if(mousePosition != null){
			mouseX = (int) mousePosition.getX();
			mouseY = (int) mousePosition.getY();
		}
			

		xDistance = (position.x+(width/2)) - mouseX;		
		yDistance = (position.y+(height/2)) - mouseY;
		double rotationAngle =(Math.atan2(yDistance, xDistance))-Math.toRadians(90);
		return rotationAngle;
		
	}
	
	public Polygon action() {
		sprite = imgLoader.getAnimation("a");
		int xPath = this.mousePosition.x - position.x;
		int yPath = this.mousePosition.y - position.y;
		double angle = calcDirection(mousePosition);
		
		int[] xPoly = new int[4];
		int[] yPoly = new int[4];
		xPoly[0] = position.x;
		yPoly[0] = position.y;
		int x2 = (int) (position.x - 50*Math.cos(angle));
		int y2 = (int) (position.x - 50*Math.cos(angle));
		xPoly[1] = x2;
		yPoly[1] = y2;
		
		int x3 = (int) (x2 - width*Math.cos(90));
		int y3 = (int) (y2 - width*Math.cos(90));
		xPoly[2] = x3;
		yPoly[2] = y3;
		
		int x4 = (int) (x3 - 50*Math.cos(angle));
		int y4 = (int) (y3 - 50*Math.cos(angle));
		xPoly[3] = x4;
		yPoly[3] = y4;
		
		
	
		
		Polygon attack = new Polygon(xPoly,yPoly,xPoly.length);
		
		return attack;
		
	}
	
	
	
}
