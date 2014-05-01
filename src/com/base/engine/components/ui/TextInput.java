package com.base.engine.components.ui;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Input;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.text.Font;

public class TextInput extends GameComponent {

	private final static float PRESSEDINTERVAL = 0.1f;

	private boolean active;
	private int length;
	private String text = "";
	private Font font;
	private Color color;
	private float passedTime = 0;
	int[] pressedKeys = new int[Keyboard.getKeyCount()];
	boolean password;

	public TextInput(Font font, Color color, int length, boolean password) {
		this.font = font;
		this.color = color;
		this.length = length;
		this.password = password;
	}

	@Override
	public void input(float delta) {
		Vector3f pos = getTransform().getTransformedPos();
		if (Input.getMouseDown(Input.MOUSE_LEFT)) {
			if(	Input.getMousePosition().getX() > pos.getX() &&
				Input.getMousePosition().getX() < pos.getX() + length &&
				Input.getMousePosition().getY() > pos.getY() &&
				Input.getMousePosition().getY() < pos.getY() + font.getSize()) {
				active = true;
			} else
				active = false;
		}
		
		
		if (!active)
			return;
		passedTime += delta;
		if (!handleKey(Input.KEY_BACK)) {
			for (int key : Input.getWritableKeycodes()) {
				// for (int key = 0; key < Keyboard.getKeyCount(); key++) {
				handleKey(key);
			}
		}
	}

	private boolean handleKey(int key) {
		Input.setKeyConsumed(key);
		if (Input.getKey(key) && passedTime > PRESSEDINTERVAL)
			pressedKeys[key] = pressedKeys[key] + 1;

		if (Input.getKeyDown(key) || Input.getKey(key) && passedTime > PRESSEDINTERVAL && pressedKeys[key] > 40) {
			if (key == Input.KEY_BACK) {
				if (text.length() > 0)
					text = text.substring(0, text.length() - 1);
			} else if (Input.getKey(Input.KEY_LSHIFT) || Input.getKey(Input.KEY_RSHIFT))
				text += Input.getUpperCase(key);
			else
				text += Input.getLowerCase(key);
			// System.out.println(key + " : " + Keyboard.getKeyName(key));
			if (passedTime > PRESSEDINTERVAL)
				passedTime = 0;
			return true;
		} else if (!Input.getKey(key))
			pressedKeys[key] = 0;
		return false;
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		try {
			String renderText = text;
			while (font.getWidth(renderText) > length) {
				renderText = renderText.substring(1);
			}
			if (password) {
				int textLength = renderText.length();
				renderText = "";
				for (int i = 0; i < textLength; i++) {
					renderText += "*";
				}
			}
			font.render(getTransform().getTransformedPos().getX(), getTransform().getTransformedPos().getY(), renderText, color);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Font getFont() {
		return font;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getcolor() {
		return color;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getText() {
		return text;
	}
}
