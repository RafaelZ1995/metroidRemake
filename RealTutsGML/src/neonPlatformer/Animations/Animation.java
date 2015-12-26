package neonPlatformer.Animations;

import java.awt.image.BufferedImage;

/*
 * An instance of this class will take care of one single animation of the player or any other entity
 * For example
 * 				one instance will take care of only the attacking animation of the player.
 * Thus, to implement all the animations of the character... you have to create an object of this class for
 * every single different animation.
 */
public class Animation {

	// speed at which it goes through the frames
	private int delay;
	private long lastTime, timer;

	// all frames that make up the animation
	private BufferedImage[] frames;

	private int currentFrame;

	public Animation(BufferedImage[] frames, int delay) {
		this.frames = frames;
		this.delay = delay;
		currentFrame = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}

	public BufferedImage getCurrentFrame() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (timer > delay) {
			currentFrame++;
			timer = 0;
		}

		// return frames[4];
		return nextFrame();
	}

	public BufferedImage playOnce() {
		timer += System.currentTimeMillis() - lastTime;

		if (currentFrame >= frames.length - 1) {
			return frames[frames.length - 1];
		}

		if (timer > delay) {
			
			currentFrame++;
			timer = 0;
		}

		return frames[currentFrame];
	}

	public BufferedImage playOnceAndRepeatFrom(int frame) {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();

		if (currentFrame >= frames.length - 1) {
			currentFrame = frame;
		}

		if (timer > delay) {
			
			currentFrame++;
			timer = 0;
		}

		return frames[currentFrame];
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public long getTimer() {
		return timer;
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}

	private BufferedImage nextFrame() {

		if (currentFrame >= frames.length) {
			currentFrame = 0;
			return frames[currentFrame];
		} else
			return frames[currentFrame];

	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getCFrame() {
		return currentFrame;
	}
}
