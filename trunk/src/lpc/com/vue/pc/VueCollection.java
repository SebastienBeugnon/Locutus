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
package com.vue.pc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.controleurs.ModuleCollection;
import com.locutus.controleurs.ModuleInterne;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.locutus.vue.pc.VuePanneau;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VueCollection extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel west;
	/**
	 * 
	 */
	private PanelCentral panContenu;

	/**
	 * 
	 */
	private PanelConcept[] tabConcept;

	private TextFieldRechercheCollection tfrc;
	
	private JScrollPane pan;

	/**
	 * 
	 * @param mi
	 */
	public VueCollection(ModuleInterne mi) {
		super(mi);
		charger();
	}

	@Override
	public void charger() {
		getModuleInterne().getModulePrincipal().getVue()
				.setBounds(new Rectangle(new Dimension(1075, 750)));
		super.setLayout(new BorderLayout());

		chargerListeConcepts(UtilisationFichier.chargerListeConcepts());

		this.panContenu = new PanelCentral(this);
		this.add(this.panContenu, BorderLayout.CENTER);

	}

	/**
	 * @param set
	 */
	public void chargerListeConcepts(List<Concept> set) {
		if (west != null) {
			west.remove(pan);
			west.setVisible(false);
		} else {
			west = new JPanel(new BorderLayout());
		}

		if (tfrc == null) {
			tfrc = new TextFieldRechercheCollection("Recherche par nom...",
					this);
			west.add(tfrc, BorderLayout.NORTH);
		}
		
		
		pan = rechargerListeConcepts(set);
		west.add(pan,BorderLayout.CENTER);
		chargerBoutons();
		this.add(west, BorderLayout.WEST);
		west.setVisible(true);
	}

	private JScrollPane rechargerListeConcepts(List<Concept> set) {

		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(set.size(), 1));

		this.tabConcept = new PanelConcept[set.size()];
		Iterator<Concept> it = set.iterator();
		int i = 0;
		while (it.hasNext()) {
			Concept local = it.next();
			System.out.println(local.toString());
			tabConcept[i] = new PanelConcept(this, local);
			toppanel.add(tabConcept[i]);
			i++;
		}
		
		JScrollPane jsp = new JScrollPane(toppanel);
		jsp.setWheelScrollingEnabled(true);

		return jsp;
	}
	
	private void chargerBoutons(){
		JPanel pan = new JPanel(new GridLayout(3,1));
	
		JButton addButton = new JButton("Créer un nouveau concept");
		addButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		final VueCollection help = this;
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				CreationConcept cc = new CreationConcept(help);
				cc.setVisible(true);

			}
		});
		pan.add(addButton);


		addButton = new JButton("Récupérer la liste des concepts");
		addButton.setFont(OptionsProgramme.getOptionsCourantes()
				.getPoliceTexte());
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				help.getModuleInterne().lancerExportationListeConceptTextuel();
			}
		});
		pan.add(addButton);
		west.add(pan,BorderLayout.SOUTH);
	}

	/**
	 * @param cpt
	 */
	public void setConceptCourant(Concept cpt) {
		this.getModuleInterne().setConceptCourant(cpt);

		nettoyageSaufConceptCourant();

		this.remove(this.panContenu);
		this.panContenu = new PanelCentral(this);
		this.add(this.panContenu, BorderLayout.CENTER);

		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	public ModuleCollection getModuleInterne() {
		return (ModuleCollection) super.getModuleInterne();
	}

	/**
	 * @return le concept courant.
	 */
	public Concept getConceptCourant() {
		return this.getModuleInterne().getConceptCourant();
	}

	/**
	 * @return le panneau central.
	 */
	public PanelCentral getPanneau() {
		return this.panContenu;
	}

	private void nettoyageSaufConceptCourant() {
		for (int i = 0; i < tabConcept.length; i++) {
			if (getConceptCourant() != null
					&& !tabConcept[i].getConcept().equals(getConceptCourant())) {
				tabConcept[i].setBackground(null);
				tabConcept[i].repaint();
			}

		}
	}

	class PanelConcept extends JPanel implements MouseListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */
		private VueCollection vc;
		/**
		 * 
		 */
		private Concept cc;

		/**
		 * 
		 * @param vc
		 * @param cc
		 */
		public PanelConcept(VueCollection vc, Concept cc) {
			this.vc = vc;
			this.cc = cc;
			charger();
		}

		/**
		 * 
		 */
		public void charger() {
			JLabel jl = new JLabel(this.getConcept().getNomConcept());
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			this.add(jl);

			super.addMouseListener(this);
		}

		/**
		 * 
		 * @return le concept actuel.
		 */
		public Concept getConcept() {
			return this.cc;
		}

		/**
		 * 
		 * @return la vue du module COllection
		 */
		public VueCollection getVueCollection() {
			return this.vc;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			super.setBackground(Color.green);
			getVueCollection().setConceptCourant(getConcept());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if (getVueCollection().getConceptCourant() != null
					&& !getVueCollection().getConceptCourant().equals(
							getConcept()))
				super.setBackground(Color.lightGray);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if (getVueCollection().getConceptCourant() != null
					&& !getVueCollection().getConceptCourant().equals(
							getConcept()))
				super.setBackground(null);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			super.setBackground(Color.green);
			getVueCollection().setConceptCourant(getConcept());
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}
	}

	/**
	 * @author Sebastien Beugnon
	 * 
	 */
	public class PanelCentral extends JPanel {

		class AjoutListener implements ActionListener {

			private TypeRessource te;
			private Concept cp;

			public AjoutListener(Concept cp, TypeRessource te) {
				this.te = te;
				this.cp = cp;
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (getModuleInterne().addRessourceParDefaut(cp, te)) {
					VueMessages.afficherMessageDansVue(getVueCollection(),
							"Ajout réussi !");
					JButton local = (JButton) arg0.getSource();
					local.setText("Modifier");
					local.setFont(OptionsProgramme.getOptionsCourantes()
							.getPoliceTexte());
				}

			}

		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private VueCollection vc;

		private JComboBox<TypeGenre> genreBox;

		private JTextField txtNomFile;

		private JTextField txtNomConcept;

		private JCheckBox boxPrivee;

		private JButton[] buttonAjout;

		/**
		 * 
		 * @return la vue du Module Collection.
		 */
		public VueCollection getVueCollection() {
			return this.vc;
		}

		/**
		 * @param vc
		 */
		public PanelCentral(VueCollection vc) {
			this.vc = vc;
			super.setLayout(new GridLayout(5, 1));
			this.charger();
		}

		/**
		 * 
		 */
		public void charger() {
			if (getVueCollection().getConceptCourant() != null) {
				this.removeAll();
				JPanel pan = new JPanel();
				JLabel jl = new JLabel("Nom de concept : ");
				jl.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				pan.add(jl);
				this.txtNomConcept = new JTextField(getConceptCourant()
						.getNomConcept());
				this.txtNomConcept.setColumns(10);
				this.txtNomConcept.setFont(OptionsProgramme
						.getOptionsCourantes().getPoliceTexte());
				pan.add(txtNomConcept);
				this.add(pan);

				pan = new JPanel();
				jl = new JLabel("Nom de fichier : ");
				jl.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				pan.add(jl);
				this.txtNomFile = new JTextField(getConceptCourant()
						.getNomFichier());
				this.txtNomFile.setColumns(10);
				this.txtNomFile.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				pan.add(txtNomFile);
				this.add(pan);

				pan = new JPanel();
				jl = new JLabel("Genre : ");
				jl.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				pan.add(jl);
				this.genreBox = new JComboBox<>(TypeGenre.values());
				this.genreBox.setSelectedItem(getConceptCourant().getGenre());
				this.genreBox.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				pan.add(genreBox);
				this.add(pan);

				pan = new JPanel();
				jl = new JLabel("Photographie privée : ");
				jl.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				pan.add(jl);

				this.boxPrivee = new JCheckBox();
				if (getConceptCourant().getPhotoPrivee())
					this.boxPrivee.setSelected(true);
				boxPrivee.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				pan.add(boxPrivee);

				this.add(pan);

				this.buttonAjout = new JButton[TypeRessource.values().length];
				for (int i = 0; i < TypeRessource.values().length; i++) {
					pan = new JPanel();
					jl = new JLabel("Etat ressource "
							+ TypeRessource.values()[i] + " : ");
					jl.setFont(OptionsProgramme.getOptionsCourantes()
							.getPoliceTexte());
					pan.add(jl);

					if (TypeRessource.values()[i] != TypeRessource.sons) {

						if (UtilisationFichier.existeRessourceImageParDefaut(
								TypeRessource.values()[i], getConceptCourant()))
							this.buttonAjout[i] = new JButton("Modifier");
						else
							this.buttonAjout[i] = new JButton("Ajouter");

						this.buttonAjout[i]
								.addActionListener(new AjoutListener(
										getConceptCourant(), TypeRessource
												.values()[i]));

						buttonAjout[i].setFont(OptionsProgramme
								.getOptionsCourantes().getPoliceTexte());
						pan.add(buttonAjout[i]);

					} else {
						JLabel jl2 = new JLabel("traitement spécial pour sons");
						jl2.setFont(OptionsProgramme.getOptionsCourantes()
								.getPoliceTexte());
						pan.add(jl2);

					}
					this.add(pan);

				}
				pan = new JPanel();

				JButton saveButton = new JButton(
						"Sauvegarder les modifications");
				saveButton.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				saveButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						getModuleInterne().modConceptCourant(
								txtNomConcept.getText(), txtNomFile.getText(),
								boxPrivee.isSelected(),
								(TypeGenre) genreBox.getSelectedItem());
					}
				});
				pan.add(saveButton);
				JButton supButton = new JButton("Supprimer concept");
				supButton.setFont(OptionsProgramme.getOptionsCourantes()
						.getPoliceTexte());
				supButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						getModuleInterne().supConceptCourant();
						chargerListeConcepts(UtilisationFichier
								.chargerListeConcepts());
					}
				});
				pan.add(supButton);
				this.add(pan);
				if (this.getVueCollection().getConceptCourant()
						.equals(new Concept("origine", "origine (graphe)"))) {
					this.genreBox.setEnabled(false);
					this.txtNomConcept.setEnabled(false);
					this.txtNomFile.setEnabled(false);
					this.boxPrivee.setEnabled(false);
					saveButton.setEnabled(false);
					supButton.setEnabled(false);
					for (int i = 0; i < buttonAjout.length; i++)
						if (buttonAjout[i] != null) {
							buttonAjout[i].setEnabled(false);

						}
				} else if (this.getVueCollection().getConceptCourant()
						.equals(new Concept("retour", "retour (graphe)"))) {
					this.genreBox.setEnabled(false);
					this.txtNomConcept.setEnabled(false);
					this.txtNomFile.setEnabled(false);
					this.boxPrivee.setEnabled(false);
					supButton.setEnabled(false);
				}
			}

		}
	}

	class CreationConcept extends JFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private VueCollection vc;

		private JTextField txtNomFile;

		private JComboBox<TypeGenre> genreBox;

		private JTextField txtNomConcept;

		private JCheckBox boxPrivee;

		public CreationConcept(VueCollection vc) {
			super.setTitle("Creation d'un concept");
			super.setLocationRelativeTo(null);
			super.setSize(new Dimension(400, 300));
			this.vc = vc;
			this.getContentPane().setLayout(new GridLayout(0, 1));
			JPanel pan = new JPanel();
			JLabel jl = new JLabel("Genre :");
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			pan.add(jl);
			this.genreBox = new JComboBox<TypeGenre>(TypeGenre.values());
			this.genreBox.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			pan.add(genreBox);
			this.add(pan);

			pan = new JPanel();
			jl = new JLabel("Nom du concept :");
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			pan.add(jl);
			this.txtNomConcept = new JTextField();
			this.txtNomConcept.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			this.txtNomConcept.setColumns(13);
			pan.add(txtNomConcept);
			this.add(pan);

			pan = new JPanel();
			jl = new JLabel("Nom du fichier :");
			jl.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			pan.add(jl);
			this.txtNomFile = new JTextField();
			this.txtNomFile.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			this.txtNomFile.setColumns(13);

			pan.add(txtNomFile);
			this.add(pan);

			pan = new JPanel();
			JLabel textBoxPrivee = new JLabel("Photographie privée ?");
			textBoxPrivee.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			this.boxPrivee = new JCheckBox();
			boxPrivee.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			pan.add(textBoxPrivee);
			pan.add(boxPrivee);
			this.add(pan);

			pan = new JPanel();

			JButton validateButton = new JButton("Créer");
			validateButton.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			validateButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (getVueCollection().getModuleInterne().addConcept(
							txtNomConcept.getText().trim(),
							txtNomFile.getText().trim(),
							boxPrivee.isSelected(),
							(TypeGenre) genreBox.getSelectedItem())) {

						setVisible(false);
						chargerListeConcepts(UtilisationFichier
								.chargerListeConcepts());

					}
				}
			});
			pan.add(validateButton);
			this.add(pan);
			pack();
		}

		public VueCollection getVueCollection() {
			return this.vc;
		}

	}
}
