package neonPlatformer.Jframe;

import java.awt.Dimension;

import javax.swing.JFrame;

import neonPlatformer.Game.Game;

public class Window {

	

	public Window(int width, int height, String title, Game game) {
		
		game.setPreferredSize(new Dimension(width, height));
		game.setMaximumSize(new Dimension(width, height));
		game.setMinimumSize(new Dimension(width, height));

		JFrame frame = new JFrame(title);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // null so it starts in the middle of
											// screen
		frame.requestFocus();
		game.setWidth(width);
		game.setHeight(height);
		game.Start();
	}
}
