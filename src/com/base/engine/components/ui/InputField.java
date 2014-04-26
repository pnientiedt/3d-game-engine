package com.base.engine.components.ui;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.base.engine.components.GameComponent;
import com.base.engine.core.Input;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.text.Font;
import com.base.engine.rendering.text.FontService;

public class InputField extends GameComponent {

	private final static float PRESSEDINTERVAL = 0.1f;

	private boolean active = true;
	private String text = "";
	private Font font;
	private Color color;
	private float passedTime = 0;
	int[] pressedKeys = new int[Keyboard.getKeyCount()];

	public InputField() {
	}

	@Override
	public void input(float delta) {
		if (!active)
			return;

		passedTime += delta;
		for (int key : Input.getWritableKeycodes()) {
			// for (int key = 0; key < Keyboard.getKeyCount(); key++) {
			Input.setKeyConsumed(key);
			if (Input.getKey(key) && passedTime > PRESSEDINTERVAL)
				pressedKeys[key] = pressedKeys[key] + 1;
			
			if (Input.getKeyDown(key) || Input.getKey(key) && passedTime > PRESSEDINTERVAL && pressedKeys[key] > 40) {
				if (Input.getKey(Input.KEY_LSHIFT) || Input.getKey(Input.KEY_RSHIFT))
					text += Input.getUpperCase(key);
				else
					text += Input.getLowerCase(key);
				// System.out.println(key + " : " + Keyboard.getKeyName(key));
				if (passedTime > PRESSEDINTERVAL)
					passedTime = 0;
			} else if (!Input.getKey(key))
				pressedKeys[key] = 0;
		}
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine) {
		try {
			if (font == null)
				font = FontService.getFont();
			if (color == null)
				color = Color.white;
			font.render(getTransform().getPos().getX(), getTransform().getPos().getY(), text, color);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
