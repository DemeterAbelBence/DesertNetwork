package prototipus;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
/**A játék egészének egybefogásáért, kezeléséért felelős osztály.*/
public class Observer extends JFrame implements Updateable {

	private Map observedMap;

	private MenuPanel menuPanel;
	private GamePanel gamePanel;

	private static ArrayList<Drawable> drawables = new ArrayList<Drawable>();

	private static HashMap<Player, Drawable> drawablePlayers = new HashMap<Player, Drawable>();
	private static HashMap<Component, Drawable> drawableComponents = new HashMap<Component, Drawable>();

	private int repairManScore = 0;
	private int saboteurScore = 0;


/**Az osztály konstruktora.
 * @param map: a játékhoz való pálya térképe
 * @throws Exception */
	public Observer(Map map) throws Exception
	{
		//device = GraphicsEnvironment
		observedMap = map;
		// feltolti a default map csoveivel, pumpaival, stb.
		//observedMap.makeDefaultMap(2, 3, 4, 8);

		String str1= "cistern 100 100 repairMan\n"
				+ "cistern 100 300 repairMan\n"
				+ "pump 400 50\n"
				+ "pump 500 200\n"
				+ "pump 400 350\n"
				+ "spring 700 100 saboteur\n"
				+ "spring 700 300 saboteur\n"
				+ "done\n";

		String str2= "0 3\n"
				+ "1 4\n"
				+ "2 3\n"
				+ "4 3\n"
				+ "4 5\n"
				+ "3 5\n"
				+ "4 6\n"
				+ "done\n";

		String[] nodeCommands = str1.split("\n");
		String[] edgeCommands = str2.split("\n");

		try {
			observedMap.createFromCommands(nodeCommands, edgeCommands);
		} catch(Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setBounds(EXIT_ON_CLOSE, ABORT, 1600, 600);
		setVisible(true);

		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(this);
		add(gamePanel);
		add(menuPanel);

		gamePanel.setBounds(0,0,getWidth()/4*3,getHeight());
		menuPanel.setBounds(gamePanel.getWidth(),0,getWidth()/4,getHeight());

		this.getContentPane().repaint();
		this.getContentPane().revalidate();
	}

	/**Változáskor újrarajzolja a képernyőt.*/
	public void updateStatus()
	{
		saboteurScore = countLeakedWater();
		repairManScore = countCityWater();

		drawables.clear();
		drawables.addAll(drawableComponents.values());
		drawables.addAll(drawablePlayers.values());

		repaint();
	}

	/**Színt képez három számból.
	 * @param r: piros színkomponens
	 * @param g: zöld színkomponens
	 * @param b: kék színkomponens
	 * @return Color*/
	public static Color colorFromRGB(int r, int g, int b)
	{
		float[] f = new float[3];
		Color.RGBtoHSB(r, g, b, f);

		return Color.getHSBColor(f[0], f[1], f[2]);
	}

	/**Összeszámolja a pályán összesen kifolyt vízmennyiséget.
	 * @return int*/
	public int countLeakedWater()
	{
		int sum = 0;
		for(Component item : observedMap.getComponents())
			sum += item.getLeakedWater();
		return sum;
	}

	/**Megszámolja, hogy mennyi víz van a ciszternákban.
	 * @return int*/
	public int countCityWater()
	{
		//This uses the fact that Springs have a 0 waterLevel
		int sum = 0;
		for(Component item : observedMap.getComponents())
			if(item.getNode() && item.getItemSource())
				sum += item.getWaterLevel();
		return sum;
	}

	/**Visszaadja a játéktérképet.
	 * @return Map*/
	public Map getObservedMap()
	{
		return observedMap;
	}

	/**Hozzáad egy játékost, kirajzolható verzióval együtt.
	 * @param player: a játékos
	 * @param drawable: a játékos kirajzolható verziója*/
	public static void addDrawablePlayer(Player player, Drawable drawable) {
		drawables.add(drawable);
		drawablePlayers.put(player, drawable);
	}

	/**Hozzáad egy komponenst, kirajzolható verzióval együtt.
	 * @param component: a kompnens
	 * @param drawable: a komponens kirajzolható verziója*/
	public static void addDrawableComponent(Component component, Drawable drawable) {
		drawables.add(drawable);
		drawableComponents.put(component, drawable);
	}

	/**Egy játékosnak megmondja a hozzá tartozó kirajzolható verzióját.
	 * @param p: játékos
	 * @return Drawable*/
	public static Drawable getDrawableOfPlayer(Player p) {
		return drawablePlayers.get(p);
	}

	/**Egy komponensnek megadja a hozzá tartozó kirjzolható verzióját.
	 * @param c: a komponens
	 * @return Drawable*/
	public static Drawable getDrawableOfComponent(Component c) {
		return drawableComponents.get(c);
	}

	/**A kirajzolható elemek kirajzolása.
	 * @param g: kirajzoláshoz való Grahpics*/
	public void drawDrawables(Graphics g) {
		Graphics g2d = (Graphics2D)g;
		for(Drawable drawable : drawables) {
			drawable.Draw(g2d);
		}
	}

	/**Visszaadja a felhasznált menü-t
	 * @return MenuPanel*/
	public MenuPanel getMenuPanel()
	{
		return menuPanel;
	}

	/**Visszaadja a szabotőrök pontszámát
	 * @return int*/
	public int getSaboteurScore() {
		return saboteurScore;
	}

	/**Visszaadja a szerelők pontszámát
	 * @return int*/
	public int getRepairManScore() {
		return  repairManScore;
	}
}
