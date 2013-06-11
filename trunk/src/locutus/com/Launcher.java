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

 **/

package com;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.controleurs.internes.ModuleMenuPrincipalClassique;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;

/**
 * 
 * Fichier : Launcher.java<br/>
 * Email : Sébastien Beugnon - sebastien.beugnon.pro@gmail.com<br/>
 * Projet : locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) Développé pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapés de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Pradès-Le-Lez 34730
 * 
 * @author Beugnon Sebastien - Chef de projet, Analyste, Développeur
 * 
 */

public class Launcher {
	/**
	 * Méthode principale servant au lancement de programme.
	 * 
	 * @param args
	 *            les arguments lors de l'exécution du programme.
	 */
	public static void main(String[] args) {
		ModulePrincipal MP = new ModulePrincipal("Locutus");
		try {
			MP.getVue().setIconImage(
					ImageIO.read(new File("ressources" + File.separator + "gui"
							+ File.separator + "icone.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (UtilisationFichier.chargerListeConcepts() != null) {

			MP.changerModuleCourant(new ModuleMenuPrincipalClassique(MP));
		} else {
			VueMessages.afficherMessageDansVue(null,
					"Erreur au chargement de la liste des concepts.");
			System.exit(1);
		}
	}
}
