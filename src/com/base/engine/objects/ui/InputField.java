package com.base.engine.objects.ui;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;

import com.base.engine.components.MeshRenderer;
import com.base.engine.components.ui.TextInput;
import com.base.engine.objects.GameObject;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.MeshUtilities;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.text.Font;
import com.base.engine.rendering.text.FontService;

public class InputField extends GameObject {
	
	private TextInput textInput;
	private int length;
	private int space;
	private int border;
	
	MeshRenderer background;
	MeshRenderer topBorder;
	MeshRenderer bottomBorder;
	MeshRenderer leftBorder;
	MeshRenderer rightBorder;
	
	public InputField() throws FontFormatException, IOException {
		this(FontService.getFont(), Color.white, 200, 0, 0);
	}
	
	public InputField(Font font, Color color, int length, int space, int border) {
		super();
		
		this.length = length;
		this.space = space;
		this.border = border;
		
		textInput = new TextInput(font, color, length);
		
		initializeLayout();
		
		GameObject backgroundObject = new GameObject();
		backgroundObject.addComponent(background);
		
		GameObject topObject = new GameObject();
		GameObject bottomObject = new GameObject();
		GameObject leftObject = new GameObject();
		GameObject rightObject = new GameObject();
		
		GameObject layoutObject = new GameObject();
		layoutObject.addChild(backgroundObject);
		addChild(layoutObject);
		
		GameObject textInputObject = new GameObject();
		textInputObject.addComponent(textInput);
		addChild(textInputObject);
	}
	
	private void initializeLayout() {
		Material backgroundMaterial = new Material();
		backgroundMaterial.addTexture("diffuse", new Texture("bricks.jpg"));
		backgroundMaterial.addFloat("specularIntensity", 1f);
		backgroundMaterial.addFloat("specularPower", 8f);
		
		Material borderMaterial = new Material();
		borderMaterial.addTexture("diffuse", new Texture("test.png"));
		borderMaterial.addFloat("specularIntensity", 1f);
		borderMaterial.addFloat("specularPower", 8f);
		
		//Background
		Mesh backgroundMesh = MeshUtilities.getRectangle(textInput.getFont().getSize() + space * 2, length + space * 2, 0);
		background = updateMeshRenderer(background, backgroundMesh, backgroundMaterial);
	}
	
	private MeshRenderer updateMeshRenderer(MeshRenderer meshRenderer, Mesh mesh, Material material) {
		if (meshRenderer == null) {
			meshRenderer = new MeshRenderer(mesh, material);
		} else {
			meshRenderer.setMesh(mesh);
			meshRenderer.setMaterial(material);
		}
		return meshRenderer;	
	}
	
	public void setFont(Font font) {
		textInput.setFont(font);
		initializeLayout();
	}

	public void setColor(Color color) {
		textInput.setColor(color);
	}

}
