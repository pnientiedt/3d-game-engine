package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.Vector3f;
import com.base.engine.objects.GameObject;
import com.base.engine.physics.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public abstract class GameComponent {
	private GameObject parent;
	
	public void input(float delta) {
	};

	public void update(float delta) {
	};

	public void render(Shader shader, RenderingEngine renderingEngine) {
	};
	
	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	
	public GameObject getParent() {
		return parent;
	}
	
	public Transform getTransform() {
		return parent.getTransform();
	}
	
	public void setVelocity(Vector3f velocity) {
		parent.setVelocity(velocity);
	}
	
	public Vector3f getVelocity() {
		return parent.getVelocity();
	}
	
	public void setAcceleration(Vector3f acceleration) {
		parent.setAcceleration(acceleration);
	}
	
	public Vector3f getAcceleration() {
		return parent.getAcceleration();
	}
	
	public void addToEngine(CoreEngine engine) {
		
	}
}
