package com.base.engine.control;

import com.base.engine.core.Input;

public class ControlLeftMouse extends Control {
	
	private boolean active = false;

	@Override
	public boolean isActivated() {
		boolean activated = Input.getMouse(Input.MOUSE_LEFT) &&
				!Input.getMouse(Input.MOUSE_RIGHT) &&
				!active;
		if (activated)
			active = true;
		return activated;
	}

	@Override
	public boolean isDeactivated() {
		boolean deactivated = (!Input.getMouse(Input.MOUSE_LEFT) ||
				Input.getMouse(Input.MOUSE_RIGHT)) && 
				active;
		if (deactivated) 
			active = false;
		
		return deactivated;
	}

	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public boolean isMouseControl() {
		return true;
	}

}
