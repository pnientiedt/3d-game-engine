package com.base.engine.rendering;

public class ForwardDirectional extends Shader {
	private static ForwardDirectional instance;

	private ForwardDirectional() {
		super("forward-directional");
	}

	public static ForwardDirectional getInstance() {
		if (instance == null) {
			synchronized (ForwardDirectional.class) {
				instance = new ForwardDirectional();
			}
		}
		return instance;
	}	
}
