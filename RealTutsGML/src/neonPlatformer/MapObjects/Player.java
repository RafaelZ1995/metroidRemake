package neonPlatformer.MapObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import neonPlatformer.Animations.Animation;
import neonPlatformer.Game.Game;
import neonPlatformer.Handler.AssetsHandler;
import neonPlatformer.Handler.Handler;
import neonPlatformer.Handler.ObjectHandler;
import neonPlatformer.ImageLoader.Texture;
import neonPlatformer.abilities.TimeRewind;


/*
 * Implementation Notes:
 * 		In Collision() method, checking the bottom rectangle bounds fixed some bugs where the character was falling
 * 		through the floor after falling off an edge during morphBall.
 * 
 * 		The class KeyInput directly changes states of the player class. Most of these states, such as falling/jumping/stillJumping, 
 * 		are actually declared in GameObject
 */
public class Player extends GameObject {

	private float width = 40, height = 96;

	private float standingWidth = 40, standingHeight = 96;
	private float crouchHeight = 64;
	private float morphBallHeight = 32;

	private final float MAX_FALLSPEED = 20;
	private final float MAX_JUMPSPEED = -10;
	private final float JUMP_SPEED = 2;
	private final float MAX_JUMPHEIGHT = 32 * 14;
	private final float AIR_SPEED = 5;

	// not final cause they can change for speed booster
	private float startMoveSpeed = 1;
	private float maxMoveSpeed = 6;

	// helpers
	private float lastYonGround; // to have a reference about where you jumped
									// from

	// Textures
	private Texture texture = Game.getTexture();

	// object Handler
	private ObjectHandler objectHandler;

	// Handler
	private Handler handler;
	private AssetsHandler assetHandler;

	// All animations of the player
	private Animation standingLeft;
	private Animation standingRight;

	private Animation goingLeft;
	private Animation goingRight;

	private Animation spinJumpLeft;
	private Animation spinJumpRight;

	private Animation jumpingLeft;
	private Animation jumpingRight;

	private Animation fallingLeft;
	private Animation fallingRight;

	private Animation crouchingLeft;
	private Animation crouchingRight;

	private Animation morphBallLeft;
	private Animation morphBallRight;

	private int animationDelay = 50;

	// control keys
	private boolean leftKey;
	private boolean rightKey;
	private boolean running;

	public Player(float x, float y, ObjectHandler objectHandler, Handler handler, AssetsHandler assetHandler,
			ObjectId id) {
		super(x, y, id);
		this.objectHandler = objectHandler;
		this.handler = handler;
		this.assetHandler = assetHandler;
		falling = true;
		gravity = 0.5f;

		facingLeft = true;
		moving = true;

		// abilities
		timeRewind = new TimeRewind(5); // 5 seconds
		timeRewinding = false;

		// load animations
		// public Animation(BufferedImage[] frames, int delay )
		standingLeft = new Animation(assetHandler.getPlayerSprites(0), 100);
		standingRight = new Animation(assetHandler.getPlayerSprites(1), 100);

		goingLeft = new Animation(assetHandler.getPlayerSprites(2), animationDelay);
		goingRight = new Animation(assetHandler.getPlayerSprites(3), animationDelay);

		spinJumpLeft = new Animation(assetHandler.getPlayerSprites(4), 40);
		spinJumpRight = new Animation(assetHandler.getPlayerSprites(5), 40);

		jumpingLeft = new Animation(assetHandler.getPlayerSprites(6), 400);
		jumpingRight = new Animation(assetHandler.getPlayerSprites(7), 400);

		fallingLeft = new Animation(assetHandler.getPlayerSprites(8), 200);
		fallingRight = new Animation(assetHandler.getPlayerSprites(9), 200);

		crouchingLeft = new Animation(assetHandler.getPlayerSprites(10), 350);
		crouchingRight = new Animation(assetHandler.getPlayerSprites(11), 350);

		morphBallLeft = new Animation(assetHandler.getPlayerSprites(12), 50);
		morphBallRight = new Animation(assetHandler.getPlayerSprites(13), 60);
	}

