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

package com.locutus.controleurs.internes.cours;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.cours.ClicCoursDefilement;
import com.locutus.vue.pc.cours.VueCoursDefilement;

/**
 * ModuleCoursDefilement, est un contrôleur -héritant de la classe ModuleCours-
 * servant à réaliser un défilement d'une ligne ou deux d'images à l'écran en
 * fonction des paramètres de l'utilisateur.
 * 
 * @author Beugnon
 * 
 */
public class ModuleCoursDefilement extends ModuleCours {

	/**
	 * La liste qui va être affiché à l'écran de l'utilisateur.
	 */
	private List<Concept> l;
	/**
	 * La position de la prochaine image n'ayant pas encore été affiché.
	 */
	private int positionDansListeCourante;
	/**
	 * La position du cuseur de sélection à l'écran (0 -
	 * Utilisateur.nombre.d.images -1)
	 */
	private int positionCurseur;
	/**
	 * Un deuxième couple de concept et d'images (pour la deuxième ligne)
	 * Remarque il en existe déjà un pour ModuleCours s'occupant de la première
	 * ligne.
	 */
	private Map<Concept, BufferedImage> image2;

	/**
	 * Le service s'occupant d'exécuter des tâches de manière périodique.
	 */
	private Timer time;

	/**
	 * La tâche à exécuter.
	 */
	private TimerTask task;

	/**
	 * L'écouteur de clic souris pour le défilement notifiant le contrôleur des
	 * évènements.
	 */
	private ClicCoursDefilement cc;

	/**
	 * un booléen indiquant s'il s'agit du démarrage de la tâche.
	 */
	private boolean start;

	/**
	 * Une sémaphore pour contrôler les méthodes de dessin.
	 */
	private Semaphore sem;

	/**
	 * 
	 * @param mp
	 *            le module principal
	 * @param mc
	 *            le module de sélection des pictogrammes
	 */
	public ModuleCoursDefilement(ModulePrincipal mp, ModuleCoursSelection mc) {
		super(mp, mc);
		// On initialise la variable s'occupant des concepts à afficher.
		this.l = new ArrayList<Concept>();
		this.image2 = new HashMap<Concept, BufferedImage>();
		this.time = new Timer();
		this.sem = new Semaphore(1);
		this.setEmplacementDansListe(0);
		this.setPositionCurseur(0);
		// On crée et on ajoute l'écouteur à la vue.
		this.cc = new ClicCoursDefilement(this);
		getModulePrincipal().getVue().addMouseListener(this.cc);
		this.start = true;
		init();
	}

	/**
	 * méthode surchargée
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#init()
	 */
	@Override
	public void init() {
		super.init();
		// Si la longueur de la liste des concepts est inférieure à celle des
		// options de l'utilisateur
		// On ramène la valeur à celle de la liste.
		// Ceci ne modifie pas les options de l'utilisateur
		if (this.getListeConcept().size() < this.getOptions()
				.getNbImgDefilement())
			this.getOptions().setNbImgDefilement(this.getListeConcept().size());

		// Si on a deux lignes à afficher
		if (this.getModuleCoursSelection().estDoubleExercice()) {

			Iterator<Concept> it = getListeConcept().iterator();
			// A ce niveau, on a déjà vérifier l'état des ressources à la
			// sélection des concepts.
			while (it.hasNext()) {
				Concept local = it.next();
				this.image2.put(local, UtilisationFichier.chargerImage(this
						.getModuleCoursSelection().getExo2(), local, this
						.getModuleCoursSelection().getUtilisateur()));
			}
		}
		// On génère la vue et on l'affiche
		super.setVue(new VueCoursDefilement(this));

		// On affecte la liste à afficher.
		affectation();
		// On ajoute la tâche à exécuter.

		addTask();
	}

	/**
	 * @return la position du curseur de sélection.
	 */
	public int getPositionCurseur() {
		return this.positionCurseur;
	}

	/**
	 * Modifie la position du curseur de sélection.
	 * 
	 * @param pos
	 */
	public void setPositionCurseur(int pos) {
		this.positionCurseur = pos;
	}

	/**
	 * @return le prochain emplacement à lire.
	 */
	public int getEmplacementDansListe() {
		return this.positionDansListeCourante;
	}

