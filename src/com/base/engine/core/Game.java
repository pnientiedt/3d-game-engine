package com.base.engine.core;

import com.base.engine.objects.GameObject;
import com.base.engine.rendering.RenderingEngine;

public abstract class Game 
{
	private GameObject root;
	private GameObject ui;
	
	public void init() {
		
	}
	public void input(float delta) {
		getRootObject().inputAll(delta);
		getUI().inputAll(delta);
	}
	public void update(float delta) {
		getRootObject().updateAll(delta);
		getUI().updateAll(delta);
	}
	
	public void render(RenderingEngine renderingEngine) {
		renderingEngine.render(getRootObject(), getUI());
	}
	
	public void addObject(GameObject object) {
		getRootObject().addChild(object);
	}
	
	private GameObject getRootObject() {
		if (root == null) {
			root = new GameObject();
		}
		return root;
	}
	
	public void addToUI(GameObject object) {
		getUI().addChild(object);
	}
	
	private GameObject getUI() {
		if (ui == null) {
			ui = new GameObject();
		}
		return ui;
	}
	
	public void setEngine(CoreEngine engine) {
		getRootObject().setEngine(engine);
		getUI().setEngine(engine);
	}
}
