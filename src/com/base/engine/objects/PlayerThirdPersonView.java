package com.base.engine.objects;

import com.base.engine.components.AxisRotate;
import com.base.engine.components.Camera3D;
import com.base.engine.components.FreeMove;
import com.base.engine.components.Zoom;
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
		addComponent(new FreeMove());
		//Y Axis Player Movement
		addComponent(new AxisRotate(new Vector3f(0,1,0), new Vector2f(1,0), 1, mouseSensitivity));
		
		//Camera
		GameObject camera = new GameObject();
		camera.addComponent(camera3D);
		//Set position
		camera.getTransform().setPos(camera.getTransform().getPos().add(new Vector3f(0,cameraInitialY,cameraInitialZ)));
		camera.getTransform().lookAt(new Vector3f(0,0,0), new Vector3f(0,1,0));
		//Zoom via mouse wheel
		camera.addComponent(new Zoom(zoomSensitivity));
		//Z Axis rotation around the player
		camera.addComponent(new AxisRotate(new Vector3f(1,0,0), new Vector2f(0,1), 1, true, mouseSensitivity));
		
		addChild(camera);		
	}
}