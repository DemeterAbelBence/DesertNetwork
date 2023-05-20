package prototipus;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.HashMap;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
public class Observer extends JFrame implements Updateable {
	
	
	private Map observedMap;

	private MenuPanel menuPanel;
	private GamePanel gamePanel;
	
	public Observer(Map map)
	{
		//device = GraphicsEnvironment
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		observedMap = map;
		
		this.getContentPane().setLayout(null);
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
		observedMap.saboteurs.add(new Saboteur(new Pipe()));
		observedMap.saboteurs.add(new Saboteur(new Pipe()));
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
}
