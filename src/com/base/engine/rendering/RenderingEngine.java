package com.base.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.components.Camera3D;
import com.base.engine.components.Camera2D;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;
import com.base.engine.objects.GameObject;
import com.base.engine.rendering.resourceManagement.MappedValues;

public class RenderingEngine extends MappedValues {
	
	
	private HashMap<String, Integer> samplerMap;
	private BaseLight activeLight;
	private ArrayList<BaseLight> lights;
	
	private Shader forwardAmbient;
	private Shader forwardUI;
	private Camera3D camera3D;
	private Camera2D camera2D;
	private boolean perspective;
	
	public RenderingEngine() {
		super();
		perspective = true;
		lights = new ArrayList<BaseLight>();
		samplerMap = new HashMap<String, Integer>();
		
		samplerMap.put("diffuse", 0);
		
		addVector3f("ambient", new Vector3f(0.1f, 0.1f, 0.1f));
		
		addVector3f("ambient2D", new Vector3f(1f, 1f, 1f));
		
		forwardAmbient = new Shader("forward-ambient");
		forwardUI = new Shader("forward-ui");
		
		glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
	}
	
	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType) {
		throw new IllegalArgumentException(uniformName + " is not a supported type in Rendering Engine");
	}
	
	public void render(GameObject object, GameObject ui) {
		/*** 3D ***/
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		object.renderAll(forwardAmbient, this);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for(BaseLight light: lights) {
			activeLight = light;
			object.renderAll(light.getShader(), this);
		}
		
		glDepthFunc(GL_LESS);		
		glDepthMask(true);
		glDisable(GL_BLEND);
		
		/*** 2D ***/
		perspective = false;
		
		ui.renderAll(forwardUI, this);
		
		perspective = true;
		/*** 2D END***/
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

	public Camera getMainCamera() {
		if (perspective)
			return camera3D;
		else
			return camera2D;
	}

	public void setCamera3D(Camera3D camera3D) {
		this.camera3D = camera3D;
	}

	public void addCamera2D(Camera2D camera2d) {
		this.camera2D = camera2d;
	}
}
