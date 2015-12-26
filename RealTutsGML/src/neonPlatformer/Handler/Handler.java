package neonPlatformer.Handler;

import neonPlatformer.Game.Game;

public class Handler {
	Game game;

	public Handler(Game game) {
		this.game = game;
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		//System.out.println(game.gethHeight());
		return game.getHeight();
	}
}
