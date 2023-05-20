package prototipus;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.*;
public class MenuPanel extends JPanel {

	Observer observer;
	ArrayList<JButton> playerButtons = new ArrayList<JButton>();
	Player focusedPlayer;
	public MenuPanel(Observer o)
	{
		observer = o;
		setBackground(Observer.colorFromRGB(77,143,195));
		int playerCount = observer.getObservedMap().repairmen.size() + observer.getObservedMap().saboteurs.size();
		for(int i = 0; i<playerCount;i++)
		{
			PlayerButton b;
			if(i >= observer.getObservedMap().repairmen.size() ) {
				b = new PlayerButton(observer.getObservedMap().saboteurs.get(i-observer.getObservedMap().repairmen.size()),i);
				b.setBackground(Color.RED);
				add(b);
			

			}
			else {
				b =new PlayerButton(observer.getObservedMap().repairmen.get(i),i);
				b.setBackground(Color.GREEN);
				add(b);
				
			}
			
			
		}
		setVisible(true);
		
	}
	
}
