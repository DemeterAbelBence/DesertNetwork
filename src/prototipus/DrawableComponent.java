package prototipus;

import java.awt.Graphics;
import java.awt.Image;

public class DrawableComponent extends Drawable{
	Component component;
	Drawable neighbour1, neighbour2;
	
	public DrawableComponent(Component component, Vector2 coordinates, Image image){
		super(coordinates, image);
		this.component = component;
	}
	
	public DrawableComponent(Component component, Vector2 coordinates, Image image, Drawable n1, Drawable n2){
		super(coordinates, image);
		this.component = component;
		neighbour1 = n1;
		neighbour2 = n2;
	}
	
	@Override
	public void Draw(Graphics g) {
		// TODO
		if(component.getNode())
			g.drawImage(image, coordinates.getX(), coordinates.getY(),null);
		else  
			g.drawLine(neighbour1.getX(), neighbour1.getY(), neighbour2.getX(), neighbour2.getY());
	}
}
