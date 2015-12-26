package neonPlatformer.Handler;

import java.awt.image.BufferedImage;

import neonPlatformer.ImageLoader.ImageLoader;
import neonPlatformer.ImageLoader.SpriteSheet;


/*
 * AssetsHandler loads up all different sprites for any player action in the game
 * 
 * To set up a new sprite/animation:
 * 		1) add a new private final int to keep track of which sprite it is and how many frames it is composed of
 * 		2) load the individual sprites into the SpriteSheet array (in constructor)
 * 		3) add an else if clause in getPlayerSprites() so that other classes can get the specified BufferedImage array
 * 	
 */
public class AssetsHandler {

	// All assets for player (contains all different spritesheets for all
	// animations)
	private SpriteSheet[] playerSprites;

	// STANDING
	private final int STANDING_LEFT = 0, NUM_STANDING_LEFT_FRAMES = 6;
	private final int STANDING_RIGHT = 1, NUM_STANDING_RIGHT_FRAMES = 5;

	// MOVING LEFT OR RIGHT
	private final int GOING_LEFT = 2, NUM_GOING_LEFT_FRAMES = 10;
	private final int GOING_RIGHT = 3, NUM_GOING_RIGHT_FRAMES = 10;

	// JUMPING WITH SPIN
	private final int SPIN_JUMP_LEFT = 4, NUM_SJUMP_LEFT_FRAMES = 9;
	private final int SPIN_JUMP_RIGHT = 5, NUM_SJUMP_RIGHT_FRAMES = 9;
	
	// STILL JUMPING
	private final int STILL_JUMP_LEFT = 6, NUM_STJUMP_LEFT_FRAMES = 2;
	private final int STILL_JUMP_RIGHT = 7, NUM_STJUMP_RIGHT_FRAMES = 2;
	
	// STILL FALLING
	private final int STILL_FALL_LEFT = 8, NUM_STFALL_LEFT_FRAMES = 4;
	private final int STILL_FALL_RIGHT = 9, NUM_STFALL_RIGHT_FRAMES = 4;
	
	// CROUCHING
	private final int CROUCHING_LEFT = 10, NUM_CROUCHING_LEFT_FRAMES = 6;
	private final int CROUCHING_RIGHT = 11, NUM_CROUCHING_RIGHT_FRAMES = 6;
	
	// Morph Ball
	private final int MORPH_BALL_LEFT = 12, NUM_MORPH_BALL_LEFT = 11;
	private final int MORPH_BALL_RIGHT = 13, NUM_MORPH_BALL_RIGHT = 11;
	

	// number of sprites/animations for player
	private final int NUM_PLAYER_SPRITES = 14;

	public AssetsHandler() {

		// used to load the images
		ImageLoader loader = new ImageLoader();

		// load player sprites
		playerSprites = new SpriteSheet[NUM_PLAYER_SPRITES];

		// STANDING
		playerSprites[STANDING_LEFT] = new SpriteSheet(loader.loadImage("/standingLeft.png"));
		playerSprites[STANDING_RIGHT] = new SpriteSheet(loader.loadImage("/standingRight.png"));

		// MOVING LEFT OR RIGHT
		playerSprites[GOING_LEFT] = new SpriteSheet(loader.loadImage("/goingLeft.png"));
		playerSprites[GOING_RIGHT] = new SpriteSheet(loader.loadImage("/goingRight.png"));

		// JUMPING WITH SPIN
		playerSprites[SPIN_JUMP_LEFT] = new SpriteSheet(loader.loadImage("/spinJumpLeft.png"));
		playerSprites[SPIN_JUMP_RIGHT] = new SpriteSheet(loader.loadImage("/spinJumpRight.png"));

		// JUMPING STILL
		playerSprites[STILL_JUMP_LEFT] = new SpriteSheet(loader.loadImage("/jumpingLeft.png"));
		playerSprites[STILL_JUMP_RIGHT] = new SpriteSheet(loader.loadImage("/jumpingRight.png"));
		
		// FALLING W/O SPIN
		playerSprites[STILL_FALL_LEFT] = new SpriteSheet(loader.loadImage("/fallingLeft.png"));
		playerSprites[STILL_FALL_RIGHT] = new SpriteSheet(loader.loadImage("/fallingRight.png"));
		
		// CROUCHING
		playerSprites[CROUCHING_LEFT] = new SpriteSheet(loader.loadImage("/crouchingLeft.png"));
		playerSprites[CROUCHING_RIGHT] = new SpriteSheet(loader.loadImage("/crouchingRight.png"));
		
		// MORPH BALL
		playerSprites[MORPH_BALL_LEFT] = new SpriteSheet(loader.loadImage("/morphBallLeft.png"));
		playerSprites[MORPH_BALL_RIGHT] = new SpriteSheet(loader.loadImage("/morphBallRight.png"));
	}

