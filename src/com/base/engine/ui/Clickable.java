package com.base.engine.ui;

import java.util.ArrayList;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;
import com.base.engine.ui.ClickEvent.ActivationType;
import com.base.engine.ui.ClickEvent.Type;

public class Clickable extends GameComponent {
	
	ArrayList<ClickableListener> listeners = new ArrayList<ClickableListener>();
	
	private int height;
	private int width;
	private boolean left;
	private boolean right;
	private int[] keys;
	
	public Clickable(int height, int width, boolean left, boolean right, int... keys) {
		this.height = height;
		this.width = width;
		this.left = left;
		this.right = right;
		this.keys = keys;
	}
	
	@Override
	public void input(float delta) {
		Vector3f pos = getTransform().getTransformedPos();
		if (left && Input.getMouseDown(Input.MOUSE_LEFT) && checkArea(pos)) {
			Input.setMouseConsumed(Input.MOUSE_LEFT);
			notifyListener(new ClickEvent(Type.MOUSEVENT, ActivationType.DOWN, Input.MOUSE_LEFT));
			return;
		} if (right && Input.getMouseDown(Input.MOUSE_RIGHT) && checkArea(pos)) {
			Input.setMouseConsumed(Input.MOUSE_RIGHT);
			notifyListener(new ClickEvent(Type.MOUSEVENT, ActivationType.DOWN, Input.MOUSE_RIGHT));
			return;
		}
		if (left && Input.getMouseUp(Input.MOUSE_LEFT) && checkArea(pos)) {
			Input.setMouseConsumed(Input.MOUSE_LEFT);
			notifyListener(new ClickEvent(Type.MOUSEVENT, ActivationType.UP, Input.MOUSE_LEFT));
			return;
		} if (right && Input.getMouseUp(Input.MOUSE_RIGHT) && checkArea(pos)) {
			Input.setMouseConsumed(Input.MOUSE_RIGHT);
			notifyListener(new ClickEvent(Type.MOUSEVENT, ActivationType.UP, Input.MOUSE_RIGHT));
			return;
		}
		for (int key : keys) {
			if (Input.getKeyDown(key)) {
				Input.setKeyConsumed(key);
				notifyListener(new ClickEvent(Type.KEYEVENT, ActivationType.DOWN, key));
				return;
			}
			if (Input.getKeyUp(key)) {
				Input.setKeyConsumed(key);
				notifyListener(new ClickEvent(Type.KEYEVENT, ActivationType.DOWN, key));
				return;
			}
		}
	}
	
	public boolean checkArea(Vector3f pos) {
		return Input.getMousePosition().getX() > pos.getX() &&
				Input.getMousePosition().getX() < pos.getX() + width &&
				Input.getMousePosition().getY() > pos.getY() &&
				Input.getMousePosition().getY() < pos.getY() + height;
	}
	
	public void addListener(ClickableListener listener) {
		listeners.add(listener);
	}
	
	public void notifyListener(ClickEvent event) {
		for (ClickableListener listener: listeners) {
			listener.clickableActivated(event);
		}
	}
}
