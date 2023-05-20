package prototipus;

import java.io.IOException;
import java.util.*;


public class Prototipus {
	public static void main(String[] args) throws IOException {
		System.out.println("Hello world!");
		Map testMap = new Map();
		
		Observer o = new Observer(testMap);
		
		o.repaint();
	}
}
