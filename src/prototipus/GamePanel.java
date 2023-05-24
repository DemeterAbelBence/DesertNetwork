package prototipus;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GamePanel extends JPanel {

	Observer observer;
	JPanel card2 = new JPanel();

	public GamePanel(Observer o)
	{
		observer = o;
		//Container c = new Container();
		setBackground(Observer.colorFromRGB(242,210,169));
		CardLayout crd = new CardLayout();
		setLayout(crd);

		JPanel card1 = new JPanel();
		card1.setLayout(new BorderLayout());
		JLabel cim = new JLabel("Drukmákor");
		card1.add(cim, BorderLayout.NORTH);
		JPanel jp = new JPanel();
		JButton b1 = new JButton("Pálya generálás");
		JButton b2 = new JButton("Alap pálya");

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: pálya generálás beállítása
				crd.next(observer);
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				observer.getObservedMap().mapInit();
				crd.next(observer);
			}
		});

		jp.setLayout(new FlowLayout());
		jp.add(b1); jp.add(b2);
		card1.add(jp);

		//for(int i = 0; i < drawables.size(); ++i)
			//drawables.get(i).Move(new Vector2(i*50,i++*50));
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//order still undecided
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
	
	private Component findPlayersComponent(Player p)
	{
		return observer.getObservedMap().getComponents().stream().filter(x -> p.getHost().equals(x)).findFirst().orElse(null);
	}

}