	/**
	 * Modifie le prochain emplacement à lire
	 * 
	 * @param pos
	 */
	public void setEmplacementDansListe(int pos) {
		this.positionDansListeCourante = pos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.controleurs.ModuleInterne#getVue()
	 */
	@Override
	public VueCoursDefilement getVue() {
		return (VueCoursDefilement) super.getVue();
	}

	/**
	 * Accesseur aux options de l'utilisateur pour cette session.
	 * 
	 * @return Les options Utilisateur pour cette session.
	 */
	public OptionsUtilisateur getOptions() {
		return this.getModuleCoursSelection().getUtilisateur()
				.getOptionsUtilisateur();
	}

	/**
	 * 
	 * @param cpt
	 *            Un concept dont on veut l'image
	 * 
	 * @return L'image utilisable du type1 pour le concept à la place (i)
	 */
	public BufferedImage getPictogrammesExo1(Concept cpt) {
		return super.getImage1(cpt);
	}

	/**
	 * 
	 * 
	 * @param cpt
	 * @return L'image utilisable du type2 pour le concept à la place (i)
	 */
	public BufferedImage getPictogrammesExo2(Concept cpt) {
		return this.image2.get(cpt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.controleurs.internes.cours.ModuleCours#
	 * chargerModuleCoursSelection()
	 */
	@Override
	public void chargerModuleCoursSelection() {
		// On stoppe la tâche
		this.time.cancel();
		super.chargerModuleCoursSelection();
		// On retire l'écouteur
		getModulePrincipal().getVue().removeMouseListener(this.cc);
	}

	/**
 * On joue le son qui est sélectionné par le programme
 */
	public void jouerSonParContact() {

		this.jouerSon(this.getVue().getPanelDessin(this.getPositionCurseur())
				.getConcept());

	}

	/**
	 * 
	 * @param k le concept à jouer
	 */
	private void jouerSon(Concept k) {
		this.getGestionnaireSon().jouerSonConcept(k);
	}

	/**
	 * 
	 * @return Vrai si et seulement si la session compte 2 types de ressources à afficher (deux lignes)
	 */
	public boolean estDoubleExercice() {
		return this.getModuleCoursSelection().estDoubleExercice();
	}

	/**
	 * Permet de modifier avec des raccourcis les options de l'utilisateur comme la vitesse de défilement
	 * @param optionsUtilisateur 
	 */
	public void chargerModificationRapide(OptionsUtilisateur optionsUtilisateur) {
		getModuleCoursSelection().getUtilisateur().setOptionsUtilisateur(
				optionsUtilisateur);
	}
	
	/**
	 * 
	 * @param l la liste à afficher
	 */
	private void setListeVisible(List<Concept> l){
		this.l = l;
	}
	
	/**
	 * 
	 * @return la liste à afficher
	 */
	private List<Concept> getListeVisible(){
		return this.l;
	}
	
	/**
	 * 
	 * @return True si et seulement si la tâche vient de démarrer.
	 */
	private boolean isStart(){
		return this.start;
	}
	
	private void setStart(boolean tr){
		this.start = tr;
	}
	
	/**
	 * 
	 * @return sem la sémaphore de contrôle d'affichage
	 */
	private Semaphore getSemaphore(){
		return this.sem;
	}

	/**
	 * Méthode définissant et ajoutant la tâche
	 */
	private void addTask() {

		this.task = new TimerTask() {

			@Override
			public void run() {
				// On réinitialise la liste à afficher
				setListeVisible(new ArrayList<Concept>());

				if (!isStart()) {
					// Si le booléen indiquant que la tâche ne vient pas de
					// commencer.
					// On décale le curseur
					setPositionCurseur(getPositionCurseur() + 1);
				}

				if (getEmplacementDansListe() < getListeConcept().size()
						&& getPositionCurseur() < getVue().getTab().length) {
					// PREMIER CAS : LA LISTE EN COURS N'EST PAS FINI
					// LE CURSEUR N'A PAS FINI LA PAGE
					// ON DOIT LAISSER LE CURSEUR CONTINUE SON CHEMIN
				} else if (getEmplacementDansListe() >= getListeConcept()
						.size()
						&& getPositionCurseur() < getVue().getTab().length) {
					// DEUXIEME CAS : LA LISTE EN COURS EST FINI
					// LE CURSEUR N'A PAS FINI LA PAGE
					// ON DOIT LAISSER LE CURSEUR CONTINUE SON CHEMIN
				} else if (getEmplacementDansListe() < getListeConcept().size()
						&& getPositionCurseur() >= getVue().getTab().length) {
					// TROISIEME CAS : LA LISTE EN COURS N'EST PAS FINI
					// LE CURSEUR A FINI LA PAGE
					// ON DOIT OUVRIR LE RESTANT DE LA LISTE ET SE REPOSITIONNER
					// AU DEBUT
					setPositionCurseur(0);
					// On modifie l'affichage
					affectation();
				} else {
					// DERNIER CAS : LA LISTE EN COURS EST FINI
					// LE CURSEUR A FINI LA PAGE
					// REPOSITIONNEMENT AU DEBUT DE LA LISTE ET DU CURSEUR
					setPositionCurseur(0);
					setEmplacementDansListe(0);
					// On modifie l'affichage
					affectation();
				}

	

				// Si l'utilisateur souhaite un bip pour signaler le changement
				// de place du curseur
				if (getOptions().getBipDeChangement())
					jouerElementSonore();

				try {
					getSemaphore().acquire();
	
					// On attend que les méthodes de dessins de la fenêtre se
					// réaliser s'il y a eu un changement à l'affichage.

					// On dessine le cadre
					getVue().dessinerCadre(getPositionCurseur());

					// On repose le jeton.
					getSemaphore().release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// S'il s'agissait du démarrage du défilement
				// On le passe maintenant à faux.
				if (isStart())
					setStart(false);
			}

		};

		this.time
				.schedule(this.task, 0, (long) getOptions().getTmpDefilement());
	}

	/**
	 * Affecte la liste qui sera affichée à l'écran
	 */
	private void affectation() {
		int i = 0;
		//On ajoute les éléments en restant en dessous du nombre d'images des options 
		//Et de la limite de la liste
		while (i < getOptions().getNbImgDefilement()
				&& getEmplacementDansListe() < getListeConcept().size()) {
			getListeVisible().add(getListeConcept().get(getEmplacementDansListe()));
			i++;
			setEmplacementDansListe(getEmplacementDansListe() + 1);
		}

		try {
			getSemaphore().acquire();
			//On dessiner la liste à l'écran
			getVue().afficherChoix(getListeVisible());
			getSemaphore().release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