	public BufferedImage[] getPlayerSprites(int assetNUM) {

		// standing Left
		if (assetNUM == STANDING_LEFT) {
			return playerSprites[STANDING_LEFT].loadIntoBufferedImageArray(NUM_STANDING_LEFT_FRAMES);
		}
		// standing Right
		else if (assetNUM == STANDING_RIGHT) {
			return playerSprites[STANDING_RIGHT].loadIntoBufferedImageArray(NUM_STANDING_RIGHT_FRAMES);
		}
		// moving left
		else if (assetNUM == GOING_LEFT) {
			return playerSprites[GOING_LEFT].loadIntoBufferedImageArray(NUM_GOING_LEFT_FRAMES);
		}
		// moving Right
		else if (assetNUM == GOING_RIGHT) {
			return playerSprites[GOING_RIGHT].loadIntoBufferedImageArray(NUM_GOING_RIGHT_FRAMES);
		}
		// spin jumping left
		else if (assetNUM == SPIN_JUMP_LEFT) {
			return reverseArray(playerSprites[SPIN_JUMP_LEFT].loadIntoBufferedImageArray(NUM_SJUMP_LEFT_FRAMES));
		}
		// spin jumping right
		else if (assetNUM == SPIN_JUMP_RIGHT) {
			return playerSprites[SPIN_JUMP_RIGHT].loadIntoBufferedImageArray(NUM_SJUMP_RIGHT_FRAMES);
		}
		// still jumping left
		else if (assetNUM == STILL_JUMP_LEFT) {
			return playerSprites[STILL_JUMP_LEFT].loadIntoBufferedImageArray(NUM_STJUMP_LEFT_FRAMES);
		}
		// still jumping right
		else if (assetNUM == STILL_JUMP_RIGHT) {
			return playerSprites[STILL_JUMP_RIGHT].loadIntoBufferedImageArray(NUM_STJUMP_RIGHT_FRAMES);
		}
		// still falling left
		else if (assetNUM == STILL_FALL_LEFT) {
			return playerSprites[STILL_FALL_LEFT].loadIntoBufferedImageArray(NUM_STFALL_LEFT_FRAMES);
		}
		// still falling right
		else if (assetNUM == STILL_FALL_RIGHT) {
			return playerSprites[STILL_FALL_RIGHT].loadIntoBufferedImageArray(NUM_STFALL_RIGHT_FRAMES);
		}
		// crouching left
		else if (assetNUM == CROUCHING_LEFT) {
			return playerSprites[CROUCHING_LEFT].loadIntoBufferedImageArray(NUM_CROUCHING_LEFT_FRAMES);
		}
		// crouching right
		else if (assetNUM == CROUCHING_RIGHT) {
			return playerSprites[CROUCHING_RIGHT].loadIntoBufferedImageArray(NUM_CROUCHING_RIGHT_FRAMES);
		}
		// morph ball left
		else if (assetNUM == MORPH_BALL_LEFT) {
			return reverseArray(playerSprites[MORPH_BALL_LEFT].loadIntoBufferedImageArray(NUM_MORPH_BALL_LEFT));
		}
		// morph ball right
		else if (assetNUM == MORPH_BALL_RIGHT) {
			return playerSprites[MORPH_BALL_RIGHT].loadIntoBufferedImageArray(NUM_MORPH_BALL_RIGHT);
		}
		
		System.out.println("returned null");
		return null;

	}

	private BufferedImage[] reverseArray(BufferedImage[] temp) {
		BufferedImage[] reversedArr = new BufferedImage[temp.length];
		for (int i = 0, k = temp.length - 1; i < temp.length; i++, k--) {
			reversedArr[i] = temp[k];
		}
		return reversedArr;
	}
}
