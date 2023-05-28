package prototipus;

import java.awt.*;

/**A komponensek rajzolásához való osztály.*/
public class DrawableComponent extends Drawable{
	Component component;

	/**Az osztály paraméter nélküli konstruktora.*/
	public DrawableComponent() { super(); }

	/**Az osztály paraméteres konstruktora.
	 * @param component: a komponens aminek a kirajzolható megfelelője
	 * @param coordinates: koordináták a kirajzoláshoz
	 * @param image: kép a kirajzoláshoz*/
	public DrawableComponent(Component component, Vector2 coordinates, Image image){
		super(coordinates, image);
		this.component = component;
	}

	/**Az osztály másik paraméteres konstruktora.
	 * @param component: a komponens amit ki kell rajzolnia*/
	public DrawableComponent(Component component){
		super(new Vector2(0, 0), null);
		this.component = component;
	}

	/**A rajzolást elvégző függvény.
	 * @param g: rajzoláshoz való Graphics*/
	@Override
	public void Draw(Graphics g) {
		// TODO
		if(component.getNode())
			g.drawImage(image, coordinates.getX(), coordinates.getY(),null);
		else  
		{
			if(component.getNeighbour(0) != null && component.getNeighbour(1) != null) {
				Color pipeColor = Color.BLACK;
				Pipe pipe = (Pipe)component;
				if(pipe.getLeaks())
					if(pipe.isSticky())
						pipeColor = Color.orange;
					else if(pipe.isSlippery())
						pipeColor = Color.getHSBColor(58,32,79);
					else
						pipeColor = Color.red;
				else if(pipe.isFull())
					if(pipe.isSticky())
						pipeColor = Color.green;
					else if(pipe.isSlippery())
						pipeColor = Color.cyan;
					else
						pipeColor = Color.blue;
				else
					if(pipe.isSticky())
					pipeColor = Color.yellow;
				else if(pipe.isSlippery())
					pipeColor = Color.getHSBColor(75,100,100);


				g.setColor(pipeColor);
				Drawable drawableOfNeighbour1 = Observer.getDrawableOfComponent(component.getNeighbour(0));
				Drawable drawableOfNeighbour2 = Observer.getDrawableOfComponent(component.getNeighbour(1));
				
				//settin pipe thickness
				Graphics2D g2d = (Graphics2D) g;
				float thickness = 5.0f; // Desired thickness
	            Stroke oldStroke = g2d.getStroke();
	            g2d.setStroke(new BasicStroke(thickness));

	            //coordinates of player on pipe
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
