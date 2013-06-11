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
package com.controleurs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.enums.FormatImage;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.outils.fichiers.TraitementFichier;
import com.locutus.outils.fichiers.UtilisationFichier;
import com.locutus.vue.pc.VueMessages;
import com.vue.pc.VueCollection;

/**
 * @author Sebastien Beugnon
 * 
 */
public class ModuleCollection extends ModuleInterne {

	/**
 * 
 */
	private Concept cpCourant;

	/**
	 * 
	 * @param mp
	 */
	public ModuleCollection(ModulePrincipal mp) {
		super(mp);
		init();
	}

	@Override
	public void init() {
		super.setVue(new VueCollection(this));
	}

	/**
	 * 
	 * @param courant
	 */
	public void setConceptCourant(Concept courant) {
		this.cpCourant = courant;
	}

	/**
	 * 
	 * @return le concept en cours de modification
	 */
	public Concept getConceptCourant() {
		return this.cpCourant;
	}

	/**
	 * 
	 */
	public void supConceptCourant() {
		if (VueMessages.afficherQuestionDansVue(getVue(),
				"Voulez-vous supprimer le concept "
						+ getConceptCourant().getNomConcept() + " ?") == VueMessages.YES_OPTION) {

			if (VueMessages
					.afficherQuestionDansVue(
							getVue(),
							"La suppression du concept \""
									+ getConceptCourant().getNomConcept()
									+ "\" détruirera tous les documents par défaut ou privées lié à ce concept,"
									+ " êtes-vous sûr de votre choix ? ") == VueMessages.YES_OPTION) {
				// SUPPRESSION DU CONCEPT

				// RESSOURCES PAR DEFAUT
				for (int i = 0; i < TypeRessource.values().length; i++) {

					if (TypeRessource.values()[i] != TypeRessource.sons) {

						int k = 0;
						while (k < FormatImage.values().length) {
							if (TraitementFichier.existeFichier("ressources"
									+ File.separator
									+ TypeRessource.values()[i]
									+ File.separator
									+ getConceptCourant().getGenre(),
									getConceptCourant().getNomFichier() + "."
											+ FormatImage.values()[i])) {
								try {
									TraitementFichier.retirerFichier(
											"ressources"
													+ File.separator
													+ TypeRessource.values()[i]
													+ File.separator
													+ getConceptCourant()
															.getGenre(),
											getConceptCourant().getNomFichier()
													+ "."
													+ FormatImage.values()[i]);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							k++;
						}

					} else {
						try {

							if (TraitementFichier.existeFichier("ressources"
									+ File.separator
									+ TypeRessource.values()[i], null)) {
								File fl = TraitementFichier.chargerFichier(
										"ressources" + File.separator
												+ TypeRessource.values()[i],
										null);

								for (int j = 0; j < fl.listFiles().length; j++) {
									try {
										if (TraitementFichier.existeFichier(fl
												.listFiles()[i]
												.getAbsolutePath(),
												getConceptCourant()
														.getNomFichier()
														+ ".mp3")) {
											TraitementFichier.retirerFichier(fl
													.listFiles()[i]
													.getAbsolutePath(),
													getConceptCourant()
															.getNomFichier()
															+ ".mp3");
										}
									} catch (IOException e) {
										e.printStackTrace();
										// ERREUR DE RETRAIT
									}

								}
							}

						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}

					}

				}

				// RESSOURCES PRIVEES
				File fl = null;
				try {

					if (TraitementFichier.existeFichier(OptionsProgramme
							.getOptionsCourantes().getCheminCourant(),
							"ressources")) {
						fl = TraitementFichier.chargerFichier(OptionsProgramme
								.getOptionsCourantes().getCheminCourant(),
								"ressources");

						for (int i = 0; i < fl.listFiles().length; i++) {
							for (int j = 0; j < TypeRessource.values().length; j++) {
								String end = null;
								if (TypeRessource.values()[j] == TypeRessource.sons)
									end = ".mp3";
								else {
									int k = 0;
									while (k < FormatImage.values().length) {
										if (TraitementFichier
												.existeFichier(
														fl.listFiles()[i]
																.getAbsolutePath()
																+ File.separator
																+ TypeRessource
																		.values()[j],
														getConceptCourant()
																.getNomFichier()
																+ "."
																+ FormatImage
																		.values().length))
											end = "."
													+ FormatImage.values().length;
										k++;
									}
								}
								if (end != null) {
									try {
										if (TraitementFichier.existeFichier(
												fl.listFiles()[i]
														.getAbsolutePath()
														+ File.separator
														+ TypeRessource
																.values()[j],
												getConceptCourant()
														.getNomFichier() + end))
											TraitementFichier
													.retirerFichier(
															fl.listFiles()[i]
																	.getAbsolutePath()
																	+ File.separator
																	+ TypeRessource
																			.values()[j],
															getConceptCourant()
																	.getNomFichier()
																	+ end);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				List<Concept> liste = UtilisationFichier.chargerListeConcepts();
				Iterator<Concept> it = liste.iterator();
				List<Concept> bis = new ArrayList<Concept>();
				while (it.hasNext()) {
					Concept local = it.next();
					if (!local.equals(getConceptCourant()))
						bis.add(local);
				}
				Collections.sort(bis);
				UtilisationFichier.enregistrerListeConcepts(bis);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				VueMessages
						.afficherMessageDansVue(null, "Suppression réussie.");

			}

		}

		getVue().chargerListeConcepts(UtilisationFichier.chargerListeConcepts());
		getModulePrincipal().getVue().setVisible(true);
	}

	/**
	 * @param nomConcept
	 * @param nomFichier
	 * @param photo
	 * @param genre
	 */
	public void modConceptCourant(String nomConcept, String nomFichier,
			boolean photo, TypeGenre genre) {

		if ((!nomConcept.equals(getConceptCourant().getNomConcept()) && !nomConcept
				.trim().equals(""))) {
			// ON CHANGE LE NOM DU CONCEPT
			if (VueMessages.afficherQuestionDansVue(getVue(),
					"Voulez-vous changer le nom du concept "
							+ getConceptCourant().getNomConcept() + " en "
							+ nomConcept + " ?") == VueMessages.YES_OPTION) {
				Concept newCp = new Concept(
						getConceptCourant().getNomFichier(), nomConcept);
				newCp.setGenre(getConceptCourant().getGenre());
				newCp.setPhotoPrivee(getConceptCourant().getPhotoPrivee());
				List<Concept> cpts = UtilisationFichier.chargerListeConcepts();
				cpts.remove(getConceptCourant());

				cpts.add(newCp);
				UtilisationFichier.enregistrerListeConcepts(cpts);
			} else {
				getVue().setConceptCourant(getConceptCourant());
			}

		}

		if ((!nomFichier.equals(getConceptCourant().getNomFichier()) && !nomFichier
				.trim().equals(""))) {
			// ON CHANGE LE NOM DE FICHIER DU CONCEPT
			// CONFIRMATION (?)

			if (VueMessages.afficherQuestionDansVue(getVue(),
					"Voulez-vous changer le nom-fichier du concept "
							+ getConceptCourant().getNomConcept() + " de "
							+ getConceptCourant().getNomFichier() + " en "
							+ nomFichier + " ?") == VueMessages.YES_OPTION) {
				// ON RECOPIE TOUS LES FICHIERS DE L'ANCIEN NOM SOUS LE NOUVEAU
				// NOM
				// DEFAUTS
				String end = null;
				for (int i = 0; i < TypeRessource.values().length; i++) {
					for (int j = 0; j < TypeGenre.values().length; j++) {
						if (TypeRessource.values()[i] == TypeRessource.sons) {
							end = ".mp3";
							try {
								for (int z = 0; z < TraitementFichier
										.chargerFichier(
												"ressources" + File.separator
														+ "voix", null)
										.listFiles().length; z++) {
									TraitementFichier
											.copierFichierVers(
													"ressources"
															+ File.separator
															+ "voix"
															+ File.separator
															+ TraitementFichier
																	.chargerFichier(
																			"ressources"
																					+ File.separator
																					+ "voix",
																			null)
																	.listFiles()[z]
																	.getName(),
													"ressources"
															+ File.separator
															+ "voix"
															+ File.separator
															+ TraitementFichier
																	.chargerFichier(
																			"ressources"
																					+ File.separator
																					+ "voix",
																			null)
																	.listFiles()[z]
																	.getName(),
													getConceptCourant()
															.getNomFichier()
															+ end, nomFichier
															+ end, false);
									try {
										TraitementFichier
												.retirerFichier(
														"ressources"
																+ File.separator
																+ "voix"
																+ File.separator
																+ TraitementFichier
																		.chargerFichier(
																				"ressources"
																						+ File.separator
																						+ "voix",
																				null)
																		.listFiles()[z]
																		.getName(),
														getConceptCourant()
																.getNomFichier()
																+ end);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
						} else {
							int k = 0;
							while (k < FormatImage.values().length) {
								if (TraitementFichier
										.existeFichier(
												"ressources"
														+ File.separator
														+ TypeRessource
																.values()[i]
														+ File.separator
														+ TypeGenre.values()[j],
												getConceptCourant()
														.getNomFichier()
														+ "."
														+ FormatImage.values()[k]))
									end = "." + FormatImage.values()[k];
								k++;
							}

						}
						if (TraitementFichier.existeFichier("ressources"
								+ File.separator + TypeRessource.values()[i]
								+ File.separator + TypeGenre.values()[j],
								getConceptCourant().getNomFichier() + end)) {
							TraitementFichier.copierFichierVers(
									"ressources" + File.separator
											+ TypeRessource.values()[i]
											+ File.separator
											+ TypeGenre.values()[j],
									"ressources" + File.separator
											+ TypeGenre.values()[i]
											+ File.separator
											+ TypeGenre.values()[j],
									getConceptCourant().getNomFichier() + end,
									nomFichier + end, false);
							try {
								TraitementFichier.retirerFichier(
										"ressources" + File.separator
												+ TypeRessource.values()[i]
												+ File.separator
												+ TypeGenre.values()[j],
										getConceptCourant().getNomFichier()
												+ end);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								// PROBLEMES DE RETRAIT DES RESSOURCES PAR
								// DEFAUT
							}
						}
					}

				}
				// PRIVEES
				File fl;
				try {
					fl = TraitementFichier.chargerFichier(OptionsProgramme
							.getOptionsCourantes().getCheminCourant(),
							"ressources");
					for (int i = 0; i < fl.listFiles().length; i++) {
						File local = fl.listFiles()[i];

						for (int k = 0; k < TypeRessource.values().length; k++) {
							for (int j = 0; j < TypeGenre.values().length; j++) {
								if (TypeRessource.values()[i] == TypeRessource.sons) {
									end = ".mp3";
									if (TraitementFichier
											.existeFichier(
													local.getAbsolutePath()
															+ TypeRessource
																	.values()[k],
													getConceptCourant()
															.getNomFichier()
															+ end)) {
										TraitementFichier.copierFichierVers(
												local.getAbsolutePath()
														+ TypeRessource
																.values()[k],
												local.getAbsolutePath()
														+ TypeRessource
																.values()[k],
												getConceptCourant()
														.getNomFichier() + end,
												nomFichier + end, false);
										TraitementFichier.retirerFichier(
												local.getAbsolutePath()
														+ TypeRessource
																.values()[k],
												getConceptCourant()
														.getNomFichier() + end);
									}
								} else {
									int z = 0;
									while (z < FormatImage.values().length) {
										if (TraitementFichier
												.existeFichier(
														local.getAbsolutePath()
																+ TypeRessource
																		.values()[k],
														getConceptCourant()
																.getNomFichier()
																+ "."
																+ FormatImage
																		.values()[i])) {
											TraitementFichier
													.copierFichierVers(
															local.getAbsolutePath()
																	+ TypeRessource
																			.values()[k],
															local.getAbsolutePath()
																	+ TypeRessource
																			.values()[k],
															getConceptCourant()
																	.getNomFichier()
																	+ FormatImage
																			.values()[i],
															nomFichier
																	+ FormatImage
																			.values()[i],
															false);
											TraitementFichier
													.retirerFichier(
															local.getAbsolutePath()
																	+ TypeRessource
																			.values()[k],
															getConceptCourant()
																	.getNomFichier()
																	+ FormatImage
																			.values()[i]);
										}

										z++;
									}

								}
							}

						}
					}

					// ON CREE LE concept DE REMPLACEMENT

					Concept newCp = new Concept(nomFichier, getConceptCourant()
							.getNomConcept());
					newCp.setGenre(getConceptCourant().getGenre());
					newCp.setPhotoPrivee(getConceptCourant().getPhotoPrivee());
					List<Concept> cpts = UtilisationFichier
							.chargerListeConcepts();
					cpts.remove(getConceptCourant());
					cpts.add(newCp);
					UtilisationFichier.enregistrerListeConcepts(cpts);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// ALERTE D'ERREURS POUR LES RESSOURCES PRIVEES
				} catch (IOException e) {
					e.printStackTrace();
					// PROBLEMES AU RETRAIT DE CERTAINS DOCUMENTS
				}

			}

			// MODIFICATION DU GENRE OU DU PHOTO PRIVEE
		}
		if (getConceptCourant().getPhotoPrivee() != photo) {
			String vis, vis2;
			if (getConceptCourant().getPhotoPrivee() == true) {
				vis = "privée";
				vis2 = "visible";
			} else {
				vis = "visible";
				vis2 = "privée";
			}

			if (VueMessages.afficherQuestionDansVue(getVue(),
					"Voulez-vous changer le statut des photographies du concept "
							+ getConceptCourant().getNomConcept() + " de "
							+ vis + " à " + vis2 + " ?") == VueMessages.YES_OPTION) {
				getConceptCourant().setPhotoPrivee(photo);
				Concept newCp = new Concept(
						getConceptCourant().getNomFichier(),
						getConceptCourant().getNomConcept());
				newCp.setGenre(getConceptCourant().getGenre());
				newCp.setPhotoPrivee(photo);
				List<Concept> cpts = UtilisationFichier.chargerListeConcepts();
				cpts.remove(getConceptCourant());

				cpts.add(newCp);
				UtilisationFichier.enregistrerListeConcepts(cpts);

			}
			getVue().getPanneau().charger();
		}

		if (!getConceptCourant().getGenre().equals(genre)) {
			// CONFIRMATION (?)
			if (VueMessages.afficherQuestionDansVue(getVue(),
					"Voulez-vous changer le genre du concept "
							+ getConceptCourant().getNomConcept() + " de "
							+ getConceptCourant().getGenre() + " à " + genre
							+ " ?") == VueMessages.YES_OPTION) {
				// LES RESSOURCES PAR DEFAUT CHANGE DE PLACE
				for (int i = 0; i < TypeRessource.values().length; i++) {

					File local;
					try {
						local = TraitementFichier.chargerFichier("ressources"
								+ File.separator + TypeRessource.values()[i]
								+ File.separator
								+ getConceptCourant().getGenre(), null);
						for (int j = 0; j < local.listFiles().length; j++)
							TraitementFichier
									.copierFichierVers(
											"ressources"
													+ File.separator
													+ TypeRessource.values()[i]
													+ File.separator
													+ getConceptCourant()
															.getGenre(),
											"ressources" + File.separator
													+ TypeRessource.values()[i]
													+ File.separator + genre,
											local.listFiles()[i].getName(),
											null, false);
						TraitementFichier.retirerFichier("ressources"
								+ File.separator + TypeRessource.values()[i]
								+ File.separator
								+ getConceptCourant().getGenre(),
								local.listFiles()[i].getName());
						getConceptCourant().setGenre(genre);
						List<Concept> cpts = UtilisationFichier
								.chargerListeConcepts();
						UtilisationFichier.enregistrerListeConcepts(cpts);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						// ERREUR AU DEPLACEMENT DES RESSOURCES PAR DEFAUT
					} catch (IOException e) {
						e.printStackTrace();
						// ERREUR AU RETRAIT DES FICHIER COPIEE
					}

				}

			}

		}
		getVue().chargerListeConcepts(UtilisationFichier.chargerListeConcepts());
	}

	/**
	 * 
	 * @param nomConcept
	 * @param nomFile
	 * @param photo
	 * @param genre
	 * @return True si et seulement si le concept a été rajouté correctement
	 */
	public boolean addConcept(String nomConcept, String nomFile, boolean photo,
			TypeGenre genre) {
		if (nomConcept.trim().equals("")) {
			VueMessages.afficherMessageDansVue(getVue(),
					"Nom du Concept obligatoire !");
			return false;
		} else if (nomFile.trim().equals("")) {
			VueMessages.afficherMessageDansVue(getVue(),
					"Nom du fichier obligatoire !");
			return false;
		} else {
			Concept test = new Concept(nomFile, nomConcept);
			test.setGenre(genre);
			test.setPhotoPrivee(photo);

			Iterator<Concept> it = UtilisationFichier.chargerListeConcepts()
					.iterator();
			while (it.hasNext()) {
				Concept local = it.next();
				if (local.getNomConcept().equals(test.getNomConcept())) {
					VueMessages.afficherMessageDansVue(getVue(),
							"Ce concept existe déjà.");
					return false;
				} else if (local.getNomFichier().equals(test.getNomFichier())) {
					VueMessages.afficherMessageDansVue(getVue(),
							"Nom de fichier est déjà utilisé.");
					return false;
				}
			}

			List<Concept> liste = new ArrayList<Concept>(
					UtilisationFichier.chargerListeConcepts());
			liste.add(test);
			Collections.sort(liste);

			UtilisationFichier.enregistrerListeConcepts(liste);
			return true;

		}
	}

	/**
	 * 
	 * @param cp
	 * @param te
	 * @return True si et seulement si la ressource a été correctement rajouté.
	 */
	public boolean addRessourceParDefaut(Concept cp, TypeRessource te) {
		File fl = VueMessages.choisirFichier(te);
		if (fl != null && fl.exists()) {

			String end = null;
			int i = 0;

			while (i < FormatImage.values().length) {
				if (fl.getName().endsWith(FormatImage.values()[i].toString()))
					end = "." + FormatImage.values()[i];
				i++;
			}

			if (end == null)
				return false;

			try {
				UtilisationFichier.addRessourceImageParDefaut(te, cp, fl);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	public void lancerExportationListeConceptTextuel() {
		JFileChooser fl = new JFileChooser();
		fl.setSelectedFile(new File(System.getProperty("user.dir")
				+ File.separator + "liste.txt"));
		int a = fl.showSaveDialog(null);

		if (a == JFileChooser.APPROVE_OPTION) {
			File f = fl.getSelectedFile();
			if (f != null) {

				try {
					f.createNewFile();
					PrintWriter pw = new PrintWriter(new FileOutputStream(f),
							false);

					List<Concept> l = UtilisationFichier.chargerListeConcepts();

					for (Concept cp : l)
						pw.println(cp.toString());
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public VueCollection getVue() {
		return (VueCollection) super.getVue();
	}

	/**
	 * @param nom
	 * @return une liste des concepts commençant par 'nom'
	 */
	public List<Concept> rechercheParNom(String nom) {
		if (nom.trim().equals("")) {
			List<Concept> a = UtilisationFichier.chargerListeConcepts();
			a.remove(new Concept("origine", "origine (graphe)"));
			return a;
		}

		List<Concept> rst = new ArrayList<Concept>();

		Iterator<Concept> it = UtilisationFichier.chargerListeConcepts()
				.iterator();
		while (it.hasNext()) {
			Concept local = it.next();
			int i = 0;
			while (i < nom.length()
					&& nom.length() <= local.getNomConcept().length()
					&& Character.toLowerCase(nom.charAt(i)) == Character
							.toLowerCase(local.getNomConcept().charAt(i)))
				i++;

			if (i == nom.length()
					&& !local
							.equals(new Concept("origine", "origine (graphe)")))
				rst.add(local);

		}
		return rst;
	}
}
