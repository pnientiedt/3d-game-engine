package com.base.engine.rendering;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

public class MeshUtilities {
	public static Mesh getRectangle(float height, float width, float depth) {
		Vertex[] vertices = new Vertex[] { 	
				new Vertex( new Vector3f(0, height, depth), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(width, height, depth), new Vector2f(1, 0)),
				new Vertex( new Vector3f(0, 0 ,depth), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(width, 0, depth), new Vector2f(1.0f, 1.0f))};
		

		int indices[] = { 0, 1, 2,
				2, 1, 3};
		
		return new Mesh(vertices, indices, true);
	}
}
