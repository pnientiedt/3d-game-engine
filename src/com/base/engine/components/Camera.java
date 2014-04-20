package com.base.engine.components;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public interface Camera {
	public Matrix4f getViewProjection();
	public Transform getTransform();
}
