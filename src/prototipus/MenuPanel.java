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
public class MenuPanel extends JPanel implements Updateable{
	Observer observer;
	//ArrayList<JButton> playerButtons = new ArrayList<JButton>();
	Player focusedPlayer;
	Player lastFocusedPlayer;
	JButton stickyButton = new JButton("Stick pipe");
	JButton punctureButton = new JButton("Puncture pipe");
	JButton slipperyButton = new JButton("Make pipe slippery");
	JLabel playerNameLabel = new JLabel("Player 0");

	JList jl;
	
	private String[] hostComponentNeighbours(Player p) {
		Component c = p.getHost();
		int length = c.getNeighbours().size();
		
		String[] result = new String[length];
			
		for(int i = 0; i < length; ++i) {
			if(c.getNeighbour(i).getNode())
				result[i] = "node" + i;
			else
				result[i] = "pipe" + i;
		}
		
		return result;
	}
	
	public MenuPanel(Observer o)
	{
		this.setLayout(new FlowLayout());
		this.setSize(200,200);
		observer = o;
		setBackground(Observer.colorFromRGB(77,143,195));
		int playerCount = observer.getObservedMap().getPlayers().size();

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
				focusedPlayer.makeSlippery();
				observer.repaint();
			}
		});

		punctureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				focusedPlayer.sabotage();
				observer.repaint();
			}
		});

		stickyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.makeSticky();
				observer.repaint();
			}
		});

		add(slipperyButton);
		add(punctureButton); 
		add(stickyButton);
		
		focusedPlayer = observer.getObservedMap().getPlayers().get(0);
		String[] s = hostComponentNeighbours(focusedPlayer);
		DefaultListModel listModel = new DefaultListModel();
		listModel.addAll(0,List.of(s));
		 jl = new JList(listModel);

		add(jl);
		
		JButton move = new JButton("Move to selected");
		move.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!jl.isSelectionEmpty()){
					int idx = jl.getSelectedIndex();
					focusedPlayer.moveTo(focusedPlayer.host.neighbours.get(idx));
					observer.repaint();
				}
			}
		});
		add(move);
		
		setVisible(true);
	
	}
	
	class PlayerButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			focusedPlayer = ((PlayerButton)e.getSource()).getPlayer();
			playerNameLabel.setText("Player" + ((PlayerButton)e.getSource()).getId());
			observer.repaint();
		}
		
	}
/*	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
	}
	*/

	@Override
	public void updateStatus() {


			DefaultListModel model = (DefaultListModel) jl.getModel();
			int index = jl.getSelectedIndex();

			model.clear();

			model.addAll(focusedPlayer.getHost().getNeighbours());


			jl.setSelectedIndex(index);


		lastFocusedPlayer = focusedPlayer;
	}
}
