package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardDirectional extends Shader{
	private static ForwardDirectional instance;

	private ForwardDirectional() {
		super();

		addVertexShaderFromFile("forward-directional.vs.glsl");
		addFragmentShaderFromFile("forward-directional.fs.glsl");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();

		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
	}

	public static ForwardDirectional getInstance() {
		if (instance == null) {
			synchronized (ForwardDirectional.class) {
				instance = new ForwardDirectional();
			}
		}
		return instance;
	}

	@Override
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);
		
		setUniform("specularIntensity", material.getSpecularIntensity());
		setUniform("specularPower", material.getSpecularpower());
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
		
		setUniform("directionalLight", getRenderingEngine().getDirectionalLight());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, DirectionalLight directionalLight ) {
		setUniform(uniformName + ".base", directionalLight.getBase());
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
}
