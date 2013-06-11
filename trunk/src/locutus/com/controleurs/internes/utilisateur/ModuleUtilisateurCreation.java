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
package com.controleurs.internes.utilisateur;

import java.io.IOException;

import com.controleurs.internes.ModuleMenuPrincipalClassique;
import com.controleurs.internes.ModuleMenuUtilisateurClassique;
import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.Voix;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.cadres.Damier;
import com.vue.pc.utilisateur.creation.VueUtilisateurCreation;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public final class ModuleUtilisateurCreation extends ModuleInterne {

	/**
	 * 
	 */
	private ModuleMenuPrincipalClassique mmp;

	/**
	 * Accesseur
	 * 
	 * @return Le ModuleMenuPrincipal.
	 */
	public ModuleMenuPrincipalClassique getModuleMenuPrincipal() {
		return this.mmp;
	}

	/**
	 * 
	 * @param mp
	 * @param mmp
	 */
	public ModuleUtilisateurCreation(ModulePrincipal mp,
			ModuleMenuPrincipalClassique mmp) {

		super(mp);
		this.mmp = mmp;
		init();
	}

	@Override
	/**
	 * 
	 */
	public void init() {
		super.setVue(new VueUtilisateurCreation(this));
	}

	/**
	 * 
	 */
	public void retourMenuPrincipal() {
		super.getModulePrincipal().changerModuleCourant(this.mmp);
	}

	/**
	 * Lance la création de l'utilisateur avec les parametres suivants si
	 * l'utilisateur est creee alors on va dans son menu sinon un message
	 * d'alerte apparait
	 * 
	 * @param nom
	 *            le nom du futur utilisateur
	 * @param prenom
	 *            le prenom du futur utilisateur
	 * @param voice
	 *            le choix de voix du futur utilisateur
	 * @param ou
	 *            le choix de paramètre du futur utilisateur
	 */
	public void lancerCreation(String nom, String prenom, Voix voice,
			OptionsUtilisateur ou) {
		Utilisateur ut = null;
		ou.addCadre(new Damier(20));
		ou.setBipDeChangement(false);
		ut = UtilisationFichier.addUtilisateur(nom, prenom, voice, ou);

		super.getModulePrincipal().changerModuleCourant(
				new ModuleMenuUtilisateurClassique(getModulePrincipal(), this
						.getModuleMenuPrincipal(), ut));
		try {
			UtilisationFichier.creerDossiersRessourcesUtilisateur(ut);
		} catch (IOException e) {
			VueMessages
					.afficherMessageDansVue(getVue(),
							"Erreur à la création des dossiers pour les ressources de l'utilisateur");
			e.printStackTrace();
		}

	}

}
