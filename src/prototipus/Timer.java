package prototipus;

import java.util.ArrayList;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Timer.java
//  @ Date : 4/22/2023
//  @ Author : 
//
//



/**Az idő megjelenítésének megvalósítására való osztály.*/
public class Timer implements Runnable {
	private ArrayList<Updateable> updateables;
	private Thread gameThread;
	private final int TPS = 1; //Tick Per Second

	private Observer observer;
	private boolean menuIsInitialized = false;
	
	private void initializeUpdateables() {
		updateables = new ArrayList<Updateable>();
		for (Updateable item:
				observer.getObservedMap().getComponents()) {
			updateables.add(item);
		}
		for (Updateable item:
				observer.getObservedMap().getPlayers()) {
			updateables.add(item);
		}
		updateables.add(observer);
		updateables.add(observer.getMenuPanel());
	}
	
	public void updateUpdateables(){
		if(updateables == null){ return;}
			updateables.clear();
			for (Updateable item:
				observer.getObservedMap().getComponents()) {
				updateables.add(item);
			}
			for (Updateable item:
				observer.getObservedMap().getPlayers()) {
				updateables.add(item);
			}
			updateables.add(observer);
			updateables.add(observer.getMenuPanel());
	}

	
	/**Az osztály konstruktora.
	 * @param observer: a játék összehangolásához*/
	public Timer(Observer o)
	{
		observer = o;
	}
	/**Az időzítő léptető függvénye, ez lépteti az összes léptetendő objektumot.*/
	public void tick() {
		if(observer.mapIsInitialized) {
			if(!menuIsInitialized) {
				initializeUpdateables();
				observer.getMenuPanel().initializeMenu();
				menuIsInitialized = true;
			}
			
			for(Updateable updateable:updateables)
				updateable.updateStatus();
		}
		observer.getMenuPanel().updateStatus();

		System.out.println("Tick!");
	}

	/**Az időzítő elindítása.*/
	public void startTimer()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**Az időzítő futtatására.*/
	@Override
	public void run() {
		double tpsToTime = 1000000000/TPS;
		double nextFrame = System.nanoTime() + tpsToTime;
		while(gameThread != null)
		{
			tick();
			double remainingTime =  nextFrame - System.nanoTime();
			//This is to eliminate some small and rare bug LMAO
			if(remainingTime <0)
				remainingTime = 0;

			try {
				Thread.sleep((long)remainingTime/1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextFrame += tpsToTime;
		}

	}
}
