package com.base.engine.ui;

import com.base.engine.components.MeshRenderer;
import com.base.engine.objects.GameObject;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.MeshUtilities;
import com.base.engine.rendering.Texture;

public class Button extends GameObject {

	String name;
	int height;
	int width;
	Clickable clickable;
	GameObject clickBackground;
	
	public Button(String name, int height, int width, boolean left, boolean right, int... keys) {
		super(name + "Button");
		this.name = name;
		this.height = height;
		this.width = width;
		clickable = new Clickable(height, width, left, right, keys);
//		addChild(new GameObject("LoginButton").addComponent(clickable));
		addComponent(clickable);
		
//		click(true);
	}
	
	public void addListener(ClickableListener listener) {
		clickable.addListener(listener);
	}
	
	public void click(boolean pressed) {
		if (pressed && clickBackground == null) {
			Material testMaterial = new Material();
			testMaterial.addTexture("diffuse", new Texture("Log_in_box_button_invert.png"));
			Mesh testMesh = MeshUtilities.getRectangle(height, width, 0);
			MeshRenderer testMeshRenderer = new MeshRenderer(testMesh, testMaterial);
			clickBackground = new GameObject(name + "ButtonClicket").addComponent(testMeshRenderer);
			addChild(clickBackground);
		} else {
			removeChild(clickBackground);
			clickBackground = null;
		}
	}

}
