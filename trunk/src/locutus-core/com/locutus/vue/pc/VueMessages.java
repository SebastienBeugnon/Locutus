/**
 * Copyright (C) 2013
	Sébastien Beugnon <sebastien.beugnon.pro[at]gmail.com>,
	E.E.A.P. Coste Rousse <http://www.adages.net/costerousse/>


    This file is part of Locutus.

    Locutus is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Locutus is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Locutus.  If not, see <http://www.gnu.org/licenses/>.

 *
 */
package com.locutus.vue.pc;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.locutus.modeles.enums.FormatImage;
import com.locutus.modeles.enums.TypeRessource;

/**
 * 
 * @author BEUGNON Sebastien
 * 
 */
public class VueMessages {
	/**
	 * 
	 */
	public static final int YES_OPTION = JOptionPane.YES_OPTION;
	/**
	 * 
	 */
	public static final int YES_NO_OPTION = JOptionPane.YES_NO_OPTION;
	/**
	 * 
	 */
	public static final int YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION;

	/**
	 * 
	 */
	public static final int OK_OPTION = JOptionPane.OK_OPTION;
	/**
	 * 
	 */
	public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
	/**
	 * 
	 */
	public static final int OK_CANCEL_OPTION = JOptionPane.OK_CANCEL_OPTION;

	/**
	 * 
	 */
	public static final int NO_OPTION = JOptionPane.NO_OPTION;

	/**
	 * @param av
	 * @param message
	 */
	public static void afficherMessageDansVue(VuePanneau av, String message) {
		JOptionPane.showMessageDialog(av, message);
	}

	/**
	 * @param av
	 * @param message
	 * @return YES_OPTION / NO_OPTION / CANCEL_OPTION
	 */
	public static int afficherQuestionDansVue(VuePanneau av, String message) {
		return JOptionPane.showConfirmDialog(av, message);
	}

	/**
	 * @return une instance File représentant un dossier
	 */
	public static File choisirDossier() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "dossiers";
			}

			@Override
			public boolean accept(File arg0) {
				if (arg0.isDirectory())
					return true;
				else
					return false;
			}
		});
		int a = jfc.showOpenDialog(null);

		if (a == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile() != null && jfc.getSelectedFile().exists()
					&& jfc.getSelectedFile().isDirectory()) {
				return jfc.getSelectedFile();
			} else if (jfc.getSelectedFile() != null
					&& !jfc.getSelectedFile().exists()) {
				jfc.getSelectedFile().mkdirs();
				return jfc.getSelectedFile();
			} else {
				if (jfc.getCurrentDirectory() != null
						&& jfc.getCurrentDirectory().exists()) {
					return jfc.getCurrentDirectory();
				}
			}
		}
		return null;
	}

	/**
	 * @param pictogrammes
	 * @return une instance File d'un fichier ressource de TypeRessource
	 */
	public static File choisirFichier(TypeRessource pictogrammes) {
		JFileChooser jfc = new JFileChooser();
		FileFilter ff = null;
		if (pictogrammes == TypeRessource.images
				|| pictogrammes == TypeRessource.photographies
				|| pictogrammes == TypeRessource.pictogrammes) {
			ff = new FileFilter() {

				@Override
				public String getDescription() {
					return "images, photographies, pictogrammes (.png, .jpg, .jpeg)";
				}

				@Override
				public boolean accept(File arg0) {
					if (arg0.isDirectory())
						return true;
					else {

						int i = 0;
						while (i < FormatImage.values().length) {
							if (arg0.getName().endsWith(
									"." + FormatImage.values()[i]))
								return true;
							i++;
						}

						return false;
					}
				}
			};
		} else if (pictogrammes == TypeRessource.sons) {
			ff = new FileFilter() {

				@Override
				public String getDescription() {
					return "sons pour voix (.mp3)";
				}

				@Override
				public boolean accept(File arg0) {
					if (arg0.isDirectory() || arg0.getName().endsWith(".mp3"))
						return true;
					else
						return false;
				}
			};
		}

		jfc.setFileFilter(ff);
		int a = jfc.showOpenDialog(null);
		if (a == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile() != null && jfc.getSelectedFile().exists())
				return jfc.getSelectedFile();
		}
		return null;
	}

	/**
	 * @param string
	 * @return une instance File d'un fichier assimilé à un graphe
	 */
	public static File choisirFichier(String string) {
		if (string.equals("graphe")) {
			JFileChooser jfc = new JFileChooser();
			FileFilter ff = new FileFilter() {

				@Override
				public String getDescription() {
					return "graphes en xml (.xgml)";
				}

				@Override
				public boolean accept(File arg0) {
					return arg0.isDirectory()
							|| arg0.getName().endsWith(".xgml");
				}

			};
			jfc.setFileFilter(ff);
			int a = jfc.showOpenDialog(null);
			if (a == JFileChooser.APPROVE_OPTION) {
				if (jfc.getSelectedFile() != null
						&& jfc.getSelectedFile().exists())
					return jfc.getSelectedFile();
			}
			return null;
		}
		return null;

	}

	/**
	 * @param fl
	 * @return une instance File de l'endroit où on souhaite copier le graphe.
	 */
	public static File sauvegarderGrapheDans() {
		JFileChooser jfc = new JFileChooser();
		jfc.setSelectedFile(new File("graphe.xgml"));
		int a = jfc.showSaveDialog(null);
		if (a == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile() != null)
				return jfc.getSelectedFile();
		}
		return null;

	}

}
