package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class BasicShader extends Shader {

	private static BasicShader instance;

	private BasicShader() {
		super();

		addVertexShaderFromFile("basicVertex.glsl");
		addFragmentShaderFromFile("basicFragment.glsl");
		compileShader();

		addUniform("transform");
		addUniform("color");
	}

	public static BasicShader getInstance() {
		if (instance == null) {
			synchronized (BasicShader.class) {
				instance = new BasicShader();
			}
		}
		return instance;
	}

	@Override
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();

		setUniform("transform", projectedMatrix);
		setUniform("color", material.getColor());
	}

}
