package com.base.engine.objects;

import com.base.engine.components.AxisMove;
import com.base.engine.components.AxisRotate;
import com.base.engine.components.Camera3D;
import com.base.engine.components.Jump;
import com.base.engine.components.ResetPlayerToCamera;
import com.base.engine.components.Zoom;
import com.base.engine.control.ControlForward;
import com.base.engine.control.ControlLeftMouse;
import com.base.engine.control.ControlMoveLeftRight;
import com.base.engine.control.ControlRightMouse;
import com.base.engine.control.ControlRotateLeftRight;
import com.base.engine.control.KeyControl;
import com.base.engine.core.Input;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class PlayerThirdPersonView extends GameObject {
	
	public PlayerThirdPersonView() {
		this(new Camera3D((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f),
				6, -10, 0.5f, 0.5f);
	}

	public PlayerThirdPersonView(Camera3D camera3D, float cameraInitialY, float cameraInitialZ, float mouseSensitivity, float zoomSensitivity) {
		super();
		
		//XY Player Movement
		int speed = 10;
		int rotationSensitivity = 2;
		ControlForward controlForward = new ControlForward(Input.KEY_W);
		addComponent(new AxisMove(Quaternion.Axis.FORWARD, speed, controlForward));
		addComponent(new AxisMove(Quaternion.Axis.BACK, speed, new KeyControl(Input.KEY_S)));
		addComponent(new AxisMove(Quaternion.Axis.LEFT, speed, new ControlMoveLeftRight(controlForward, Input.KEY_A)));
		addComponent(new AxisMove(Quaternion.Axis.RIGHT, speed,new ControlMoveLeftRight(controlForward, Input.KEY_D)));
		addComponent(new AxisRotate(Quaternion.Axis.DOWN, null, new ControlRotateLeftRight(controlForward,  Input.KEY_A) , rotationSensitivity));
		addComponent(new AxisRotate(Quaternion.Axis.UP, null, new ControlRotateLeftRight(controlForward,  Input.KEY_D) , rotationSensitivity));
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
