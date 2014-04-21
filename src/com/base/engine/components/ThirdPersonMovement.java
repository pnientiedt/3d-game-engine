package com.base.engine.components;

import com.base.engine.objects.GameObject;

public class ThirdPersonMovement extends GameComponent {
	
	public ThirdPersonMovement(GameObject player, GameObject camera) {
		player.addComponent(new FreeMove());
	}

}
