package com.stibocatalog.hunspell;

import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Simple testing and native build utility class, not useful in applications.
 *
 * The Hunspell java bindings are licensed under LGPL, see the file COPYING.txt
 * in the root of the distribution for the exact terms.
 *
 * @author Flemming Frandsen (flfr at stibo dot com)
 */

public class HunspellMain {

	static JFrame frame;
	static JLabel label;
	static String output;
	private static void println(String msg) {
		System.err.println(msg);
		if (frame != null) {
			output += msg + "<br>";
			label.setText("<html>"+output+"</html>");
			frame.pack();
		}
	}
	private static void print(String msg) {
		System.err.print(msg);
		if (frame != null) {
			output += msg;
			label.setText("<html>"+output+"</html>");
			frame.pack();
		}
	}

    public static void main(String[] args) {
		try {
			if (args.length == 1 && args[0].equals("-libname")) {
				System.out.println(Hunspell.libName());
			
			} else { 

				try {
					frame = new JFrame("Testing Hunspell");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					label = new JLabel("Output from Hunspell...");
					output = "";
					frame.getContentPane().add(label);
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					frame = null;
				}

				System.err.println("Loading Hunspell");
				String dir = ".";
				if (System.getProperties().containsKey("root")) {
					dir = System.getProperty("root");
				}				
			
				Hunspell.Dictionary d = Hunspell.getInstance().getDictionary(dir+"/dict/de_DE/de_DE");
			
						
				String words[] = {"Test", "Hest", "guest", "ombudsmandshat", "ombudsman",
								  "ymerfest", "grøftegraver", "hængeplante", "garageport", "postbil", "huskop",
								  "arne", "pladderballe", "Doctor", "Leo", "Lummerkrog",
								  "Barnevognsbrand","barnehovedbeklædning"};
			
				for (int i=0;i<words.length;i++) {
				
					for (int j=0;j<3;j++) {
						String word = words[i];
						if (d.misspelled(word)) {
							print("misspelled: "+word);
							Iterator<String> itr = d.suggest(word).iterator();
							print("\tTry:");
							while (itr.hasNext()) {
								String s = itr.next();
								print(" "+s);
							}	
							println("");
						} else {
							println("ok: "+word);		
						}
					}
				}
			}
		
		} catch (Exception e) {
			println("Failed: "+e);
		}
    }
}

