package prototipus;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Drawable {
	Image image;
	// Koordinatak
	Vector2 coordinates;
	static Observer observer;

	//Hogy a közepére kerüljön a node-nak a player
	final static int spriteOffset = 16;
	public abstract void Draw(Graphics g);
	
	public void Move(Vector2 coordinates) {
		// ??
		this.coordinates = coordinates;
		observer.repaint();
	}
	
	public Drawable(Vector2 coordinates, Image image) {
		this.image = image;
		this.coordinates = coordinates;
	}
	
	public int getX() {
		return coordinates.getX();
	}
	
	public int getY() {
		return coordinates.getY();
	}
	
	public static void setObserver(Observer o) {
		observer = o;
	}
	public Vector2 getCoordinates() {return coordinates;}
}
