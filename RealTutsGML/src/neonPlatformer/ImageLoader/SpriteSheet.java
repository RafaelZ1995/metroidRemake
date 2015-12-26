package neonPlatformer.ImageLoader;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage spriteSheet;

	// helper for getNextFrame
	private int previousFrame;

	public SpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public BufferedImage getImageAt(int col, int row, int width, int height) {
		return spriteSheet.getSubimage((col * width) - width, (row * height) - height, width, height);

	}

	// get image from Spritesheet at exact x,y location
	public BufferedImage getUniImage(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);

	}

	public BufferedImage getSprite() {
		return spriteSheet;
	}

	public BufferedImage[] loadIntoBufferedImageArray(int numFrames) {
		BufferedImage[] frames = new BufferedImage[numFrames];

		previousFrame = 0;
		for (int i = 0; i < numFrames; i++) {
			//System.out.println("i: " + i);
			frames[i] = getNextFrame(previousFrame);
		}

		return frames;
	}

	// goes through the image pixel by pixel to find the next image in the
	// spritesheet
	// useful when all the frames are not of the same size
	public BufferedImage getNextFrame(int searchFrom) {
		int leftEdgeCol = searchFrom;
		boolean found = false;

		// get left edge starting point of frame
		for (int col = leftEdgeCol; col < spriteSheet.getWidth(); col++) {
			for (int row = 0; row < spriteSheet.getHeight(); row++) {

				if (spriteSheet.getRGB(col, row) != 0) {
					//System.out.println("col found " + col);
					leftEdgeCol = col;
					found = true;
					break;
				}
			}

			if (found)
				break;
		}

		int rightEdgeCol = -1;
		found = false;
		boolean foundEmptyCol = true;
		// get right edge end point of frame
		for (int col = leftEdgeCol; col < spriteSheet.getWidth(); col++) {
			foundEmptyCol = true;

			for (int row = 0; row < spriteSheet.getHeight(); row++) {

				if (spriteSheet.getRGB(col, row) != 0)
					foundEmptyCol = false;
			}

			// if rightEdge of frame or if we are at the end of picture
			if (foundEmptyCol) {
				//System.out.println("rightEdgeCol " + col);
				rightEdgeCol = col - 1;
				break;
			}
		}
		
		// if foundEmptyCol is still false after for loop
		if (foundEmptyCol == false){
			rightEdgeCol = spriteSheet.getWidth() - 1;
			//System.out.println("rightEdgeCol " + rightEdgeCol);
			}

		//update previousFrame
		previousFrame = rightEdgeCol + 1;
		
		int widthFound = rightEdgeCol - leftEdgeCol;
		//System.out.println("widthFound: " + widthFound);
		//System.out.println();
		return spriteSheet.getSubimage(leftEdgeCol, 0, widthFound, spriteSheet.getHeight());
		
		
	}

	/*
	public static void main(String[] args) {
		ImageLoader loader = new ImageLoader();
		BufferedImage test = loader.loadImage("/goingRight.png");
		SpriteSheet frameTest = new SpriteSheet(test);
		frameTest.loadIntoBufferedImageArray(10);
	}
	*/
}
