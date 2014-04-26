package com.base.engine.control;

import java.util.ArrayList;
import java.util.Arrays;

import com.base.engine.core.Input;

public class KeyControl extends Control {
	
	private boolean active = false;
	
	public ArrayList<Integer> keys;
	
	public KeyControl(Integer... keys) {
		this.keys = new ArrayList<Integer>();
		this.keys.addAll(Arrays.asList(keys));
	}
	
	@Override
	public boolean isActive() {
		for (Integer key: keys) {
			if (Input.getKey(key) && !Input.isKeyConsumed(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isActivated() {
		return isActive() && !active;
	}

	@Override
	public boolean isDeactivated() {
		return !isActive() && active;
	}

}
