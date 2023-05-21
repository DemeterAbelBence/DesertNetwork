package prototipus;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Drawable {
	Image image;
	// Koordinatak
	Vector2 coordinates;
	
	public abstract void Draw(Graphics g);
	
	public void Move(Vector2 coordinates) {
		// ??
		this.coordinates = coordinates;
	}
	
	public Drawable(Vector2 coordinates, Image image) {
		this.image = image;
		this.coordinates = coordinates;
	}
}
