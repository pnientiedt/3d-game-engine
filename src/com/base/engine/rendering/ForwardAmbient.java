package com.base.engine.rendering;

public class ForwardAmbient extends Shader{
	private static ForwardAmbient instance;

	private ForwardAmbient() {
		super("forward-ambient");
	}

	public static ForwardAmbient getInstance() {
		if (instance == null) {
			synchronized (ForwardAmbient.class) {
				instance = new ForwardAmbient();
			}
		}
		return instance;
	}
}
