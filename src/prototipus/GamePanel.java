package prototipus;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;


/**A pálya megjelenítéséért felelős osztály.*/
public class GamePanel extends JPanel {

	Observer observer;

	/**Az osztály konstruktora.
	 * @param o: az observer amin keresztül a vltozásokat kezeljük*/
	public GamePanel(Observer o)
	{
		observer = o;
		setBackground(Observer.colorFromRGB(242,210,169));

		//for(int i = 0; i < drawables.size(); ++i)
			//drawables.get(i).Move(new Vector2(i*50,i++*50));
	}
	
	private Vector2 getArrowPoint(Vector2 A, Vector2 B, double arrowAngle) {
		
		/* TODO
		 * valami fancy matek ami
		 * elforgat a cso kozeppontja korul
		 * egy pontot
		 */
		
		return null;
	}
	
	private void displayPipeOrientation(Graphics g) { 
		ArrayList<Component> components = observer.getObservedMap().getComponents();
		
		
		for(Component c : components) {
			if(!c.getNode()) {
				Component input = c.getInput();
				Component output = c.getOutput();
				DrawableComponent inputD = (DrawableComponent) observer.getDrawableOfComponent(input);
				DrawableComponent outputD = (DrawableComponent) observer.getDrawableOfComponent(output);
				DrawableComponent pipeD = (DrawableComponent) observer.getDrawableOfComponent(c);
				
				Vector2 inputPosition = inputD.getCoordinates();
				Vector2 outputPosition = outputD.getCoordinates();
				Vector2 pipeMiddle = pipeD.getCoordinates();	
				
				Vector2 arrowPoint1 = getArrowPoint(pipeMiddle, inputPosition, Math.PI / 4);
				Vector2 arrowPoint2 = getArrowPoint(pipeMiddle, inputPosition, -Math.PI / 4);
				
				g.drawLine(arrowPoint1.getX(), arrowPoint1.getY(),
						pipeMiddle.getX(), pipeMiddle.getY());
				
				g.drawLine(arrowPoint2.getX(), arrowPoint2.getY(),
						pipeMiddle.getX(), pipeMiddle.getY());
			}
		}
	}

	/**Bekeretezi a kiválasztott játékost a láthatóságért
	 * @param g: a kirajzoláshoz való Graphics*/
	private void outlineFocusedPlayer(Graphics g) {
		//player to outline
		Player p = observer.getMenuPanel().focusedPlayer;
		Drawable d = observer.getDrawableOfPlayer(p);
		
		//drawing parameteres
		Vector2 v = d.getCoordinates();
		int w = d.getImage().getWidth(observer);
		int h = d.getImage().getHeight(observer);
		int delta = 5;
		
		//drawingoutline
		Vector2 outlinePosition = new Vector2();
		outlinePosition.setX(v.getX() - delta);
		outlinePosition.setY(v.getY() - delta);
		g.setColor(Color.orange);
		g.fillRect(outlinePosition.getX(), outlinePosition.getY(), w + 2*delta, h + 2*delta);
	}

	/**Bekeretezi a kiválasztott komponenst az átláthatóságért.
	 * @param g: a kirajzoláshoz való Graphics*/
	private void outlineSelectedComponent(Graphics g) {
		//getting selected index
		int index = observer.getMenuPanel().jl.getSelectedIndex();
		Player p = observer.getMenuPanel().focusedPlayer;
		
		int neighbourCount = p.host.countNeighbours();
		if (index < 0 || index > neighbourCount)
			return;
		
		//component to outline
		Component c = p.host.getNeighbour(index);
		Drawable d = observer.getDrawableOfComponent(c);
		int delta = 5; //outline thickness
		
		//outline node component
		if(c.getNode()) {
			//drawing parameteres
			Vector2 v = d.getCoordinates();
			int w = d.getImage().getWidth(observer);
			int h = d.getImage().getHeight(observer);
			
			//drawing outline
			Vector2 outlinePosition = new Vector2();
			outlinePosition.setX(v.getX() - delta);
			outlinePosition.setY(v.getY() - delta);
			g.setColor(Color.yellow);
			g.fillRect(outlinePosition.getX(), outlinePosition.getY(), w + 2*delta, h + 2*delta);
		}
		//outline edge component(in this case pipe)
		else {
			Drawable neighbourDrawable0 = observer.getDrawableOfComponent(c.getNeighbour(0));
			Drawable neighbourDrawable1 = observer.getDrawableOfComponent(c.getNeighbour(1));
			
			//position of the nodes connected by edge
			Vector2 pos0 = neighbourDrawable0.getCoordinates(); 
			Vector2 pos1 = neighbourDrawable1.getCoordinates(); 
			
			//dimensions of previous nodes
			int imageWidth0 = neighbourDrawable0.getImage().getWidth(observer);
			int imageHeight0 = neighbourDrawable0.getImage().getHeight(observer);
			int imageWidth1 = neighbourDrawable1.getImage().getWidth(observer);
			int imageHeight1 = neighbourDrawable1.getImage().getHeight(observer);
			
			
			//setting outline thickness
			Graphics2D g2d = (Graphics2D) g; 
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(delta + 4));
			
            //coordinates to draw at
            int x0 = pos0.getX() + imageWidth0 / 2;
            int y0 = pos0.getY() + imageHeight0 / 2;
            int x1 = pos1.getX() + imageWidth1 / 2;
            int y1 = pos1.getY() + imageHeight1 / 2; 
            
            //drawing outline
			g.setColor(Color.yellow);
			g.drawLine(x0, y0, x1, y1);
		}
	}

	/**A pontok kirajzolása.
	 * @param g: a kirajzoláshoz való Graphics*/
	public void displayScores(Graphics g)
	{
		g.drawString("Saboteur score: " + observer.getSaboteurScore(), 20,20);
		g.drawString("RepairMan score: " + observer.getRepairManScore(), 20,40);

	}
	/**Egy komponens kirajzolása.
	 * @param g: a kirajzoláshoz való Graphics*/
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		displayScores(g);
		outlineFocusedPlayer(g);
		outlineSelectedComponent(g);
		observer.drawDrawables(g);

	}
}
