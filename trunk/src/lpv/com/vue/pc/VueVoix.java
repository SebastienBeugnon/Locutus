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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.controleurs.ModuleVoix;
import com.locutus.controleurs.ModuleInterne;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.outils.son.GestionnaireSon;
import com.locutus.vue.pc.VuePanneau;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeGenre;
import com.locutus.vue.pc.utilisateur.creation.SelecteurDeVoix;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VueVoix extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private SelecteurDeVoix selecteurVoix;

	/**
	 * 
	 */
	private SelecteurDeGenre selecteurGenre;

	/**
	 * 
	 */
	private JPanel panel1;

	/**
	 * 
	 */
	private JPanel panel2;

	/**
	 * 
	 */
	private JScrollPane jsp;

	/**
	 * @param mi
	 */
	public VueVoix(ModuleInterne mi) {
		super(mi);
		charger();

	}

	/**
	 * @return le sélecteur de voix courant.
	 */
	public SelecteurDeVoix getSelecteurDeVoix() {
		return this.selecteurVoix;
	}

	/**
	 * @return le sélecteur de genre courant.
	 */
	public SelecteurDeGenre getSelecteurDeGenre() {
		return this.selecteurGenre;
	}

	public ModuleVoix getModuleInterne() {
		return (ModuleVoix) super.getModuleInterne();
	}

	@Override
	public void charger() {
		chargerListeVoix();
		chargerVueListeConcept();
	}

	/**
	 * 
	 */
	public void chargerListeVoix() {
		super.setLayout(new GridLayout(2, 0));

		// SI ON RECHARGE LA LISTE
		if (this.panel1 != null)
			this.remove(panel1);

		this.panel1 = new JPanel();
		this.selecteurVoix = new SelecteurDeVoix();
		this.selecteurVoix.addActionListener(new ChangeVoix());
		this.selecteurGenre = new SelecteurDeGenre();
		this.selecteurGenre.addActionListener(new ChangeGenreVoix());

		panel1.add(this.selecteurVoix);
		panel1.add(this.selecteurGenre);
		JButton jb = new JButton("Ajouter une voix");
		jb.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		jb.addActionListener(new LanceCreation(this));
		panel1.add(jb);
		TextFieldRechercheVoix t = new TextFieldRechercheVoix(
				"Recherche par concept", this);
		t.setColumns(13);
		panel1.add(t);
		this.add(panel1);
	}

	/**
 * 
 */
	public void chargerVueListeConcept() {
		if (this.jsp != null)
			this.remove(jsp);

		if (UtilisationFichier.chargerListeConcepts().size() > 0) {
			this.panel2 = new JPanel(new GridLayout(UtilisationFichier
					.chargerListeConcepts().size() + 1, 0, 0, 4));
			Iterator<Concept> it = UtilisationFichier.chargerListeConcepts()
					.iterator();
			if (getSelecteurDeVoix().getVoix() != null) {
				while (it.hasNext()) {
					Concept local = it.next();
					if (!local
							.equals(new Concept("origine", "origine (graphe)"))) {
						PanelConcept pc = new PanelConcept(this, local);
						if (UtilisationFichier.existeSonPour(
								getSelecteurDeVoix().getVoix(), local))
							pc.setBackground(Color.green);
						else
							pc.setBackground(Color.yellow);

						this.panel2.add(pc);
					}
				}
			}
		} else {
			JLabel jl = new JLabel("Aucun concept.");
			this.panel2 = new JPanel();
			this.panel2.add(jl);
		}
		jsp = new JScrollPane(this.panel2);
		this.add(jsp);
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * @param li
	 */
	public void rechargerVueListeConcept(List<Concept> li) {
		if (this.jsp != null)
			this.remove(jsp);

		if (li.size() > 0) {
			this.panel2 = new JPanel(new GridLayout(li.size() + 1, 0, 0, 4));
			Iterator<Concept> it = li.iterator();
			if (getSelecteurDeVoix().getVoix() != null) {
				while (it.hasNext()) {
					Concept local = it.next();
					if (!local
							.equals(new Concept("origine", "origine (graphe)"))) {
						PanelConcept pc = new PanelConcept(this, local);
						if (UtilisationFichier.existeSonPour(
								getSelecteurDeVoix().getVoix(), local))
							pc.setBackground(Color.green);
						else
							pc.setBackground(Color.yellow);

						this.panel2.add(pc);
					}
				}
			}
		} else {
			JLabel jl = new JLabel("Aucun concept.");
			this.panel2 = new JPanel();
			this.panel2.add(jl);
		}
		jsp = new JScrollPane(this.panel2);
		this.add(jsp);
		getModuleInterne().getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * 
	 */
	public void chargerGenre() {
		getSelecteurDeGenre().setSelectedItem(
				getSelecteurDeVoix().getVoix().getTypeGenre());
	}

	/**
	 * 
	 * @author Beugnon
	 * 
	 */
	private class LanceCreation implements ActionListener {

		/**
		 * 
		 */
		private VueVoix vc;

		/**
		 * 
		 * @param vc
		 */
		private LanceCreation(VueVoix vc) {
			this.vc = vc;
		}

		/**
		 * 
		 * @return
		 */
		private VueVoix getVueVoix() {
			return this.vc;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			CreationVoix cv = new CreationVoix(getVueVoix());
			cv.setVisible(true);
		}

	}

	/**
	 * 
	 * @author Beugnon
	 * 
	 */
	private class ChangeVoix implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			chargerVueListeConcept();

		}

	}

	/**
	 * 
	 * @author Beugnon
	 * 
	 */
	private class PanelConcept extends JPanel {

		/**
		 * 
		 * @author Beugnon
		 * 
		 */
		private class SelectionConcept extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getVueVoix().getModuleInterne().lancerModificationSon(
						getSelecteurDeVoix().getVoix(), getConcept());
			}

		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private VueVoix vc;
		/**
		 * 
		 */
		private Concept cc;

		/**
		 * 
		 * @param cc
		 */
		private PanelConcept(VueVoix vc, Concept cc) {
			this.vc = vc;
			this.cc = cc;
			charger();
		}

		/**
		 * 
		 */
		private void charger() {
			super.setLayout(new GridLayout(1, 3));
			this.add(new JLabel(this.getConcept().getNomConcept()));

			JButton add;
			if (!UtilisationFichier.existeSonPour(getSelecteurDeVoix()
					.getVoix(), getConcept())) {
				add = new JButton("Ajouter");

			} else {
				add = new JButton("Modifier");
			}
			add.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
			add.addMouseListener(new SelectionConcept());
			this.add(add);

			JButton play = new JButton("Jouer");
			play.setFont(OptionsProgramme.getOptionsCourantes()
					.getPoliceTexte());
			play.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GestionnaireSon gs = new GestionnaireSon();
					gs.jouerElementSonore(UtilisationFichier.chargerSon(
							getConcept(), getSelecteurDeVoix().getVoix()));
				}
			});
			if (!UtilisationFichier.existeSonPour(getSelecteurDeVoix()
					.getVoix(), getConcept()))
				play.setEnabled(false);

			this.add(play);
		}

		/**
		 * 
		 * @return
		 */
		private Concept getConcept() {
			return this.cc;
		}

		/**
		 * 
		 * @return
		 */
		private VueVoix getVueVoix() {
			return this.vc;
		}

	}

	/**
	 * 
	 * @author Beugnon
	 * 
	 */
	private class CreationVoix extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */
		private VueVoix vc;
		/**
		 * 
		 */
		private JTextField txtNomVoix;

		/**
		 * 
		 */
		private JComboBox<TypeGenre> genreBox;

		/**
		 * 
		 * @param vc
		 **/
		private CreationVoix(VueVoix vc) {
			this.vc = vc;
			this.txtNomVoix = new JTextField();
			this.genreBox = new JComboBox<TypeGenre>(TypeGenre.values());

			super.setTitle("Création d'une voix");
			super.getContentPane().setLayout(new GridLayout(3, 0));
			super.setLocationRelativeTo(null);

			JPanel pan = new JPanel();
			JLabel jl = new JLabel("Nom de la voix :");
			pan.add(jl);
			pan.add(txtNomVoix);
			txtNomVoix.setColumns(13);

			this.getContentPane().add(pan);

			pan = new JPanel();
			jl = new JLabel("Genre :");
			pan.add(jl);
			pan.add(genreBox);

			this.getContentPane().add(pan);
			pan = new JPanel();
			JButton validateButton = new JButton("Créer");
			validateButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (getVueVoix().getModuleInterne().lancerCreationVoix(
							getNomVoix(), getGenreVoix())) {
						setVisible(false);
						System.gc();
					}
				}
			});
			pan.add(validateButton);

			this.getContentPane().add(pan);
			this.pack();
		}

		/**
		 * 
		 * @return
		 */
		private VueVoix getVueVoix() {
			return this.vc;
		}

		/**
		 * 
		 * @return
		 */
		private String getNomVoix() {
			return txtNomVoix.getText();
		}

		/**
		 * 
		 * @return
		 */
		private TypeGenre getGenreVoix() {
			return (TypeGenre) genreBox.getSelectedItem();
		}
	}

	private class ChangeGenreVoix implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			getModuleInterne().lancerModificationVoixGenre(
					getSelecteurDeVoix().getVoix(),
					getSelecteurDeGenre().getGenre());

		}

	}

}
