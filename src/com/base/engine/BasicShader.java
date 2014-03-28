package com.base.engine;

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
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
		if (material.getTexture() != null)
			material.getTexture().bind();
		else
			RenderUtil.unbindTextures();

		setUniform("transform", projectedMatrix);
		setUniform("color", material.getColor());
	}

}
