package com.base.engine.rendering.text;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import com.base.engine.rendering.Window;

public class Font {
	
	private static final String RES = "/res/fonts";
	
	private TrueTypeFont font;
	private int id;
	private int size;

	public Font(String name, int size) throws FontFormatException, IOException {
		this.size = size;
		if (Files.exists(FileSystems.getDefault().getPath(RES + "/" + name))) {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("brinathyn.ttf");
			 
			java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(24f); // set font size
			font = new TrueTypeFont(awtFont, false);
		} else {
			java.awt.Font awtFont = new java.awt.Font("Times New Roman", java.awt.Font.BOLD, size);
			font = new TrueTypeFont(awtFont, false);
		}
		id = glGetInteger(GL_TEXTURE_BINDING_2D);
	}
	
	public void render(float x, float y, String text, Color color) {
		glUseProgram(0);
		glDisable(GL_CULL_FACE);
		glDisable(GL11.GL_DEPTH_TEST);
		glDisable(GL11.GL_LIGHTING);
		glEnable(GL11.GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glBindTexture(GL11.GL_TEXTURE_2D, id);
		Color.white.bind();
		font.drawString(x, Window.getHeight() - y - size, text, color);
		glEnable(GL_CULL_FACE);
		glEnable(GL11.GL_DEPTH_TEST);
		glEnable(GL11.GL_LIGHTING);
		glDisable(GL11.GL_BLEND);
	}

}
