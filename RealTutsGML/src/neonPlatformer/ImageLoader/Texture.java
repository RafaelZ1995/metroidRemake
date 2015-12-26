package neonPlatformer.ImageLoader;

import java.awt.image.BufferedImage;

//holds the subImage of a spritesheet
public class Texture {

	private SpriteSheet bs,ps;
	public static BufferedImage blocks_sheet;
	private BufferedImage player_sheet;
	
	//tiles
	public BufferedImage[] blocks = new BufferedImage[2];
	public BufferedImage[] player = new BufferedImage[1];
	
	public Texture(){
		//load images
		ImageLoader loader = new ImageLoader();
		
		blocks_sheet = loader.loadImage("/RealTutBlocks.png");
		//player_sheet = loader.loadImage("load path here");
		
		bs = new SpriteSheet(blocks_sheet);
		//ps = new SpriteSheet(player_sheet);
		
		//initialize/load each tile
		getTextures();
	}
	
	private void getTextures(){	
		player[0] = bs.getUniImage(32, 0, 64, 46);
		
		//grass block
		blocks[0] = bs.getImageAt(1, 1, 32, 32);
		//dirt block at (col,row.. of dimensions 32, 32)
		blocks[1] = bs.getImageAt(1, 2, 32, 32); // R:128 G:0 B:0
	}
}
