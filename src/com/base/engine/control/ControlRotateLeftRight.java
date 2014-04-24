package com.base.engine.control;

import com.base.engine.core.Input;

public class ControlRotateLeftRight extends KeyControl {

	private ControlForward controlForward;

	public ControlRotateLeftRight(ControlForward controlForward, Integer... keys) {
		super(keys);
		this.controlForward = controlForward;
	}

	@Override
	public boolean isActive() {
		return super.isActive() && controlForward.isActive() && !Input.getMouse(Input.MOUSE_RIGHT);
	}
}
