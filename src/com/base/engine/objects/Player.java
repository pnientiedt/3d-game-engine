package com.base.engine.objects;

import com.base.engine.components.AxisRotate;
import com.base.engine.components.Camera3D;
import com.base.engine.components.FreeMove;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.Zoom;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Window;

public class Player extends GameObject {

	public Player() {
		super();
		addComponent(new FreeMove());
		GameObject camera = new GameObject();
		camera.addComponent(new Camera3D((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f));
		
		addChild(camera);
		camera.getTransform().setPos(camera.getTransform().getPos().add(new Vector3f(0,0,-3)));
//		camera.addComponent(new FreeLook());
		camera.addComponent(new Zoom(0.5f));
		
		Material material = new Material();
		material.addTexture("diffuse", new Texture("bricks.jpg"));
		material.addFloat("specularIntensity", 1f);
		material.addFloat("specularPower", 8f);
		Mesh tempMesh = new Mesh("monkey3.obj");
		addComponent(new MeshRenderer(tempMesh, material));
		
		addComponent(new AxisRotate(new Vector3f(0,1,0), new Vector2f(1,0), 0.5f));
	}
}
