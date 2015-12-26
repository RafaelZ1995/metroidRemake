package neonPlatformer.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import neonPlatformer.Handler.ObjectHandler;
import neonPlatformer.MapObjects.GameObject;
import neonPlatformer.MapObjects.ObjectId;
import neonPlatformer.MapObjects.Player;

public class KeyInput extends KeyAdapter {

	private ObjectHandler objectHandler;

	public KeyInput(ObjectHandler objectHandler) {
		this.objectHandler = objectHandler;
	}

	// key Pressed
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// go through every object
		for (int i = 0; i < objectHandler.getObjects().size(); i++) {
			GameObject tempObject = objectHandler.getObjects().get(i);

			// but do it only for player
			if (tempObject.getId() == ObjectId.player) {

				Player tempObjectP = (Player) objectHandler.getObjects().get(i);

				// left Key
				if (key == KeyEvent.VK_A) {
					tempObject.setFacingLeft(true);
					tempObjectP.setLeftKey(true);
					tempObjectP.setCrouching(false);

				}
				// right Key
				else if (key == KeyEvent.VK_D) {
					tempObjectP.setRightKey(true);
					tempObject.setFacingLeft(false);
					tempObjectP.setCrouching(false);
				}

				// down key
				if (key == KeyEvent.VK_S) {

					if (tempObject.isCrouching()) {
						tempObjectP.setCrouching(false);
						tempObjectP.setMorphBall(true);

					} else if (!tempObject.isMorphBall()) {
						tempObjectP.setCrouching(true);
					}
				}

				if (key == KeyEvent.VK_W) {
					if (tempObjectP.isCrouching())
						tempObjectP.setCrouching(false);
					else if (tempObjectP.isMorphBall()) {
						tempObject.setCrouching(true);
						tempObject.setMorphBall(false);
					}
				}

				if (key == KeyEvent.VK_SPACE && !tempObject.isJumping() && !tempObject.isStillJumping()
						&& !tempObject.isFalling() && tempObject.getDy() <= 0.7) {

					// when morphball
					if (tempObject.isMorphBall()) {
						tempObject.setMorphBall(false);
						tempObject.setCrouching(true);
						tempObject.setSpinFalling(false);
						tempObject.setJumping(false);
						tempObject.setStillJumping(false);
					}
					// when crouching
					else if (tempObject.isCrouching()) {

						tempObject.setCrouching(false);
						if (tempObjectP.isLeftKey() || tempObjectP.isRightKey()) {
							tempObject.setSpinFalling(true);
							tempObject.setJumping(true);
						} else if (Math.abs(tempObject.getDx()) == 0)
							tempObject.setStillJumping(true);

					}
					// when standing
					else {

						if (tempObjectP.isLeftKey() || tempObjectP.isRightKey()) {
							tempObject.setSpinFalling(true);
							tempObject.setJumping(true);
						} else if (Math.abs(tempObject.getDx()) == 0)
							tempObject.setStillJumping(true);

					}

					// tempObject.setDy(-2);
				}

				if (key == KeyEvent.VK_Q)
					tempObject.setTimeRewinding(true);

				if (key == KeyEvent.VK_R) {
					if (tempObject.getSaveX() == 0){
					tempObject.setX(4000);
					tempObject.setY(7450);
					}else{
						tempObject.setX((int) tempObject.getSaveX());
						tempObject.setY((int) tempObject.getSaveY());
						
					}
				}
				
				if (key == KeyEvent.VK_N)
					tempObjectP.setRunning(true);
				
				if (key == KeyEvent.VK_T){
					tempObject.setSaveX(tempObject.getX());
					tempObject.setSaveY(tempObject.getY());
				}
			}
		}

		if (key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}

	// key Released
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for (int i = 0; i < objectHandler.getObjects().size(); i++) {
			GameObject tempObject = objectHandler.getObjects().get(i);
			// but do it only for player
			if (tempObject.getId() == ObjectId.player) {

				Player tempObjectP = (Player) objectHandler.getObjects().get(i);

				if (key == KeyEvent.VK_A) {
					tempObjectP.setCrouching(false);
					tempObjectP.setLeftKey(false);
				}
				if (key == KeyEvent.VK_D) {
					tempObjectP.setCrouching(false);
					tempObjectP.setRightKey(false);
				}

				if (key == KeyEvent.VK_Q) {
					tempObject.setTimeRewinding(false);
				}
				if (key == KeyEvent.VK_SPACE) {
					tempObject.setJumping(false); // spin jumping
					tempObject.setStillJumping(false);
					
					if (!tempObject.isCrouching()) // if its crouching
						tempObject.setFalling(true);
				}
				
				if (key == KeyEvent.VK_N)
					tempObjectP.setRunning(false);

			}
		}

	}
}
