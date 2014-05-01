package com.base.engine.objects;

import java.util.ArrayList;
import java.util.Collections;

import com.base.engine.components.GameComponent;
import com.base.engine.core.CoreEngine;
import com.base.engine.core.Vector3f;
import com.base.engine.physics.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class GameObject implements Comparable<GameObject> {
	private static final int RECALIBRATION_VALUE = Integer.MAX_VALUE - 100;

	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private GameObject parent;
	private Transform transform;
	private Vector3f velocity;
	private Vector3f acceleration;
	private CoreEngine engine;
	private Integer priority = 0;
	private boolean recalibratePriority = false;

	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		velocity = new Vector3f(0, 0, 0);
		acceleration = new Vector3f(0, 0, 0);
		engine = null;
	}

	public ArrayList<GameObject> getAllAttached() {
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for (GameObject child : children) {
			result.addAll(child.getAllAttached());
		}

		result.add(this);
		return result;
	}

	public ArrayList<GameObject> getAllMovingAttached() {
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for (GameObject child : children) {
			result.addAll(child.getAllMovingAttached());
		}

		if (velocity.length() > 0 || acceleration.length() > 0)
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

	public void addChildLowPriority(GameObject o) {
		for (GameObject child : children) {
			child.decreasePriority();
		}
		addChild(o);
		sortChildren();
	}

	public void addChildHighPriority(GameObject o) {
		int prio = 0;
		for (GameObject child : children) {
			if (child.getPriority() > prio)
				prio = child.getPriority();
		}
		prio++;
		o.setPriority(prio);
		System.out.println("Add object with prio " + o.getPriority());
		addChild(o);
		sortChildren();
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

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Integer getPriority() {
		return priority;
	}

	public void decreasePriority() {
		priority++;
		
		if (priority > RECALIBRATION_VALUE) {
			parent.requestRecalibration();
		}
	}

	@Override
	public int compareTo(GameObject o) {
		return o.getPriority().compareTo(priority);
	}

	public void sortChildren() {
		Collections.sort(children);
		
		if (recalibratePriority) {
			for (int i = 0; i < children.size(); i++) {
				children.get(i).setPriority(i);
			}
		}
	}

	public void sortChildrenRecursively() {
		sortChildren();
		for (GameObject child : children) {
			child.sortChildrenRecursively();
		}
	}

	public boolean isRootObject() {
		return parent == null;
	}

	/**
	 * Usable for UI Rendering. Sets the GameObject on the first layer behind
	 * the root object to highest priority
	 */
	public void setToHighestPriority(GameObject caller) {
		if (isRootObject()) {
			for (GameObject child : children) {
				child.decreasePriority();
			}
			caller.setPriority(0);
			sortChildren();
		}
	}
	
	public void requestRecalibration() {
		recalibratePriority = true;
	}
}
