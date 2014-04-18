package com.base.engine.rendering;

public class ForwardSpot extends Shader {
	private static ForwardSpot instance;

	private ForwardSpot() {
		super("forward-spot");
	}

	public static ForwardSpot getInstance() {
		if (instance == null) {
			synchronized (ForwardSpot.class) {
				instance = new ForwardSpot();
			}
		}
		return instance;
	}
}
