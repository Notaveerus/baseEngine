package baseEngine;


import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
	boolean canAttack;
	
	public Player(int windowWidth, int windowHeight, InputHandler input){		
		this.sprite=defaultImg;
		try {					
			defaultImg.add(ImageIO.read(new File("./res/images/w1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.input = input;
		this.identity = new AffineTransform();		
		canAttack = true;
		
	}
	
	
	
	public void update(Point mousePosition, float interpolation){		
		@SuppressWarnings("unused")
		//use as anim key once implemented
		String facing = getFacing(mousePosition);
		
		hitbox = calculateHitbox(position.x, position.y, width, height);
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
		
		
		
		this.mousePosition = mousePosition;
		
		
		
	}
	
	Polygon calculateHitbox(int x, int y, int width, int height){
		int xCentre = x + (width/2);
		int yCentre = y + (height/2);
		double pythag = (width*width + height*height)/8;
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
		return new Polygon(xHitbox, yHitbox, 4);
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
	
	public void action() {
		if(canAttack){			
			attack = calculateHitbox(xHitbox[0], yHitbox[0], width, 40);
			canAttack = false;
			Timer timer = new Timer();
			 timer.schedule(new TimerTask(){
				@Override
				public void run() {
					attack = null;
					
				}			 
			 },500);
			 ;
		}
		if(canAttack == false){
			Timer attackDelay = new Timer();
			attackDelay.schedule(new TimerTask(){
				public void run(){
					canAttack = true;
				}
			}, 1000);
		}
	}
	
	
	
}
