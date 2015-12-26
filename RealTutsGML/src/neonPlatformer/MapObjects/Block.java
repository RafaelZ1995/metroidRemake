package neonPlatformer.MapObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

import neonPlatformer.Game.Game;
import neonPlatformer.ImageLoader.Texture;

public class Block extends GameObject {
	
	private int type; //to designate what type of block
	private Texture texture = Game.getTexture(); //static so you can access through Game.ect

	public Block(float x, float y, int type, ObjectId id) {
		super(x, y, id);
		this.type = type;
	}

	

	@Override
	public void tick(ArrayList<GameObject> object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		if (type == 0) //grass block 
		g.drawImage(texture.blocks[0], (int)x, (int)y, null);
		if (type == 1) //grass block 
			g.drawImage(texture.blocks[1], (int)x, (int)y, null);
	}

	@Override
	public ObjectId getId() {
		// TODO Auto-generated method stub
		return id;
	}



	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle((int) x,(int) y, 32, 32);
	}
}
