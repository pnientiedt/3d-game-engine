package com.base.engine.physics;

import com.base.engine.objects.GameObject;

public class PhysicEngine {

	private static float gravity = 30f;

	public void update(float delta, GameObject rootObject) {
		//update objects
		rootObject.updateAll(delta);
		//calculate physics
		for (GameObject object : rootObject.getAllMovingAttached()) {
			
			//Simple ground collision if ground is always height zero
			if(object.getTransform().getPos().getY() <= 0 && object.getAcceleration().getY() < 0 && object.getVelocity().getY() < 0 ) {
				object.getAcceleration().setY(0);
				object.getVelocity().setY(0);
			}
			
			object.getTransform().setPos(object.getTransform().getPos().add(object.getVelocity().add(object.getAcceleration().div(2).mul(delta)).mul(delta)));
			
			if (object.getAcceleration().getY() != 0) {
				object.getVelocity().setY(object.getVelocity().getY() + object.getAcceleration().getY() * delta);
			}
			
			object.getVelocity().set(0, object.getVelocity().getY(), 0);
		}
	}

	public static void setGravity(float gravity) {
		PhysicEngine.gravity = gravity;
	}

	public static float getGravity() {
		return gravity;
	}

}
