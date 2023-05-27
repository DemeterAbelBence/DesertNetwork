package prototipus;

import java.awt.*;

public class DrawableComponent extends Drawable{
	Component component;
	
	public DrawableComponent() { super(); }
	
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

				g.setColor(((Pipe)component).getLeaks() ?Color.red : (component.isFull() ? Color.BLUE : Color.BLACK));
				Drawable drawableOfNeighbour1 = Observer.getDrawableOfComponent(component.getNeighbour(0));
				Drawable drawableOfNeighbour2 = Observer.getDrawableOfComponent(component.getNeighbour(1));
				
				Graphics2D g2d = (Graphics2D) g;
				float thickness = 5.0f; // Desired thickness
	            Stroke oldStroke = g2d.getStroke();
	            g2d.setStroke(new BasicStroke(thickness));

				
	            int imageWidth1 = drawableOfNeighbour1.getImage().getWidth(observer);
	            int imageHeight1 = drawableOfNeighbour1.getImage().getHeight(observer);
	            int imageWidth2 = drawableOfNeighbour2.getImage().getWidth(observer);
	            int imageHeight2 = drawableOfNeighbour2.getImage().getHeight(observer);
	            
	            int x1 = drawableOfNeighbour1.getX() + imageWidth1 / 2;
	            int y1 = drawableOfNeighbour1.getY() + imageHeight1 / 2;
	            
	            int x2 = drawableOfNeighbour2.getX() + imageWidth2 / 2;
	            int y2 = drawableOfNeighbour2.getY() + imageHeight2 / 2;
	            
				g2d.drawLine(x1, y1, x2, y2);
			}
		}
	}
}
