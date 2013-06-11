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
package com.locutus.modeles;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import jcifs.smb.NtlmPasswordAuthentication;

import com.locutus.modeles.enums.TypeStatut;
import com.locutus.outils.fichiers.TraitementFichier;

/**
 * 
 * @author Beugnon Sebastien
 * 
 */
public class OptionsProgramme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private static OptionsProgramme opcourant = null;

	/**
	 * 
	 */
	private Font font_title;
	/**
	 * 
	 */
	private Font font_text;
	/**
	 * 
	 */
	private String path;
	/**
	 * 
	 */
	private TypeStatut statutprogramme;
	/**
	 * 
	 */
	private String pathDistant;
	/**
	 * 
	 */
	private NtlmPasswordAuthentication npa;

	/**
	 * 
	 */
	private OptionsProgramme() {
		setStatut(TypeStatut.Independant);
		setCheminCourant("local");
		setPoliceTitre(new Font("tahoma", Font.BOLD, 28));
		setPoliceTexte(new Font("tahoma", Font.PLAIN, 24));
		OptionsProgramme.opcourant = this;
	}

	/**
	 * 
	 */
	public static void initialisation() {
		if (TraitementFichier.existeFichier(System.getProperty("user.dir"),
				"config.bin")) {
			File fl;
			try {
				fl = TraitementFichier.chargerFichier(
						System.getProperty("user.dir"), "config.bin");
				ObjectInputStream oos;
				oos = new ObjectInputStream(new FileInputStream(fl));
				OptionsProgramme op = (OptionsProgramme) oos.readObject();
				oos.close();
				OptionsProgramme.opcourant = op;
			} catch (IOException e) {
				e.printStackTrace();
				System.err
						.println("OptionsProgramme:initialisation():IOException relevée au chargement du fichier de configuration config.bin (existant). Démarrage par défaut");
				OptionsProgramme.opcourant = new OptionsProgramme();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.err
						.println("OptionsProgramme:initialisation():Classe Incorrecte. Lecture Impossible. Démarrage par défaut");
				OptionsProgramme.opcourant = new OptionsProgramme();
			}
		} else {
			OptionsProgramme defaut = new OptionsProgramme();
			try {
				TraitementFichier.enregistrerFichier(
						System.getProperty("user.dir"), "config.bin", defaut);
			} catch (IOException e) {
				e.printStackTrace();
				System.err
						.println("OptionsProgramme:initialisation():Erreur à l'enregistrement de la Configuration par défaut");
			}
		}

	}

	/**
	 * 
	 * @return Les options du programme en cours d'utilisation
	 */
	public static OptionsProgramme getOptionsCourantes() {
		return opcourant;
	}

	/**
	 * 
	 */
	private void enregistrerConfiguration() {
		try {
			TraitementFichier.enregistrerFichier(
					System.getProperty("user.dir"), "config.bin", this);
		} catch (IOException e) {
			e.printStackTrace();
			System.err
					.println("OptionsProgramme:enregistrerConfiguration():Erreur à l'enregistrement de la Configuration");
		}
	}

	/**
	 * 
	 * @return Le statut du programme actuel
	 */
	public TypeStatut getStatut() {
		return statutprogramme;
	}

	/**
	 * 
	 * @param st
	 */
	public void setStatut(TypeStatut st) {
		this.statutprogramme = st;
		enregistrerConfiguration();
	}

	/**
	 * 
	 * @return Le chemin distant du dossier de ressources partagées
	 */
	public String getCheminDistant() {
		return pathDistant;
	}

	/**
	 * 
	 * @param concat
	 */
	public void setCheminDistant(String concat) {
		this.pathDistant = concat;
		enregistrerConfiguration();
	}

	/**
	 * 
	 * @return Le chemin du dossier courant
	 */
	public String getCheminCourant() {
		return path;
	}

	/**
	 * 
	 * @param concat
	 */
	public void setCheminCourant(String concat) {
		this.path = concat;
		enregistrerConfiguration();
	}

	/**
	 * 
	 * @return La police d'écriture pour le texte.
	 */
	public Font getPoliceTitre() {
		return font_title;
	}

	/**
	 * 
	 * @return La police d'écriture pour les titres.
	 */
	public Font getPoliceTexte() {
		return font_text;
	}

	/**
	 * 
	 * @param font
	 */
	public void setPoliceTitre(Font font) {
		this.font_title = font;
		enregistrerConfiguration();
	}

	/**
	 * 
	 * @param font
	 */
	public void setPoliceTexte(Font font) {
		this.font_text = font;
		enregistrerConfiguration();
	}

	/**
	 * 
	 * @param npa
	 */
	public void setAuthentificateurSMB(NtlmPasswordAuthentication npa) {
		this.npa = npa;
		enregistrerConfiguration();
	}

	/**
	 * 
	 * @return L'authentificateur de connexion au réseau SMB, si le programme
	 *         n'est pas connecté par SMB ceci renvoie null.
	 */
	public NtlmPasswordAuthentication getAuthentificateurSMB() {
		return this.npa;
	}
}
