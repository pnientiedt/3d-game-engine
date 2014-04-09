package com.base.engine.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import com.base.engine.rendering.BaseLight;
import com.base.engine.rendering.Camera;
import com.base.engine.rendering.DirectionalLight;
import com.base.engine.rendering.ForwardAmbient;
import com.base.engine.rendering.ForwardDirectional;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.Window;

public class RenderingEngine {
	
	private Camera mainCamera;
	private Vector3f ambientLight;
	private DirectionalLight directionalLight;
	private DirectionalLight directionalLight2;
	
	public RenderingEngine() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		
		mainCamera = new Camera((float)Math.toRadians(70f), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000);
	
		ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);
		directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0,0,1), 0.4f), new Vector3f(1,1,1));
		directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(1,0,0), 0.4f), new Vector3f(-1,1,-1));
	}
	
	public Vector3f getAmbientLight() {
		return ambientLight;
	}
	
	public DirectionalLight getDirectionalLight() {
		return directionalLight;
	}
	
	public void input(float delta) {
		mainCamera.input(delta);
	}
	
	public void render(GameObject object) {
		clearScreen();
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		Shader forwardDirectional = ForwardDirectional.getInstance();
		forwardAmbient.setRenderingEngine(this);
		forwardDirectional.setRenderingEngine(this);		
		
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		object.render(forwardDirectional);
		
		DirectionalLight temp = directionalLight;
		directionalLight = directionalLight2;
		directionalLight2 = temp;
		
		object.render(forwardDirectional);
		
		temp = directionalLight;
		directionalLight = directionalLight2;
		directionalLight2 = temp;
		
		glDepthFunc(GL_LESS);		
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	private static void clearScreen() {
		// TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	@SuppressWarnings("unused")
	private static void setTextures(boolean enabled) {
		if(enabled) {
			glEnable(GL_TEXTURE_2D);
		} else {
			glDisable(GL_TEXTURE_2D);
		}
	}
	
	@SuppressWarnings("unused")
	private static void unbindTextures() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	@SuppressWarnings("unused")
	private static void setClearColor(Vector3f color) {
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
	}
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
}
