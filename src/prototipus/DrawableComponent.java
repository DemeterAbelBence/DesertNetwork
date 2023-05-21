package prototipus;

import java.awt.Image;

public class DrawableComponent extends Drawable{
	Component component;
	
	public DrawableComponent(Component component, GamePanel.Vector2 coordinates, Image image){
		super(coordinates, image);
		this.component = component;
	}
	
	@Override
	public void Draw() {
		// TODO
	}
}
