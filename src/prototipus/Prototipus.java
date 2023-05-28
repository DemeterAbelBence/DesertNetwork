package prototipus;

import java.io.IOException;
import java.util.*;

/**A játék indításáért, futtatásáért felelős osztály.*/
public class Prototipus {
	/**Játék futtatásáért felelős.
	 * @param args: nem felhasznált*/
	public static void main(String[] args) throws IOException {
		Map testMap = new Map();
		
		Observer o = new Observer(testMap);
		Drawable.setObserver(o);
		
		//o.repaint();
        Timer t = new Timer(o);
        t.startTimer();
	}
}
