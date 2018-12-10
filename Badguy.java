package game;

import java.awt.Image;
import java.awt.Toolkit;

public class Badguy {

	private int xCoord = 0;
	private int yCoord = 0;
	private int width = 10;
	private int height = 10;
	private Image img;
	
	/**
	 * Good guy default constructor
	 */
	public Badguy() {
		setxCoord(10);
		setyCoord(10);
		setWidth(10) ;
		setHeight(10) ;
		setImg("../files/Goodguy.png");
	}

 /**
  * Goodguy overloaded constructor
  * @param x initial x location 
  * @param y initial y location
  * @param w initial width 
  * @param h initial height
  */
	public Badguy(int x, int y, int w, int h, String imgpath) {
	setxCoord(x);
	setyCoord(y);
	setWidth(w);
	setHeight(h);
	setImg(imgpath);
	
	}
	

	public void setImg(String imgpath) {
		this.img = Toolkit.getDefaultToolkit().getImage(imgpath);
	}
	
	public Image getImg() {
		return img;
	}
	
	public int getxCoord() {
		return this.xCoord;
	}
	
	public int getyCoord() {
		return this.yCoord;
	}
	
	public void setxCoord(int x) {
		this.xCoord = x;
	}
	
	
	public void setyCoord(int y) {
		this.yCoord = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	public void setImg(Image img) {
		this.img = img;
	}
}
