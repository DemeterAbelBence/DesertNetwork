package prototipus;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.event.*;
public class MenuPanel extends JPanel {
	Observer observer;
	//ArrayList<JButton> playerButtons = new ArrayList<JButton>();
	Player focusedPlayer;
	JButton stickyButton = new JButton("Stick pipe");
	JButton punctureButton = new JButton("Puncture pipe");
	JButton slipperyButton = new JButton("Make pipe slippery");
	JLabel playerNameLabel = new JLabel("Player 0");
	public MenuPanel(Observer o)
	{
		this.setLayout(new FlowLayout());
		this.setSize(200,200);
		observer = o;
		setBackground(Observer.colorFromRGB(77,143,195));
		int playerCount = observer.getObservedMap().repairmen.size() + observer.getObservedMap().saboteurs.size();

			PlayerButton b;
			int i = 0;
			for(Player p : observer.getObservedMap().getPlayers()) {
				b = new PlayerButton(observer.getObservedMap().getPlayers().get(observer.getObservedMap().getPlayers().indexOf(p)),i++);
				b.setBackground(Color.GREEN);
			
			b.revalidate();
			b.addActionListener(new PlayerButtonListener());
			add(b);
			b.revalidate();
		}
			add(Box.createRigidArea(new Dimension(getWidth(),200)));


		slipperyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean temp = focusedPlayer.host.getNode();
				if(!temp && focusedPlayer.host.getSlipperyCounter() == 0){
					focusedPlayer.host.resetSlipperyCounter();
				}
			}
		});

		punctureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.sabotage();
			}
		});

		stickyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.makeSticky();
			}
		});


		add(slipperyButton);
		add(punctureButton); 
		add(stickyButton);
		focusedPlayer = observer.getObservedMap().getPlayers().get(0);
		String[] s = {
				"neighbourPipe1","neighbourPipe2","neighbourPipe3","neighbourPipe4","neighbourPipe5","neighbourPipe6",
		};
		JList igen = new JList(s);
		
		
		add(igen);
		add(new JButton("Move to selected"));
		
		setVisible(true);
	
	}
	
	class PlayerButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			focusedPlayer = ((PlayerButton)e.getSource()).getPlayer();
			playerNameLabel.setText("Player" + ((PlayerButton)e.getSource()).getId());
		}
		
	}
/*	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
	}
	*/
}
