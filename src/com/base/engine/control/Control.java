package com.base.engine.control;

public abstract class Control {
	
	public abstract boolean isActive();
	public abstract boolean isActivated();
	public abstract boolean isDeactivated();
	
	public boolean isMouseControl() {
		return false;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}
}
