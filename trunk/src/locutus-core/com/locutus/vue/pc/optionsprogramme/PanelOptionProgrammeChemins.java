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
package com.locutus.vue.pc.optionsprogramme;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;

import com.locutus.exceptions.ConnexionException;
import com.locutus.exceptions.NonSpecifieException;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.enums.TypeStatut;

/**
 * @author Sebastien Beugnon
 * 
 */
public class PanelOptionProgrammeChemins extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<TypeStatut> statutbox;
	private JButton statutbutton;
	private JTextField pathdistant;
	private PanelConnexion panelco;
	private VueOptionsProgramme vop;
	private JButton testbutton;

	/**
	 * @param vopu
	 */
	public PanelOptionProgrammeChemins(VueOptionsProgramme vopu) {
		// LOADER DE MODIF
		this.vop = vopu;
		this.setLayout(new GridLayout(3, 1));

		// CREATION DU PANEL HAUD DE PAGE
		JPanel up = new JPanel();

		this.statutbox = new JComboBox<>(TypeStatut.values());
		this.statutbox.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		this.statutbox.setSelectedItem(OptionsProgramme.getOptionsCourantes()
				.getStatut());

		ModificationListener mod = new ModificationListener();
		// MODIFICATION DU STATUT DU PROGRAMME
		this.statutbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unchecked")
				JComboBox<TypeStatut> test = (JComboBox<TypeStatut>) arg0
						.getSource();

				if (!(OptionsProgramme.getOptionsCourantes().getStatut() == (TypeStatut) test
						.getSelectedItem())) {
					// CHOIX DIFFERENT DU MODE ACTUEL
					statutbutton.setEnabled(false);
					if (test.getSelectedItem() == TypeStatut.Independant) {
						// VERROUILLE LE CHEMIN DISTANT
						pathdistant.setText("local");
						pathdistant.setEnabled(false);
						// CACHE LE PANEL DE CONNEXION
						testbutton.setVisible(false);
						panelco.setVisible(false);
						statutbutton.setEnabled(true);
					} else if (test.getSelectedItem() == TypeStatut.DependantAuReseauSmb) {
						// DEVEROUILLE LE CHEMIN DISTANT
						pathdistant.setText("smb://");
						pathdistant.setEnabled(true);
						// DONNE ACCES AU PANEL DE CONNEXION
						testbutton.setVisible(true);
						panelco.setVisible(true);

					} else {
						// AUTRE STATTUT POUR DEVELOPPEMENT FUTUR
					}

				} else {
					// CHOIX ACTUEL == Options Courantes

					if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

						pathdistant.setText("local");
						pathdistant.setEnabled(false);
						statutbutton.setEnabled(false);
						testbutton.setVisible(false);
						panelco.setVisible(false);

					} else if (OptionsProgramme.getOptionsCourantes()
							.getStatut() == TypeStatut.DependantAuReseauSmb) {
						testbutton.setVisible(true);
						pathdistant.setText(OptionsProgramme
								.getOptionsCourantes().getCheminDistant());
						pathdistant.setEnabled(true);
						panelco.setVisible(true);

					} else {
						// AUTRE STATUT POUR DEVELOPPEMENT FUTUR
					}
				}
			}
		});

		// CREATION PANEL DE CONNEXION (Uniquement visible pour le SMB
		// actuellement)
		this.panelco = new PanelConnexion(OptionsProgramme
				.getOptionsCourantes().getAuthentificateurSMB());

		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			this.pathdistant = new JTextField("local");
			this.pathdistant.setEnabled(false);
			this.panelco.setVisible(false);

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			this.pathdistant = new JTextField(OptionsProgramme
					.getOptionsCourantes().getCheminDistant());
			this.panelco.setVisible(true);
		}

		this.pathdistant.setColumns(12);
		this.pathdistant.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		up.add(this.statutbox);

		JLabel jl = new JLabel("Chemin distant :");
		jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		up.add(jl);
		up.add(pathdistant);

		// PANEL BAS DE PAGE AVEC LES BOUTONS CRUCIAUX
		JPanel down = new JPanel();
		this.testbutton = new JButton("Tester la connexion");
		this.testbutton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());

		// TESTER LA POSSIBILITE DE CONNEXION AU SERVEUR/RESEAU
		this.testbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if ((TypeStatut) statutbox.getSelectedItem() == TypeStatut.DependantAuReseauSmb) {
					try {

						vop.getModuleInterne().testerConnexionSmb(
								panelco.getAuthentificateur(),
								pathdistant.getText());
						statutbutton.setEnabled(true);
					} catch (UnknownHostException e) {

						vop.afficherMessage("Accès impossible ! Le client ne reconnait pas le réseau spécifié dans le chemin distant.");

					} catch (SmbException e) {
						statutbutton.setEnabled(false);
						if (e instanceof SmbAuthException) {
							vop.afficherMessage("Connexion impossible. Nom d'utilisateur inconnu ou mauvais mot de passe.");
						} else {
							vop.afficherMessage("Accès refusé. (Voir les permission de l'utilisateur avec l'administrateur du serveur samba)");
						}
						e.printStackTrace();

					} catch (IOException e) {
						statutbutton.setEnabled(false);
						e.printStackTrace();

					} catch (ConnexionException e) {
						statutbutton.setEnabled(false);
						vop.afficherMessage("Chemin incorrect ou dossier non existant.");
						e.printStackTrace();

					} catch (NonSpecifieException e) {
						statutbutton.setEnabled(false);
						vop.afficherMessage("Identifiant ou/et mot de passe non spécifié(s).");
						e.printStackTrace();
					}
				} else {
					// AUTRE STATUT POUR UN DEVELOPPEMENT FUTUR
				}
			}

		});

		this.statutbutton = new JButton("Modifier le statut");

		// VALIDATION DES CHANGEMENTS APPORTES
		this.statutbutton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		this.statutbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (statutbox.getSelectedItem() == TypeStatut.DependantAuReseauSmb) {
					vop.getModuleInterne().changerStatutSmb(
							panelco.getAuthentificateur(),
							pathdistant.getText());
				} else if (statutbox.getSelectedItem() == TypeStatut.Independant) {
					vop.getModuleInterne().changerStatutIndependant();
				}
			}
		});

		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			this.statutbutton.setEnabled(false);

		}

		// LIAISON AVEC LE PANNEAU BAS DE PAGE
		down.add(this.testbutton);
		down.add(this.statutbutton);

		// LIAISON AVEC LE PANNEAU CENTRAL
		this.add(up);
		this.add(this.panelco);
		this.add(down);
		this.pathdistant.getDocument().addDocumentListener(mod);
		this.vop.getModuleInterne().getModulePrincipal().getVue()
				.setVisible(true);
	}

	/**
	 * 
	 * @author Beugnon Sebastien
	 * 
	 */
	class PanelConnexion extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextField domaine;
		private JTextField nom;
		private JTextField motDePasse;

		public PanelConnexion(NtlmPasswordAuthentication npa) {
			Font font = OptionsProgramme.getOptionsCourantes().getPoliceTexte();
			// CREATION DU PANEL DE CONNEXION
			JPanel profil = new JPanel(new GridLayout(4, 1, 10, 10));
			this.domaine = new JTextField();

			this.domaine.setFont(font);
			this.nom = new JTextField();

			this.nom.setFont(font);
			this.motDePasse = new JPasswordField();

			this.motDePasse.setFont(font);

			JLabel jl = new JLabel("Domaine");
			jl.setFont(font);
			profil.add(jl);
			profil.add(this.domaine);
			jl = new JLabel("Nom de connexion");
			jl.setFont(font);
			profil.add(jl);
			profil.add(this.nom);
			jl = new JLabel("Mot de passe");
			jl.setFont(font);
			profil.add(jl);
			profil.add(this.motDePasse);
			if (npa != null) {

				this.domaine.setText(npa.getDomain());
				this.nom.setText(npa.getUsername());
				this.motDePasse.setText(npa.getPassword());
			}
			this.domaine.getDocument().addDocumentListener(
					(new ModificationListener()));
			this.nom.getDocument().addDocumentListener(
					(new ModificationListener()));
			this.motDePasse.getDocument().addDocumentListener(
					(new ModificationListener()));
			this.add(profil);
		}

		public NtlmPasswordAuthentication getAuthentificateur() {
			return new NtlmPasswordAuthentication(this.domaine.getText(),
					this.nom.getText(), this.motDePasse.getText());
		}
	}

	/**
	 * @author Sebastien Beugnon
	 * 
	 */
	public class ModificationListener implements ActionListener,
			DocumentListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			statutbutton.setEnabled(false);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			statutbutton.setEnabled(false);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			statutbutton.setEnabled(false);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			statutbutton.setEnabled(false);
		}

	}
}
