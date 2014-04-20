package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Vector3f;

public class Camera3D extends GameComponent implements Camera {
	private Matrix4f projection;
	
	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}
	
	@Override
	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	@Override
	public void addToEngine(CoreEngine engine) {
		engine.getRenderingEngine().setCamera3D(this);
	}
}
