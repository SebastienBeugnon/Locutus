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
package com.locutus.vue.pc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.locutus.controleurs.internes.autres.ModulePropos;
import com.locutus.modeles.OptionsProgramme;


/**
 * @author Sebastien Beugnon
 * 
 */
public class VuePropos extends VuePanneau {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param mi
	 */
	public VuePropos(ModulePropos mi) {
		super(mi);
		charger();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.vue.pc.VuePanneau#charger()
	 */
	@Override
	public void charger() {
		super.setLayout(new BorderLayout());
		JButton retour = new JButton("Retour au menu principal");
		retour.setFont(OptionsProgramme.getOptionsCourantes().getPoliceTexte());
		retour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getModuleInterne().chargerModuleMenuPrincipal();

			}
		});
		JPanel top = new JPanel();
		top.add(retour);
		super.add(top,BorderLayout.NORTH);
		
		JTextArea jta = new JTextArea();
		jta.setRows(64);
		try {
			Scanner scn = new Scanner(new File("COPYING.txt"));
			while(scn.hasNext()){
				jta.append(scn.nextLine());
				jta.append("\n");
			}
			scn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		jta.setEditable(false);
		
		super.add(new JPanel(),BorderLayout.WEST);
		super.add(new JPanel(),BorderLayout.EAST);
		super.add(new JPanel(),BorderLayout.SOUTH);
		JScrollPane jsp = new JScrollPane(jta);
		super.add(jsp,BorderLayout.CENTER);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.locutus.vue.pc.VuePanneau#getModuleInterne()
	 */
	@Override
	public ModulePropos getModuleInterne() {
		return (ModulePropos) super.getModuleInterne();
	}

}
