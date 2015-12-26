package neonPlatformer.MapObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

import neonPlatformer.abilities.TimeRewind;

/*
 * Things to note:
 * 
 * - the movement booleans such as falling,jumping, or facingLeft are all set either true or false
 * in KeyInput.java, which is where we check what keys the user is pressing
 * 
 */

public abstract class GameObject {

	protected ObjectId id;
	protected float x, y;
	protected float dx, dy;
	
	protected float saveX, saveY;

	public float getSaveX() {
		return saveX;
	}

	public void setSaveX(float saveX) {
		this.saveX = saveX;
	}

	public float getSaveY() {
		return saveY;
	}

	public void setSaveY(float saveY) {
		this.saveY = saveY;
	}

	// movement
	protected float gravity;
	protected boolean falling;

	protected boolean stillJumping;
	protected boolean jumping;
	protected boolean facingLeft;
	protected boolean moving;
	
	// only for player
	protected boolean spinFalling;
	protected boolean crouching;
	protected boolean morphBall;

	public boolean isSpinFalling() {
		return spinFalling;
	}

	public boolean isMorphBall() {
		return morphBall;
	}

	public void setMorphBall(boolean morphBall) {
		this.morphBall = morphBall;
	}

	public boolean isCrouching() {
		return crouching;
	}

	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
	}

	public void setSpinFalling(boolean spinFall) {
		this.spinFalling = spinFall;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	// Abilities
	protected TimeRewind timeRewind;
	protected boolean timeRewinding;
	
	
	
	

	public GameObject(float x, float y, ObjectId id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public abstract void tick(ArrayList<GameObject> objects);

	public abstract void render(Graphics g);

	public abstract ObjectId getId();

	public abstract Rectangle getBounds();

	// Getters and setters
	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
	
	
	//booleans
	public boolean isTimeRewinding() {
		return timeRewinding;
	}

	public void setTimeRewinding(boolean timeRewinding) {
		this.timeRewinding = timeRewinding;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	public boolean isStillJumping(){
		return stillJumping;
	}
	public void setStillJumping(boolean sJumping){
		this.stillJumping = sJumping;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
		
	}
}
