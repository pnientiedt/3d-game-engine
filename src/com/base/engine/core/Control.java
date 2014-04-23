package com.base.engine.core;

public abstract class Control {
	
	public abstract boolean isActivated();
	public abstract boolean isDeactivated();
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}
}
