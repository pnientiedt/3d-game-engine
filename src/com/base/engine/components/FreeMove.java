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
//		float movAmt = speed * delta;
		
		Vector3f velocity = new Vector3f(0,0,0);
		
//		System.out.println(getTransform().getRot().getForward());
//		System.out.println(getTransform().getRot().getForward().mul(speed));
//		System.out.println(velocity.add(getTransform().getRot().getForward().mul(speed)));
		
		if (Input.getKey(forwardKey))
			velocity = velocity.add(getTransform().getRot().getForward().mul(speed));
		if (Input.getKey(backKey))
			velocity = velocity.add(getTransform().getRot().getForward().mul(-speed));
		if (Input.getKey(leftKey))
			velocity = velocity.add(getTransform().getRot().getLeft().mul(speed));
		if (Input.getKey(rightKey))
			velocity = velocity.add(getTransform().getRot().getRight().mul(speed));
//		if (velocity.length() > 0)
//		System.out.println(velocity);
//		if (Input.getKey(forwardKey))
//			move(getTransform().getRot().getForward(), movAmt);
//		if (Input.getKey(backKey))
//			move(getTransform().getRot().getForward(), -movAmt);
//		if (Input.getKey(leftKey))
//			move(getTransform().getRot().getLeft(), movAmt);
//		if (Input.getKey(rightKey))
//			move(getTransform().getRot().getRight(), movAmt);
		
		setVelocity(velocity);
	}

	public void move(Vector3f dir, float amt) {
//		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
