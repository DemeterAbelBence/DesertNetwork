package prototipus;

import java.io.IOException;
import java.util.*;


public class Prototipus {
	public static void main(String[] args) throws IOException {
		Map testMap = new Map();
		
		Observer o = new Observer(testMap);
		Drawable.setObserver(o);
		
		o.repaint();
	}
}
