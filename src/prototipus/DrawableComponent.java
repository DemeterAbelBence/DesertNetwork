package prototipus;

import java.awt.Graphics;
import java.awt.Image;

public class DrawableComponent extends Drawable{
	Component component;
	
	public DrawableComponent(Component component, Vector2 coordinates, Image image){
		super(coordinates, image);
		this.component = component;
	}
	
	@Override
	public void Draw(Graphics g) {
		// TODO
		if(component.getNode())
			g.drawImage(image, coordinates.getX(), coordinates.getY(),null);
		else  
			;
	}
}
