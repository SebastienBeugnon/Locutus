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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Concept;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.modeles.graphes.DiGraph;
import com.locutus.modeles.graphes.Node;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.outils.son.GestionnaireSon;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.pratique.RaccourcisPratique;
import com.locutus.vue.pc.pratique.VuePratique;

/**
 * @author Sebastien Beugnon
 * 
 */
public abstract class ModulePratique extends ModuleInterne {

	/**
	 * 
	 */
	private Node<Concept> racine;

	/**
	 * 
	 */
	private Node<Concept> curseur;
	/**
	 * 
	 */
	private ModulePratiqueSelection mp;
	/**
	 * 
	 */
	private GestionnaireSon gs;
	/**
	 * 
	 */
	private List<Node<Concept>> liste;
	/**
	 * 
	 */
	private Map<Node<Concept>, BufferedImage> image1;
	/**
	 * 
	 */
	private RaccourcisPratique rc;

	/**
	 * 
	 */
	private Node<Concept> retour;

	/**
	 * @param mp
	 * @param modulePratiqueSelection
	 */
	public ModulePratique(ModulePrincipal mp,
			ModulePratiqueSelection modulePratiqueSelection) {
		super(mp);
		this.mp = modulePratiqueSelection;
		this.image1 = new HashMap<Node<Concept>, BufferedImage>();
		this.liste = new ArrayList<Node<Concept>>();
		this.rc = new RaccourcisPratique(this);
		this.gs = new GestionnaireSon();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#init()
	 */
	@Override
	public void init() {
		if (getGraphe().getNodeByObject(
				new Concept("origine", "origine (graphe)")) != null
				|| getGraphe().getNodeByObject(
						new Concept("racine", "racine (graphe)")) != null) {

			if (getGraphe().getNodeByObject(
					new Concept("origine", "origine (graphe)")) != null) {
				this.setRacine(getGraphe().getNodeByObject(
						new Concept("origine", "origine (graphe)")));
			} else if (getGraphe().getNodeByObject(
					new Concept("racine", "racine (graphe)")) != null) {
				this.setRacine(getGraphe().getNodeByObject(
						new Concept("racine", "racine (graphe)")));
			} else {
				VueMessages
						.afficherMessageDansVue(getVue(),
								"Arborescence erronée ! Impossible de trouver le concept : racine ou origine");
				getModulePrincipal().changerModuleCourant(
						getModulePratiqueSelection());
			}

			retour = new Node<Concept>(new Concept("Retour", "retour (graphe)"));
			this.image1.put(getRetour(), UtilisationFichier.chargerImage(
					TypeRessource.pictogrammes, getRetour().get(),
					getUtilisateur()));
			this.getGestionnaireSon().ajouterSonConcept(
					getRetour().get(),
					UtilisationFichier.chargerSon(getRetour().get(),
							getModulePratiqueSelection().getUtilisateur()));

			Iterator<Node<Concept>> it = getGraphe().getNodes().iterator();

			while (it.hasNext()) {
				Node<Concept> local = it.next();

				if (UtilisationFichier.chargerImage(TypeRessource.pictogrammes,
						local.get(), getUtilisateur()) != null)
					this.image1.put(local, UtilisationFichier.chargerImage(
							TypeRessource.pictogrammes, local.get(),
							getUtilisateur()));
				else {
					VueMessages.afficherMessageDansVue(getVue(),
							"Il manque le pictogramme pour :" + local.get());
					getModulePrincipal().changerModuleCourant(
							getModulePratiqueSelection());
				}

				if (UtilisationFichier
						.chargerSon(local.get(), getUtilisateur()) != null)
					this.gs.ajouterSonConcept(local.get(), UtilisationFichier
							.chargerSon(local.get(), getUtilisateur()));
				else {
					VueMessages.afficherMessageDansVue(getVue(),
							"Il manque le son pour :" + local.get());
					getModulePrincipal().changerModuleCourant(
							getModulePratiqueSelection());
				}

			}

		}
	}

	/**
	 * 
	 * @return le noeud de retour.
	 */
	public Node<Concept> getRetour() {
		return this.retour;
	}

	/**
	 * @return le noeud servant de racine.
	 */
	public Node<Concept> getRacine() {
		return this.racine;
	}

	/**
	 * 
	 * @param nodeByObject
	 */
	private void setRacine(Node<Concept> nodeByObject) {
		this.racine = nodeByObject;
	}

	/**
	 * 
	 * @return
	 */
	private DiGraph<Concept> getGraphe() {
		return getModulePratiqueSelection().getGraphe();
	}

	/**
	 * 
	 * @return l'utilisateur courant.
	 */
	public Utilisateur getUtilisateur() {
		return getModulePratiqueSelection().getUtilisateur();
	}

	/**
	 * 
	 */
	public void chargerModulePratiqueSelection() {
		getModulePrincipal().getVue()
				.removeKeyListener(getRaccourcisPratique());
		getModulePrincipal().changerModuleCourant(getModulePratiqueSelection());
	}

	/**
	 * 
	 * @return le module de selection de pratique.
	 */
	public ModulePratiqueSelection getModulePratiqueSelection() {
		return this.mp;
	}

	/**
	 * @return le gestionnaire de son.
	 */
	public GestionnaireSon getGestionnaireSon() {
		return gs;

	}

	/**
	 * @param node
	 * @return l'image liée au noeud node.
	 */
	public BufferedImage getImage(Node<Concept> node) {
		return this.image1.get(node);
	}

	/**
	 * @return la liste des Concepts choisis dans l'arborescence.
	 */
	public List<Node<Concept>> getChoix() {
		return liste;
	}

	/**
	 * @return l'écouteur des raccourcis pratique
	 */
	public RaccourcisPratique getRaccourcisPratique() {
		return this.rc;
	}

	/**
	 * @param rc
	 */
	public void setRaccourcisPratique(RaccourcisPratique rc) {
		this.rc = rc;
	}

	/**
	 * @param concept
	 */
	public void selectionConcept(Node<Concept> concept) {
		if (concept != null) {
			if (concept.getStraightSuccessors().size() == 0
					&& !concept.equals(getRetour())) {
				getChoix().add(concept);

				getVue().jouerListeSelection();

				getChoix().clear();

				setCurseur(getRacine());
				List<Node<Concept>> liste = new ArrayList<Node<Concept>>();
				liste.addAll(getRacine().getStraightSuccessors());
				getVue().afficherChoix(liste);

			} else {
				List<Node<Concept>> liste = new ArrayList<Node<Concept>>();
				jouerSon(concept);
				if (!concept.equals(getRetour())) {
					getChoix().add(concept);
					setCurseur(concept);

					liste.addAll(getCurseur().getStraightSuccessors());

					liste.add(getRetour());

				} else {
					getChoix().remove(getChoix().size() - 1);
					if (getChoix().size() == 0) {
						setCurseur(getRacine());
						liste.addAll(getCurseur().getStraightSuccessors());
					} else {
						setCurseur(getChoix().get(getChoix().size() - 1));
						liste.addAll(getCurseur().getStraightSuccessors());
						liste.add(getRetour());

					}
				}
				getVue().afficherChoix(liste);
			}

		}

	}

	protected Node<Concept> getCurseur() {
		return this.curseur;
	}

	/**
	 * 
	 * @param racine2
	 */
	protected void setCurseur(Node<Concept> racine2) {
		this.curseur = racine2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleInterne#getVue()
	 */
	@Override
	public VuePratique getVue() {
		return (VuePratique) super.getVue();
	}

	/**
	 * 
	 * @param node
	 */
	public void jouerSon(Node<Concept> node) {
		this.getGestionnaireSon().jouerSonConcept(node.get());
		while (!this.getGestionnaireSon().estTermine())
			;
	}

	/**
	 * 
	 */
	public void jouerElementSonore() {
		try {
			getGestionnaireSon().jouerElementSonore(
					UtilisationFichier.chargerGui("bip.mp3"));
		} catch (IOException e) {
			System.err.println("Chargement du but sonore impossible"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

}
