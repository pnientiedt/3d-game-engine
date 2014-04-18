package com.base.engine.rendering;

public class ForwardPoint extends Shader {
	private static ForwardPoint instance;

	private ForwardPoint() {
		super("forward-point");
	}

	public static ForwardPoint getInstance() {
		if (instance == null) {
			synchronized (ForwardPoint.class) {
				instance = new ForwardPoint();
			}
		}
		return instance;
	}
}
