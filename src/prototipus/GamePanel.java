package prototipus;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;


public class GamePanel extends JPanel {

	Observer observer;
	
	class Vector2{
		private int x;
		private int y;
		public Vector2() {x=0;y=0;}
		public Vector2(int x,int y)
		{
			this.x = x; this.y = y;
		}
		public int getX() {return x;}
		public void setX(int x) {this.x = x;}
		public void setY(int y) {this.y = y;}
		public int getY() {return y;}
	}
	
	HashMap<Component, Vector2> nodePositions = new HashMap<Component, Vector2>();
	public GamePanel(Observer o)
	{
		observer = o;
		setBackground(Observer.colorFromRGB(242,210,169));
		int i = 0;
		for(var x : observer.getObservedMap().getComponents())
			nodePositions.put(x,new Vector2(observer.getObservedMap().getComponents().indexOf(x)*100%1000,i++*150%600));
	}
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//order still undecided
		Graphics g2d = (Graphics2D)g;
		
		for(var x : observer.getObservedMap().getComponents())
			if(x.getNode())
			{
				
				g2d.drawImage(x.getSprite(),nodePositions.get(x).getX(),nodePositions.get(x).getY(),null);
				
			}else {
				
				g2d.drawLine(nodePositions.get(x.getNeighbours().get(0)).getX(), nodePositions.get(x.getNeighbours().get(0)).getY(),nodePositions.get(x.getNeighbours().get(1)).getX(),nodePositions.get(x.getNeighbours().get(1)).getY());
				
			}
		
		System.out.println(getWidth());
	}
	
	private Component findPlayersComponent(Player p)
	{
		return observer.getObservedMap().getComponents().stream().filter(x -> p.getHost().equals(x)).findFirst().orElse(null);
	}

}
