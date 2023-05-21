package prototipus;

import java.awt.Image;

public class DrawablePlayer extends Drawable{
	Player player;
	
	public DrawablePlayer(Player player, GamePanel.Vector2 coordinates, Image image){
		super(coordinates, image);
		this.player = player;
	}
	
	@Override
	public void Draw() {
		// TODO 
	}
}
