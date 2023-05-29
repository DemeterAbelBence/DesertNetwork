package prototipus;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Player.java
//  @ Date : 4/22/2023
//  @ Author : 
//
//



/**A játékban szereplő játékosok megvalósítására való osztály.*/
public abstract class Player implements Updateable{
	protected int stuckCounter;
	public final int stuckTime = 10;
	protected Component host;

	//absztrakt függvények

	public abstract void repair();
	public abstract void pickUpPipe(Pipe pipe);
	public abstract void pickUpPump();
	public abstract void placeDownPump();
	public abstract void placeDownPipe();


	public abstract void makeSlippery();

	/**A játékos megcsúszik és véletlenszerűen egy szomszédos mezőre kerül.*/
	private void slips() {
		ArrayList<Component> neighboursOfHostPipe = host.getNeighbours();
		Random random = new Random();
		int seged = random.nextInt(2);
		host.slipperyCounter=0;
		if(neighboursOfHostPipe.size() >= seged)
			setNewHost(neighboursOfHostPipe.get(0));
		else setNewHost(neighboursOfHostPipe.get(seged));
	}

	/**Adott mezőre lép a játékos ha ez lehetséges.
	 * @param dest: célmező ahová lépni szeretne*/
	public void moveTo(Component dest) {
		if(stuckCounter > 0)
			return;
		
		boolean destCanBeSteppedOn = dest.canBeSteppedOn();
		int destStickyUntil = dest.getStickyCounter();
		int destSlipperyUntil = dest.getSlipperyCounter();
		
		if(destCanBeSteppedOn) {
			if(destSlipperyUntil > 0) {
				setNewHost(dest);
				slips();
				return;
			}
			if(destStickyUntil > 0) {
				stuckCounter = stuckTime;
			}
			setNewHost(dest);
		}
	}

	/**Egy pumpa bemenetének megváltoztatása.
	 * @param input: bemenetnek szánt komponens*/
	public void changePumpInput(Component input) {
		if(host.getNode() && host.capacity > 0) {
			host.setInput(input);
			input.setOutput(host);
		}
	}

	/**Egy pumpa kimenetének megváltoztatása.
	 * @param output: kimenetnek szánt komponens*/
	public void changePumpOutput(Component output) {
		if(host.getNode() && host.capacity > 0) {
			host.setOutput(output);
			output.setInput(host);
		}
	}

	/**Cső (amin áll) kilukasztására.*/
	public void sabotage() {
		host.punctured();
	}

	/**Cső (ain áll) csúszóssá tétele*/
	public void makeSticky() {
		boolean isHostNode = host.getNode();
		if(!isHostNode && host.slipperyCounter == 0) {
			host.resetStickyCounter();
		}
	}

	/**Játékos host mezőjének átállítása.
	 * @param dest: célmezőnek szánt komponens*/
	public void setNewHost(Component dest) {
		host.removePlayer(this);
		dest.addPlayer(this);
		host = dest;
	}

	/**Az odaragadási számláló csökkentése.*/
	public void updateStatus() {
		decreaseStuckCounter();
	}

	/**Játékos stringesítése.
	 * @return String*/
	public String toString() {
		return "stuckCounter: " + stuckCounter;
	}

	/**Azon komponens visszaadása amin a játékos áll.
	 * @return Component*/
	public Component getHost() {
		return host;
	}

	/**Ragadóssági számláló csökkentése, ha az nagyobb mint 0.*/
	public void decreaseStuckCounter() {
		if(stuckCounter > 0)
			stuckCounter --;
	}
}
