package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class AxisRotate extends GameComponent {
	public final static Vector3f yAxis = new Vector3f(0, 1, 0);

	private Vector3f axis;
	private Vector2f mouseAxis;
	private float sensitivity;
	private Vector2f mousePosition;
	
	public AxisRotate(Vector3f axis, Vector2f mouseAxis, float sensitivity) {
		this.axis = axis;
		this.mouseAxis = mouseAxis;
		this.sensitivity = sensitivity;
	}
	
	@Override
	public void input(float delta) {
		Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

		if (Input.getMouseDown(1)) {
			if (mousePosition == null) {
				mousePosition = Input.getMousePosition();
				Input.setCursor(false);
			}
			
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

			if (mouseAxis.getX() > 0)
				getTransform().rotate(axis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			else if (mouseAxis.getY() > 0)
				getTransform().rotate(axis, (float) Math.toRadians(-deltaPos.getY() * sensitivity));
		} else if (mousePosition != null){
			Input.setCursor(true);
			Input.setMousePosition(mousePosition);
			mousePosition = null;
		}
	}

	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
