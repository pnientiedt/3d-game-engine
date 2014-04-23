package com.base.engine.objects.player;

import com.base.engine.core.Input;
import com.base.engine.core.Control;

public class ControlLeftMouse extends Control {
	
	private boolean active = false;

	@Override
	public boolean isActivated() {
		boolean activated = Input.getMouseDown(Input.MOUSE_LEFT) && 
				!Input.getMouseDown(Input.MOUSE_RIGHT) && 
				!Input.getMouse(Input.MOUSE_RIGHT) && 
				Input.getMouse(Input.MOUSE_LEFT) &&
				!active;
		if (activated)
			active = true;
		return activated;
	}

	@Override
	public boolean isDeactivated() {
		boolean deactivated = (Input.getMouseUp(Input.MOUSE_LEFT) || 
				Input.getMouseDown(Input.MOUSE_RIGHT) || 
				Input.getMouse(Input.MOUSE_RIGHT)) && 
				active;
		if (deactivated) 
			active = false;
		
		return deactivated;
	}

}
