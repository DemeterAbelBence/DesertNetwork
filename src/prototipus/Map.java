package prototipus;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Map.java
//  @ Date : 4/22/2023
//  @ Author : 
//
//




public class Map {
	Random random = new Random();
	private ArrayList<Component> components = new ArrayList<Component>();
	private ArrayList<Player> players = new ArrayList<Player>();;
	
	public List<Spring> springs = new ArrayList<Spring>();
	public List<Pump> pumps = new ArrayList<Pump>();
	public List<Cistern> cisterns = new ArrayList<Cistern>();
	public List<Pipe> pipes = new ArrayList<Pipe>();
	public List<Saboteur> saboteurs = new ArrayList<Saboteur>();
	public List<RepairMan> repairmen = new ArrayList<RepairMan>();

	
	public int createNew(String[] inputCommands) {
		int n = 0;
		for(int j = 0; !inputCommands[j].equals("done"); ++j) {
			n++;
			
			String line = inputCommands[j];
			String[] cmd = line.split(" ");
			switch (cmd[0]) {
			case "springs":
				springs.clear();
				int nrOfSprings = Integer.parseInt(cmd[1]);
				for (int i = 0; i < nrOfSprings; ++i)
					springs.add(new Spring());
				break;
			case "pumps":
				pumps.clear();
				int nrOfPumps = Integer.parseInt(cmd[1]);
				for (int i = 0; i < nrOfPumps; ++i)
					pumps.add(new Pump());
				break;
			case "cisterns":
				cisterns.clear();
				int nrOfCisterns = Integer.parseInt(cmd[1]);
				for (int i = 0; i < nrOfCisterns; ++i)
					cisterns.add(new Cistern());
				break;
			case "saboteur": {
				char endChar = cmd[1].charAt(cmd[1].length() - 1);
				int endNumber = Character.getNumericValue(endChar) - 1;
				String componentType = cmd[1].substring(0, cmd[1].length() - 1);
				Component position = null;
				switch (componentType) {
				case "spring":
					position = springs.get(endNumber);
					break;
				case "pump":
					position = pumps.get(endNumber);
					break;
				case "cistern":
					position = cisterns.get(endNumber);
					break;
				case "pipe":
					position = pipes.get(endNumber);
					break;
				}
				saboteurs.add(new Saboteur(position));
				break;
			}
			case "repairman": {
				char endChar = cmd[1].charAt(cmd[1].length() - 1);
				int endNumber = Character.getNumericValue(endChar) - 1;
				String componentType = cmd[1].substring(0, cmd[1].length() - 1);
				Component position = null;
				switch (componentType) {
				case "spring":
					position = springs.get(endNumber);
					break;
				case "pump":
					position = pumps.get(endNumber);
					break;
				case "cistern":
					position = cisterns.get(endNumber);
					break;
				case "pipe":
					position = pipes.get(endNumber);
					break;
				}
				RepairMan newRepairMan = new RepairMan(position);
				repairmen.add(newRepairMan);
				break;
			}
			default: {
				if(cmd[0].equals("newMap"))
					break;
				
				char endChar = cmd[0].charAt(cmd[0].length() - 1);
				String componentType;
				Pipe newPipe = new Pipe();
				for (int i = 0; i < 2; ++i) {
					endChar = cmd[i].charAt(cmd[i].length() - 1);
					componentType = cmd[i].substring(0, cmd[i].length() - 1);
					if (Character.isDigit(endChar)) {
						int endNumber = Character.getNumericValue(endChar) - 1;
						switch (componentType) {
						case "spring":
							newPipe.addNeighbour(springs.get(endNumber));
							springs.get(endNumber).addNeighbour(newPipe);
							break;
						case "pump":
							newPipe.addNeighbour(pumps.get(endNumber));
							pumps.get(endNumber).addNeighbour(newPipe);
							break;
						case "cistern":
							newPipe.addNeighbour(cisterns.get(endNumber));
							cisterns.get(endNumber).addNeighbour(newPipe);
							break;
						}
					}
				}
				for (int i = 2; i < cmd.length; ++i) {
					switch (cmd[i]) {
					case "punctured":
						newPipe.punctured();
						break;
					case "slippery":
						newPipe.resetSlipperyCounter();
						break;
					case "sticky":
						newPipe.resetStickyCounter();
						break;
					}
				}
				pipes.add(newPipe);
				break;
			}
			}
		}
		return n;
	}
	
	public void mapInit() {
		
	}

	public ArrayList<Component> getComponents() {
		return components;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void makeDefaultMap(int nrOfCisterns, int nrOfSprings, int nrOfPumps, int nrOfPipes) {
		try {
			Image cisternImage = ImageIO.read(this.getClass().getResource("cistern.png"));
			Image springImage = ImageIO.read(this.getClass().getResource("spring.png"));
			Image pumpImage = ImageIO.read(this.getClass().getResource("pump.png"));

			for(int i = 0; i < nrOfCisterns; ++i) 
			{
				Cistern newCistern = new Cistern();
				GamePanel.addDrawable(new DrawableComponent(newCistern, new Vector2(1000, i*100), cisternImage));
				components.add(newCistern);
			}
			for(int i = 0; i < nrOfSprings; ++i)
			{
				Spring newSpring = new Spring();
				GamePanel.addDrawable(new DrawableComponent(newSpring, new Vector2(0, i * 100), springImage));
				components.add(newSpring);
			}
			for(int i = 0; i < nrOfPumps; ++i)
			{
				Pump newPump = new Pump();
				GamePanel.addDrawable(new DrawableComponent(newPump, new Vector2((i+4) * 100, i * 100), pumpImage));
				components.add(newPump);
			}
			for(int i = 0; i < nrOfPipes; ++i)
			{
				Pipe newPipe = new Pipe();
				int firstRandom = random.nextInt(components.size() - 1 - i);
				newPipe.addNeighbour(components.get(firstRandom));
				int secondRandom;
				do {
					secondRandom = random.nextInt(components.size() - 1 - i);
				} while (secondRandom == firstRandom);
				newPipe.addNeighbour(components.get(secondRandom));
				GamePanel.addDrawable(new DrawableComponent(newPipe, new Vector2(0, 0), null, GamePanel.getDrawable(firstRandom), GamePanel.getDrawable(secondRandom)));
				components.add(newPipe);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		getPlayers().add(new RepairMan(new Pump()));
		getPlayers().add(new RepairMan(new Pump()));
		getPlayers().add(new Saboteur(new Pump()));
		getPlayers().add(new Saboteur(new Pipe()));
	}
}
