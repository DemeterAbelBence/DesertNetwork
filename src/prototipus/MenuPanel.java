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
/**A menü kirajzolásáért felelős osztály*/
public class MenuPanel extends JPanel implements Updateable{
	Observer observer;
	//ArrayList<JButton> playerButtons = new ArrayList<JButton>();
	Player focusedPlayer;
	Player lastFocusedPlayer;
	JButton stickyButton = new JButton("Sticky pipe");
	JButton punctureButton = new JButton("Puncture pipe");
	JButton slipperyButton = new JButton("Make pipe slippery");
	JButton brepair = new JButton("Repair");
	JButton bplacepipe = new JButton("Place Pipe");
	JButton bpickPipe = new JButton("Pick Pipe");
	JButton bchangeinput = new JButton("ChangeInput");
	JButton bchangeoutput = new JButton("ChangeOutput");
	JButton bInitializeMap = new JButton("InitializeMap");
	JButton bpickpump = new JButton("PickupPump");
	JButton bCreateDefaultMap = new JButton("CreateDafaultMap");
	JButton bplacedownPump = new JButton("PlacedownPump");
	JButton move = new JButton("Move to selected");
	JTextField tEdge = new JTextField("EdgeCommands");
	JTextField tNode = new JTextField("NodeCommands");

	JLabel playerNameLabel = new JLabel("Player 0");

	JList jl;

	/**Adott játékos szomszédjainak visszaadása String tömbként
	 * @param p: az adott játékos
	 * @return String[]*/
	private String[] hostComponentNeighbours(Player p) {
		Component c = p.getHost();
		int length = c.getNeighbours().size();
		
		String[] result = new String[length];
			
		for(int i = 0; i < length; ++i) 
			result[i] = c.getNeighbours().get(i).toString() + i;
		
		return result;
	}

