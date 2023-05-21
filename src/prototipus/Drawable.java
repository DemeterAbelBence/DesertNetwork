package prototipus;

import java.awt.Image;

public abstract class Drawable {
	Image image;
	// Koordinatak
	GamePanel.Vector2 coordinates;
	
	public abstract void Draw();
	
	public void Move(GamePanel.Vector2 coordinates) {
		// ??
		this.coordinates = coordinates;
	}
	
	public Drawable(GamePanel.Vector2 coordinates, Image image) {
		this.image = image;
		this.coordinates = coordinates;
	}
}
