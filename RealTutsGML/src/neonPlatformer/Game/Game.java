package neonPlatformer.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import neonPlatformer.Camera.Camera;
import neonPlatformer.Handler.AssetsHandler;
import neonPlatformer.Handler.Handler;
import neonPlatformer.Handler.ObjectHandler;
import neonPlatformer.ImageLoader.ImageLoader;
import neonPlatformer.ImageLoader.Texture;
import neonPlatformer.Input.KeyInput;
import neonPlatformer.Jframe.Window;
import neonPlatformer.MapObjects.Block;
import neonPlatformer.MapObjects.GameObject;
import neonPlatformer.MapObjects.ObjectId;
import neonPlatformer.MapObjects.Player;

public class Game extends Canvas implements Runnable {

	private int width;
	private int height;

	// Thread
	private boolean running;
	private Thread thread;

	// Handler
	private Handler handler;
	private ObjectHandler objectHandler;
	private AssetsHandler assetHandler;

	// Camera
	private Camera camera;
	
	//image level
	private BufferedImage level_1;
	private BufferedImage level_2;
	
	//textures
	private static Texture texture;
	
	

	public void init() {
		texture = new Texture();
		handler = new Handler(this);
		objectHandler = new ObjectHandler(handler);
		assetHandler = new AssetsHandler();
		camera = new Camera(handler, 0, 0);

		this.addKeyListener(new KeyInput(objectHandler));
		
		//loading the level
		ImageLoader imageLoader = new ImageLoader();
		level_1 = imageLoader.loadImage("/level_1.png");
		level_2 = imageLoader.loadImage("/level_2.png");
		loadImageLevel(level_2);

		//objectHandler.createLevel();
		//objectHandler.addObject(new Player(100, 100, objectHandler, handler, ObjectId.player));

		// instead of declaring an instance variable KeyInput, we just create it
		// here

	}

	@Override
	public void run() {
		init();

		// Just to keep track
		int fps = 0;
		int updates = 0;

		// note: 1 billion nano seconds = 1 second
		long fpsTimer = System.currentTimeMillis();
		double nsPerUpdate = 1000000000.0 / 60; // 1b divided by 60 = 16.6m
												// program should update every
												// 16.6m nanosecs to update 60
												// times in 1 normal second

		// last update time in nanoseconds
		double then = System.nanoTime();
		double timer = 0; // to keep track if we reached 1b nanosecs yet
		double debugTimer = 0;

		while (running) {

			boolean shouldRender = false;

			double now = System.nanoTime();
			timer += (now - then);
			debugTimer += (now - then);
			then = now;

			// update queue if we wait till 1b then we will update once for
			// every second
			while (timer >= 1000000000 / 60) {
				updates++;
				update();
				timer = 0;
				shouldRender = true;

			}

			while (shouldRender) {
				fps++;
				render();
				shouldRender = false;
			}

			// fps Timer; debugging purposes
			if (debugTimer >= 1000000000) {
				// System.out.printf("%d fps %d updates", fps, updates);
				// System.out.println();

				if (fps >= 60)
					fps = 0;

				if (updates >= 60)
					updates = 0;

				debugTimer = 0;
			}

		}
		stop(); // wait for thread to die
	}

	public void update() {
		objectHandler.update();

		for (GameObject object : objectHandler.getObjects())
			if (object.getId() == ObjectId.player)
				camera.update(object);
	}

	public void render() {
		
		// Set Buffer Strategy
		BufferStrategy bs = this.getBufferStrategy(); // automatically equals itself to null
		if (bs == null){
			this.createBufferStrategy(3);
			return;
		}

		// Graphics
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		// Draw here
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());	
		
		//@@ g2d.translate adds camera.getX and camera.getY to any rendering 
		//that comes after it.  Subsequent rendering is translated by the specified 
		//distance relative to the previous position.
		
		g2d.translate(-camera.getX(), -camera.getY());
		objectHandler.render(g);
		//we substract it at the end so that everything after it is
		//drawn without the extra camera.getx or getY
		g2d.translate(camera.getX(), camera.getY());
		
		//
		g.dispose();
		bs.show();
		
	}

	// synchronized -> thread stuff
	public synchronized void Start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (running == false)
			return;

		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Load Image into actual level
	public void loadImageLevel(BufferedImage map){
		int w = map.getWidth();
		int h = map.getHeight();
		
		for (int x = 0; x < h; x++){
			for (int y = 0; y < w; y++){
				int pixel = map.getRGB(x, y);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				//System.out.println(blue);
				
				if(red == 128 && green == 0 && blue == 0)
					objectHandler.addObject(new Block(x*32, y*32, 1, ObjectId.block));
				
				if(red == 0 && green == 128 && blue == 0)
					objectHandler.addObject(new Block(x*32, y*32, 0, ObjectId.block));
				
				if (red == 0 && green == 0 && blue == 255)
					objectHandler.addObject(new Player(x*32, y*32, objectHandler, handler, assetHandler, ObjectId.player));
			}
		}
	}
	
	
	// getters and setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Handler getHandler() {
		return handler;
	}
	
	public static Texture getTexture(){
		return texture;
	}

	public static void main(String args[]) {
		Window window = new Window(800, 640, "Neon plat", new Game());

	}

}
