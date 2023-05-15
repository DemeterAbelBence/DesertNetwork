package prototipus;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class PrototypeTest {
	
	private File outputFile;
	private Map map;
	
	private Component getComponentFromTypeAndIndex(String string) {
		char endChar = string.charAt(string.length() - 1);
		int endNumber = Character.getNumericValue(endChar) - 1;
		String componentType = string.substring(0, string.length() - 1);
		switch (componentType) {
		case "pump":
			return map.pumps.get(endNumber);
		case "cistern":
			return map.cisterns.get(endNumber);
		case "spring":
			return map.springs.get(endNumber);
		case "pipe":
			return map.pipes.get(endNumber);
		default:
			return null;
		}
	}

	private String getComponentTypeAndIndex(Component component) {
		if (map.springs.contains(component))
			return "spring" + (map.springs.indexOf(component) + 1);
		if (map.pumps.contains(component))
			return "pump" + (map.pumps.indexOf(component) + 1);
		if (map.cisterns.contains(component))
			return "cistern" + (map.cisterns.indexOf(component) + 1);
		if (map.pipes.contains(component))
			return "pipe" + (map.pipes.indexOf(component) + 1);
		return null;
	}
	
	private String getPlayerTypeAndIndex(Player player) {
		if (map.saboteurs.contains(player))
			return "saboteur" + (map.saboteurs.indexOf(player) + 1);
		if (map.repairmen.contains(player))
			return "repairman" + (map.repairmen.indexOf(player) + 1);
		return null;
	}
	
	private void newMap(String[] inputCommands) { map.createNew(inputCommands); }
	
	private String[] executeCommands(String[] inputCommands) {
		int length= inputCommands.length;
		ArrayList<String> output = new ArrayList<String>();
		
		for(int j = 0; j < length; ++j) {
			String line = inputCommands[j].trim();
			if (line.isEmpty())
				break;
			String[] cmd = line.split(" ");

			switch (cmd[0]) {
			case "newMap":
				map.createNew(inputCommands);
				System.out.println("Successful command execution!");
				break;
			case "showMap":
				System.out.print(System.lineSeparator());
				for(int i = 0; i < map.cisterns.size(); ++i) {
					Cistern cistern = map.cisterns.get(i);
					System.out.println("cistern" + (i + 1) + System.lineSeparator() + cistern.toString());
				}
				for(int i = 0; i < map.pumps.size(); ++i) {
					Pump pump = map.pumps.get(i);
					System.out.println("pump" + (i + 1) + System.lineSeparator() + pump.toString());
				}
				for(int i = 0; i < map.springs.size(); ++i) {
					Spring spring = map.springs.get(i);
					System.out.println("spring" + (i + 1) + System.lineSeparator() + spring.toString());
				}
				for(int i = 0; i < map.pipes.size(); ++i) {
					Pipe pipe = map.pipes.get(i);
					System.out.println("pipe" + (i + 1) + System.lineSeparator() + pipe.toString());
				}
				break;
			case "showPlayers":
				for (int i = 0; i < map.repairmen.size(); ++i) {
					RepairMan repairman = map.repairmen.get(i);
					String positionTypeAndIndex = getComponentTypeAndIndex(repairman.getHost());
					System.out.println(
							"repairman" + (i + 1) + " at component: " + positionTypeAndIndex + ", " + repairman.toString());
				}
				for (int i = 0; i < map.saboteurs.size(); ++i) {
					Saboteur saboteur = map.saboteurs.get(i);
					String positionTypeAndIndex = getComponentTypeAndIndex(saboteur.getHost());
					System.out.println(
							"saboteur" + (i + 1) + " at component: " + positionTypeAndIndex + ", " + saboteur.toString());
				}
				break;
			case "flowWater":
			case "sabotagePump":
				//timer.Tick();
				break;
			default: {
				char endChar = cmd[0].charAt(cmd[0].length() - 1);
				int endNumber = Character.getNumericValue(endChar) - 1;
				String playerType = cmd[0].substring(0, cmd[0].length() - 1);
				Player player;
				switch (playerType) {
					case "saboteur": {
						player = map.saboteurs.get(endNumber);
						break;
					}
					case "repairman": {
						player = map.repairmen.get(endNumber);
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
							map.pipes.add(pickedUpPipe);
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
						Component out = getComponentFromTypeAndIndex(cmd[2]);
						player.changePumpOutput(out);
						System.out.println(getComponentTypeAndIndex(player.host) + 
								" changePumpOutput " + getComponentTypeAndIndex(out));
						break;
					case "changePumpInput":
						Component in = getComponentFromTypeAndIndex(cmd[2]);
						player.changePumpInput(getComponentFromTypeAndIndex(cmd[2]));
						System.out.println(getComponentTypeAndIndex(player.host) + 
								" changePumpInput " + getComponentTypeAndIndex(in));
					break;
				}
				
				break;
			}
		}
		}
		
		return null;
	}
	
	private String[] readInputFile(String fileName) {
		File file = new File("asd.txt");
		
        String line;
        ArrayList<String> result = new ArrayList<String>();
        try (
        	FileInputStream fileInputStream = new FileInputStream(file);
        	InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
       	    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);)
        {
        	
        	 while ((line = bufferedReader.readLine()) != null) {
                 result.add(line);
             }
        }catch (IOException e) {}
        
        return (String[])result.toArray();
	}
	
	@Before
	public void setUp() {
		outputFile = new File("output.txt");
	}
	
	@Test
	public void PlayerMovesGeneral() {
			
	}
	
}
