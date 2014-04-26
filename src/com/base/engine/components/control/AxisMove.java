package com.base.engine.components.control;

import com.base.engine.components.GameComponent;
import com.base.engine.control.Control;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;

public class AxisMove extends GameComponent {
	private float speed;
	private Control control;
	Quaternion.Axis axis;
	
	public AxisMove(Quaternion.Axis axis, float speed, Control control) {
		this.axis = axis;
		this.speed = speed;
		this.control = control;
	}

	@Override
	public void input(float delta) {
		Vector3f velocity = getVelocity();
		
		if (control.isActive())
			velocity = velocity.add(getTransform().getRot().getAxis(axis).mul(speed));		
		setVelocity(velocity);
	}
}