	@Override
	public void tick(ArrayList<GameObject> objects) {

		// printStatus();

		// set moving speed
		if (running)
			maxMoveSpeed = 10;
		else
			maxMoveSpeed = 6;

		// left and right
		if (leftKey && !crouching) {
			dx -= startMoveSpeed;

			if (dx <= -maxMoveSpeed)
				dx = -maxMoveSpeed;
		} else if (rightKey && !crouching) {
			dx += startMoveSpeed;

			if (dx >= maxMoveSpeed)
				dx = maxMoveSpeed;
		}
		// should come to a stop left
		else if (dx < 0 && !falling && !jumping && !stillJumping) {
			dx += startMoveSpeed;

			if (dx >= 0)
				dx = 0;
		} else if (dx > 0 && !falling && !jumping && !stillJumping) {
			dx -= startMoveSpeed;

			if (dx <= 0)
				dx = 0;
		}

		// still jumping
		if (stillJumping) {
			dy -= JUMP_SPEED;

			if (dy <= MAX_JUMPSPEED)
				dy = MAX_JUMPSPEED;

			// if reached peak of jump then set falling
			if (lastYonGround - y > MAX_JUMPHEIGHT) {
				jumping = false;
				stillJumping = false;
				falling = true;
			}
		}

		// jumping is spin jumping
		if (jumping) {
			dy -= JUMP_SPEED;

			if (dy <= MAX_JUMPSPEED)
				dy = MAX_JUMPSPEED;

			// if reached peak of jump then set falling
			if (lastYonGround - y > MAX_JUMPHEIGHT) {
				jumping = false;
				stillJumping = false;
				falling = true;
				spinFalling = true;
			}
		}

		// falling
		if (falling) {

			dy += gravity;

			if (dy >= MAX_FALLSPEED)
				dy = MAX_FALLSPEED;
		}

		x += dx;
		y += dy;

		Collision(objectHandler.getObjects());
	}

	private void printStatus() {
		System.out.println("stillJump " + stillJumping);
		System.out.println("jumping " + jumping);
		System.out.println("falling " + falling);
		System.out.println("facingLeft " + facingLeft);
		System.out.println("spinFalling " + spinFalling);
		System.out.println("crouching " + crouching);
		System.out.println("morphBall " + morphBall);
		System.out.println("----------------------------------");

	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRightKey() {
		return rightKey;
	}

	public void setRightKey(boolean rightKey) {
		this.rightKey = rightKey;
	}

	/*
	 * Notes: Collision() takes care of -setting jumping/falling true or false
	 * -checking for collision with every other object in the whole map
	 */
	private void Collision(ArrayList<GameObject> objects) {
		boolean blockUnderPlayer = false;
		boolean blockAbovePlayer = false;

		for (int i = 0; i < objectHandler.getObjects().size(); i++) {
			GameObject tempObject = objectHandler.getObjects().get(i);

			if (tempObject.getId() == ObjectId.block) {

				// bottom
				// && !stillJumping?
				if (getBounds().intersects(tempObject.getBounds())) {

					// + 2 cause it stops it from falling thru the floor in some
					// places
					y = tempObject.getY() - standingHeight + 1;

					// y = tempObject.getY() - 94;
					dy = 0;

					// System.out.println("RAN@@@");
					blockUnderPlayer = true;
					lastYonGround = y;
				}

				// top
				if (getBoundsTop().intersects(tempObject.getBounds())) {

					if (!crouching && !morphBall) {
						System.out.println("standing and hit top");
						y = tempObject.getY() + 32;
					} else if (crouching) {
						System.out.println("crouching and hit top");
						y = tempObject.getY();
					} else if (morphBall) {
						System.out.println("ball and hit top");
						y = tempObject.getY() - 32;
					}
					// @@ IMPLEMENTATION IDEA: STICK TO ROOF AND ROLL
					/*
					 * else if (stickyMorphBall) y = tempObject.getY() - 32;
					 */

					dy = 0;

					blockAbovePlayer = true;
				}

				// right
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = tempObject.getX() - width;
				}
				// left
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					// 32 is the width of the blocks
					x = tempObject.getX() + 32;
				}

			}
		}

		System.out.println("blockUnderPlayer? " + blockUnderPlayer);

		//
		if (!blockUnderPlayer && !jumping && !stillJumping) {
			falling = true;
		}

		if ((!blockUnderPlayer && crouching) || (!blockUnderPlayer && morphBall)) {
			falling = true;
		}

