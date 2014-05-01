package com.base.engine.objects.ui;

import org.newdawn.slick.Color;

import com.base.engine.components.MeshRenderer;
import com.base.engine.components.ui.TextInput;
import com.base.engine.objects.GameObject;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.MeshUtilities;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.text.Font;

public class InputField extends GameObject {
	
	private TextInput textInput;
	private int length;
	
	private GameObject testBackground;
	
	public InputField(String name, Font font, Color color, int length) {
		super(name);
		
		this.length = length;
		
		textInput = new TextInput(font, color, length);
		
		GameObject textInputObject = new GameObject(name + "TextInput");
		textInputObject.addComponent(textInput);
		addChild(textInputObject);
	}
	
	public void setFont(Font font) {
		textInput.setFont(font);
	}

	public void setColor(Color color) {
		textInput.setColor(color);
	}

	public int getLength() {
		return length;
	}
	
	public String getText() {
		return textInput.getText();
	}
	
	public void setTestMode(boolean testMode) {
		if (testMode && testBackground == null) {
			int height = textInput.getFont().getSize();
			
			Material testMaterial = new Material();
			testMaterial.addTexture("diffuse", new Texture("test.png"));
			Mesh testMesh = MeshUtilities.getRectangle(height, length, 0);
			MeshRenderer testMeshRenderer = new MeshRenderer(testMesh, testMaterial);
			testBackground = new GameObject(name + "TestBackground").addComponent(testMeshRenderer);
			addChildLowPriority(testBackground);
			
		} else {
			removeChild(testBackground);
			testBackground = null;
		}
	}

}
