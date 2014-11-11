package baseEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageLoader {
	private ArrayList<BufferedImage>  animation;
	
	public ImageLoader(){		
		animation = new ArrayList<BufferedImage>();	
		
	}
	
	public ArrayList<BufferedImage> getAnimation(String name){
		animation.clear();
		this.getClass().getClassLoader().getResourceAsStream("/images");
		File[] file = new File("./res/images").listFiles();		
		
		for(int j=0;j<file.length;j++){
			if(file[j].getName().contains(name)){
				
				try {					
					animation.add(ImageIO.read(file[j]));
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
		}
		
		return animation;
	}
}
