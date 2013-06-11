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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.controleurs.internes.ModuleMenuPrincipalClassique;
import com.controleurs.internes.ModuleMenuUtilisateurClassique;
import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.exceptions.modeles.UtilisateurException;
import com.locutus.modeles.Utilisateur;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.vue.pc.utilisateur.selection.VueUtilisateurSelection;

/**
 * 
 * @author Sébastien Beugnon
 * 
 */
public final class ModuleUtilisateurSelection extends ModuleInterne {

	/**
	 * 
	 */
	private ModuleMenuPrincipalClassique mmp;

	/**
	 * 
	 * @param mp
	 * @param mmp
	 */
	public ModuleUtilisateurSelection(ModulePrincipal mp,
			ModuleMenuPrincipalClassique mmp) {
		super(mp);
		// TODO Auto-generated constructor stub
		this.mmp = mmp;
		this.getModulePrincipal().chargerListeUtilisateur();
		init();
	}

	@Override
	public void init() {
		this.setVue(new VueUtilisateurSelection(this));
	}

	/**
	 * 
	 */
	public void chargerMenuPrincipal() {
		super.getModulePrincipal().changerModuleCourant(
				getModuleMenuPrincipal());
	}

	/**
	 * 
	 * @return le module du menu principal lié à ce module.
	 */
	public ModuleMenuPrincipalClassique getModuleMenuPrincipal() {
		// TODO Auto-generated method stub
		return this.mmp;
	}

	/**
	 * 
	 */
	@Override
	public VueUtilisateurSelection getVue() {
		return (VueUtilisateurSelection) super.getVue();
	}

	/**
	 * 
	 * @param ut
	 */
	public void chargerUtilisateur(Utilisateur ut) {
		// TODO Auto-generated method stub
		this.getModulePrincipal().changerModuleCourant(
				new ModuleMenuUtilisateurClassique(this.getModulePrincipal(),
						this.getModuleMenuPrincipal(), ut));
	}

	/**
	 * 
	 * @param utilisateur
	 */
	public void supprimerUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub

		int a = VueMessages.afficherQuestionDansVue(getVue(),
				"Voulez-vous supprimer l'utilisateur : " + utilisateur.getNom()
						+ " " + utilisateur.getPrenom() + " ?");

		if (a == VueMessages.YES_OPTION) {
			try {
				UtilisationFichier.supUtilisateur(utilisateur);
				this.getModulePrincipal().chargerListeUtilisateur();
				this.getVue().recharger(
						UtilisationFichier.chargerListeUtilisateur());
				VueMessages.afficherMessageDansVue(getVue(),
						"Utilisateur supprimé !");
			} catch (UtilisateurException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param debutDuNom
	 *            le debut du nom recherche
	 * @return une collection d'utilisateur commencant par le parametre
	 */
	public List<Utilisateur> rechercheParNom(String debutDuNom) {
		this.getModulePrincipal().chargerListeUtilisateur();
		List<Utilisateur> li = UtilisationFichier.chargerListeUtilisateur();
		if (debutDuNom.trim().equals(""))
			return li;

		List<Utilisateur> le = new ArrayList<Utilisateur>();
		Iterator<Utilisateur> it = li.iterator();
		while (it.hasNext()) {
			Utilisateur local = it.next();
			int i = 0;
			while (i < debutDuNom.length()
					&& debutDuNom.length() <= local.getNom().length()
					&& Character.toLowerCase(debutDuNom.charAt(i)) == Character
							.toLowerCase(local.getNom().charAt(i)))
				i++;

			if (i == debutDuNom.length())
				le.add(local);

		}
		return le;
	}
}
