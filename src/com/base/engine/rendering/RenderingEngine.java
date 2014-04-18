package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.core.GameObject;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.resourceManagement.MappedValues;

public class RenderingEngine extends MappedValues {
	
	private Camera mainCamera;
	
	private BaseLight activeLight;
	private ArrayList<BaseLight> lights;
	
	private HashMap<String, Integer> samplerMap;
	
	public RenderingEngine() {
		super();
		lights = new ArrayList<BaseLight>();
		samplerMap = new HashMap<String, Integer>();
		
		samplerMap.put("diffuse", 0);
		
		addVector3f("ambient", new Vector3f(0.1f, 0.1f, 0.1f));
		
		glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		
//		mainCamera = new Camera((float)Math.toRadians(70f), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000);
	
//		ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
//		activeDirectionalLight = new DirectionalLight(new BaseLight(new Vector3f(0,0,1), 0.4f), new Vector3f(1,1,1));
//		directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(1,0,0), 0.4f), new Vector3f(-1,1,-1));
//		
//		int lightFieldWidth = 5;
//		int lightFieldDepth = 5;
//
//		float lightFieldStartX = 0;
//		float lightFieldStartY = 0;
//		float lightFieldStepX = 7;
//		float lightFieldStepY = 7;
//		
//		pointLightList = new PointLight[lightFieldWidth * lightFieldDepth];
//
//		for (int i = 0; i < lightFieldWidth; i++) {
//			for (int j = 0; j < lightFieldDepth; j++) {
//				pointLightList[i * lightFieldWidth + j] = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.4f), new Attenuation(0, 0, 1),
//						new Vector3f(lightFieldStartX + lightFieldStepX * i, 0, lightFieldStartY + lightFieldStepY * j), 100);
//			}
//		}
//
//		activePointLight = pointLightList[0];//new PointLight(new BaseLight(new Vector3f(0,1,0), 0.4f), new Attenuation(0,0,1), new Vector3f(5,0,5), 100);
//	
//		spotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(0,1,1), 0.4f), new Attenuation(0,0,0.1f), new Vector3f(lightFieldStartX,0,lightFieldStartY), 100), new Vector3f(1,0,0), 0.7f);
	}
	
	public void render(GameObject object) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		lights.clear();
		object.addToRenderingEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		
		object.render(forwardAmbient, this);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for(BaseLight light: lights) {
			activeLight = light;
			object.render(light.getShader(), this);
		}
		
		glDepthFunc(GL_LESS);		
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}
	
	public void addLight(BaseLight light) {
		lights.add(light);
	}
	
	public BaseLight getActiveLight() {
		return activeLight;
	}
	
	public int getSamplerSlot(String samplerName) {
		return samplerMap.get(samplerName);
	}
	
	public void addCamera(Camera camera) {
		mainCamera = camera;
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
}
