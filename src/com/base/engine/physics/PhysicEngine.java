package com.base.engine.physics;

import com.base.engine.objects.GameObject;

public class PhysicEngine {
	
	private float gravity = 0.5f;
	
	public void update(float delta, GameObject object) {
		object.updateAll(delta);
	}
	
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public float getGravity() {
		return gravity;
	}
	
}
