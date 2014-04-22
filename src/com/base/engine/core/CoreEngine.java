package com.base.engine.core;

import com.base.engine.physics.PhysicEngine;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class CoreEngine {

	private boolean isRunning;
	private Game game;
	private RenderingEngine renderingEngine;
	private PhysicEngine physicEngine;
	private int width;
	private int height;
	private boolean fullscreen;
	private boolean vsync;
	private double frameTime;

	public CoreEngine(int width, int height, boolean fullscreen, boolean vsync, double framerate, Game game) {
		isRunning = false;
		this.game = game;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		this.vsync = vsync;
		this.frameTime = 1.0/framerate;
		game.setEngine(this);
	}
	
	public void createWindow(String title) {
		Window.createWindow(width, height, title, fullscreen, vsync);
		this.renderingEngine = new RenderingEngine();
		this.physicEngine = new PhysicEngine();
	}

	public void start() {
		if (isRunning)
			return;
		run();
	}

	public void stop() {
		if (!isRunning)
			return;

		isRunning = false;
	}

	private void run() {
		isRunning = true;

		int frames = 0;
		double frameCounter = 0;
		
		game.init();

		double lastTime = Time.getTime();
		double unprocessedTime = 0;

		while (isRunning) {
			boolean render = false;

			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;

			unprocessedTime += passedTime;
			frameCounter += passedTime;

			while (unprocessedTime > frameTime) {
				render = true;

				unprocessedTime -= frameTime;
				if (Window.isCloseRequested())
					stop();

				game.input((float)frameTime);
				Input.update();

				game.update(physicEngine, (float)frameTime);

				if (frameCounter >= 1) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}

			if (render) {
				game.render(renderingEngine);
				Window.render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		cleanUp();
	}

	private void cleanUp() {
		Window.dispose();
	}
	
	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}
	
	public PhysicEngine getPhysicEngine() {
		return physicEngine;
	}

}
