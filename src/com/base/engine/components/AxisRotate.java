package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class AxisRotate extends GameComponent {
	public final static Vector3f yAxis = new Vector3f(0, 1, 0);

	private boolean mouseLocked = false;
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
		
		if (Input.getMouseUp(1) && mousePosition != null) {
			Input.setMousePosition(mousePosition);
			Input.setCursor(true);
			mouseLocked = false;
		}
		if (Input.getMouseDown(1)) {
			mousePosition = Input.getMousePosition();
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}

		if (mouseLocked) {			
			Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

			if (mouseAxis.getX() > 0 && deltaPos.getX() != 0) {
				getTransform().rotate(axis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
				Input.setMousePosition(centerPosition);
			}
			else if (mouseAxis.getY() > 0 && deltaPos.getY() != 0) {
				getTransform().rotate(axis, (float) Math.toRadians(-deltaPos.getY() * sensitivity));
				Input.setMousePosition(centerPosition);
			}
		}
	}

	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
