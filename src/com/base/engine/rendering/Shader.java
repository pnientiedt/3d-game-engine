package com.base.engine.rendering;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.components.BaseLight;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;

public class Shader {
	private int program;
	private HashMap<String, Integer> uniforms;
	private ArrayList<String> uniformNames;
	private ArrayList<String> uniformTypes;

	public Shader(String fileName) {
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		uniformNames = new ArrayList<String>();
		uniformTypes = new ArrayList<String>();

		if (program == 0) {
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}

		String vertexShaderText = loadShader(fileName + ".vs.glsl");
		String fragmentShaderText = loadShader(fileName + ".fs.glsl");

		addVertexShader(vertexShaderText);
		addFragmentShader(fragmentShaderText);

		addAllAttributes(vertexShaderText);

		compileShader();

		addAllUniforms(vertexShaderText);
		addAllUniforms(fragmentShaderText);
	}

	public void bind() {
		glUseProgram(program);
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f MVPMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);

		for (int i = 0; i < uniformNames.size(); i++) {
			String uniformName = uniformNames.get(i);
			String uniformType = uniformTypes.get(i);

			if (uniformType.equals("sampler2D")) {
				int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
				material.getTexture(uniformName).bind(samplerSlot);
				setUniform(uniformName, samplerSlot);
			} else if (uniformName.startsWith("T_")) {
				if (uniformName.equals("T_MVP"))
					setUniform(uniformName, MVPMatrix);
				else if (uniformName.equals("T_model"))
					setUniform(uniformName, worldMatrix);
				else
					throw new IllegalArgumentException(uniformName + " is not valid component of Transform");
			} else if (uniformName.startsWith("R_")) {
				String unprefixedUniformName = uniformName.substring(2);
				if (uniformType.equals("vec3"))
					setUniform(uniformName, renderingEngine.getVector3f(unprefixedUniformName));
				else if (uniformType.equals("float"))
					setUniform(uniformName, renderingEngine.getFloat(unprefixedUniformName));
				else if (uniformType.equals("DirectionalLight"))
					setUniformDirectionalLight(uniformName, (DirectionalLight) renderingEngine.getActiveLight());
				else if (uniformType.equals("SpotLight"))
					setUniformSpotLight(uniformName, (SpotLight) renderingEngine.getActiveLight());
				else if (uniformType.equals("PointLight"))
					setUniformPointLight(uniformName, (PointLight) renderingEngine.getActiveLight());
				else
					renderingEngine.updateUniformStruct(transform, material, this, unprefixedUniformName, uniformType);
			} else if (uniformName.startsWith("C_")) {
				if (uniformName.equals("C_eyePos"))
					setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
				else
					throw new IllegalArgumentException(uniformName + " is not valid component of Camera");
			} else {
				if (uniformType.equals("vec3"))
					setUniform(uniformName, material.getVector3f(uniformName));
				else if (uniformType.equals("float"))
					setUniform(uniformName, material.getFloat(uniformName));
				else
					throw new IllegalArgumentException(uniformName + " is not a supported type in Material");
			}
		}
	}

	private class GLSLStruct {
		public GLSLStruct(String name, String type) {
			this.name = name;
			this.type = type;
		}

		public String name;
		public String type;
	}

	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

		final String STRUCT_KEYWORD = "struct";
		int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
		while (structStartLocation != -1) {
			if (!(structStartLocation != 0 && (Character.isWhitespace(shaderText.charAt(structStartLocation - 1)) || shaderText.charAt(structStartLocation - 1) == ';') && Character
					.isWhitespace(shaderText.charAt(structStartLocation + STRUCT_KEYWORD.length()))))
				continue;

			int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
			int braceBegin = shaderText.indexOf("{", nameBegin);
			int braceEnd = shaderText.indexOf("}", braceBegin);

			String structName = shaderText.substring(nameBegin, braceBegin).trim();

			ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

			int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
			while (componentSemicolonPos != -1 && componentSemicolonPos < braceEnd) {
				int componentNameEnd = componentSemicolonPos + 1;

				while (Character.isWhitespace(shaderText.charAt(componentNameEnd - 1)) || shaderText.charAt(componentNameEnd - 1) == ';')
					componentNameEnd--;

				int componentNameStart = componentSemicolonPos;

				while (!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
					componentNameStart--;

				int componentTypeEnd = componentNameStart;

				while (Character.isWhitespace(shaderText.charAt(componentTypeEnd - 1)))
					componentTypeEnd--;

				int componentTypeStart = componentTypeEnd;

				while (!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
					componentTypeStart--;

				String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
				String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

				glslStructs.add(new GLSLStruct(componentName, componentType));

				componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
			}

			result.put(structName, glslStructs);

			structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
		}

		return result;
	}

	private void addAllAttributes(String shaderText) {
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while (attributeStartLocation != -1) {
			if (!(attributeStartLocation != 0 && (Character.isWhitespace(shaderText.charAt(attributeStartLocation - 1)) || shaderText.charAt(attributeStartLocation - 1) == ';') && Character
					.isWhitespace(shaderText.charAt(attributeStartLocation + ATTRIBUTE_KEYWORD.length()))))
				continue;

			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String attributeLine = shaderText.substring(begin, end).trim();
			String attributeName = attributeLine.substring(attributeLine.indexOf(" ") + 1, attributeLine.length()).trim();

			setAttribLocation(attributeName, attribNumber);
			attribNumber++;

			attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
		}
	}

	private void addAllUniforms(String shaderText) {
		HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);

		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while (uniformStartLocation != -1) {
			if (!(uniformStartLocation != 0 && (Character.isWhitespace(shaderText.charAt(uniformStartLocation - 1)) || shaderText.charAt(uniformStartLocation - 1) == ';') && Character
					.isWhitespace(shaderText.charAt(uniformStartLocation + UNIFORM_KEYWORD.length()))))
				continue;

			int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String uniformLine = shaderText.substring(begin, end).trim();

			// TODO: check also for tabs etc.
			int whiteSpacePos = uniformLine.indexOf(' ');

			String uniformName = uniformLine.substring(whiteSpacePos + 1, uniformLine.length()).trim();
			String uniformType = uniformLine.substring(0, whiteSpacePos).trim();

			uniformNames.add(uniformName);
			uniformTypes.add(uniformType);
			addUniform(uniformName, uniformType, structs);

			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
		}
	}

	private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs) {
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);
		if (structComponents != null) {
			addThis = false;
			for (GLSLStruct struct : structComponents) {
				addUniform(uniformName + "." + struct.name, struct.type, structs);
			}
		}

		if (!addThis)
			return;

		int uniformLocation = glGetUniformLocation(program, uniformName);

		if (uniformLocation == 0xFFFFFFFF) {
			System.err.println("Error: Could not find uniform " + uniformName);
			new Exception().printStackTrace();
			System.exit(1);
		}

		uniforms.put(uniformName, uniformLocation);
	}

	private void addVertexShader(String text) {
		addProgramm(text, GL_VERTEX_SHADER);
	}

	private void addGeometryShader(String text) {
		addProgramm(text, GL_GEOMETRY_SHADER);
	}

	private void addFragmentShader(String text) {
		addProgramm(text, GL_FRAGMENT_SHADER);
	}

	private void setAttribLocation(String attributeName, int location) {
		glBindAttribLocation(program, location, attributeName);
	}

	private void compileShader() {
		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}

		glValidateProgram(program);

		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
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

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}

		glAttachShader(program, shader);
	}

	private String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";

		try {
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;
			while ((line = shaderReader.readLine()) != null) {
				if (line.startsWith(INCLUDE_DIRECTIVE)) {
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
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
		glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value)); // true
																							// =
																							// row
																							// mature
																							// order
	}

	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight) {
		setUniformBaseLight(uniformName + ".base", directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}

	public void setUniformPointLight(String uniformName, PointLight pointLight) {
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniform(uniformName + ".atten.constant", pointLight.getConstant());
		setUniform(uniformName + ".atten.linear", pointLight.getLinear());
		setUniform(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniform(uniformName + ".range", pointLight.getRange());
	}

	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		setUniformPointLight(uniformName + ".pointLight", spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniform(uniformName + ".cutoff", spotLight.getCutoff());
	}
}
