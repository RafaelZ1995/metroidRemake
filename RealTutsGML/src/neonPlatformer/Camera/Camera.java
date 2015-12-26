package neonPlatformer.Camera;

import neonPlatformer.Handler.Handler;
import neonPlatformer.MapObjects.GameObject;

public class Camera {

	private float x, y;
	private Handler handler;

	public Camera(Handler handler, float x, float y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
	}

	public void update(GameObject player) {
		//without tweening
		// x = handler.getWidth()/2 - player.getX();
		// y = handler.getHeight() / 2 - player.getY();
		
		
		// you can add the player width to center it more
		float finalValue = player.getX() - handler.getWidth()/2 + 32;
		x += ( finalValue - x) * 0.05;
		finalValue = player.getY() - handler.getHeight()/2 + 32;
		y += (finalValue - y) * 0.05;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
