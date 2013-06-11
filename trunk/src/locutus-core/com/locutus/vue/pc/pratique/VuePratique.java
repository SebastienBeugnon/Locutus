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
package com.locutus.vue.pc.pratique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.locutus.controleurs.internes.pratique.ModulePratique;
import com.locutus.controleurs.internes.pratique.ModulePratiqueDefilement;
import com.locutus.controleurs.internes.pratique.ModulePratiqueSouris;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.graphes.Node;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.VuePanneau;

/**
 * @author Sebastien Beugnon
 * 
 */
public abstract class VuePratique extends VuePanneau {

	/**
	 * version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tableau de panneau à dessin pour Pratique.
	 */
	private PanelDessinPratique tab[];

	/**
	 * 
	 * @param mi
	 */
	public VuePratique(ModulePratiqueDefilement mi) {
		super(mi);
		// On rajoute les RaccourcisPratique (De sortie) à la vue Principal
		getModuleInterne().getModulePrincipal().getVue()
				.addKeyListener(getModuleInterne().getRaccourcisPratique());
		charger();
	}

	/**
	 * 
	 * @param mi
	 */
	public VuePratique(ModulePratiqueSouris mi) {
		super(mi);
		// On rajoute les RaccourcisPratique (De sortie) à la vue Principal
		getModuleInterne().getModulePrincipal().getVue()
				.addKeyListener(getModuleInterne().getRaccourcisPratique());
		charger();
	}

	@Override
	public abstract void charger();

	/**
	 * Méthode servant à mettre en forme la liste passée à l'écran en dessinant
	 * les images liés aux concepts. Elle s'occupe aussi de rafraîchir la
	 * fenêtre.
	 * 
	 * @param liste
	 *            de noeuds de Concept devant être dessiné à l'écran.
	 */
	public void afficherChoix(List<Node<Concept>> liste) {
		// On supprime les panneaux existant.
		removePanels();
		// On recréé les panneaux en fonction du nombre de noeuds dans la liste
		// passée.
		tab = new PanelDessinPratique[liste.size()];
		super.setLayout(new BorderLayout());

		JPanel pan = new JPanel();
		// On arrange l'organisation visuel de la fenêtre.
		if (liste.size() <= 4) {
			// ligne de 4 pictogrammes.
			pan.setLayout(new GridLayout(1, liste.size()));
		} else if (liste.size() <= 8) {
			// Ligne de X pictogrammes.
			pan.setLayout(new GridLayout(0, 4));
		}
		for (int i = 0; i < getTab().length; i++) {
			// On construit chaque panel dessin de pratique
			getTab()[i] = new PanelDessinPratique(this, liste.get(i));
			// On les rajoute à la fenêtre
			pan.add(getTab()[i]);
		}
		// On rafraîchit la fenêtre.
		JButton jb = new JButton("");
		jb.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTitre());
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (VueMessages.YES_OPTION == VueMessages
						.afficherQuestionDansVue(null,
								"Voulez-vous quitter ce mode ?"))
					getModuleInterne().chargerModulePratiqueSelection();
			}
		});
		JPanel pan2 = new JPanel(new BorderLayout());
		pan2.add(jb, BorderLayout.EAST);
		this.add(pan2, BorderLayout.NORTH);

		this.add(pan, BorderLayout.CENTER);
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
		// On nettoie la mémoire des panneaux dont on a perdu la référence.
		System.gc();
	}

	/**
	 * Méthode servant à retiré les panneaux de dessin pratique, afin
	 * d'installer des nouveaux dans la vue.
	 */
	public void removePanels() {
		if (tab != null) {
			for (int i = 0; i < tab.length; i++) {
				if (tab[i] != null)
					this.remove(tab[i]);
			}
		}
		this.removeAll();

	}

	/**
	 * Méthode servant à afficher les concepts sélectionnés lors de la pratique
	 * afin de composer une phrase ou dire une idée.
	 */
	public void jouerListeSelection() {
		// On retire tout les panneaux présents.
		this.removeAll();
		// On génére un tableau de panneau en fonction du nombres de concepts
		// sélectionnés.

		setTab(new PanelDessinPratique[getModuleInterne().getChoix().size()]);

		// Arrangement affichage
		if (getModuleInterne().getChoix().size() <= 4) {
			this.setLayout(new GridLayout(1, getModuleInterne().getChoix()
					.size()));
		} else if (getModuleInterne().getChoix().size() <= 8) {
			this.setLayout(new GridLayout(1, 4));
		}

		// On va dessiner chaque pictogramme.
		for (int i = 0; i < getTab().length; i++) {
			// On construit les panneaux un par un.
			getTab()[i] = new PanelDessinPratique(this, getModuleInterne()
					.getChoix().get(i));
			this.add(getTab()[i]);
			// On affiche les concepts un par un.
			getModuleInterne().getModulePrincipal().getVue().setVisible(true);
			// En jouant leur son.
			getModuleInterne().jouerSon(getTab()[i].getNode());
			// En attendant que le son précédent soit fini.
			while (!getModuleInterne().getGestionnaireSon().estTermine())
				;

		}

		// On attend 3secondes avant de revenir à la racine de l'arborescence.
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.gc();
	}

	/**
	 * @return le tableau de panneaux à dessin.
	 */
	public PanelDessinPratique[] getTab() {
		return this.tab;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vue.pc.VuePanneau#getModuleInterne()
	 */
	@Override
	public ModulePratique getModuleInterne() {
		return (ModulePratique) super.getModuleInterne();
	}

	/**
	 * Méthode servant à attribuer un nouveau tableau de panneaux.
	 * 
	 * @param panelDessinPratiques
	 *            le nouveau à tableau pour les panneaux de dessin.
	 */
	public void setTab(PanelDessinPratique[] panelDessinPratiques) {
		this.tab = panelDessinPratiques;
	}

}
