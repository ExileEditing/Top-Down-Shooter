package com.game.src.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -7228164263489589306L;
	public static final int WIDTH = 320, HEIGHT = WIDTH/12*9, SCALE = 2;
	public final String TITLE = "Top Down Shooter";

	private boolean running = false;
	private Thread thread;
	
	//private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	
	// temporary
	private BufferedImage player;
	
	int SMALL_SCALE = 48;
	
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/sprite_sheet.png");	
		}catch(IOException e){
			e.printStackTrace();
		}
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		player = ss.grabImage(1, 1, 253, 216);
	}
	
	/* Creates a new window for the game
	 *  GameWindow is from GameWindow.java
	 */
	
	public Game() {
		new GameWindow(WIDTH, HEIGHT, SCALE, TITLE, this);
		System.out.println("[" +TITLE + " has started.]");
	}
	
	/* Called in GameWindow.java
	 * Creates and starts a thread
	 * Changes running to TRUE (because the game is now running)
	 */
	
	public synchronized void start(){
		if(running)
			return;
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	/* Called in run when running is FALSE
	 * Stops the game and if it fails then print an error
	 */
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	// Game Loop
	
	public void run(){
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("Ticks: " + updates + ", FPS: " + frames);
				updates = 0;
				frames = 0;
			}			
		}
		stop();
	}	
	
	private void tick(){
		
	}
			
	AffineTransform transform = new AffineTransform();
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;			
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
	
		// background
		g.clearRect(0, 0, getWidth(), getHeight());
		//g.drawImage(image, 0, 0, getWidth(), getHeight(), this);    
		
		// attempting to draw an image and rotate it
		g2d.translate(WIDTH+8, HEIGHT-48);
		g2d.rotate(1);
		g2d.drawImage(player, 0, 0, SMALL_SCALE, SMALL_SCALE, this);
		//g.drawImage(player, WIDTH-24, HEIGHT-38, SMALL_SCALE, SMALL_SCALE, this);
		
		g.dispose();
		bs.show();
	}

	public static void main(String args[]){
		new Game();
	
	}

}
