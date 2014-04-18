package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

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
