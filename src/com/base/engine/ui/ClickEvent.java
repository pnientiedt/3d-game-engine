package com.base.engine.ui;

public class ClickEvent {
	public enum Type {
		MOUSEVENT, KEYEVENT
	}

	public enum ActivationType {
		DOWN, UP
	}

	private Type type;
	private ActivationType activationType;
	private int key;

	public ClickEvent(Type type, ActivationType activationType, int key) {
		super();
		this.type = type;
		this.activationType = activationType;
		this.key = key;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ActivationType getActivationType() {
		return activationType;
	}

	public void setActivationType(ActivationType activationType) {
		this.activationType = activationType;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

}
