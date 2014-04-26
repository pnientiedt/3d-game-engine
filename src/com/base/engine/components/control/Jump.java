package com.base.engine.components.control;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Input;
import com.base.engine.physics.PhysicEngine;

public class Jump extends GameComponent {
	private float initialVelocity;
	private int jumpKey;
	
	public Jump(float initialVelocity, int jumKey) {
		this.initialVelocity = initialVelocity;
		this.jumpKey = jumKey;
	}

	@Override
	public void input(float delta) {
		if (Input.getKey(jumpKey)) {
			getVelocity().setY(initialVelocity);
			getAcceleration().setY(-PhysicEngine.getGravity());
		}
		
	}
}
