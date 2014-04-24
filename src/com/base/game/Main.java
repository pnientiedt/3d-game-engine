package com.base.game;

import com.base.engine.core.CoreEngine;

public class Main {

	public static void main(String[] args) {
		CoreEngine engine;
			engine = new CoreEngine(800, 600, false, false, 60, new TestGame()); //Window
//			engine = new CoreEngine(1680, 1050, true, true, 60, new TestGame()); //Fullscreen
		engine.createWindow("3D Game Engine");
		engine.start();
	}
}
