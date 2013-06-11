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
import java.io.IOException;

import javax.imageio.ImageIO;

import com.controleurs.ModuleCollection;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;

/**
 * 
 * @author Beugnon
 * 
 */

public class LauncherConcept {
	/**
	 * Méthode principale de lancement de programme
	 * 
	 * @param args
	 */
	public static void main(String[] args) {


		ModulePrincipal MP = new ModulePrincipal(
				"Locutus - outil de gestion des Concepts",
				ModulePrincipal.FLAG_NOT_FULLSCREEN,
				ModulePrincipal.FLAG_IGNORE_USERS,
				ModulePrincipal.FLAG_IGNORE_VOICES);
		if (UtilisationFichier.chargerListeConcepts() != null) {
			try {
				MP.getVue()
						.setIconImage(
								ImageIO.read(new File("ressources"
										+ File.separator + "gui"
										+ File.separator + "icone-concept.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			MP.changerModuleCourant(new ModuleCollection(MP));
		} else {
			VueMessages.afficherMessageDansVue(null,
					"Erreur au chargement de la liste des Concepts.");
			System.exit(1);
		}

	}
}
