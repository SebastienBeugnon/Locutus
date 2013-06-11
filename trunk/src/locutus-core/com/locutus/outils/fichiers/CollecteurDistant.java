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
package com.locutus.outils.fichiers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.locutus.modeles.OptionsProgramme;

/**
 * Cette classe est lancée au démarrage de Locutus fonctionnant à distance (Smb
 * actuellement) pour remplir les dossiers locaux avec les ressources au cas où
 * le stockage à distance subit des dégâts entrainant la perte des ressources.
 * 
 * @author Sebastien Beugnon
 * 
 * 
 */
public class CollecteurDistant {

	private static CollecteurDistant ccd = null;

	private CollecteurDistant() {

	}

	/**
	 * @return une Instance Collecteur Distant.
	 */
	public static CollecteurDistant getInstance() {
		if (ccd == null)
			ccd = new CollecteurDistant();

		return ccd;
	}

	/**
	 * 
	 */
	public final void recuperation() {
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				// Recopie du dossier RESSOURCES
				try {
					TraitementSambaFichier.copierFichierVersLocal(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant(),
							"ressources", System.getProperty("user.dir"), null,
							false);
				} catch (IOException e) {
					System.err
							.println("Erreur du collecteur de données. IOException relevé.");
					e.printStackTrace();
				}
				// Recopie du dossier LOCAL
				try {
					TraitementSambaFichier.copierFichierVersLocal(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant(),
							"local", System.getProperty("user.dir"), null,
							false);
				} catch (IOException e) {
					System.err
							.println("Erreur du collecteur de données. IOException relevé.");
					e.printStackTrace();
				}
			}
		});
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(th);
		es.shutdown();

	}
}
