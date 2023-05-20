package prototipus;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
public class Observer extends JFrame implements Updateable {
	private Map observedMap;
	
	//GraphicsDevice device;
	
	

	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	
	public Observer(Map map)
	{
		//device = GraphicsEnvironment
		
		observedMap = map;
		
		
		 //       .getLocalGraphicsEnvironment().getScreenDevices()[0];observedMap.cisterns.add(new Cistern());
		
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.cisterns.add(new Cistern());
		observedMap.springs.add(new Spring());
		observedMap.springs.add(new Spring());
		observedMap.springs.add(new Spring());
		observedMap.pumps.add(new Pump());
		observedMap.pumps.add(new Pump());
		observedMap.pumps.add(new Pump());
		observedMap.pumps.add(new Pump());
		observedMap.pumps.add(new Pump());
		observedMap.repairmen.add(new RepairMan(new Pump()));
		observedMap.repairmen.add(new RepairMan(new Pump()));
		observedMap.saboteurs.add(new Saboteur(new Pump()));
		observedMap.saboteurs.add(new Saboteur(new Pipe()));
		
		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(this);
		
		add(gamePanel);
		add(menuPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
		
		
		
	
		
		gamePanel.setSize(getWidth()/4*3,getHeight());
		menuPanel.setSize(getWidth()/4,getHeight());
		
		
		
		
		
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
}