		if (blockUnderPlayer) {
			falling = false;
			spinFalling = false;
		}

		if (blockAbovePlayer) {
			jumping = false;
			stillJumping = false;
			falling = true;
		}
	}

	@Override
	public void render(Graphics g) {
		// g.setColor(Color.blue);
		// g.fillRect((int) x, (int) y, (int) width, (int) height);

		// implementing animations
		// standing still
		if (Math.abs(dx) <= 0 && facingLeft && !stillJumping && !falling && (dx == 0) && !crouching && !morphBall)
			g.drawImage(standingLeft.getCurrentFrame(), (int) x, (int) y, null);
		else if (dx <= 0 && !facingLeft && !stillJumping && !falling && (dx == 0) && !crouching && !morphBall)
			g.drawImage(standingRight.getCurrentFrame(), (int) x, (int) y, null);
		// going left and right
		else if (facingLeft && !jumping && !stillJumping && !falling && !crouching && !morphBall)
			g.drawImage(goingLeft.getCurrentFrame(), (int) x, (int) y, null);
		else if (!facingLeft && !jumping && !stillJumping && !falling && !crouching && !morphBall)
			g.drawImage(goingRight.getCurrentFrame(), (int) x, (int) y, null);
		// sping jumping left and right
		else if (jumping && facingLeft || spinFalling && facingLeft)
			g.drawImage(spinJumpLeft.getCurrentFrame(), (int) x, (int) y, null);
		else if (jumping && !facingLeft || spinFalling && !facingLeft)
			g.drawImage(spinJumpRight.getCurrentFrame(), (int) x, (int) y, null);
		// still jumping left and right
		else if (stillJumping && facingLeft && !crouching && !morphBall)
			g.drawImage(jumpingLeft.playOnce(), (int) x, (int) y, null);
		else if (stillJumping && !facingLeft && !crouching && !morphBall)
			g.drawImage(jumpingRight.playOnce(), (int) x, (int) y, null);
		// still falling
		else if (falling && facingLeft && !morphBall && !crouching)
			g.drawImage(fallingLeft.playOnce(), (int) x, (int) y, null);
		else if (falling && !facingLeft && !morphBall && !crouching)
			g.drawImage(fallingRight.playOnce(), (int) x, (int) y, null);
		// crouching left
		else if (crouching && facingLeft)
			g.drawImage(crouchingLeft.playOnce(), (int) x, (int) (y), null);
		// crouching right
		else if (crouching && !facingLeft)
			g.drawImage(crouchingRight.playOnce(), (int) x, (int) (y), null);
		// morph ball left
		else if (morphBall && facingLeft)
			g.drawImage(morphBallLeft.playOnceAndRepeatFrom(2), (int) x, (int) (y + 56), null);
		// morph ball right
		else if (morphBall && !facingLeft)
			g.drawImage(morphBallRight.playOnceAndRepeatFrom(2), (int) x, (int) (y + 56), null);

		// refresh animations
		// this is required to run the animation from frame 0 every time we
		// perform the action
		if (!stillJumping) {
			jumpingLeft.setCurrentFrame(0);
			jumpingLeft.setTimer(0);
			jumpingLeft.setLastTime(System.currentTimeMillis());

			jumpingRight.setCurrentFrame(0);
			jumpingRight.setTimer(0);
			jumpingRight.setLastTime(System.currentTimeMillis());
		}

		// refresh walking animations
		if (dx == 0) {
			goingLeft.setCurrentFrame(0);
			goingRight.setCurrentFrame(0);
		}

		// refresh falling still
		if (!falling) {
			fallingLeft.setCurrentFrame(0);
			fallingLeft.setTimer(0);
			fallingLeft.setLastTime(System.currentTimeMillis());

			fallingRight.setCurrentFrame(0);
			fallingRight.setTimer(0);
			fallingRight.setLastTime(System.currentTimeMillis());
		}

		if (!crouching) {
			crouchingLeft.setCurrentFrame(0);
			crouchingLeft.setTimer(0);
			crouchingLeft.setLastTime(System.currentTimeMillis());

			crouchingRight.setCurrentFrame(0);
			crouchingRight.setTimer(0);
			crouchingRight.setLastTime(System.currentTimeMillis());
		}

		if (!morphBall) {
			morphBallRight.setCurrentFrame(0);
			morphBallLeft.setCurrentFrame(0);
		}

		/*
		 * if (facingLeft) g.drawImage(texture.player[0], (int) x, (int) y,
		 * (int) width, (int) height, null); else g.drawImage(texture.player[0],
		 * (int) (x + width), (int) y, (int) -width, (int) height, null);
		 */

		/*
		 * Graphics2D g2d = (Graphics2D) g; g.setColor(Color.RED);
		 * g2d.draw(getBounds()); g2d.setColor(Color.WHITE);
		 * g2d.draw(getBoundsTop()); g2d.setColor(Color.GREEN);
		 * g2d.draw(getBoundsLeft()); g2d.setColor(Color.YELLOW);
		 * g2d.draw(getBoundsRight());
		 */
	}

	@Override
	public ObjectId getId() {

		return id;
	}

	// Bounds
	// BOTTOM
	public Rectangle getBounds() {// getBoundsBottom

		// crouched
		if (crouching) {
			height = crouchHeight;
			float diffInHeight = standingHeight - crouchHeight;
			return new Rectangle((int) (x + width / 4), (int) (y + height / 2 + diffInHeight),
					(int) (width - width / 2), (int) (height - height / 2));
		}
		// morph ball
		else if (morphBall) {
			height = morphBallHeight;
			float diffInHeight = standingHeight - morphBallHeight;
			return new Rectangle((int) (x + width / 4), (int) (y + height / 2 + diffInHeight),
					(int) (width - width / 2), (int) (height - height / 2));
		}
		// standing
		else {

			height = standingHeight;
			return new Rectangle((int) (x + width / 4), (int) (y + height / 2), (int) (width - width / 2),
					(int) (height - height / 2));
		}
	}

	// TOP
	public Rectangle getBoundsTop() {
		if (crouching) {
			height = crouchHeight;
			float diffInHeight = standingHeight - crouchHeight;
			return new Rectangle((int) (x + width / 4), (int) (y + diffInHeight), (int) (width - width / 2),
					(int) (height - height / 2));
		} else if (morphBall) {

			height = morphBallHeight;
			float diffInHeight = standingHeight - morphBallHeight;
			return new Rectangle((int) (x + width / 4), (int) (y + diffInHeight), (int) (width - width / 2),
					(int) (height - height / 2));
		} else {

			height = standingHeight;
			return new Rectangle((int) (x + width / 4), (int) y, (int) (width - width / 2),
					(int) (height - height / 2));
		}
	}

	// LEFT
	public Rectangle getBoundsLeft() {
		if (crouching) {
			height = crouchHeight;
			float diffInHeight = standingHeight - crouchHeight;
			return new Rectangle((int) x, (int) (y + height / 8 + diffInHeight), (int) (width - width * 0.8),
					(int) (height - height / 4));
		} else if (morphBall) {

			height = morphBallHeight;
			float diffInHeight = standingHeight - morphBallHeight;
			return new Rectangle((int) x, (int) (y + height / 8 + diffInHeight), (int) (width - width * 0.8),
					(int) (height - height / 4));
		} else {

			height = standingHeight;
			return new Rectangle((int) x, (int) (y + height / 8), (int) (width - width * 0.8),
					(int) (height - height / 4));
		}
	}

	// RIGHT
	public Rectangle getBoundsRight() {
		if (crouching) {
			height = crouchHeight;
			float diffInHeight = standingHeight - crouchHeight;
			return new Rectangle((int) (x + width * 0.8), (int) (y + height / 8 + diffInHeight),
					(int) (width - width * 0.8), (int) (height - height / 4));
		} else if (morphBall) {
			height = morphBallHeight;
			float diffInHeight = standingHeight - morphBallHeight;
			return new Rectangle((int) (x + width * 0.8), (int) (y + height / 8 + diffInHeight),
					(int) (width - width * 0.8), (int) (height - height / 4));
		} else {
			height = standingHeight;
			return new Rectangle((int) (x + width * 0.8), (int) (y + height / 8), (int) (width - width * 0.8),
					(int) (height - height / 4));
		}
	}

	public boolean isLeftKey() {
		return leftKey;
	}

	public void setLeftKey(boolean leftKey) {
		this.leftKey = leftKey;
	}
}
