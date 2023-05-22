package prototipus;

import java.awt.Graphics;
import java.awt.Image;

public class DrawableComponent extends Drawable{
	Component component;
	
	public DrawableComponent(Component component, Vector2 coordinates, Image image){
		super(coordinates, image);
		this.component = component;
	}
	
	public DrawableComponent(Component component){
		super(new Vector2(0, 0), null);
		this.component = component;
	}
	
	@Override
	public void Draw(Graphics g) {
		// TODO
		if(component.getNode())
			g.drawImage(image, coordinates.getX(), coordinates.getY(),null);
		else  
		{
			if(component.getNeighbour(0) != null && component.getNeighbour(1) != null) {
				Drawable drawableOfNeighbour1 = Observer.getDrawableOfComponent(component.getNeighbour(0));
				Drawable drawableOfNeighbour2 = Observer.getDrawableOfComponent(component.getNeighbour(1));
				g.drawLine(drawableOfNeighbour1.getX(), drawableOfNeighbour1.getY(), 
						drawableOfNeighbour2.getX(), drawableOfNeighbour2.getY());
			}
		}
	}
}
