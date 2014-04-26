package com.base.engine.components.control;

import org.lwjgl.input.Mouse;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Vector3f;

public class Zoom extends GameComponent {
	private float speed;
	
	public Zoom(float speed) {
		this.speed = speed;
	}
	
	@Override
	public void input(float delta) {
		int mouse = Mouse.getDWheel();
		float moveAmt = (mouse / 120) * speed;
		if (mouse != 0) {
			move(getTransform().getRot().getForward(), moveAmt);
		}
	}
	
	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
