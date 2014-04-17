package com.base.engine.rendering;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;

public class Shader {
	private int program;
	private HashMap<String, Integer> uniforms;
	
	public Shader() {
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if (program == 0) {
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
	}
	
	public void bind() {
		glUseProgram(program);
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		
	}
	
	public void addAllAttributes(String shaderText) {
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while(attributeStartLocation != -1) {
			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);
			
			String attributeLine = shaderText.substring(begin, end);
			String attributeName = attributeLine.substring(attributeLine.indexOf(" ") + 1, attributeLine.length());
			
			setAttribLocation(attributeName, attribNumber);
			attribNumber++;
			
			attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
		}
	}
	
	public void addAllUniforms(String shaderText) {
		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while(uniformStartLocation != -1) {
			int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);
			
			String uniformLine = shaderText.substring(begin, end);
			String uniformName = uniformLine.substring(uniformLine.indexOf(" ") + 1, uniformLine.length());
			
			addUniform(uniformName);
			
			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
		}
	}
	
	public void addUniform(String uniform) {
		int uniformLocation = glGetUniformLocation(program, uniform);
		
		if (uniformLocation == 0xFFFFFFFF) {
			System.err.println("Error: Could not find uniform " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	public void addVertexShaderFromFile(String text) {
		addProgramm(loadShader(text), GL_VERTEX_SHADER);
	}
	
	public void addGeometryShaderFromFile(String text) {
		addProgramm(loadShader(text), GL_GEOMETRY_SHADER);
	}
	
	public void addFragmentShaderFromFile(String text) {
		addProgramm(loadShader(text), GL_FRAGMENT_SHADER);
	}
	
	public void addVertexShader(String text) {
		addProgramm(text, GL_VERTEX_SHADER);
	}
	
	public void addGeometryShader(String text) {
		addProgramm(text, GL_GEOMETRY_SHADER);
	}
	
	public void addFragmentShader(String text) {
		addProgramm(text, GL_FRAGMENT_SHADER);
	}
	
	public void setAttribLocation(String attributeName, int location) {
		glBindAttribLocation(program, location, attributeName);
	}
	
	public void compileShader() {
		glLinkProgram(program);
		
		if(glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	private void addProgramm(String text, int type) {
		int shader = glCreateShader(type);
		
		if (shader == 0) {
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(program, shader);
	}
	
	public void setUniform(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value)); //true = row mature order
	}
	
	protected String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";
		
		try {
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;
			while ((line = shaderReader.readLine()) != null) {
				if (line.startsWith(INCLUDE_DIRECTIVE)) {
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length()-1)));
				} else {
					shaderSource.append(line).append("\n");
				}
			}

			shaderReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		return shaderSource.toString();
	}
}
