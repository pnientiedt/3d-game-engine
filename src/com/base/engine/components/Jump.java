package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class Jump extends GameComponent {
	private float speed;
	private int jumKey;
	
	public Jump(float speed, int jumKey, int backKey, int leftKey, int rightKey) {
		this.speed = speed;
		this.jumKey = jumKey;
	}

	@Override
	public void input(float delta) {
		float movAmt = speed * delta;
		
		if (Input.getKey(jumKey))
			move(getTransform().getRot().getForward(), movAmt);
	}

	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
