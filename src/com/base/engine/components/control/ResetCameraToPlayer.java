package com.base.engine.components.control;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;
import com.base.engine.physics.Transform;

public class ResetCameraToPlayer extends GameComponent {
	private Transform player;
	private Transform camera;
	private float time = 0;
	private float multi = 0;

	public ResetCameraToPlayer(Transform player, Transform camera) {
		this.player = player;
		this.camera = camera;
	}

	@Override
	public void input(float delta) {
		if (!Input.getMouse(Input.MOUSE_LEFT) && !Input.getMouse(Input.MOUSE_RIGHT)) {
			time += delta;
			// calculate angle between camera direction and player direction
			float angle = getAngle();

			if (angle != 0 && time > 1f) {
				multi += delta;
				//rotate camera based on time	
				rotate(new Vector3f(0,1,0), angle, delta);
				//check if agle gets smaller, otherwise rotate in the other direction
				if (angle < getAngle()) {
					rotate(new Vector3f(0,-1,0), angle*2, delta);
				}
			}
		} else {
			time = 0;
			multi = 0;
		}
	}
	
	private void rotate(Vector3f axis, float angle, float delta) {
		float distance = camera.getPos().length();
		camera.rotate(axis, angle * delta * multi * multi);
		camera.setPos(player.getPos());
		camera.setPos(camera.getRot().getForward().mul(-distance));
	}
	
	private float getAngle() {
		Vector3f a = new Vector3f(0,0,0).set(camera.getTransformedPos()).setY(player.getTransformedPos().getY()).sub(player.getTransformedPos());
		Vector3f b = player.getTransformedRot().getBack();
		return getAngle(a, b);
	}

	public float getAngle(Vector3f a, Vector3f b) {
		float angle = (float) Math.acos(a.dot(b) / ((a.length() * b.length())));
		if (!Float.isNaN(angle))
			return angle;
		return 0;
	}
}
