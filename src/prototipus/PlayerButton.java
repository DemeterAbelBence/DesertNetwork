package prototipus;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**Játékoskiválasztás gombjának osztálya.*/
public class PlayerButton extends JButton {

	private Player player;
	private int id;
	/**Az osztály konstruktora.
	 * @param p: a gombnyomáskor kiválasztandó játékos
	 * @param id: a játékos száma*/
	public PlayerButton(Player p, int id) {
		player = p;
		this.id = id;
		setText("Player " + id);
	}


	/**A játékos beállítása.
	 * @param player: a beállítandó játékos*/
	public void setPlayer(Player player) {
		this.player = player;
		
	}

	/**Visszaadja a player értékét.
	 * @return Player*/
	public Player getPlayer()
	{
		return player;
	}


	/**Visszaadja az id értékét.
	 * @return int*/
	public int getId() {
		return id;
	}
	
}
