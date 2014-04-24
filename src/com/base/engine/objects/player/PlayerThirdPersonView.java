package com.base.engine.objects.player;

import com.base.engine.components.AxisRotate;
import com.base.engine.components.Camera3D;
import com.base.engine.components.FreeMove;
import com.base.engine.components.Jump;
import com.base.engine.components.ResetPlayerToCamera;
import com.base.engine.components.Zoom;
import com.base.engine.core.Input;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.objects.GameObject;
import com.base.engine.rendering.Window;

public class PlayerThirdPersonView extends GameObject {
	
	public PlayerThirdPersonView() {
		this(new Camera3D((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f),
				6, -10, 0.5f, 0.5f);
	}

	public PlayerThirdPersonView(Camera3D camera3D, float cameraInitialY, float cameraInitialZ, float mouseSensitivity, float zoomSensitivity) {
		super();
		
		//XY Player Movement
		addComponent(new FreeMove());
		//Y Axis Player Movement
		addComponent(new AxisRotate(Quaternion.Axis.Y, new Vector2f(1,0), new ControlRightMouse(), mouseSensitivity));
		
		addComponent(new Jump(15, Input.KEY_SPACE));
		
		//Camera
		GameObject camera = new GameObject();
		camera.addComponent(camera3D);
		//Set position
		camera.getTransform().setPos(camera.getTransform().getPos().add(new Vector3f(0,cameraInitialY,cameraInitialZ)));
		camera.getTransform().lookAt(new Vector3f(0,0,0), new Vector3f(0,1,0));
		//Zoom via mouse wheel
		camera.addComponent(new Zoom(zoomSensitivity));
		//Z Axis rotation around the player
		camera.addComponent(new AxisRotate(Quaternion.Axis.RIGHT, new Vector2f(0,1), new ControlRightMouse(), true, mouseSensitivity));
		
		//Left Key camera rotation
		camera.addComponent(new AxisRotate(Quaternion.Axis.Y, new Vector2f(1,0), new ControlLeftMouse(), true, mouseSensitivity));
		camera.addComponent(new AxisRotate(Quaternion.Axis.RIGHT, new Vector2f(0,1), new ControlLeftMouse(), true, mouseSensitivity));
		
		addChild(camera);		
		
		addComponent(new ResetPlayerToCamera(getTransform(), camera.getTransform(), Input.MOUSE_RIGHT));
	}
}
