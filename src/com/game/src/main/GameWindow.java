package com.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameWindow extends Canvas {

	private static final long serialVersionUID = 4477322821623755143L;

	public GameWindow(int WIDTH, int HEIGHT, int SCALE, String TITLE, Game game) {
		JFrame frame = new JFrame(TITLE);
		
		frame.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		try { // "res/pistol-gun.png" , "res/ak47.png"
			frame.setIconImage(ImageIO.read(new File("res/pistol-gun.png")));
		} catch (IOException e) {
			frame.setIconImage(null);
			e.printStackTrace();
			System.out.println("ICON ERROR: Icon image not found. (Default Icon is set.)");
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		game.start();
	}	
	
}
