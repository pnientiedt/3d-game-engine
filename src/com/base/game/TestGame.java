package com.base.game;

import com.base.engine.components.Camera;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Vertex;
import com.base.engine.rendering.Window;

public class TestGame extends Game {

	public TestGame() {
		
	}
	
	@Override
	public void init()
	{
		
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
		
		Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth/10, 0.0f, -fieldDepth/10), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-fieldWidth/10, 0.0f, fieldDepth/10 * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(fieldWidth/10 * 3, 0.0f, -fieldDepth/10), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(fieldWidth/10 * 3, 0.0f, fieldDepth/10 * 3), new Vector2f(1.0f, 1.0f))};
		
		int indices[] = { 0, 1, 2,
					      2, 1, 3};
		
		int indices2[] = { 0, 1, 2,
			      2, 1, 3};
		
		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material(new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		
		Mesh mesh2 = new Mesh(vertices2, indices2, true);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPos().set(0, -1, 5);
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(0,0,1), 0.4f, new Vector3f(1,1,1));;
		directionalLightObject.addComponent(directionalLight);
		
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0), 0.4f, new Vector3f(0,0,1)));
		
		GameObject spotLightObject = new GameObject();
		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f
				, new Vector3f(0,0,0.1f), 0.7f);
				spotLightObject.addComponent(spotLight);
				
		spotLight.getTransform().getPos().set(5,0,5);
		spotLight.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), (float) Math.toRadians(-90)));
		
		getRootObject().addChild(planeObject);
		getRootObject().addChild(directionalLightObject);
		getRootObject().addChild(pointLightObject);
		getRootObject().addChild(spotLightObject);
		
//		getRootObject().addChild(new GameObject().addComponent(new Camera((float)Math.toRadians(70f), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000)));
	
		GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		
		testMesh1.getTransform().getPos().set(0, 2, 0);
		testMesh1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), 0.4f));
		testMesh2.getTransform().getPos().set(0, 0, 5);
		testMesh2.addChild(new GameObject().addComponent(new Camera((float)Math.toRadians(70f), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000)));
		
		testMesh1.addChild(testMesh2);
		
		getRootObject().addChild(testMesh1);
	}
}
