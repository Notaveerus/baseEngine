package baseEngine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.ArrayList;

import javax.swing.JFrame;


public class GameWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	boolean isRunning;
	int windowWidth;
	int windowHeight;
	Player player;
	BufferedImage backBuffer;
	Insets insets;
	InputHandler input;
	Point mousePosition;
	Point mouseClick;
	ArrayList<BufferedImage> spriteList;
	BufferedImage currSprite;
	float animCount;
	
	
	public GameWindow(){
		this.isRunning = true;		
		this.windowWidth = 1280;
		this.windowHeight = 720;		
		this.backBuffer = new BufferedImage(this.windowWidth, this.windowHeight, BufferedImage.TYPE_INT_RGB);
		input = new InputHandler(this);
		
	}
	
	public static void main(String[] args){
		GameWindow game = new GameWindow();
		game.run();
		System.exit(0);
	}
	
	//Starts the game and runs in a loop
	public void run(){
		 initialize(); 
	        int TICKS_PER_SECOND = 60;
	        int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	        int MAX_FRAMESKIP = 5;
	        long next_game_tick = System.currentTimeMillis();
	        
	        int loops;
	        float interpolation;
	        while(isRunning) 
	        { 
	        		loops = 0;
	        		while((System.currentTimeMillis() - next_game_tick) < SKIP_TICKS && loops < MAX_FRAMESKIP){     			       		
	        			draw();
	        			anim();
	        			
	        			next_game_tick += SKIP_TICKS;
	        			loops++;
	        		}
	                
	        		interpolation =  (next_game_tick-System.currentTimeMillis())/SKIP_TICKS;
	        		next_game_tick =System.currentTimeMillis();
	        		update(interpolation);
	        		
	                
	        } 
	        
	        setVisible(false); 
	}
	
	
	
	//Set everything required for the game to run
	void initialize(){
		setTitle("Game Window");
		setUndecorated(false);
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		insets = getInsets();
		setSize(insets.left + windowWidth + insets.right, 
                insets.top + windowHeight + insets.bottom);
		
		player = new Player(windowWidth, windowHeight, input); 
		animCount =0;		
		
	}
	
	
	
	//Check for input, move things, etc.
	void update(float interpolation){
		player.update(mousePosition, interpolation);
		if(mouseClick != null){
			player.action();
		}
		if(player.attack!=null){
			player.attack = player.calculateHitbox(player.xHitbox[0], player.yHitbox[0], player.width, 40);
		}
		
	}
	
	
	
	//Draws everything
	void draw(){
		Graphics g = getGraphics();
		Graphics2D bbg = (Graphics2D) backBuffer.getGraphics();
		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);
		int mouseX = 0;
		int mouseY = 0;
		if(mousePosition!= null ){
			mouseX = (int) mousePosition.getX();
			mouseY = (int) mousePosition.getY();
		}
	
		double rotationAngle = player.calcDirection(mousePosition);
		AffineTransform trans = new AffineTransform();
		bbg.setColor(Color.BLACK);
		bbg.drawLine(player.getX(), player.getY(), mouseX, mouseY);
		
		bbg.draw(player.hitbox);
		if(player.attack != null){
			bbg.draw(player.attack);
		}
			
		trans.rotate(Math.toRadians(0),0,0);
		bbg.rotate(rotationAngle, player.getX()+(player.getWidth()/2), player.getY()+(player.getHeight()/2));		
		bbg.drawImage(currSprite, player.getX(),player.getY(),player.getWidth(),player.getHeight(), this);		
		bbg.setTransform(trans);
		//+(player.getWidth()/2)
		//+(player.getHeight()/2)
		
		
		g.drawImage(backBuffer, insets.left, insets.top, this);
		
	}
	
	void anim(){
		
		if(animCount==0){
			spriteList = player.getSprite();
		}
		if(spriteList!= null && animCount<spriteList.size()){
			currSprite = spriteList.get((int)animCount);
			animCount+= 0.1;
		}
		else{
			animCount = 0;
		}
		
		
	}
	
	
	

	
	
}
