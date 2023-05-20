package prototipus;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	Observer observer;
	HashMap<String,Image> sprites = new HashMap<String,Image>();
	
	public GamePanel(Observer o)
	{
		observer = o;
		try {
			LoadImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't load images :(");
			e.printStackTrace();
		}
		setBackground(Observer.colorFromRGB(242,210,169));
	}
	
	public void LoadImages() throws IOException
	{
		sprites.put("cistern", ImageIO.read(this.getClass().getResource("cistern.png")));
		sprites.put("spring", ImageIO.read(this.getClass().getResource("spring.png")));
		sprites.put("player", ImageIO.read(this.getClass().getResource("kacsa.jpg")));
	}
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//order still undecided
		Graphics g2d = (Graphics2D)g;
	
		observer.getObservedMap().cisterns.forEach( c->{
			g2d.drawImage(sprites.get("cistern"), 20, observer.getObservedMap().cisterns.indexOf(c)*getHeight()/observer.getObservedMap().cisterns.size() , null);
			}
		);
		observer.getObservedMap().springs.forEach( s -> {
			g2d.drawImage(sprites.get("spring"), getWidth()-100, observer.getObservedMap().springs.indexOf(s)*getHeight()/observer.getObservedMap().springs.size() , null);
		});
		observer.getObservedMap().pumps.forEach(x -> {
			
			g2d.drawImage(sprites.get("player"), 20, 20 ,null);
		});
		
		System.out.println(getWidth());
		
		//observedMap.saboteurs.forEach(null);
		//observedMap.repairmen.forEach(null);
		//observedMap.pipes.forEach(null);
		
	}
	
	/*private Component findPlayersComponent(Player p)
	{
		Component c;
		observer.observedMap.cisterns.forEach(x->{ if(p.getHost().equals(x)) c = x;});
	}*/

}
