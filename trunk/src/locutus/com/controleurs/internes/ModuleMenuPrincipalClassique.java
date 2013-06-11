/**
 * Copyright (C) 2013
	S�bastien Beugnon <sebastien.beugnon.pro[at]gmail.com>,
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
 * Fichier : ModuleInterne.java<br/>
 * Email : S�bastien Beugnon - sebastien.beugnon.pro[at]gmail.com <br/>
 * Projet : Locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) D�velopp� pour l'Etablissement Educatif pour Adolescents
 * Poly-handicap�s de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Prad�s-Le-Lez 34730 <br/>
 * <br/>
 * Les modules internes consistent � g�rer une sc�ne particuli�re (un menu, un
 * d�filement, etc). Ils seront donner au Module Principal qui s'occupera de les
 * afficher.<br/>
 * 
 * 
 * @author Copyright (C) 2013 - E.E.A.P. CosteRousse, Beugnon Sebastien (Chef de
 *         projet, Analyste, D�veloppeur)
 */
package com.controleurs.internes;

import com.controleurs.internes.utilisateur.ModuleUtilisateurCreation;
import com.controleurs.internes.utilisateur.ModuleUtilisateurSelection;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.ModuleMenuPrincipal;
import com.locutus.controleurs.internes.autres.ModuleGestionOptionsProgramme;
import com.locutus.controleurs.internes.autres.ModulePropos;
import com.vue.pc.VueMenuPrincipalClassique;

/**
 * @author Sebastien Beugnon
 * 
 */
public class ModuleMenuPrincipalClassique extends ModuleMenuPrincipal {

	/**
	 * 
	 * @param mp
	 */
	public ModuleMenuPrincipalClassique(ModulePrincipal mp) {
		super(mp);
		init();
	}

	@Override
	public void init() {
		this.setVue(new VueMenuPrincipalClassique(this));
	}

	/**
	 * Accesseur
	 * 
	 * @return La Vue du menu principal.
	 */
	public VueMenuPrincipalClassique getVue() {
		return ((VueMenuPrincipalClassique) super.getVue());
	}

	/**
	 * 
	 */
	public void chargerModuleCreation() {
		this.getModulePrincipal().changerModuleCourant(
				new ModuleUtilisateurCreation(getModulePrincipal(), this));
	}

	/**
	 * 
	 */
	public void chargerModuleSelection() {
		this.getModulePrincipal()
				.changerModuleCourant(
						new ModuleUtilisateurSelection(this
								.getModulePrincipal(), this));
	}

	/**
	 * 
	 */
	public void chargerModuleOptionsProgramme() {
		this.getModulePrincipal().changerModuleCourant(
				new ModuleGestionOptionsProgramme(this.getModulePrincipal(),
						this));
	}

	/**
	 * 
	 */
	public void chargerModulePropos() {
		this.getModulePrincipal().changerModuleCourant(
				new ModulePropos(this.getModulePrincipal(), this));
	}

	/**
	 * 
	 */
	public void quitter() {
		System.exit(0);
	}

}
