package com.base.engine.rendering;

import com.base.engine.components.PointLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardSpot extends Shader{
	private static ForwardSpot instance;

	private ForwardSpot() {
		super();

		addVertexShaderFromFile("forward-spot.vs.glsl");
		addFragmentShaderFromFile("forward-spot.fs.glsl");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();

		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("spotLight.pointLight.base.color");
		addUniform("spotLight.pointLight.base.intensity");
		addUniform("spotLight.pointLight.atten.constant");
		addUniform("spotLight.pointLight.atten.linear");
		addUniform("spotLight.pointLight.atten.exponent");
		addUniform("spotLight.pointLight.position");
		addUniform("spotLight.pointLight.range");
		
		addUniform("spotLight.direction");
		addUniform("spotLight.cutoff");
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
	public void updateUniforms(Transform transform, Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);
		
		setUniform("specularIntensity", material.getSpecularIntensity());
		setUniform("specularPower", material.getSpecularpower());
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
		
		setUniform("spotLight",getRenderingEngine().getSpotLight());
	}
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, PointLight pointLight) {
		setUniform(uniformName + ".base", pointLight.getBaseLight());
		setUniform(uniformName + ".atten.constant", pointLight.getAtten().getConstant());
		setUniform(uniformName + ".atten.linear", pointLight.getAtten().getLinear());
		setUniform(uniformName + ".atten.exponent", pointLight.getAtten().getExponent());
		setUniform(uniformName + ".position", pointLight.getPosition());
		setUniform(uniformName + ".range", pointLight.getRange());
	}
	
	public void setUniform(String uniformName, SpotLight spotLight) {
		setUniform(uniformName + ".pointLight", spotLight.getPointLight());
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniform(uniformName + ".cutoff", spotLight.getCutoff());
	}
}
