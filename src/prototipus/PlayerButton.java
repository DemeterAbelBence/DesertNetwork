package prototipus;
import javax.swing.*;
public class PlayerButton extends JButton {

	private Player player;
	private int id;
	public PlayerButton(Player p, int id) {player = p;this.id = id;setText("Player " + id);}



	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
}
