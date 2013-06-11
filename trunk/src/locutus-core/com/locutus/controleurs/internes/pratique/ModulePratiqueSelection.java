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

package com.locutus.controleurs.internes.pratique;

import java.io.File;
import java.util.Iterator;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.controleurs.internes.ModuleMenuUtilisateur;
import com.locutus.exceptions.GraphConversionException;
import com.locutus.modeles.Concept;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.modeles.graphes.DiGraph;
import com.locutus.modeles.graphes.Node;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.pratique.VuePratiqueSelection;

/**
 * @author Sebastien Beugnon
 * 
 */
public final class ModulePratiqueSelection extends ModuleInterne {

	/**
	 * 
	 */
	private DiGraph<Concept> graphe;
	/**
	 * 
	 */
	private ModuleMenuUtilisateur mmu;

	/**
	 * @return Le module du menu utilisateur.
	 */
	public ModuleMenuUtilisateur getModuleMenuUtilisateur() {
		return this.mmu;
	}

	/**
	 * @param mp
	 * @param mmu
	 */
	public ModulePratiqueSelection(ModulePrincipal mp, ModuleMenuUtilisateur mmu) {
		super(mp);
		this.mmu = mmu;
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#init()
	 */
	@Override
	public void init() {
		this.setVue(new VuePratiqueSelection(this));

		try {
			if (UtilisationFichier.existeGrapheUtilisateur(getUtilisateur())) {
				this.setGraphe(UtilisationFichier
						.chargerGrapheUtilisateur(getUtilisateur()));
				this.getVue().chargerStatistiques(getGraphe());

			}
		} catch (GraphConversionException e) {
			e.printStackTrace();
		}
		this.getVue().lockButtons();
	}

	/**
	 * 
	 */
	public void chargerMenuUtilisateur() {
		this.getModulePrincipal().changerModuleCourant(
				getModuleMenuUtilisateur());
	}

	/**
	 * 
	 */
	public void chargerPratiqueParSouris() {
		this.getModulePrincipal().changerModuleCourant(
				new ModulePratiqueSouris(getModulePrincipal(), this));
	}

	/**
	 * 
	 */
	public void chargerPratiqueParDefilement() {
		this.getModulePrincipal().changerModuleCourant(
				new ModulePratiqueDefilement(getModulePrincipal(), this));
	}

	/**
	 * 
	 * @return le graphe du module pratique.
	 */
	public DiGraph<Concept> getGraphe() {
		return this.graphe;
	}

	/**
	 * @return l'utilisateur de cette session.
	 */
	public Utilisateur getUtilisateur() {
		return this.getModuleMenuUtilisateur().getUtilisateur();
	}

	/**
	 * @param fl
	 */
	public void lancerChargementNouveauGraphe(File fl) {
		if (UtilisationFichier.enregistrerGrapheVenantDe(getUtilisateur(), fl)) {
			VueMessages.afficherMessageDansVue(getVue(),
					"Graphe de communication copiée !");
			try {
				this.setGraphe(UtilisationFichier
						.chargerGrapheUtilisateur(getUtilisateur()));
				if (this.getGraphe() != null && this.grapheUtilisable()
						&& this.getGraphe().orderOfGraph() > 0) {
					this.getVue().lockButtons();
					this.getVue().chargerStatistiques(getGraphe());
				} else {
					VueMessages
							.afficherMessageDansVue(getVue(),
									"Problème à la recopie du graphe dans le programme !");
				}
			} catch (GraphConversionException e) {
				VueMessages.afficherMessageDansVue(getVue(), e.getMessage());
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * @param fl
	 */
	public void lancerExportationGraphe(File fl) {
		if (UtilisationFichier.enregistrerGrapheVers(getUtilisateur(), fl)) {
			VueMessages.afficherMessageDansVue(getVue(),
					"Graphe de communication de "
							+ getUtilisateur().getPrenom() + " exporté !");
		} else {
			VueMessages.afficherMessageDansVue(getVue(),
					"Problème à l'exporation du graphe !");
		}
	}

	/**
	 * 
	 * @param chargerGrapheUtilisateur
	 */
	private void setGraphe(DiGraph<Concept> chargerGrapheUtilisateur) {
		// TODO Auto-generated method stub
		this.graphe = chargerGrapheUtilisateur;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleInterne#getVue()
	 */
	@Override
	public VuePratiqueSelection getVue() {
		return (VuePratiqueSelection) super.getVue();
	}

	/**
	 * 
	 * @return True si le graphe est utilisable comme arborescence de
	 *         communication, sinon faux.
	 */
	public boolean grapheUtilisable() {
		Iterator<Node<Concept>> it = this.getGraphe().getNodes().iterator();
		boolean defautTous = true;

		if (this.getGraphe().getNodeByObject(
				new Concept("origine", "origine (graphe)")) != null) {
			while (it.hasNext() && defautTous) {
				Concept local = it.next().get();
				if (!UtilisationFichier.existeRessourceImageParDefaut(
						TypeRessource.pictogrammes, local)
						|| !UtilisationFichier.existeSonPour(getUtilisateur()
								.getVoix(), local)) {
					System.out.println(local.getNomFichier());
					defautTous = false;
				}
			}
		} else {
			return false;
		}

		return defautTous;
	}
}
