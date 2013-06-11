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

package com.locutus.vue.pc.modification.personnalisation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.locutus.modeles.enums.TypeRessource;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.JImagePanel;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelTypeRessource extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final String FinePath = "fine.png";
	/**
	 * 
	 */
	private static final String NeedPath = "need.png";
	/**
	 * 
	 */
	private static final String FreePath = "free.png";

	private JImagePanel jim;

	private FenetrePersonnalisation fen;

	private TypeRessource te;

	protected PanelTypeRessource(FenetrePersonnalisation fen, TypeRessource te) {
		super.setLayout(new GridLayout(1, 4));
		this.fen = fen;
		this.te = te;
		chargerMenu();
	}

	private void chargerMenu() {
		this.removeAll();
		JLabel jl = new JLabel(te.toString());
		this.add(jl);
		if (getFenetre().getConcept().getPhotoPrivee()
				&& getType().equals(TypeRessource.photographies)) {
			if (UtilisationFichier.existeRessourceUtilisateur(getType(),
					getFenetre().getConcept(), getFenetre().getUtilisateur())) {
				try {

					jim = new JImagePanel(ImageIO.read(UtilisationFichier.chargerGui(FinePath)));
					jim.setStretch(false);

					this.add(jim);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					jim = new JImagePanel(ImageIO.read(UtilisationFichier.chargerGui(NeedPath)));
					this.add(jim);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			if (UtilisationFichier.existeRessourceUtilisateur(te, getFenetre()
					.getConcept(), getFenetre().getUtilisateur())) {
				try {
					jim = new JImagePanel(ImageIO.read(UtilisationFichier.chargerGui(FinePath)));
					jim.setStretch(false);
					this.add(jim);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					jim = new JImagePanel(ImageIO.read(UtilisationFichier.chargerGui(FreePath)));
					jim.setStretch(false);
					this.add(jim);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (!UtilisationFichier.existeRessourceUtilisateur(getType(),
				getFenetre().getConcept(), getFenetre().getUtilisateur())) {
			JButton addButton = new JButton("ajouter");
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (getFenetre().getVueMenuUtilisateur().getModuleInterne()
							.addRessource(getType(), getFenetre().getConcept())) {
						getFenetre().chargerListe();
					}

				}
			});
			this.add(addButton);
		} else {
			JButton modButton = new JButton("modifier");
			modButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (getFenetre().getVueMenuUtilisateur().getModuleInterne()
							.modRessource(getType(), getFenetre().getConcept())) {
						getFenetre().chargerListe();
					}

				}
			});
			this.add(modButton);
			JButton supButton = new JButton("retirer");
			supButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (getFenetre().getVueMenuUtilisateur().getModuleInterne()
							.supRessource(getType(), getFenetre().getConcept())) {
						getFenetre().chargerListe();
					}

				}
			});
			this.add(supButton);
		}
	}

	private FenetrePersonnalisation getFenetre() {
		return this.fen;
	}

	private TypeRessource getType() {
		return this.te;
	}

}