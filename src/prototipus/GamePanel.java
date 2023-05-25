package prototipus;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;


public class GamePanel extends JPanel {

	Observer observer;
	
	public GamePanel(Observer o)
	{
		observer = o;
		setBackground(Observer.colorFromRGB(242,210,169));

		//for(int i = 0; i < drawables.size(); ++i)
			//drawables.get(i).Move(new Vector2(i*50,i++*50));
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		observer.drawDrawables(g);
		/*
		for(var x : observer.getObservedMap().getComponents())
			if(x.getNode())
			{
				
				g2d.drawImage(x.getSprite(),nodePositions.get(x).getX(),nodePositions.get(x).getY(),null);
				
			}else {
				
				g2d.drawLine(nodePositions.get(x.getNeighbours().get(0)).getX(), nodePositions.get(x.getNeighbours().get(0)).getY(),nodePositions.get(x.getNeighbours().get(1)).getX(),nodePositions.get(x.getNeighbours().get(1)).getY());
				
			}
		*/
	}


	/*private Component findPlayersComponent(Player p)
	{
		return observer.getObservedMap().getComponents().stream().filter(x -> p.getHost().equals(x)).findFirst().orElse(null);
	}*/

}
