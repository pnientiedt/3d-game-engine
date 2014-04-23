package com.base.engine.physics;

import com.base.engine.objects.GameObject;

public class PhysicEngine {

	private float gravity = 0.5f;

	public void update(float delta, GameObject rootObject) {
		rootObject.updateAll(delta);

		for (GameObject object : rootObject.getAllAttached()) {
			object.getTransform().setPos(object.getTransform().getPos().add(object.getVelocity().mul(delta)));
		}
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public float getGravity() {
		return gravity;
	}

}
