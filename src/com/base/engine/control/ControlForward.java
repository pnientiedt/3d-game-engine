package com.base.engine.control;

import com.base.engine.core.Input;

public class ControlForward extends KeyControl {
	
	public ControlForward(Integer... keys) {
		super(keys);
	}

	@Override
	public boolean isActive() {
		return super.isActive() || Input.getMouse(Input.MOUSE_LEFT) && Input.getMouse(Input.MOUSE_RIGHT);
	}
}
