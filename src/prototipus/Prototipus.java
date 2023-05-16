package prototipus;

import java.util.*;

public class Prototipus {
	private static List<Spring> springs = new ArrayList<Spring>();
	public static List<Pump> pumps = new ArrayList<Pump>();
	private static List<Cistern> cisterns = new ArrayList<Cistern>();
	public static List<Pipe> pipes = new ArrayList<Pipe>();
	private static List<Saboteur> saboteurs = new ArrayList<Saboteur>();
	private static List<RepairMan> repairmen = new ArrayList<RepairMan>();

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		while (scanner.hasNext()) {
			String line = scanner.nextLine().trim();
			if (line.isEmpty())
				break;
			String[] cmd = line.split(" ");

			switch (cmd[0]) {
			case "newMap":
				newMap();
				System.out.println("Successful command execution!");
				break;
			case "showMap":
				System.out.print(System.lineSeparator());
				for(int i = 0; i < cisterns.size(); ++i) {
					Cistern cistern = cisterns.get(i);
					System.out.println("cistern" + (i + 1) + System.lineSeparator() + cistern.toString());
				}
				for(int i = 0; i < pumps.size(); ++i) {
					Pump pump = pumps.get(i);
					System.out.println("pump" + (i + 1) + System.lineSeparator() + pump.toString());
				}
				for(int i = 0; i < springs.size(); ++i) {
					Spring spring = springs.get(i);
					System.out.println("spring" + (i + 1) + System.lineSeparator() + spring.toString());
				}
				for(int i = 0; i < pipes.size(); ++i) {
					Pipe pipe = pipes.get(i);
					System.out.println("pipe" + (i + 1) + System.lineSeparator() + pipe.toString());
				}
				break;
			case "showPlayers":
				for (int i = 0; i < repairmen.size(); ++i) {
					RepairMan repairman = repairmen.get(i);
					String positionTypeAndIndex = getComponentTypeAndIndex(repairman.getHost());
					System.out.println(
							"repairman" + (i + 1) + " at component: " + positionTypeAndIndex + ", " + repairman.toString());
				}
				for (int i = 0; i < saboteurs.size(); ++i) {
					Saboteur saboteur = saboteurs.get(i);
					String positionTypeAndIndex = getComponentTypeAndIndex(saboteur.getHost());
					System.out.println(
							"saboteur" + (i + 1) + " at component: " + positionTypeAndIndex + ", " + saboteur.toString());
				}
				break;
			case "flowWater":
				
			case "sabotagePump":
				//timer.Tick();
				Prototipus.pumps.get(0).broken=true;
				break;
			default: {
				char endChar = cmd[0].charAt(cmd[0].length() - 1);
				int endNumber = Character.getNumericValue(endChar) - 1;
				String playerType = cmd[0].substring(0, cmd[0].length() - 1);
				Player player;
				switch (playerType) {
					case "saboteur": {
						player = saboteurs.get(endNumber);
						break;
					}
					case "repairman": {
						player = repairmen.get(endNumber);
						break;
					}
					default:
						player = null;
						break;
				}
				
				String playerString = getPlayerTypeAndIndex(player);
				switch (cmd[1]) {
					case "moveTo":
						Component dest = getComponentFromTypeAndIndex(cmd[2]);
						player.moveTo(dest);
						String componentString = getComponentTypeAndIndex(dest);
						System.out.println(playerString + " field: " + componentString);
						break;
					case "makePipeSlippery":
						((Saboteur) player).makeSlippery();
						System.out.println(playerString + " makePipeSlippery "
						+ getComponentTypeAndIndex(player.host));
						break;
					case "makePipeSticky":
						player.makeSticky();
						System.out.println(playerString + " makePipeSticky "
						+ getComponentTypeAndIndex(player.host));
						break;
					case "placeDownPump":
						System.out.println(playerString + " placeDownPump "
					+ getComponentTypeAndIndex(player.host));
						((RepairMan) player).placeDownPump();
						break;
					case "placeDownPipe":
						System.out.println(playerString + " placeDownPipe "
					+ getComponentTypeAndIndex(player.host));
						((RepairMan) player).placeDownPipe();
						break;
					case "pickUpPipe":
						if(cmd[2].equals("new")) {
							((RepairMan) player).pickUpPipe(null);
							Pipe pickedUpPipe = ((RepairMan) player).getPipeInHand();
							pipes.add(pickedUpPipe);
						}
						else {
							Pipe pipe = (Pipe) getComponentFromTypeAndIndex(cmd[2]);
							((RepairMan) player).pickUpPipe(pipe);
						}
						System.out.println(playerString + " pickUpPipe from: " 
						+ getComponentTypeAndIndex(player.host) + ", picked up: " 
								+ getComponentTypeAndIndex(((RepairMan) player).getPipeInHand()));
						break;
					case "pickUpPump":
						System.out.println(playerString + " pickUpPump " 
								+ getComponentTypeAndIndex(player.host));
						((RepairMan) player).pickUpPump();
						break;
					case "repairPipe":
						((RepairMan) player).repair();
						System.out.println(playerString + " repairPipe " 
								+ getComponentTypeAndIndex(player.host));
						break;
					case "repairPump":
						((RepairMan) player).repair();
						System.out.println(playerString + " repairPump " 
								+ getComponentTypeAndIndex(player.host));
						break;
					case "sabotagePipe":
						player.sabotage();
						System.out.println(playerString + " sabotagePipe " 
								+ getComponentTypeAndIndex(player.host));
						break;
					case "changePumpOutput":
						Component output = getComponentFromTypeAndIndex(cmd[2]);
						player.changePumpOutput(output);
						System.out.println(getComponentTypeAndIndex(player.host) + 
								" changePumpOutput " + getComponentTypeAndIndex(output));
						break;
					case "changePumpInput":
						Component input = getComponentFromTypeAndIndex(cmd[2]);
						player.changePumpInput(getComponentFromTypeAndIndex(cmd[2]));
						System.out.println(getComponentTypeAndIndex(player.host) + 
								" changePumpInput " + getComponentTypeAndIndex(input));
					break;
				}
				
				break;
			}
			}
		}
	}
	
	public static Component getComponentFromTypeAndIndex(String string) {
		char endChar = string.charAt(string.length() - 1);
		int endNumber = Character.getNumericValue(endChar) - 1;
		String componentType = string.substring(0, string.length() - 1);
		switch (componentType) {
		case "pump":
			return pumps.get(endNumber);
		case "cistern":
			return cisterns.get(endNumber);
		case "spring":
			return springs.get(endNumber);
		case "pipe":
			return pipes.get(endNumber);
		default:
			return null;
		}
	}

	public static String getComponentTypeAndIndex(Component component) {
		if (springs.contains(component))
			return "spring" + (springs.indexOf(component) + 1);
		if (pumps.contains(component))
			return "pump" + (pumps.indexOf(component) + 1);
		if (cisterns.contains(component))
			return "cistern" + (cisterns.indexOf(component) + 1);
		if (pipes.contains(component))
			return "pipe" + (pipes.indexOf(component) + 1);
		return null;
	}
	
	public static String getPlayerTypeAndIndex(Player player) {
		if (saboteurs.contains(player))
			return "saboteur" + (saboteurs.indexOf(player) + 1);
		if (repairmen.contains(player))
			return "repairman" + (repairmen.indexOf(player) + 1);
		return null;
	}

	public static void newMap() {
		String line;

		while (!(line = scanner.nextLine().trim()).equals("done")) {
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
	}
}
