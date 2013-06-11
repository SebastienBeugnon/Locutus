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
package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

import com.controleurs.internes.ModuleMenuUtilisateurMono;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Utilisateur;
import com.locutus.outils.fichiers.TraitementFichier;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;

/**
 * 
 * Fichier : Launcher.java<br/>
 * Email : Sébastien Beugnon - sebastien.beugnon.pro@gmail.com<br/>
 * Projet : Locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) Développé pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapés de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Pradès-Le-Lez 34730
 * 
 * @author Beugnon Sebastien - Chef de projet, Analyste, Développeur
 * 
 */

public class LauncherMono {
	/**
	 * Méthode principale servant au lancement de programme.
	 * 
	 * @param args
	 *            les arguments lors de l'exécution du programme.
	 */
	public static void main(String[] args) {
		ModulePrincipal MP = new ModulePrincipal("LAPIS",
				ModulePrincipal.FLAG_MONO_USER);
		try {
			MP.getVue().setIconImage(
					ImageIO.read(new File("ressources" + File.separator + "gui"
							+ File.separator + "icone.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (UtilisationFichier.chargerListeConcepts() != null) {
			if (TraitementFichier.existeFichier(System.getProperty("user.dir"),
					"utilisateur.bin")) {
				File fl;
				try {
					fl = TraitementFichier.chargerFichier(
							System.getProperty("user.dir"), "utilisateur.bin");

					ObjectInputStream in;
					try {
						in = new ObjectInputStream(new FileInputStream(fl));
						Utilisateur ut;
						try {
							ut = (Utilisateur) in.readObject();

							MP.changerModuleCourant(new ModuleMenuUtilisateurMono(
									MP, ut));
						} catch (ClassNotFoundException e) {
							VueMessages.afficherMessageDansVue(null,
									"Développeur : Classes incompatibles.");
							System.err
									.println("Développeur : Classes incompatibles.");
							e.printStackTrace();
							System.exit(1);
						}

					} catch (IOException e) {
						VueMessages.afficherMessageDansVue(null,
								"Problème de chargement du fichier.");
						System.err
								.println("Problème de chargement du fichier.");
						e.printStackTrace();
						System.exit(1);
					}

				} catch (FileNotFoundException e) {
					VueMessages.afficherMessageDansVue(null,
							"Fichier introuvable.");
					System.err.println("Fichier introuvable.");
					e.printStackTrace();
					System.exit(1);
				}

			} else {
				VueMessages.afficherMessageDansVue(null,
						"Fichier pour mono utilisateur introuvable.");
				System.exit(1);
			}
		} else {
			VueMessages.afficherMessageDansVue(null,
					"Erreur au chargement de la liste des Concepts.");
			System.exit(1);
		}
	}
}
