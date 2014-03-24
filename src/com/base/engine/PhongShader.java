package com.base.engine;

public class PhongShader extends Shader {
	
	private static PhongShader instance;
	
	private static Vector3f ambientLight;

	private PhongShader() {
		super();

		addVertexShader(ResourceLoader.loadShader("phongVertex.glsl"));
		addFragmentShader(ResourceLoader.loadShader("phongFragment.glsl"));
		compileShader();

		addUniform("transform");
		addUniform("baseColor");
		addUniform("ambientLight");
	}

	public static PhongShader getInstance() {
		if (instance == null) {
			instance = new PhongShader();
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
		setUniform("baseColor", material.getColor());
		setUniform("ambientLight", ambientLight);
	}

	public static Vector3f getAmgientLight() {
		return ambientLight;
	}

	public static void setAmgientLight(Vector3f ambientLight) {
		PhongShader.ambientLight = ambientLight;
	}

}
