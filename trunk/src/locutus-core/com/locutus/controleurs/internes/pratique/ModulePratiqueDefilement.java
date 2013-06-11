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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Concept;
import com.locutus.modeles.graphes.Node;
import com.locutus.vue.pc.pratique.ClicPratiqueDefilement;
import com.locutus.vue.pc.pratique.VuePratiqueDefilement;

/**
 * ModulePratiqueDefilement, est un contrôleur servant à afficher en fonction des paramètres de l'utilisateur
 * @author Sebastien Beugnon
 * 
 */
public final class ModulePratiqueDefilement extends ModulePratique {

	/**
	 * position du prochain pictogramme à récupérer
	 */
	private int positionDansListeCourante;
	/**
	 * position du curseur à l'affichage (0 - à nombre d'images de l'utilisateur -1)
	 */
	private int positionCurseur;
	/**
	 * 
	 */
	private List<Node<Concept>> listeCourante;

	/**
	 * 
	 */
	private List<Node<Concept>> l;
	/**
	 * 
	 */
	private Timer time;
	/**
	 * 
	 */
	private TimerTask task;

	/**
	 * 
	 */
	private boolean start;
	/**
	 * 
	 */
	private ClicPratiqueDefilement cp;

	private Semaphore sem;

	/**
	 * 
	 * @param mp
	 * @param modulePratiqueSelection
	 */
	public ModulePratiqueDefilement(ModulePrincipal mp,
			ModulePratiqueSelection modulePratiqueSelection) {
		super(mp, modulePratiqueSelection);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#init()
	 */
	@Override
	public void init() {
		super.init();
		sem = new Semaphore(1);
		super.setVue(new VuePratiqueDefilement(this));

		this.cp = new ClicPratiqueDefilement(getVue());
		getModulePrincipal().getVue().addMouseListener(this.cp);

		setCurseur(getRacine());
		this.listeCourante = getCurseur().getStraightSuccessors();
		time = new Timer();
		this.start = true;
		addTask();
	}

	@Override
	public void chargerModulePratiqueSelection() {

		supTask();
		this.time.cancel();
		super.chargerModulePratiqueSelection();
		getModulePrincipal().getVue().removeMouseListener(this.cp);

	}

	@Override
	public void selectionConcept(Node<Concept> concept) {
		if (concept != null) {
			supTask();

			this.listeCourante = new ArrayList<Node<Concept>>();

			if (concept.getStraightSuccessors().size() == 0
					&& !concept.equals(getRetour())) {
				// Le concept n'a pas de descedants, on joue donc la série de
				// choix.
				if (!getChoix().contains(concept)) {
					getChoix().add(concept);
				}

				getVue().jouerListeSelection();

				getChoix().clear();

				// On revient à la racine de l'arborescence.
				setCurseur(getRacine());

				setEmplacementDansListe(0);
				setPositionCurseur(0);

				listeCourante.addAll(getCurseur().getStraightSuccessors());

			} else {
				jouerSon(concept);
				// Le concept possède une descendance ou est un concept de
				// Retour
				if (!concept.equals(getRetour())) {
					// Le concept n'est pas un retour
					if (!getChoix().contains(concept))
						getChoix().add(concept);
					setCurseur(concept);
					setPositionCurseur(0);
					setEmplacementDansListe(0);
					// On refait la liste de défilement courant.
					this.listeCourante = new ArrayList<Node<Concept>>();
					this.listeCourante.addAll(getCurseur()
							.getStraightSuccessors());

					// On rajoute un concept retour.
					this.listeCourante.add(getRetour());

				} else {
					// Le concept choisi est Retour.
					// On supprime le dernier choix
					setPositionCurseur(0);
					setEmplacementDansListe(0);
					getChoix().remove(getChoix().size() - 1);
					if (getChoix().size() == 0) {
						// S'il n'y a plus de concepts choisis on retourne à la
						// racine.
						setCurseur(getRacine());
						this.listeCourante.addAll(getCurseur()
								.getStraightSuccessors());
					} else {
						// Sinon on retourne à la liste de choix du concept
						// précédent.
						setCurseur(getChoix().get(getChoix().size() - 1));
						this.listeCourante.addAll(getCurseur()
								.getStraightSuccessors());

						this.listeCourante.add(getRetour());
					}
				}

			}
			// On réinitialise le start-task.
			this.start = true;
			// On relance un nouveau Task.

			addTask();
		}
	}

	/**
	 * @return la position du curseur dans le défilement.
	 */
	public int getPositionCurseur() {
		return this.positionCurseur;
	}

	/**
	 * @param pos
	 */
	public void setPositionCurseur(int pos) {
		this.positionCurseur = pos;
	}

	/**
	 * @return la position du prochain pictogramme à dessiner.
	 */
	public int getEmplacementDansListe() {
		return this.positionDansListeCourante;
	}

	/**
	 * @param pos
	 */
	public void setEmplacementDansListe(int pos) {
		this.positionDansListeCourante = pos;
	}

	@Override
	public VuePratiqueDefilement getVue() {
		return (VuePratiqueDefilement) super.getVue();

	}

	private void supTask() {
		task.cancel();
		// time.purge();
	}

	private void addTask() {

		task = new TimerTask() {
			@Override
			public void run() {
				l = new ArrayList<Node<Concept>>();

				if (!start) {
					setPositionCurseur(getPositionCurseur() + 1);
				} else {
					setPositionCurseur(0);
					affectation();
				}

				if (getEmplacementDansListe() < listeCourante.size()
						&& getPositionCurseur() < getVue().getTab().length) {
					// PREMIER CAS : LA LISTE EN COURS N'EST PAS FINI
					// LE CURSEUR N'A PAS FINI LA PAGE
					// ON DOIT LAISSER LE CURSEUR CONTINUE SON CHEMIN
				} else if (getEmplacementDansListe() >= listeCourante.size()
						&& getPositionCurseur() < getVue().getTab().length) {
					// DEUXIEME CAS : LA LISTE EN COURS EST FINI
					// LE CURSEUR N'A PAS FINI LA PAGE
					// ON DOIT LAISSER LE CURSEUR CONTINUE SON CHEMIN
				} else if (getEmplacementDansListe() < listeCourante.size()
						&& getPositionCurseur() >= getVue().getTab().length) {
					// TROISIEME CAS : LA LISTE EN COURS N'EST PAS FINI
					// LE CURSEUR A FINI LA PAGE
					// ON DOIT OUVRIR LE RESTANT DE LA LISTE ET SE REPOSITIONNER
					// AU DEBUT

					setPositionCurseur(0);
					affectation();

				} else {
					// DERNIER CAS : LA LISTE EN COURS EST FINI
					// LE CURSEUR A FINI LA PAGE
					// REPOSITIONNEMENT AU DEBUT DE LA LISTE ET DU CURSEUR
					setPositionCurseur(0);
					setEmplacementDansListe(0);

					affectation();

				}

				
				if (getUtilisateur().getOptionsUtilisateur()
						.getBipDeChangement())
					jouerElementSonore();

				try {
					sem.acquire();
					Thread.sleep(500);
					getVue().dessinerCadre(getPositionCurseur());
					sem.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (start)
					start = false;

			}

		};

		time.scheduleAtFixedRate(task, 0, (long) getUtilisateur()
				.getOptionsUtilisateur().getTmpDefilement());

	}

	private void affectation() {
		int i = 0;
		while (i < getUtilisateur().getOptionsUtilisateur()
				.getNbImgDefilement()
				&& getEmplacementDansListe() < listeCourante.size()) {
			l.add(listeCourante.get(getEmplacementDansListe()));
			i++;
			setEmplacementDansListe(getEmplacementDansListe() + 1);
		}

		try {
			sem.acquire();
			getVue().afficherChoix(l);
			sem.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
