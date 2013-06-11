package com.locutus.outils;

import javax.swing.UIManager;

/**
 * A few utilities that simplify using windows in Swing. 1998-99 Marty Hall,
 * http://www.apl.jhu.edu/~hall/java/
 */

public class WindowUtilities {

	/**
	 * Tell system to use native look and feel, as in previous releases. Metal
	 * (Java) LAF is the default otherwise.
	 */

	public static void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
	}

	/**
 * 
 */
	public static void setJavaLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting Java LAF: " + e);
		}
	}

	/**
 * 
 */
	public static void setMotifLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println("Error setting Motif LAF: " + e);
		}
	}

	/**
 * 
 */
	public static void setMetalLookAndFeel() {
		try {

			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			System.out.println("Erro setting Metal LAF : " + e);
		}
	}

	/**
 * 
 */
	public static void setNimbusLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus");
		} catch (Exception e) {
			System.out.println("Erro setting Nimbus LAF : " + e);
		}
	}

	/**
 * 
 */
	public static void setSynthLookAndFeel() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.synth");
		} catch (Exception e) {
			System.out.println("Erro setting synth LAF : " + e);
		}
	}
}