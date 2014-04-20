package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.Matrix4f;
import com.base.engine.rendering.Window;

public class Camera2D extends GameComponent implements Camera {
	private Matrix4f projection;
	
	public Camera2D() {
		this.projection = new Matrix4f().initOrthographic(0, Window.getWidth(), 0, Window.getHeight(), -1, 1);
	}
	
	@Override
	public Matrix4f getViewProjection() {
		return projection;
	}
	
	@Override
	public void addToEngine(CoreEngine engine) {
		engine.getRenderingEngine().addCamera2D(this);
	}
}