	/**Az osztály paraméteres konstruktora
	 * @param o: a játékért felelős Observer*/
	public MenuPanel(Observer o)
	{
		this.setLayout(null);
		//this.setSize(200,200);
		observer = o;
		setBackground(Observer.colorFromRGB(77,143,195));
		int playerCount = observer.getObservedMap().getPlayers().size();

		PlayerButton b;
		int i = 0;
		
		for(Player p : observer.getObservedMap().getPlayers()) {
			b = new PlayerButton(observer.getObservedMap().getPlayers().get(observer.getObservedMap().getPlayers().indexOf(p)),i);
			b.setBackground(Color.GREEN);
			b.setBounds(30 + 80 * i, 30, 80, 30);
			b.revalidate();
			b.addActionListener(new PlayerButtonListener());
			add(b);
			b.revalidate();
			i++;
		}
		add(Box.createRigidArea(new Dimension(getWidth(),200)));

		tNode.setBounds(200, 100, 100, 30);
		this.add(tNode);
		
		tEdge.setBounds(50, 100, 100, 30);
		this.add(tEdge);
		
		//---------------------------//
		bCreateDefaultMap.setBounds(200, 150, 150, 30);
		bCreateDefaultMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//observer.
			}
		});
		add(bCreateDefaultMap);
		
		//---------------------------//
		bInitializeMap.setBounds(30, 150, 150, 30);
		bInitializeMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//observer.
			}
		});
		add(bInitializeMap);
		
		//---------------------------//
		focusedPlayer = observer.getObservedMap().getPlayers().get(0);
		String[] s = hostComponentNeighbours(focusedPlayer);
		DefaultListModel listModel = new DefaultListModel();
		listModel.addAll(0,List.of(s));
		 jl = new JList(listModel);
		 jl.setBounds(50, 270, 70, 200);
		add(jl);
		
		//action button parameters
		int k = 0;
		int actionButtonHeight = 20;
		int offsetY = 230;
		int offsetX = 170;
		int spacing = 25;
		
		//---------------------------//
		/**Implementálja, hogy a játékos gombnyomásra a kiválasztott mezőre lépjen.*/
		move.setBounds(offsetX, offsetY + spacing * k++, 160, 20);
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
		
		//---------------------------//
		/**Implementálja, hogy az adott gomb csúszóssá tegye a csövet.*/
		slipperyButton.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		slipperyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.makeSlippery();
				observer.repaint();
			}
		});
		add(slipperyButton);

		//---------------------------//
		/**implementálja, hogy az adott gomb ragadóssá tegye a csövet*/
		stickyButton.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		stickyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.makeSticky();
				observer.repaint();
			}
		});
		add(stickyButton);

		//---------------------------//
		/**Implementálja, hogy a játékos megjavítson gombnyomásra egy elromlott/kilyukadt elemet*/
		brepair.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		brepair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.host.repaired();
				observer.repaint();
			}
		});
		add(brepair);

		//---------------------------//
		/**Implementálja, hogy felvegyen a játékos gombnyomásra egy pumpát.*/
		bpickpump.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		bpickpump.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.pickUpPump();
				observer.repaint();
			}
		});
		add(bpickpump);

		//---------------------------//
		/**Implementálja, hogy egy játékos lerakjon gombnyomásra egy pumpát*/
		bplacedownPump.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		bplacedownPump.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.placeDownPump();
				observer.repaint();
			}
		});
		add(bplacedownPump);
		
		//---------------------------//
		/**Implementálja, hogy a játékos letegyen gombnyomásra egy csövet.*/
		bplacepipe.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		bplacepipe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.placeDownPipe();
				observer.repaint();
			}
		});
		add(bplacepipe);
		
		//---------------------------//
		/**Implementálja, hogy az adott gomb kilyukassza a csövet.*/
		punctureButton.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		punctureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				focusedPlayer.sabotage();
				observer.repaint();
			}
		});
		add(punctureButton);
		
		//---------------------------//
		/**Implementálja, hogy egy játékos felvegyen egy csövet gombnyomásra. (Nem ciszternánál kiválasztott elemet)*/
		bpickPipe.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		bpickPipe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(focusedPlayer.host.getItemSource()) focusedPlayer.pickUpPipe(null);
				else if(!jl.isSelectionEmpty()) {
					int idx = jl.getSelectedIndex();
					if(!focusedPlayer.host.getNode()) return;
					Pipe p = (Pipe) focusedPlayer.host.neighbours.get(idx);
					focusedPlayer.pickUpPipe(p);
					observer.repaint();
				}
			}
		});
		add(bpickPipe);	
		
		//---------------------------//
		/**Implementálja, hogy egy pumpának megváltozzon a bemenete gombnyomásra kiválasztott elemre*/
		bchangeinput.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		bchangeinput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!jl.isSelectionEmpty()){
					int idx = jl.getSelectedIndex();
					focusedPlayer.changePumpInput(focusedPlayer.host.neighbours.get(idx));
					observer.repaint();
				}
			}
		});
		add(bchangeinput);
		
		//---------------------------//
		/**Implementálja, hogy egy pumpának gombnyomásra megváltozzon a kimenete kiválasztott elemre.*/
		bchangeoutput.setBounds(offsetX, offsetY + spacing * k++, 160, actionButtonHeight);
		bchangeoutput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!jl.isSelectionEmpty()){
					int idx = jl.getSelectedIndex();
					Component c = focusedPlayer.getHost().getNeighbours().get(idx);
					focusedPlayer.changePumpOutput(c);
					observer.repaint();
				}
			}
		});
		add(bchangeoutput);

		setVisible(true);
	
	}

	/**Implementálja, hogy adott gomb megnyomásakor az aktuális játékos megváltozzon.*/
	class PlayerButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			focusedPlayer = ((PlayerButton)e.getSource()).getPlayer();
			playerNameLabel.setText("Player" + ((PlayerButton)e.getSource()).getId());
			observer.repaint();
		}
		
	}

	/**Változáskor frissíti a megjelenő szomszédlistát.*/
	@Override
	public void updateStatus() {

		//updating host neighbour list
		//-----------------
		DefaultListModel model = (DefaultListModel) jl.getModel();
		int index = jl.getSelectedIndex();

		model.clear();

		String[] neighbours = hostComponentNeighbours(focusedPlayer);
		for(String n : neighbours)
			model.addElement(n);
		//-----------------
		
		

		jl.setSelectedIndex(index);


		lastFocusedPlayer = focusedPlayer;
	}
}
