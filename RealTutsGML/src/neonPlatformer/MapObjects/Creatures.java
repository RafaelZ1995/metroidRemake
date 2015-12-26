package neonPlatformer.MapObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

public class Creatures extends GameObject {

	public Creatures(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	

	@Override
	public void tick(ArrayList<GameObject> object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.fillRect((int) x,(int) y, 32, 32);
	}

	@Override
	public ObjectId getId() {
		// TODO Auto-generated method stub
		return id;
	}


	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}
