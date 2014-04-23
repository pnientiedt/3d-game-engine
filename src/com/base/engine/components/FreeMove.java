package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;

public class FreeMove extends GameComponent {
	private float speed;
	private int forwardKey;
	private int backKey;
	private int leftKey;
	private int rightKey;
	
	public FreeMove() {
		this(10);
	}
	
	public FreeMove(float speed) {
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}

	@Override
	public void input(float delta) {
		Vector3f velocity = new Vector3f(0,getVelocity().getY(),0);
		
		if (Input.getKey(forwardKey))
			velocity = velocity.add(getTransform().getRot().getForward().mul(speed));
		if (Input.getKey(backKey))
			velocity = velocity.add(getTransform().getRot().getForward().mul(-speed));
		if (Input.getKey(leftKey))
			velocity = velocity.add(getTransform().getRot().getLeft().mul(speed));
		if (Input.getKey(rightKey))
			velocity = velocity.add(getTransform().getRot().getRight().mul(speed));
		
		setVelocity(velocity);
	}
}
