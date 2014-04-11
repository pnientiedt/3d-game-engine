package com.base.engine.rendering;

import com.base.engine.components.PointLight;
import com.base.engine.core.Vector3f;

public class SpotLight {

	private PointLight pointLight;
	private Vector3f direction;
	private float cutoff;

	public SpotLight(PointLight pointLight, Vector3f direction, float cutoff) {
		super();
		this.pointLight = pointLight;
		this.direction = direction.normalized();
		this.cutoff = cutoff;
	}

	public PointLight getPointLight() {
		return pointLight;
	}

	public void setPointLights(PointLight pointLight) {
		this.pointLight = pointLight;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction.normalized();
	}

	public float getCutoff() {
		return cutoff;
	}

	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

}
