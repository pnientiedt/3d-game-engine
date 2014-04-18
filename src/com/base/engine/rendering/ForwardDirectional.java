package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.DirectionalLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

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

	@Override
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniform("specularIntensity", material.getFloat("specularIntensity"));
		setUniform("specularPower", material.getFloat("specularPower"));

		setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTransformedPos());

		setUniformDirectionalLight("directionalLight", (DirectionalLight) renderingEngine.getActiveLight());
	}

	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight) {
		setUniformBaseLight(uniformName + ".base", directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
}
