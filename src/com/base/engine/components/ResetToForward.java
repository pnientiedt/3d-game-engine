package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;
import com.base.engine.physics.Transform;

public class ResetToForward extends GameComponent {
	private Transform reset;
	private Transform forward;
	private boolean includeY;
	private int resetButton;
	
	public ResetToForward(Transform reset, Transform forward, boolean includeY, int resetButton) {
		this.reset = reset;
		this.forward = forward;
		this.includeY = includeY;
		this.resetButton = resetButton;
	}
	
	@Override
	public void input(float delta) {
		if (Input.getMouseDown(resetButton)) {
			Vector3f direction = new Vector3f(0,0,0).set(forward.getRot().getForward());
			if (!includeY) {
				direction.setY(reset.getPos().getY());
				direction = direction.normalized();
			}
			System.out.println(forward.getRot().getForward());
			System.out.println(direction);
		}
		
	}
}
