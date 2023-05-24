package prototipus;

import javax.imageio.ImageIO;
import javax.swing.*;

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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
public class Observer extends JFrame implements Updateable {
	
	private Map observedMap;

	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	
	private static ArrayList<Drawable> drawables  = new ArrayList<Drawable>();
	
	private static HashMap<Player, Drawable> drawablePlayers = new HashMap<Player, Drawable>();
	private static HashMap<Component, Drawable> drawableComponents = new HashMap<Component, Drawable>();
	
	public Observer(Map map)
	{
		//device = GraphicsEnvironment
		observedMap = map;
		// feltolti a default map csoveivel, pumpaival, stb.
		//observedMap.makeDefaultMap(2, 3, 4, 8);
		observedMap.mapInit();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		this.getContentPane().setLayout(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		 //       .getLocalGraphicsEnvironment().getScreenDevices()[0];observedMap.cisterns.add(new Cistern());
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
	
	public void updateStatus()
	{
		repaint();
	}
	
	public static Color colorFromRGB(int r, int g, int b)
	{
		float[] f = new float[3];
		Color.RGBtoHSB(r, g, b, f);
		
		return Color.getHSBColor(f[0], f[1], f[2]);
	}
	
	public Map getObservedMap()
	{
		return observedMap;
	}
	
	public static void addDrawablePlayer(Player player, Drawable drawable) {
		drawables.add(drawable);
		drawablePlayers.put(player, drawable);
	}
	
	public static void addDrawableComponent(Component component, Drawable drawable) {
		drawables.add(drawable);
		drawableComponents.put(component, drawable);
	}
	
	public static Drawable getDrawableOfPlayer(Player p) {
		return drawablePlayers.get(p);
	}
	
	public static Drawable getDrawableOfComponent(Component c) {
		return drawableComponents.get(c);
	}
	
	public void drawDrawables(Graphics g) {
		Graphics g2d = (Graphics2D)g;
		for(Drawable drawable : drawables) {
			drawable.Draw(g2d);
		}
	}
}
