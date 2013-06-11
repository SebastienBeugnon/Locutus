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
package com.locutus.starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.locutus.exceptions.modeles.OptionsUtilisateurException;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.modeles.enums.TypeStatut;
import com.locutus.outils.fichiers.TraitementSambaFichier;
import com.locutus.outils.fichiers.UtilisationFichier;

/**
 * @author Sebastien Beugnon
 * 
 */
public class StarterUtilisateur {
	/**
	 * @param args
	 * @throws OptionsUtilisateurException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void starter() throws OptionsUtilisateurException,
			IOException {

		List<Utilisateur> liste = new ArrayList<Utilisateur>();
		Utilisateur ut = new Utilisateur("Test", "Marius", new Voix("homme1",
				TypeGenre.HOMME), new OptionsUtilisateur(5000.0f, 2));
		liste.add(ut);

		Collections.sort(liste);

		OptionsProgramme.initialisation();
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			File fl = new File(OptionsProgramme.getOptionsCourantes()
					.getCheminCourant() + File.separator + "utilisateurs.bin");
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(fl));
			oos.writeObject(liste);
			oos.close();

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			TraitementSambaFichier.enregistrerFichier(OptionsProgramme
					.getOptionsCourantes().getAuthentificateurSMB(),
					OptionsProgramme.getOptionsCourantes().getCheminDistant(),
					OptionsProgramme.getOptionsCourantes().getCheminCourant(),
					"utilisateurs.bin", (Serializable) liste);

		}

		UtilisationFichier.creerDossiersRessourcesUtilisateur(ut);
	}
}
