package prototipus;

import java.io.IOException;
import java.util.*;

/**A játék indításáért, futtatásáért felelős osztály.*/
public class Prototipus {
	/**Játék futtatásáért felelős.
	 * @param args: nem felhasznált
	 * @throws Exception */
	public static void main(String[] args) throws Exception {
		Map testMap = new Map();
		
		Observer o = null;
		
		try{
			o = new Observer(testMap);
			Drawable.setObserver(o);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//o.repaint();
		if(o != null) {
	        Timer t = new Timer(o);
	        t.startTimer();
		}
	}
}
