package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import sun.audio.*;


public class MyCanvas extends Canvas implements KeyListener {
	
	// global variables - accessible in all methods 
	Goodguy link = new Goodguy(10,10,100,100,"files/Goodguy.png");
	Goodguy fire = new Goodguy(0,0,80,80, "files/Explosion.png");
	LinkedList badguys = new LinkedList();
	LinkedList grenade = new LinkedList();
	boolean showexp = false;
	boolean start = false;
	boolean GameOver = false;
	/**
	 * MyCanvas drawing canvas inherits from java.awt.Canvas
	 * @author scott.mercier
	 * @since Oct. 17, 2018
	 * @param no parameters, default constructor
	 */
	public MyCanvas() {
		this.setSize(800,800); // set same size as MyScreen
		this.addKeyListener(this); // add the listener to your canvas
		playIt("files/background.wav");
		TimerTask repeatedTask = new TimerTask() {
            public void run() {
                for(int i = 0; i < badguys.size(); i++) {// draw bad guys
                    Badguy bg = (Badguy) badguys.get(i);
                    bg.setxCoord(bg.getxCoord() - 2);
                }
                repaint();
            }
        };
        Timer timer = new Timer("Timer");
        long delay  = 40L;
        long period = 40L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
	
		Random rand =new Random();
		int winwidth = this.getWidth();
		int winheight = this.getHeight();
		for(int i = 0; i<10; i++) {
			Badguy bg = new Badguy(750, rand.nextInt(winheight),50,50,"files/Badguy.png");
			Rectangle r = new Rectangle(link.getxCoord(), link.getyCoord(), link.getWidth(), link.getHeight());
			Rectangle bgr = new Rectangle(bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight());

			if (r.intersects(bgr)) {
				System.out.println("badguy on top of link");
				continue;
			}
			badguys.add(bg);
		}
	}
		
	public void playIt(String filename) {
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
	}



	@Override
	public void paint(Graphics g) {
		if (badguys.size()==0) {
			this.setBackground(Color.BLACK);
			g.setColor(Color.WHITE);
			Font myFont = new Font("Arial", 5, 30);
			g.setFont(myFont);
			g.drawString("You have destroyed all of the bush campers!", 0, 400);
		}
		else if (start == true && GameOver == false) {
			Badguy grass = new Badguy(0,0,800,800, "files/Grass.png");
			g.drawImage(grass.getImg(), grass.getxCoord(), grass.getyCoord(), grass.getHeight(), grass.getWidth(), this);
			g.drawImage(link.getImg(), link.getxCoord(), link.getyCoord(), link.getHeight(), link.getWidth(), this);
			for (int i = 0; i < badguys.size(); i++) {
			
				Badguy bg = (Badguy) badguys.get(i);
			g.drawImage(bg.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getWidth(),bg.getHeight(),this);
			Rectangle r = new Rectangle(bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight());
			
		
			if (bg.getxCoord() <= 2) {
				
				start = false;

				GameOver = true;
			}

		
		for(int j=0; j < grenade.size(); j++) {
				Projectile k = (Projectile) grenade.get(j);
				if (k.getxCoord()>this.getWidth()) {grenade.remove(k);}
				k.setxCoord(k.getxCoord()+1);
				
				g.drawImage(k.getImg(), k.getxCoord(), k.getyCoord(), k.getWidth(),k.getHeight(),this);
				int h;
				int w;
				Rectangle kr = new Rectangle(k.getxCoord(),k.getyCoord(),k.getWidth(),k.getHeight());
			

				if(kr.intersects(r)) {
					badguys.remove(i); 
					grenade.remove(j);
					g.drawImage(fire.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getHeight(), bg.getWidth(), this);
					playIt("files/Soundeffect.wav");
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
			 
					}
					
				repaint();
			}
		}
	}
}
	else if (start == false && GameOver == true) {

		g.setColor(Color.WHITE);
		Font myFont = new Font("Comic Sans", 0, 50);
		g.setFont(myFont);
		g.drawString("You lost, better luck next time.", 0, 400);
		this.setBackground(Color.BLACK);
	}

	else if (!GameOver) {
		this.setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		Font myFont = new Font("Comic Sans", 0, 60);
		g.setFont(myFont);
		g.drawString("Press Enter to Start", 50, 400);
	}
}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== 32) {
			Projectile grenades = new Projectile(link.getxCoord(), link.getyCoord(),50, 50, "files/grenade.png");
			grenade.add(grenades);
		}
		link.moveIt(e.getKeyCode(),this.getWidth(),this.getHeight());
		if (e.getKeyCode()==10) {
			start = true;
		}
	
		repaint();	
	}		

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
