package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.objects.GameObject;
import com.base.engine.objects.player.PlayerThirdPersonView;
import com.base.engine.rendering.*;

public class TestGame extends Game
{
	@Override
	public void init()
	{
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;

		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

		int indices[] = { 0, 1, 2,
				2, 1, 3};

		Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, -fieldDepth/ 10), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth/ 10, 0.0f, fieldDepth/ 10 * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, -fieldDepth/ 10), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth/ 10 * 3, 0.0f, fieldDepth/ 10 * 3), new Vector2f(1.0f, 1.0f))};

		int indices2[] = { 0, 1, 2,
				2, 1, 3};
		
		

		Mesh mesh2 = new Mesh(vertices2, indices2, true);

		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material();//)new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material.addTexture("diffuse", new Texture("bricks.jpg"));//material.addTexture("diffuse", new Texture("test.png"));
		material.addFloat("specularIntensity", 1f);
		material.addFloat("specularPower", 8f);
		
		Material material2 = new Material();//)new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material2.addTexture("diffuse", new Texture("bricks.jpg"));
		material2.addFloat("specularIntensity", 1f);
		material2.addFloat("specularPower", 8f);
		
		Mesh tempMesh = new Mesh("monkey3.obj");
		
		
				
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPos().set(0, -1, 5);

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0,0,1), 0.4f);

		directionalLightObject.addComponent(directionalLight);

		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0), 0.4f, new Attenuation(0,0,1)));

		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
				new Attenuation(0,0,0.1f), 0.7f);

		GameObject spotLightObject = new GameObject();
		spotLightObject.addComponent(spotLight);

		spotLightObject.getTransform().getPos().set(5, 0, 5);
		spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(90.0f)));

		addObject(planeObject);
		addObject(directionalLightObject);
		addObject(pointLightObject);
		addObject(spotLightObject);

		//getRootObject().addChild(new GameObject().addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f)));

		GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh3 = new GameObject().addComponent(new LookAtComponent()).addComponent(new MeshRenderer(tempMesh, material));

		testMesh1.getTransform().getPos().set(0, 2, 0);
		testMesh1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));

		testMesh2.getTransform().getPos().set(0, 0, 5);

		testMesh1.addChild(testMesh2);
//		testMesh2
		//getRootObject()
//						.addChild(new GameObject().addComponent(new FreeLook()).addComponent(new FreeMove()).addComponent(new Camera3D((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f)));

		addObject(testMesh1);
		addObject(testMesh3);
		
		testMesh3.getTransform().getPos().set(5,5,5);
		testMesh3.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(-70.0f)));
		
		addObject(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2)));

		directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1,0,0), (float)Math.toRadians(-45)));
		
		//Player
		Material playerMaterial = new Material();
		playerMaterial.addTexture("diffuse", new Texture("bricks.jpg"));
		playerMaterial.addFloat("specularIntensity", 1f);
		playerMaterial.addFloat("specularPower", 8f);
		Mesh playerMesh = new Mesh("monkey3.obj");
		addObject(new PlayerThirdPersonView().addComponent(new MeshRenderer(playerMesh, playerMaterial)));
		
		//UI
		
		Material material3 = new Material();//)new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material3.addTexture("diffuse", new Texture("test.png"));//material.addTexture("diffuse", new Texture("test.png"));
		material3.addFloat("specularIntensity", 1f);
		material3.addFloat("specularPower", 8f);
		
		Vertex[] vertices3 = new Vertex[] { 	new Vertex( new Vector3f(0, 0, 0), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(0, 100, 0), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(100, 0 ,0), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(100, 100, 0), new Vector2f(1.0f, 1.0f))};

		int indices3[] = { 0, 1, 2,
				2, 1, 3};
		
		Mesh ui = new Mesh(vertices3, indices3, true);
		
		MeshRenderer uiRenderer = new MeshRenderer(ui, material3);
		GameObject uiObject = new GameObject();
//		uiObject.getTransform().setPos(new Vector3f(0, 0, 0));
		
		addToUI(uiObject.addComponent(uiRenderer));
		
		addToUI(new GameObject().addComponent(new Camera2D()));
	}
}
