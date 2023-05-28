package prototipus;

import java.awt.Graphics;
import java.awt.Image;

/**Absztrakt osztály, a rajzolásért felelős*/
public abstract class Drawable {
	Image image;
	// Koordinatak
	Vector2 coordinates;
	static Observer observer;

	//Hogy a közepére kerüljön a node-nak a player
	final static int spriteOffset = 16;
	/**A kirajzolásért felelős abstract felüldefiniálandó függvény.
	 * @param g: felhasználandó Graphic osztály*/
	public abstract void Draw(Graphics g);

	/**Egy objektum áthelyezéséért felelős.
	 * @param coordinates: új koordináták*/
	public void Move(Vector2 coordinates) {
		// ??
		this.coordinates = coordinates;
		observer.repaint();
	}
	/**Az osztály paraméter nélküli konstruktora.*/
	public Drawable() {}

	/**Azt osztály paraméteres konstruktora
	 * @param coordinates: a koordináták beállításához
	 * @param image: a kép beállításához*/
	public Drawable(Vector2 coordinates, Image image) {
		this.image = image;
		this.coordinates = coordinates;
	}

	/**Visszaadja az x koordinátát.
	 * @return int*/
	public int getX() {
		return coordinates.getX();
	}

	/**Visszaadja az y koordinátát.
	 * @return int*/
	public int getY() {
		return coordinates.getY();
	}

	/**Beállítja az x koordinátát.
	 * @param x: beállítandó érték*/
	public void setX(int x) { this.coordinates.setX(x); }

	/**Beállítja az y koordinátát.
	 * @param y: beállítandó érték*/
	public void setY(int y) { this.coordinates.setY(y); }

	/**Visszaadja a képet
	 * @return Image*/
	public Image getImage() { return image; }

	/**Beállítja az observer-t
	 * @param o: beállítandó Observer*/
	public static void setObserver(Observer o) {
		observer = o;
	}
	/**Visszaadja a koordinátákat.
	 * @return Vector2*/
	public Vector2 getCoordinates() {return coordinates;}
}
