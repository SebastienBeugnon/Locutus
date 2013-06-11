package com.vue.pc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.controleurs.internes.ModuleMenuUtilisateurMono;
import com.locutus.controleurs.ModuleInterne;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.vue.pc.VueMenuUtilisateur;

/**
 * @author Sebastien Beugnon
 * 
 */
public class VueMenuUtilisateurMono extends VueMenuUtilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param mi
	 */

	public VueMenuUtilisateurMono(ModuleInterne mi) {
		super(mi);
		charger();
	}

	@Override
	public void charger() {
		this.setLayout(new BorderLayout());

		this.chargerTitre();

		this.chargerBlocsSpeciaux();

		this.chargerMenu();
	}

	@Override
	protected void chargerMenu() {
		// TODO Auto-generated method stub
		// /// MENU
		Font font = OptionsProgramme.getOptionsCourantes().getPoliceTexte();
		JPanel pan2 = new JPanel();
		pan2.setLayout(new GridLayout(0, 1, 10, 10));
		JButton j = new JButton("Module Cours");
		j.setFont(font);
		pan2.add(j);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chargerBlocCours();
			}

		});

		j = new JButton("Modifications");
		j.setFont(font);
		pan2.add(j);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chargerBlocModification();
			}

		});

		j = new JButton("Module Pratique");
		j.setFont(font);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModulePratiqueSelection();
			}
		});
		pan2.add(j);

		j = new JButton("Quitter");
		j.setFont(font);
		j.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		pan2.add(j);

		super.add(pan2, BorderLayout.WEST);
		// ///FIN MENU
	}

	@Override
	public ModuleMenuUtilisateurMono getModuleInterne() {
		return (ModuleMenuUtilisateurMono) super.getModuleInterne();
	}

}
