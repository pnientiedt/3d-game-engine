package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.PointLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardPoint extends Shader {
	private static ForwardPoint instance;

	private ForwardPoint() {
		super();

		addVertexShaderFromFile("forward-point.vs.glsl");
		addFragmentShaderFromFile("forward-point.fs.glsl");

		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

		compileShader();

		addUniform("model");
		addUniform("MVP");

		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");

		addUniform("pointLight.base.color");
		addUniform("pointLight.base.intensity");
		addUniform("pointLight.atten.constant");
		addUniform("pointLight.atten.linear");
		addUniform("pointLight.atten.exponent");
		addUniform("pointLight.position");
		addUniform("pointLight.range");
	}

	public static ForwardPoint getInstance() {
		if (instance == null) {
			synchronized (ForwardPoint.class) {
				instance = new ForwardPoint();
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

		setUniformPointLight("pointLight", (PointLight) getRenderingEngine().getActiveLight());
	}

	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniformPointLight(String uniformName, PointLight pointLight) {
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniform(uniformName + ".atten.constant", pointLight.getConstant());
		setUniform(uniformName + ".atten.linear", pointLight.getLinear());
		setUniform(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getPosition());
		setUniform(uniformName + ".range", pointLight.getRange());
	}
}
