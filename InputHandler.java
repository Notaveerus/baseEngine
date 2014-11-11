package baseEngine;

import java.awt.event.*;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;


public class InputHandler implements KeyListener, MouseMotionListener, MouseListener{
	boolean[] keys;
	Point mousePosition;
	GameWindow game;
	
	public InputHandler(GameWindow c){
		c.addKeyListener(this);
		c.addMouseMotionListener(this);
		c.addMouseListener(this);
		game = c;
		keys = new boolean [256];
		
	}
	
	public boolean isKeyDown(int keyCode){
		
		if (keyCode > 0 && keyCode <256){
			return keys[keyCode];
		}
		return false;
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() > 0 && e.getKeyCode() < 256) 
        { 
                keys[e.getKeyCode()] = true; 
        } 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() > 0 && e.getKeyCode() <256){
			keys[e.getKeyCode()] = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		PointerInfo a = MouseInfo.getPointerInfo();
		game.mousePosition = new Point(a.getLocation());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		PointerInfo a = MouseInfo.getPointerInfo();
		game.mousePosition = new Point(a.getLocation());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		game.mouseClick = new Point(e.getX(), e.getY());
		mouseMoved(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		game.mouseClick = null;
		
	}


}
