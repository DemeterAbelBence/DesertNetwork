package prototipus;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class PrototypeTest {
	
	private static Map map;
			
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
				j = map.createNew(inputCommands);
				break;
			case "showMap":
				output.add("\n");
				for(int i = 0; i < map.cisterns.size(); ++i) {
					Cistern cistern = map.cisterns.get(i);
					output.add("cistern" + (i + 1) + System.lineSeparator() + cistern.toString());
				}
				for(int i = 0; i < map.pumps.size(); ++i) {
					Pump pump = map.pumps.get(i);
					output.add("pump" + (i + 1) + System.lineSeparator() + pump.toString());
				}
				for(int i = 0; i < map.springs.size(); ++i) {
					Spring spring = map.springs.get(i);
					output.add("spring" + (i + 1) + System.lineSeparator() + spring.toString());
				}
				for(int i = 0; i < map.pipes.size(); ++i) {
					Pipe pipe = map.pipes.get(i);
					output.add("pipe" + (i + 1) + System.lineSeparator() + pipe.toString());
				}
				break;
			case "showPlayers":
				for (int i = 0; i < map.repairmen.size(); ++i) {
					RepairMan repairman = map.repairmen.get(i);
					String positionTypeAndIndex = getComponentTypeAndIndex(repairman.getHost());
					output.add("repairman" + (i + 1) + " at component: " + positionTypeAndIndex + ", " + repairman.toString());
				}
				for (int i = 0; i < map.saboteurs.size(); ++i) {
					Saboteur saboteur = map.saboteurs.get(i);
					String positionTypeAndIndex = getComponentTypeAndIndex(saboteur.getHost());
					output.add("saboteur" + (i + 1) + " at component: " + positionTypeAndIndex + ", " + saboteur.toString());
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
						output.add(playerString + " field: " + componentString);
						break;
					case "makePipeSlippery":
						((Saboteur) player).makeSlippery();
						output.add(playerString + " makePipeSlippery " + getComponentTypeAndIndex(player.host));
						break;
					case "makePipeSticky":
						player.makeSticky();
						output.add(playerString + " makePipeSticky " + getComponentTypeAndIndex(player.host));
						break;
					case "placeDownPump":
						output.add(playerString + " placeDownPump " + getComponentTypeAndIndex(player.host));
						((RepairMan) player).placeDownPump();
						break;
					case "placeDownPipe":
						output.add(playerString + " placeDownPipe " + getComponentTypeAndIndex(player.host));
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
						output.add(playerString + " pickUpPipe from: " 
						+ getComponentTypeAndIndex(player.host) + ", picked up: " 
								+ getComponentTypeAndIndex(((RepairMan) player).getPipeInHand()));
						break;
					case "pickUpPump":
						output.add(playerString + " pickUpPump " 
								+ getComponentTypeAndIndex(player.host));
						((RepairMan) player).pickUpPump();
						break;
					case "repairPipe":
						((RepairMan) player).repair();
						output.add(playerString + " repairPipe " 
								+ getComponentTypeAndIndex(player.host));
						break;
					case "repairPump":
						((RepairMan) player).repair();
						output.add(playerString + " repairPump " + getComponentTypeAndIndex(player.host));
						break;
					case "sabotagePipe":
						player.sabotage();
						output.add(playerString + " sabotagePipe " + getComponentTypeAndIndex(player.host));
						break;
					case "changePumpOutput":
						Component out = getComponentFromTypeAndIndex(cmd[2]);
						player.changePumpOutput(out);
						output.add(getComponentTypeAndIndex(player.host) + " changePumpOutput " + getComponentTypeAndIndex(out));
						break;
					case "changePumpInput":
						Component in = getComponentFromTypeAndIndex(cmd[2]);
						player.changePumpInput(getComponentFromTypeAndIndex(cmd[2]));
						output.add(getComponentTypeAndIndex(player.host) + " changePumpInput " + getComponentTypeAndIndex(in));
					break;
				}
				
				break;
			}
		}
		}
		return listToArray(output);
	}
	
	private String[] readInputFile(String fileName) {
		File file = new File("test_inputs/test1in.txt");
		
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
        
        return listToArray(result);
	}
	
	private void writeOutputFile(String fileName, String[] output) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			int length = output.length;
			for(int i = 0; i < length; ++i) {
				writer.write(output[i]);
				writer.write('\n');
			}
        } catch (IOException e) {}
	}
	
	private void displayTestResult(String fileName, String[] expected, String[] given, boolean success) {
		if(success) {
			String[] result1 = new String[1];
			result1[0] = "Successful command execution!\n";
			writeOutputFile(fileName, result1);
		}else {
			ArrayList<String> result2 = new ArrayList<String>();
			result2.add("Command execution failed!\n");
			
			result2.add("\n");
			result2.add("Expected:\n");
			
			int length = expected.length;
			for(int i = 0; i < length; ++i) {
				result2.add(expected[i]);
			}
			
			result2.add("\n");
			result2.add("Given:\n");
			
			length = given.length;
			for(int i = 0; i < length; ++i) {
				result2.add(given[i]);
			}
			writeOutputFile(fileName, listToArray(result2));
		}
	}
	
	private String[] listToArray(ArrayList<String> a) {
		int size = a.size();
		String[] result = new String[size];
		for(int i = 0; i < size; ++i) 
			result[i] = a.get(i); 
		
		return result;
	}
	
	public static Component getComponentFromTypeAndIndex(String string) {
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

	public static String getComponentTypeAndIndex(Component component) {
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
	
	public static String getPlayerTypeAndIndex(Player player) {
		if (map.saboteurs.contains(player))
			return "saboteur" + (map.saboteurs.indexOf(player) + 1);
		if (map.repairmen.contains(player))
			return "repairman" + (map.repairmen.indexOf(player) + 1);
		return null;
	}

	public static void addCistern(Cistern c) { map.cisterns.add(c); }
	public static void addSpring(Spring s) { map.springs.add(s); }
	public static void addPump(Pump p) { map.pumps.add(p); }
	public static void addPipe(Pipe p) { map.pipes.add(p); }
	
	@Before
	public void initialize() {
		map = new Map();
	}
	
	@Test
	public void PlayerMovesGeneral() {
		String[] input = readInputFile("input_files/test1in.txt");		
		String[] output = executeCommands(input);
		
		String s = 	"saboteur1 field: pipe1\n"
					+"saboteur1 field: pump2\n"
					+"\n"
					+"\n"
					+"pump1\n"
					+"waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
					+"input: null\n"
					+"output: pipe1\n"
					+"Players standing on this component:\n"
					+"Neighbouring components:\n"
					+"neighbour1: pipe1\n"
					+"\n"
					+"pump2\n"
					+"waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
					+"input: pipe1\n"
					+"output: null\n"
					+"Players standing on this component:\n"
					+"player1: saboteur1\n"
					+"Neighbouring components:\n"
					+"neighbour1: pipe1\n"
					+"\n"
					+"pipe1\n"
					+"leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
					+"input: pump1\n"
					+"output: pump2\n"
					+"Players standing on this component:\n"
					+"Neighbouring components:\n"
					+"neighbour1: pump1\n"
					+"neighbour2: pump2\n"
					+"\n"
					+"\n"
					+"saboteur1 at component: pump2, stuckCounter: 0";
		
		
		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test1out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PlayerMovestoSlippery() {
		String[] input = readInputFile("input_files/test2in.txt");
		String[] output = executeCommands(input);

		String s = 	"saboteur1 field: pipe1\n"
				+ "saboteur1 makePipeSlippery pipe1\n"
				+ "saboteur1 field: pump1\n"
				+ "saboteur1 field: pipe1\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "player1: saboteur1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pump2\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: true, sticky: false, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: pump2\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "saboteur1 at component: pump1, stuckCounter: 0";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test2out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PickUpPump() {
		String[] input = readInputFile("input_files/test3in.txt");
		String[] output = executeCommands(input);

		String s = 	"repairman1 pickUpPump cistern1\n"
				+ "repairman1 repairPipe cistern1\n"
				+ "\n"
				+ "cistern1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "\n"
				+ "repairman1 at component: cistern1, stuckCounter: 0, hasPump: true, pipeInHand: null";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test3out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PlaceDownPump() {
		String[] input = readInputFile("input_files/test4in.txt");
		String[] output = executeCommands(input);

		String s = 	"repairman1 pickUpPump cistern1\n"
				+ "repairman1 field: pipe1\n"
				+ "repairman1 placeDownPump pipe1\n"
				+ "\n"
				+ "cistern1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe2\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe2\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pump2\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: pipe2\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "neighbour2: pipe2\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: pump2\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "pipe2\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump2\n"
				+ "output: cistern1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: cistern1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "repairman1 at component: pipe1, stuckCounter: 0, hasPump: false, pipeInHand: null";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test4out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PlaceDownPipe() {
		String[] input = readInputFile("input_files/test5in.txt");
		String[] output = executeCommands(input);

		String s = 	"repairman1 pickUpPipe from: cistern1, picked up: pipe2\n"
				+ "repairman1 placeDownPipe cistern1\n"
				+ "repairman1 field: pipe1\n"
				+ "repairman1 field: pump1\n"
				+ "repairman1 placeDownPipe pump1\n"
				+ "\n"
				+ "cistern1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "neighbour2: pipe2\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe2\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "neighbour2: pipe2\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: cistern1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: cistern1\n"
				+ "\n"
				+ "pipe2\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: cistern1\n"
				+ "output: pump1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: cistern1\n"
				+ "neighbour2: pump1\n"
				+ "\n"
				+ "repairman1 at component: pump1, stuckCounter: 0, hasPump: false, pipeInHand: null";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test5out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PickUpPipe() {
		String[] input = readInputFile("input_files/test6in.txt");
		String[] output = executeCommands(input);

		String s = 	"repairman1 pickUpPipe from: cistern1, picked up: pipe2\n"
				+ "repairman1 placeDownPipe cistern1\n"
				+ "repairman1 field: pipe1\n"
				+ "repairman1 field: pump1\n"
				+ "repairman1 placeDownPipe pump1\n"
				+ "repairman1 pickUpPipe from: pump1, picked up: pipe1\n"
				+ "repairman1 field: pipe2\n"
				+ "repairman1 field: cistern1\n"
				+ "repairman1 placeDownPipe cistern1\n"
				+ "\n"
				+ "cistern1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe2\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe2\n"
				+ "neighbour2: pipe1\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe2\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe2\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: true\n"
				+ "input: null\n"
				+ "output: cistern1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: cistern1\n"
				+ "\n"
				+ "pipe2\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: cistern1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: cistern1\n"
				+ "neighbour2: pump1\n"
				+ "\n"
				+ "repairman1 at component: cistern1, stuckCounter: 0, hasPump: false, pipeInHand: null";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test6out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void RepairPipe() {
		String[] input = readInputFile("input_files/test7in.txt");
		String[] output = executeCommands(input);

		String s = 	"repairman1 repairPipe pipe1\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pump2\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: pump2\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "repairman1 at component: pipe1, stuckCounter: 0, hasPump: false, pipeInHand: null";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test7out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PipeSabotage() {
		String[] input = readInputFile("input_files/test8in.txt");
		String[] output = executeCommands(input);

		String s = 	"repairman1 sabotagePipe pipe1\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pump2\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: true\n"
				+ "input: pump1\n"
				+ "output: pump2\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "repairman1 at component: pipe1, stuckCounter: 0, hasPump: false, pipeInHand: null";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test8out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void RepairPump() {
		String[] input = readInputFile("input_files/test9in.txt");
		String[] output = executeCommands(input);

		String s = 	"repairman1 repairPump pump1\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "\n"
				+ "repairman1 at component: pump1, stuckCounter: 0, hasPump: false, pipeInHand: null";

		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test9out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PumpInputOtputChange() {
		String[] input = readInputFile("input_files/test10in.txt");
		String[] output = executeCommands(input);

		String s = 	"pump2 changePumpOutput pipe1\n"
				+ "pump2 changePumpInput pipe2\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pump2\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe2\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "player1: repairman1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "neighbour2: pipe2\n"
				+ "\n"
				+ "pump3\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe2\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe2\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump2\n"
				+ "output: pump1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "pipe2\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump3\n"
				+ "output: pump2\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump2\n"
				+ "neighbour2: pump3\n"
				+ "\n"
				+ "repairman1 at component: pump2, stuckCounter: 0, hasPump: false, pipeInHand: null";


		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test10out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void WaterFlowsRepairMan() {
		String[] input = readInputFile("input_files/test11in.txt");
		String[] output = executeCommands(input);

		String s = 	"cistern1\n"
				+ "waterLevel: 1, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe2\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe2\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 1, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: pipe2\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "neighbour2: pipe2\n"
				+ "\n"
				+ "spring1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 1, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: spring1\n"
				+ "output: pump1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: spring1\n"
				+ "neighbour2: pump1\n"
				+ "\n"
				+ "pipe2\n"
				+ "leaked water: 0, waterLevel: 1, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: cistern1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: cistern1";


		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test11out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void WaterFlowsSaboteur() {
		String[] input = readInputFile("input_files/test12in.txt");
		String[] output = executeCommands(input);

		String s = 	"pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "spring1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 2, waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: true\n"
				+ "input: spring1\n"
				+ "output: pump1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: spring1\n"
				+ "neighbour2: pump1";


		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test12out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PumpMalfunction() {
		String[] input = readInputFile("input_files/test13in.txt");
		String[] output = executeCommands(input);

		String s = 	"pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: true, punctured: false\n"
				+ "input: null\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n";


		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test13out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void SaboteurMakesSlippery() {
		String[] input = readInputFile("input_files/test14in.txt");
		String[] output = executeCommands(input);

		String s = 	"saboteur1 makePipeSlippery pipe1\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pump2\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: true, sticky: false, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: pump2\n"
				+ "Players standing on this component:\n"
				+ "player1: saboteur1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "saboteur1 at component: pipe1, stuckCounter: 0";


		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test14out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}

	@Test
	public void PlayerMakesSticky() {
		String[] input = readInputFile("input_files/test15in.txt");
		String[] output = executeCommands(input);

		String s = 	"saboteur1 makePipeSticky pipe1\n"
				+ "\n"
				+ "pump1\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: null\n"
				+ "output: pipe1\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pump2\n"
				+ "waterLevel: 0, slippery: false, sticky: false, broken: false, punctured: false\n"
				+ "input: pipe1\n"
				+ "output: null\n"
				+ "Players standing on this component:\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pipe1\n"
				+ "\n"
				+ "pipe1\n"
				+ "leaked water: 0, waterLevel: 0, slippery: false, sticky: true, broken: false, punctured: false\n"
				+ "input: pump1\n"
				+ "output: pump2\n"
				+ "Players standing on this component:\n"
				+ "player1: saboteur1\n"
				+ "Neighbouring components:\n"
				+ "neighbour1: pump1\n"
				+ "neighbour2: pump2\n"
				+ "\n"
				+ "saboteur1 at component: pipe1, stuckCounter: 0";


		String[] expectedOutput = s.split("\n");
		boolean success = Arrays.equals(expectedOutput, output);
		displayTestResult("test_outputs/test15out.txt", expectedOutput, output, success);
		Assert.assertTrue(success);
	}
	
}
