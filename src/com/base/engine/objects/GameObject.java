package com.base.engine.objects;

import java.util.ArrayList;

import com.base.engine.components.GameComponent;
import com.base.engine.core.CoreEngine;
import com.base.engine.core.Vector3f;
import com.base.engine.physics.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class GameObject {
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private GameObject parent;
	private Transform transform;
	private Vector3f velocity;
	private Vector3f acceleration;
	private CoreEngine engine;

	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		engine = null;
	}

	public ArrayList<GameObject> getAllAttached() {
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		;

		for (GameObject child : children) {
			result.addAll(child.getAllAttached());
		}

		result.add(this);
		return result;
	}

	public Transform getTransform() {
		return transform;
	}
	
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	
	public Vector3f getVelocity() {
		return velocity;
	}
	
	public void setAcceleration(Vector3f acceleration) {
		this.acceleration = acceleration;
	}
	
	public Vector3f getAcceleration() {
		return acceleration;
	}
	
	public void setParent(GameObject object) {
		this.parent = object;
	}
	
	public GameObject getParent() {
		return parent;
	}

	public void addChild(GameObject child) {
		children.add(child);
		child.setParent(this);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
	}

	public GameObject addComponent(GameComponent component) {
		components.add(component);
		component.setParent(this);
		return (this);
	}

	public void inputAll(float delta) {
		input(delta);
		
		for (GameObject child : children)
			child.inputAll(delta);
	}

	public void updateAll(float delta) {
		update(delta);
		
		for (GameObject child : children)
			child.updateAll(delta);
	}

	public void renderAll(Shader shader, RenderingEngine renderingEngine) {
		render(shader, renderingEngine);
		
		for (GameObject child : children)
			child.renderAll(shader, renderingEngine);
	}
	
	public void input(float delta) {
		transform.update();

		for (GameComponent component : components)
			component.input(delta);
	}

	public void update(float delta) {
		for (GameComponent component : components)
			component.update(delta);
	}

	public void render(Shader shader, RenderingEngine renderingEngine) {
		for (GameComponent component : components)
			component.render(shader, renderingEngine);
	}

	public void setEngine(CoreEngine engine) {
		if (this.engine != engine) {
			this.engine = engine;
			for (GameComponent component : components)
				component.addToEngine(engine);
			for (GameObject child : children)
				child.setEngine(engine);
		}
	}
}
