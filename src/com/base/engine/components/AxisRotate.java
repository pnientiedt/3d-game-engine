package com.base.engine.components;

import com.base.engine.core.Control;
import com.base.engine.core.Input;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

public class AxisRotate extends GameComponent {
	private Control control;
	private boolean mouseLocked = false;
	private Quaternion.Axis axisState;
	private Vector2f mouseAxis;
	private boolean rotateAroundParent;
	private float sensitivity;
	
	public AxisRotate(Quaternion.Axis axisState, Vector2f mouseAxis, Control control, float sensitivity) {
		this(axisState, mouseAxis, control, false, sensitivity);
	}
	
	public AxisRotate(Quaternion.Axis axisState, Vector2f mouseAxis, Control control, boolean rotateAroundParent, float sensitivity) {
		this.axisState = axisState;
		this.mouseAxis = mouseAxis;
		this.control = control;
		this.rotateAroundParent = rotateAroundParent;
		this.sensitivity = sensitivity;
	}
	
	@Override
	public void input(float delta) {
		
		if (control.isDeactivated()) {
			System.out.println("deactivated by " + control);
			Input.releaseDelta();
			mouseLocked = false;
		}
		if (control.isActivated()) {
			System.out.println("activated by " + control);
			Input.subscribeDelta();
			mouseLocked = true;
		}

		if (mouseLocked) {		
			Vector3f axis = getTransform().getRot().getAxis(axisState);;
			Vector2f deltaPos = Input.getMouseDelta();

			if (mouseAxis.getX() > 0 && deltaPos.getX() != 0) 
				getTransform().rotate(axis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			else if (mouseAxis.getY() > 0 && deltaPos.getY() != 0) 
				getTransform().rotate(axis, (float) Math.toRadians(-deltaPos.getY() * sensitivity));
			
			if (rotateAroundParent) {
				getTransform().setPos(getTransform().getRot().getForward().mul(-getTransform().getPos().length()));
			}
		}
	}

	public void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
