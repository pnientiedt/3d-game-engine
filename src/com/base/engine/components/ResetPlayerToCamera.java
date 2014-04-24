package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.physics.Transform;

public class ResetPlayerToCamera extends GameComponent {
	private Transform player;
	private Transform camera;
	private int resetButton;
	
	public ResetPlayerToCamera(Transform player, Transform camera, int resetButton) {
		this.player = player;
		this.camera = camera;
		this.resetButton = resetButton;
	}
	
	@Override
	public void input(float delta) {
		if (Input.getMouseDown(resetButton)) {
			
			//angle between camera and player
			Vector3f b = new Vector3f(0,0,0).set(camera.getTransformedPos()).setY(player.getTransformedPos().getY()).sub(player.getTransformedPos());
			Vector3f a= player.getTransformedRot().getBack();
			float angle = getAngle(a,b);
			
			//rotate player to camera direction
			if (a.cross(b).getY() > 0)
				player.rotate(new Vector3f(0,1,0), angle);
			else
				player.rotate(new Vector3f(0,1,0), -angle);
		
			//save camera distance
			float distance = camera.getPos().length();
			
			//calculate angle between camera direction and player direction
			a = new Vector3f(0,0,0).set(camera.getPos()).setY(0);
			b = camera.getPos();
			angle = getAngle(a,b);
			
			//move camera to location
			camera.setRot(new Quaternion(new Matrix4f().initIdentity()));
			camera.rotate(camera.getRot().getAxis(Quaternion.Axis.RIGHT), angle);
			camera.setPos(camera.getRot().getForward().mul(-distance));
		}
		
	}
	
	public float getAngle(Vector3f a, Vector3f b) {
		float angle = (float) Math.acos(  a.dot(b)/((a.length() * b.length())) );
		if (!Float.isNaN(angle))
			return angle;
		return 0;
	}
}
