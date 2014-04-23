package com.base.engine.objects.player;

import com.base.engine.core.Input;
import com.base.engine.core.Control;

public class ControlRightMouse extends Control {

	@Override
	public boolean isActivated() {
		return Input.getMouseDown(Input.MOUSE_RIGHT);
	}

	@Override
	public boolean isDeactivated() {
		return Input.getMouseUp(Input.MOUSE_RIGHT);
	}


}
