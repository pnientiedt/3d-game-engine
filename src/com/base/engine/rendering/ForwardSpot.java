package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardSpot extends Shader {
	private static ForwardSpot instance;

	private ForwardSpot() {
		super("forward-spot");
	}

	public static ForwardSpot getInstance() {
		if (instance == null) {
			synchronized (ForwardSpot.class) {
				instance = new ForwardSpot();
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

		setUniformSpotLight("spotLight", (SpotLight) renderingEngine.getActiveLight());
	}

	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniform(String uniformName, PointLight pointLight) {
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniform(uniformName + ".atten.constant", pointLight.getConstant());
		setUniform(uniformName + ".atten.linear", pointLight.getLinear());
		setUniform(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniform(uniformName + ".range", pointLight.getRange());
	}

	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		setUniform(uniformName + ".pointLight", spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniform(uniformName + ".cutoff", spotLight.getCutoff());
	}
}
