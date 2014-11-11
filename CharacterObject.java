package baseEngine;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharacterObject {
	
	ArrayList<BufferedImage> img = new ArrayList<BufferedImage>();
	int width;
	int height;
	Point position;
	ArrayList<BufferedImage> sprite;
	ArrayList<BufferedImage> defaultImg;
	protected ImageLoader imgLoader;
	//Rectangle hitbox;
	
	//NEW
	int[] xHitbox = new int[4];
	int[] yHitbox = new int[4];
	Polygon hitbox;
	
	public CharacterObject(){
		this.width = 100;
		this.height = 100;
		this.position = new Point(100,100);
		this.imgLoader = new ImageLoader();
		this.defaultImg = new ArrayList<BufferedImage>();
		this.sprite=defaultImg;
		//this. hitbox = new Rectangle(position.x, position.y,width,height);
		//NEW
		this.hitbox = new Polygon(xHitbox, yHitbox, 4);
	}
	
	public int getX(){
		return position.x;
	}
	public int getY(){
		return position.y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void update(){
		
	}
	public ArrayList<BufferedImage> getSprite(){
		return sprite;
	}
	
	public String getFacing(Point mouse){
		int directionX = 0;
		int directionY = 0;
		if(mouse !=null){
			directionX = mouse.x - getX();
			directionY = mouse.y - getY();
		}
		
		
		String xFacing = "";
		String yFacing = "";
		String facing;
		if(directionX >0){
			xFacing = "e";
		}
		else{
			xFacing= "w";
		}
		if(directionY>0){
			yFacing = "s";
		}
		else{
			yFacing = "n";
		}
		
		if(directionX>directionY){
			facing = xFacing;
		}
		else{
			facing = yFacing;
		}
		
		return facing;
	}
	
	public void calcDirection() {
		
	}
	
	public boolean collision(Rectangle attack){
		if(hitbox.intersects(attack)){
			return true;
		}
		
		return false;
		
	}
	
	public Polygon action() {
		sprite = imgLoader.getAnimation("a");
		
		Polygon attack = new Polygon();
		return attack;
		
	}
}
