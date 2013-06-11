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
package com.locutus.vue.pc.modification;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.exceptions.modeles.OptionsUtilisateurException;
import com.locutus.modeles.Cadre;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.vue.pc.VueMenuUtilisateur;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.utilisateur.creation.PanelNombreImage;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeCadre;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeDuree;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeVoix;

/**
 * @author Sebastien Beugnon
 * 
 */
public final class PanelModification extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private VueMenuUtilisateur vmu;

	/**
	 * 
	 */
	private SelecteurDeDuree se;

	/**
	 * 
	 */
	private PanelNombreImage jcb;

	/**
	 * 
	 */
	private SelecteurDeCadre selecteurDeCadre;

	/**
	 * 
	 */
	private SelecteurDeVoix selecteurDeVoix;

	private JPanel listeArea;

	private JPanel listeCadrePanel;

	private JCheckBox bip;

	private PanelModificationPhotoPicto jsp;

	/**
	 * @param vmu
	 */
	public PanelModification(VueMenuUtilisateur vmu) {
		this.vmu = vmu;
		charger();
	}

	private void charger() {
		// MISE EN FORME DU LAYOUT DU PANNEAU
		this.setLayout(new GridLayout(2, 1));

		chargerPanel1();
		chargerPanel2();
		chargerPanel3();
		chargerPanel4();

	}

	private void chargerPanel3() {
		jsp = new PanelModificationPhotoPicto(this);
		this.add(jsp);
	}

	private void chargerPanel1() {
		// RECUPERATION DE LA POLICE DU PROGRAMME
		Font font = (OptionsProgramme.getOptionsCourantes().getPoliceTexte());

		// PREMIER PANNEAU DE MODIFICATION
		JPanel pan1 = new JPanel();
		JPanel pan12 = new JPanel();
		// CREATION ET AJOUT DES PANNEAUX DE NOMBRE D'IMAGES / DE TEMPS
		// INITIALISE SUR LES OPTIONS DE L'UTILISATEUR
		this.jcb = new PanelNombreImage(getVue().getModuleInterne()
				.getUtilisateur().getOptionsUtilisateur().getNbImgDefilement());
		this.jcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					if (!getVue().getModuleInterne()
							.lancerModificationUtilisateur(
									new OptionsUtilisateur(se.getValue(), jcb
											.getNbImg(), getBipDeChangement()))) {
						VueMessages.afficherMessageDansVue(getVue(),
								"La modification a échouée !");
					}

				} catch (OptionsUtilisateurException e) {
					e.printStackTrace();
				}
			}

		});
		pan12.add(jcb);
		pan1.add(pan12);

		pan12 = new JPanel();
		this.se = new SelecteurDeDuree(this.getVue().getModuleInterne()
				.getUtilisateur().getOptionsUtilisateur().getTmpDefilement());

		this.se.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				try {
					if (!getVue().getModuleInterne()
							.lancerModificationUtilisateur(
									new OptionsUtilisateur(se.getValue(), jcb
											.getNbImg(), getBipDeChangement()))) {
						VueMessages.afficherMessageDansVue(getVue(),
								"La modification a échouée !");
					}

				} catch (OptionsUtilisateurException e) {
					e.printStackTrace();
				}
			}

		});

		pan12.add(se);
		pan1.add(pan12);
		pan12 = new JPanel();
		JLabel jl = new JLabel("Bip de changement :");
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		bip = new JCheckBox();
		if (getVue().getModuleInterne().getUtilisateur()
				.getOptionsUtilisateur().getBipDeChangement())
			bip.setSelected(true);
		bip.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		bip.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {
					if (!getVue().getModuleInterne()
							.lancerModificationUtilisateur(
									new OptionsUtilisateur(se.getValue(), jcb
											.getNbImg(), getBipDeChangement()))) {
						VueMessages.afficherMessageDansVue(getVue(),
								"La modification a échouée !");
					}

				} catch (OptionsUtilisateurException e) {
					e.printStackTrace();
				}
			}

		});
		pan12.add(jl);
		pan12.add(bip);
		pan1.add(pan12);

		// CREATION ET AJOUT DU BOUTTON PAR DEFAUT
		final JButton j2 = new JButton("Par défaut");
		j2.setFont(font);
		// AJOUT DE L'ACTIONLISTENER DE DEFAUT
		j2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					getVue().getModuleInterne().lancerModificationUtilisateur(
							new OptionsUtilisateur(5000.0f, 2));
					se.setValue(5000);
					jcb.setValue(2);
				} catch (OptionsUtilisateurException e) {

					e.printStackTrace();
				}
			}

		});
		pan1.add(j2);

		this.selecteurDeVoix = new SelecteurDeVoix(this.getVue()
				.getModuleInterne().getUtilisateur().getVoix());

		this.selecteurDeVoix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!getVue().getModuleInterne().lancerModificationVoix(
						selecteurDeVoix.getVoix())) {
					VueMessages.afficherMessageDansVue(getVue(),
							"La modification a échouée !");
				}
			}

		});

		pan1.add(selecteurDeVoix);

		//

		this.add(pan1);
	}

	private void chargerPanel2() {

		JPanel pan3 = new JPanel();
		pan3.setLayout(new GridLayout(2, 0));
		this.selecteurDeCadre = new SelecteurDeCadre();
		JPanel t = new JPanel(new FlowLayout());

		t.add(this.selecteurDeCadre);

		JButton jb = new JButton("Ajouter");
		jb.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getVue().getModuleInterne().lancerModificationAjoutCadre(
						selecteurDeCadre.getCadre());
				chargerListeCadre();
			}

		});
		JPanel a = new JPanel();
		a.add(jb);
		t.add(a);
		pan3.add(t);

		this.add(pan3);
	}

	private void chargerPanel4() {
		listeArea = new JPanel();

		this.add(listeArea);

		chargerListeCadre();
	}

	/**
	 * 
	 * @return la vue du menu utilisateur.
	 */
	public VueMenuUtilisateur getVue() {
		return this.vmu;
	}

	private boolean getBipDeChangement() {
		return this.bip.isSelected();

	}

	/**
	 * 
	 */
	protected void chargerListeCadre() {
		if (listeCadrePanel != null) {
			listeArea.removeAll();
		}

		listeCadrePanel = new JPanel();
		if (getVue().getModuleInterne().getUtilisateur()
				.getOptionsUtilisateur().getCadres().size() > 0) {
			listeCadrePanel
					.setLayout(new GridLayout(getVue().getModuleInterne()
							.getUtilisateur().getOptionsUtilisateur()
							.getCadres().size() + 1, 4, 30, 10));
			Iterator<Cadre> it = getVue().getModuleInterne().getUtilisateur()
					.getOptionsUtilisateur().getCadres().iterator();
			while (it.hasNext()) {
				Cadre local = it.next();
				PanelCadre pc = new PanelCadre(this, local);
				listeCadrePanel.add(pc);
			}
		} else {
			JLabel jl = new JLabel("Pas de cadre.");
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			listeArea.add(jl);
		}

		listeArea.add(listeCadrePanel);
		listeArea.repaint();
		getVue().getModuleInterne().getModulePrincipal().getVue()
				.setVisible(true);
	}

	/**
	 * 
	 * @param rechercheParNom
	 */
	public void rechargerListe(List<Concept> rechercheParNom) {
		this.jsp.load(rechercheParNom);

	}
}
