package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.physics.Transform;

public class ResetPlayerToCamera extends GameComponent {
	private Transform reset;
	private Transform forward;
	private int resetButton;
	
	public ResetPlayerToCamera(Transform reset, Transform forward, int resetButton) {
		this.reset = reset;
		this.forward = forward;
		this.resetButton = resetButton;
	}
	
	@Override
	public void input(float delta) {
		if (Input.getMouseDown(resetButton)) {
			
			//angle between camera and player
			Vector3f b = new Vector3f(0,0,0).set(forward.getTransformedPos()).setY(reset.getTransformedPos().getY()).sub(reset.getTransformedPos());
			Vector3f a= reset.getTransformedRot().getBack();
			float angle = getAngle(a,b);
			
			//rotate player to camera direction
			if (a.cross(b).getY() > 0)
				reset.rotate(new Vector3f(0,1,0), angle);
			else
				reset.rotate(new Vector3f(0,1,0), -angle);
		
			//save camera distance
			float distance = forward.getPos().length();
			
			//calculate angle between camera direction and player direction
			a = new Vector3f(0,0,0).set(forward.getPos()).setY(0);
			b = forward.getPos();
			angle = getAngle(a,b);
			
			//move camera to location
			forward.setRot(new Quaternion(new Matrix4f().initIdentity()));
			forward.rotate(forward.getRot().getAxis(Quaternion.Axis.RIGHT), angle);
			forward.setPos(forward.getRot().getForward().mul(-distance));
		}
		
	}
	
	public float getAngle(Vector3f a, Vector3f b) {
		float angle = (float) Math.acos(  (double)(a.dot(b)/((a.length() * b.length()))) );
		if (!Float.isNaN(angle))
			return angle;
		return 0;
	}
}
