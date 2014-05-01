package com.base.engine.core;

import com.base.engine.objects.GameObject;
import com.base.engine.physics.PhysicEngine;
import com.base.engine.rendering.RenderingEngine;

public abstract class Game 
{
	private GameObject root;
	private GameObject ui;
	
	public void init() {
		
	}
	public void input(float delta) {
		getUI().inputAll(delta);
		getRootObject().inputAll(delta);
	}
	public void update(PhysicEngine physicEngine, float delta) {
		physicEngine.update(delta, getRootObject());
		getUI().updateAll(delta);
	}
	
	public void render(RenderingEngine renderingEngine) {
//		System.out.println();
//		System.out.println("New Scene Graph render:");
		renderingEngine.render(getRootObject(), getUI());
	}
	
	public void addObject(GameObject object) {
		getRootObject().addChild(object);
	}
	
	private GameObject getRootObject() {
		if (root == null) {
			root = new GameObject("ROOT");
		}
		return root;
	}
	
	public void addToUI(GameObject object) {
		getUI().addChild(object);
	}
	
	private GameObject getUI() {
		if (ui == null) {
			ui = new GameObject("UI");
		}
		return ui;
	}
	
	public void setEngine(CoreEngine engine) {
		getRootObject().setEngine(engine);
		getUI().setEngine(engine);
	}
	
	public void resetGameObjects() {
		getRootObject().reset();
	}
	
	public void resetUI() {
		getUI().reset();
	}
	
	public void removeObject(GameObject o) {
		getRootObject().removeChild(o);
	}
	
	public void removeFromUI(GameObject o) {
		getUI().removeChild(o);
	}
}
