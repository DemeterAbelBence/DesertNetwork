package prototipus;

import java.awt.Image;
import java.io.*;
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
	private static ArrayList<Component> components = new ArrayList<Component>();
	private static ArrayList<Player> players = new ArrayList<Player>();;

	public List<Spring> springs = new ArrayList<Spring>();
	public List<Pump> pumps = new ArrayList<Pump>();
	public List<Cistern> cisterns = new ArrayList<Cistern>();
	public List<Pipe> pipes = new ArrayList<Pipe>();
	public List<Saboteur> saboteurs = new ArrayList<Saboteur>();
	public List<RepairMan> repairmen = new ArrayList<RepairMan>();

	static Image cisternImage;
	static Image springImage;
	static Image pumpImage;
	static Image repairManImage;
	static Image saboteurImage;

	public Map() {
		try {
			cisternImage = ImageIO.read(this.getClass().getResource("cistern.png"));
			springImage = ImageIO.read(this.getClass().getResource("spring.png"));
			pumpImage = ImageIO.read(this.getClass().getResource("pump.png"));
			repairManImage = ImageIO.read(this.getClass().getResource("repairMan.png"));
			saboteurImage = ImageIO.read(this.getClass().getResource("saboteur.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createNodes(String[] inputCommands) {
		for(int i = 0; !inputCommands[i].equals("done"); ++i) {
			
			//command variables
			String line = inputCommands[i];
			String[] cmd = line.split(" ");
			
			//new Component position
			int X = Integer.parseInt(cmd[1]);
			int Y = Integer.parseInt(cmd[2]);
			Vector2 position = new Vector2(X, Y);
			
			//result
			Component newComponent = null;
			DrawableComponent newDc = null;
			
			switch (cmd[0]) {
				case "spring":
					newComponent = new Spring();
					newDc = new DrawableComponent(newComponent, position, springImage);
					break;
					
				case "pump":
					newComponent = new Pump();
					newDc = new DrawableComponent(newComponent, position, pumpImage);
					break;
				case "cistern":
					newComponent = new Cistern();
					newDc = new DrawableComponent(newComponent, position, cisternImage);
					break;			
			}
			
			if(newComponent != null && newDc != null) {
				Observer.addDrawableComponent(newComponent, newDc);
				components.add(newComponent);
				
				if(cmd.length > 3) {
					DrawablePlayer newDp = null;
					Player newPlayer = null;
					
					if(cmd[3].equals("repairMan")) {
						newPlayer = new RepairMan(newComponent);
						newComponent.addPlayer(newPlayer);
						Vector2 playerPos = newDc.getCoordinates();
						newDp = new DrawablePlayer(newPlayer, playerPos, repairManImage);
					}
					if(cmd[3].equals("saboteur")) {
						newPlayer = new Saboteur(newComponent);
						newComponent.addPlayer(newPlayer);
						Vector2 playerPos = newDc.getCoordinates();
						newDp = new DrawablePlayer(newPlayer, playerPos, saboteurImage);
					}
					if(newDp != null && newPlayer != null) {
						Observer.addDrawablePlayer(newPlayer, newDp);
						players.add(newPlayer);
					}
				}	
			}
		}
	
	}

	private void createEdges(String[] inputCommands) {
		ArrayList<Component> pipes = new ArrayList<Component>();
		
		for(int i = 0; !inputCommands[i].equals("done"); ++i) {
			
			//command variables
			String line = inputCommands[i];
			String[] cmd = line.split(" ");
			
			//new Component position
			int index1 = Integer.parseInt(cmd[0]);
			int index2 = Integer.parseInt(cmd[1]);
			
			if(index1 >= 0 && index1 < components.size() &&
					index2 >= 0 && index2 < components.size()) {
				
				Pipe p = new Pipe();
				p.addNeighbour(components.get(index1));
				p.addNeighbour(components.get(index2));
				components.get(index1).addNeighbour(p);
				components.get(index2).addNeighbour(p);
				
				pipes.add(p);
				
				DrawableComponent d = new DrawableComponent(p);
				Observer.addDrawableComponent(p, d);
			}
		}
		components.addAll(pipes);
	}
	
	public void createFromCommands(String[] nodeCommands, String[] edgeCommands) {
			createNodes(nodeCommands);
			createEdges(edgeCommands);
	}
	
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
					for (int i = 0; i < nrOfSprings; ++i){
						Spring newSpring = new Spring();
						springs.add(newSpring);
						Observer.addDrawableComponent(newSpring, new DrawableComponent(newSpring, new Vector2(0, i * 100), springImage));
						components.add(newSpring);
					}
	
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
		//beolvas adott fajlbol
		File file = new File("map1.txt");

		String line;
		String[] result = new String[4];
		try (
				FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);)
		{

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				result = line.split(" ");
				switch (result[0]) {
					case "cistern":
						cisterns.clear();
						Cistern newCistern = new Cistern();
						Observer.addDrawableComponent(newCistern, new DrawableComponent(newCistern, new Vector2(Integer.parseInt(result[1]), Integer.parseInt(result[2])), cisternImage));
						components.add(newCistern);
						cisterns.add(newCistern);
						break;
					case "spring":
						springs.clear();
						Spring newSpring = new Spring();
						Observer.addDrawableComponent(newSpring, new DrawableComponent(newSpring, new Vector2(Integer.parseInt(result[1]), Integer.parseInt(result[2])), springImage));
						components.add(newSpring);
						springs.add(newSpring);
						break;
					case "pump":
						pumps.clear();
						Pump newPump = new Pump();
						Observer.addDrawableComponent(newPump, new DrawableComponent(newPump, new Vector2(Integer.parseInt(result[1]), Integer.parseInt(result[2])), pumpImage));
						components.add(newPump);
						pumps.add(newPump);
						break;

					case "pipe":
						pipes.clear();
						Pipe newPipe = new Pipe();
						for (int i = 1; i < 3; i++) {
							String n = result[i].substring(0, result[i].length() - 1);
							int num = Integer.parseInt(result[i].substring(result[i].length() - 1));

							switch (n) {
								case "pump":
									newPipe.addNeighbour(pumps.get(num));
									pumps.get(num).addNeighbour(newPipe);
									break;
								case "cistern":
									newPipe.addNeighbour(cisterns.get(num));
									cisterns.get(num).addNeighbour(newPipe);
									break;
								case "spring":
									newPipe.addNeighbour(springs.get(num));
									springs.get(num).addNeighbour(newPipe);
									break;
							}
						}
						if(!result[3].equals("-") ){
							switch(result[3]){
								case "punctured": newPipe.punctured(); break;
								case "slippery": newPipe.resetSlipperyCounter(); break;
								case "sticky": newPipe.resetStickyCounter(); break;
							}
						}
						Observer.addDrawableComponent(newPipe, new DrawableComponent(newPipe));
						components.add(newPipe);
						pipes.add(newPipe);
						break;
					case "repairman":
						repairmen.clear();
						RepairMan r1;
						String host = result[2].substring(0, result[2].length() - 1);
						int num = Integer.parseInt(result[2].substring(result[2].length() - 1));
						switch(host){
							case "pump": r1 = new RepairMan(pumps.get(num)); break;
							case "spring": r1 = new RepairMan(springs.get(num)); break;
							case "cistern": r1 = new RepairMan(cisterns.get(num)); break;
							case "pipe": r1 = new RepairMan(pipes.get(num)); break;
							default: r1 = new RepairMan(springs.get(0)); break;
						}
						players.add(r1);
						repairmen.add(r1);
						//Observer.addDrawablePlayer(r1, new DrawablePlayer(r1, Observer.getDrawableOfComponent(r1.host).getCoordinates(), repairManImage));
						break;
					case "saboteur":
						Saboteur s1;
						String host1 = result[2].substring(0, result[2].length() - 1);
						int num1 = Integer.parseInt(result[2].substring(result[2].length() - 1));
						switch(host1){
							case "pump": s1 = new Saboteur(pumps.get(num1)); break;
							case "spring": s1 = new Saboteur(springs.get(num1)); break;
							case "cistern": s1 = new Saboteur(cisterns.get(num1)); break;
							case "pipe": s1 = new Saboteur(pipes.get(num1)); break;
							default: s1 = new Saboteur(cisterns.get(0)); break;
						}
						players.add(s1);
						saboteurs.add(s1);
						//Observer.addDrawablePlayer(s1, new DrawablePlayer(s1, Observer.getDrawableOfComponent(s1.host).getCoordinates(), saboteurImage));
						break;
				}
			}
		}catch (IOException e) {}
	}

	public ArrayList<Component> getComponents() {
		return components;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public static void addPump(RepairMan repairman, Pump p) {
		components.add(p);
		Drawable drawableOfRepairman = Observer.getDrawableOfPlayer(repairman);
		Observer.addDrawableComponent(p, new DrawableComponent(p, drawableOfRepairman.coordinates, pumpImage));
	}

	public static void addPipe(Pipe p) {
		components.add(p);
		Observer.addDrawableComponent(p, new DrawableComponent(p));
	}

	public void makeDefaultMap(int nrOfCisterns, int nrOfSprings, int nrOfPumps, int nrOfPipes) {
		for(int i = 0; i < nrOfCisterns; ++i)
		{
			Cistern newCistern = new Cistern();
			Observer.addDrawableComponent(newCistern, new DrawableComponent(newCistern, new Vector2(900, i*100 + 100), cisternImage));
			components.add(newCistern);
		}
		for(int i = 0; i < nrOfSprings; ++i)
		{
			Spring newSpring = new Spring();
			Observer.addDrawableComponent(newSpring, new DrawableComponent(newSpring, new Vector2(50, i * 100 + 100), springImage));
			components.add(newSpring);
		}
		for(int i = 0; i < nrOfPumps; ++i)
		{
			Pump newPump = new Pump();
			Observer.addDrawableComponent(newPump, new DrawableComponent(newPump, new Vector2((i+4) * 100, i * 100 + 100), pumpImage));
			components.add(newPump);
		}
		for(int i = 0; i < nrOfPipes; ++i)
		{
			Pipe newPipe = new Pipe();
			int firstRandom = random.nextInt(components.size() - 1 - i);
			newPipe.addNeighbour(components.get(firstRandom));
			components.get(firstRandom).addNeighbour(newPipe);
			
			int secondRandom;
			do {
				secondRandom = random.nextInt(components.size() - 1 - i);
			} while (secondRandom == firstRandom);
			newPipe.addNeighbour(components.get(secondRandom));
			components.get(secondRandom).addNeighbour(newPipe);
			Observer.addDrawableComponent(newPipe, new DrawableComponent(newPipe));
			components.add(newPipe);
		}

		//Playerek rákerülnek előre kiválasztott komponensre
		getPlayers().add(new RepairMan(components.get(0)));
		getPlayers().add(new RepairMan(components.get(1)));
		getPlayers().add(new Saboteur(components.get(3)));
		getPlayers().add(new Saboteur(components.get(10)));

		Observer.addDrawablePlayer(getPlayers().get(0), new DrawablePlayer(getPlayers().get(0),new Vector2(),repairManImage));
		Observer.addDrawablePlayer(getPlayers().get(1),new DrawablePlayer(getPlayers().get(1),new Vector2(),repairManImage));
		Observer.addDrawablePlayer(getPlayers().get(2),new DrawablePlayer(getPlayers().get(2),new Vector2(),saboteurImage));
		Observer.addDrawablePlayer(getPlayers().get(3),new DrawablePlayer(getPlayers().get(3),new Vector2(),saboteurImage));
	}
}
