package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Input.MouseState;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

public class AxisRotate extends GameComponent {
	private boolean mouseLocked = false;
	private Quaternion.Axis axisState;
	private Vector3f axis;
	private Vector2f mouseAxis;
	private int mouseButton;
	private boolean rotateAroundParent;
	private float distanceToParent;
	private float sensitivity;
	
	public AxisRotate(Vector3f axis, Vector2f mouseAxis, int mouseButton, float sensitivity) {
		this(axis, mouseAxis, mouseButton, false, sensitivity);
	}
	
	public AxisRotate(Quaternion.Axis axisState, Vector2f mouseAxis, int mouseButton, boolean rotateAroundParent, float sensitivity) {
		this.axisState = axisState;
		this.mouseAxis = mouseAxis;
		this.mouseButton = mouseButton;
		this.rotateAroundParent = rotateAroundParent;
		this.sensitivity = sensitivity;
	}
	
	public AxisRotate(Vector3f axis, Vector2f mouseAxis, int mouseButton, boolean rotateAroundParent, float sensitivity) {
		this.axis = axis;
		this.mouseAxis = mouseAxis;
		this.mouseButton = mouseButton;
		this.rotateAroundParent = rotateAroundParent;
		this.sensitivity = sensitivity;
	}
	
	@Override
	public void input(float delta) {
		
		if (axisState != null)
			axis = getTransform().getRot().getAxis(axisState);
		
		if (Input.getMouseUp(mouseButton)) {
			Input.setMouseState(MouseState.CURSOR);
			mouseLocked = false;
		}
		if (Input.getMouseDown(mouseButton)) {
			Input.setMouseState(MouseState.DELTA);
			mouseLocked = true;
		}

		if (mouseLocked) {		
			
			
			
			if (rotateAroundParent) {
				distanceToParent = getTransform().getPos().length();
			}
			
			Vector2f deltaPos = Input.getMouseDelta();

			if (mouseAxis.getX() > 0 && deltaPos.getX() != 0) 
				getTransform().rotate(axis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			else if (mouseAxis.getY() > 0 && deltaPos.getY() != 0) 
				getTransform().rotate(axis, (float) Math.toRadians(-deltaPos.getY() * sensitivity));
			
			if (rotateAroundParent) {
				getTransform().setPos(getTransform().getRot().getForward().mul(-distanceToParent));
			}
		}
	}

	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
