package prototipus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PlayerButton extends JButton {

	private Player player;
	private int id;
	public PlayerButton(Player p, int id) {
		player = p;
		this.id = id;
		setText("Player " + id);
	}



	public void setPlayer(Player player) {
		this.player = player;
		
	}
	
	public Player getPlayer()
	{
		return player;
	}



	public int getId() {
		return id;
	}
	
}
