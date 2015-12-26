package neonPlatformer.Handler;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

import neonPlatformer.Game.Game;
import neonPlatformer.MapObjects.Block;
import neonPlatformer.MapObjects.Creatures;
import neonPlatformer.MapObjects.GameObject;
import neonPlatformer.MapObjects.ObjectId;

public class ObjectHandler {

	private Handler handler;
	// array list much more efficient than linkedlist in this case
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	GameObject tempObject;

	public ObjectHandler(Handler handler){
		this.handler = handler;
	}
	// could also be done with a for enhance loop
	public void update() {
		for (int i = 0; i < objects.size(); i++) {
			tempObject = objects.get(i);
			tempObject.tick(objects);
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			tempObject = objects.get(i);
			tempObject.render(g);
		}
	}

	public void addObject(GameObject object) {
		this.objects.add(object);
	}

	public void removeObject() {
		this.objects.remove(objects.size());
	}

	
	
	public ArrayList<GameObject> getObjects(){
		return objects;
	}
}
