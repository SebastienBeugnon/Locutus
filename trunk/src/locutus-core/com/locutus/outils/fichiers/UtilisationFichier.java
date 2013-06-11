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
package com.locutus.outils.fichiers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.jdom2.JDOMException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import com.locutus.exceptions.FichierVoixInvalideException;
import com.locutus.exceptions.GraphConversionException;
import com.locutus.exceptions.modeles.ConceptException;
import com.locutus.exceptions.modeles.UtilisateurException;
import com.locutus.modeles.Concept;
import com.locutus.modeles.OptionsProgramme;
import com.locutus.modeles.OptionsUtilisateur;
import com.locutus.modeles.ProfilListeConcept;
import com.locutus.modeles.Utilisateur;
import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.FormatImage;
import com.locutus.modeles.enums.TypeGenre;
import com.locutus.modeles.enums.TypeRessource;
import com.locutus.modeles.enums.TypeStatut;
import com.locutus.modeles.graphes.DiGraph;
import com.locutus.outils.graphes.ConvertisseurXGMLGraphe;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public class UtilisationFichier {

	/**
	 * 
	 * @param utilisateurs
	 */
	private static void enregistrerListeUtilisateur(
			List<Utilisateur> utilisateurs) {
		Collections.sort(utilisateurs);
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			try {

				TraitementFichier.enregistrerFichier(OptionsProgramme
						.getOptionsCourantes().getCheminCourant(),
						"utilisateurs.bin", (Serializable) utilisateurs);

			} catch (IOException e) {
				e.printStackTrace();
				System.err
						.println("UtilisationFichier:enregistrerListeUtilisateur():branche(Statut.Independant):"
								+ "IOException relevée à l'enregistrement de la liste des utilisateurs"
								+ " pour un programme au statut \"Independant\".");
			}
		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {

				TraitementSambaFichier.enregistrerFichier(

						OptionsProgramme.getOptionsCourantes()
								.getAuthentificateurSMB(),

						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()+"/local/",

						OptionsProgramme.getOptionsCourantes()
								.getCheminCourant(),

						"utilisateurs.bin",

						(Serializable) utilisateurs);

			} catch (IOException e) {
				System.err
						.println("UtilisationFichier:"
								+ "enregistrerListeUtilisateur():"
								+ "branche(Statut.DependantAuReseauSmb):"
								+ "IOException relevée à l'enregistrement de la liste des utilisateurs sur le réseau"
								+ " pour un programme au statut \""
								+ TypeStatut.DependantAuReseauSmb + "\".");
				e.printStackTrace();
			}

		} else {
			// =/= Independant et DependantReseauSmb
		}

	}

	/**
	 * 
	 * @return Une instance de List<Utilisateur> contenant les utilisateurs
	 *         stockés.
	 */
	public static List<Utilisateur> chargerListeUtilisateur() {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			try {

				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(
								TraitementFichier.chargerFichier(
										OptionsProgramme.getOptionsCourantes()
												.getCheminCourant(),
										"utilisateurs.bin")));

				@SuppressWarnings("unchecked")
				List<Utilisateur> liste = (List<Utilisateur>) ois.readObject();

				ois.close();

				return liste;
			} catch (IOException e) {
				e.printStackTrace();
				System.err
						.println("UtilisationFichier:chargerListeUtilisateur():branche(Statut.Independant):"
								+ "IOException relevée au chargement de la liste des utilisateurs.");
				// DECISION EFFACEE ET REFAIRE UNE NOUVELLE LISTE DE TEST ?

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.err
						.println("UtilisationFichier:chargerListeUtilisateur():branche(Statut.Independant):"
								+ "Classe Incorrecte.");
				// DECISION EFFACEE ET REFAIRE UNE NOUVELLE LISTE DE TEST ?
			}
		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {

				File fl = TraitementSambaFichier.chargerFichier(
						OptionsProgramme.getOptionsCourantes()
								.getAuthentificateurSMB(), OptionsProgramme
								.getOptionsCourantes().getCheminDistant()+"/local/",
						"utilisateurs.bin", OptionsProgramme
								.getOptionsCourantes().getCheminCourant());
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(fl));

				try {

					@SuppressWarnings("unchecked")
					List<Utilisateur> liste = (List<Utilisateur>) ois
							.readObject();
					return liste;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					ois.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
				System.err
						.println("UtilisationFichier:chargerListeUtilisateur():branche(Statut.Independant):"
								+ "IOException relevée au chargement de la liste des utilisateurs.");
				// DECISION EFFACEE ET REFAIRE UNE NOUVELLE LISTE DE TEST ?

			}
		} else {
			// AUTRE STATUTS
		}
		return null;
	}

	/**
	 * @param concepts
	 */
	public static synchronized void enregistrerListeConcepts(List<Concept> concepts) {
		Collections.sort(concepts);
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			try {

				TraitementFichier.enregistrerFichier(OptionsProgramme
						.getOptionsCourantes().getCheminCourant(),
						"concepts.bin", (Serializable) concepts);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {
				TraitementSambaFichier.enregistrerFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()+"/local/", OptionsProgramme
								.getOptionsCourantes().getCheminCourant(),
						"concepts.bin", (Serializable) concepts);

			} catch (IOException e) {
				System.err.println("Erreur enregistrement Liste Concept.");
				e.printStackTrace();
			}
		} else {
			// Autres Statuts.
		}
	}

	/**
	 * 
	 * @return une collection de Concept stocké dans le répertoire spécial du
	 *         programme
	 */
	@SuppressWarnings("unchecked")
	public static List<Concept> chargerListeConcepts() {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			File fl = null;

			ObjectInputStream ois = null;
			try {
				fl = TraitementFichier.chargerFichier(OptionsProgramme
						.getOptionsCourantes().getCheminCourant(),
						"concepts.bin");

				ois = new ObjectInputStream(new FileInputStream(fl));
				List<Concept> t = (List<Concept>) ois.readObject();
				ois.close();
				return t;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {
				File fl = TraitementSambaFichier.chargerFichier(
						OptionsProgramme.getOptionsCourantes()
								.getAuthentificateurSMB(), OptionsProgramme
								.getOptionsCourantes().getCheminDistant()+"/local/",
						"concepts.bin", OptionsProgramme.getOptionsCourantes()
								.getCheminCourant());
				ObjectInputStream ois = null;
				try {

					ois = new ObjectInputStream(new FileInputStream(fl));
					List<Concept> t = (List<Concept>) ois.readObject();
					ois.close();
					return t;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// Autre Statut
		}
		return null;
	}

	/**
	 * 
	 * @param nom
	 *            le nom de l'utilisateur
	 * @param prenom
	 *            le prénom de l'utilisateur
	 * @param voix
	 *            la voix sélectionée
	 * @param optionsUtilisateur
	 *            les options de l'utilisateur
	 * @return l'utilisateur ajouté
	 */
	public static Utilisateur addUtilisateur(String nom, String prenom,
			Voix voix, OptionsUtilisateur optionsUtilisateur) {
		List<Utilisateur> liste = chargerListeUtilisateur();

		Utilisateur ut = new Utilisateur(nom, prenom, voix, optionsUtilisateur);
		liste.add(ut);
		enregistrerListeUtilisateur(liste);
		return ut;
	}

	/**
	 * 
	 * @param utilisateur
	 *            l'utilisateur modifié
	 * @throws UtilisateurException
	 */
	public static void modUtilisateur(Utilisateur utilisateur)
			throws UtilisateurException {
		List<Utilisateur> liste = chargerListeUtilisateur();

		if (liste.contains(utilisateur)) {

			liste.remove(utilisateur);
			liste.add(utilisateur);
			enregistrerListeUtilisateur(liste);
		} else {

			throw new UtilisateurException(
					"UtilisationFichier:modUtilisateur():Utilisateur inexistant dans la liste : "
							+ utilisateur.getNom() + " "
							+ utilisateur.getPrenom());
		}
	}

	/**
	 * 
	 * @param utilisateur
	 * @return une collection d'utilisateur sans l'utilisateur contenu dans le
	 *         paramètre utilisateur
	 * @throws UtilisateurException
	 */
	public static List<Utilisateur> supUtilisateur(Utilisateur utilisateur)
			throws UtilisateurException {
		List<Utilisateur> liste = chargerListeUtilisateur();
		Iterator<Utilisateur> it = liste.iterator();
		while (it.hasNext()) {
			Utilisateur local = it.next();
			if (local.equals(utilisateur)) {
				liste.remove(local);
				enregistrerListeUtilisateur(liste);
				supDossiersRessourcesUtilisateur(utilisateur);
				return chargerListeUtilisateur();
			}
		}

		throw new UtilisateurException(
				"UtilisationFichier:SupUtilisateur(): Utilisateur inexistant dans la liste : "
						+ utilisateur.getNom() + " " + utilisateur.getPrenom());
	}

	/**
	 * 
	 * @param typeExercice
	 *            le type de l'image (Pictogramme/Image/Photographie)
	 * @param pictogramme
	 *            le pictogramme dont on veut l'image
	 * @param utilisateur
	 *            l'utilisateur courant
	 * @return une instance BufferedImage utilisable
	 */
	public static BufferedImage chargerImage(TypeRessource typeExercice,
			Concept pictogramme, Utilisateur utilisateur) {
		if (utilisateur != null
				&& !TypeRessource.sons.equals(typeExercice)
				&& existeRessourceUtilisateur(typeExercice, pictogramme,
						utilisateur)) {
			if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
				try {
					int i = 0;
					while (i < FormatImage.values().length) {
						if (TraitementFichier
								.existeFichier(
										OptionsProgramme
												.getOptionsCourantes()
												.getCheminCourant()
												.concat(File.separator
														+ "ressources"
														+ File.separator
														+ utilisateur
																.getIdDossier()
														+ File.separator
														+ typeExercice),
										pictogramme.getNomFichier() + "."
												+ FormatImage.values()[i])) {
							return ImageIO
									.read(TraitementFichier.chargerFichier(
											OptionsProgramme
													.getOptionsCourantes()
													.getCheminCourant()
													+ File.separator
													+ "ressources"
													+ File.separator
													+ utilisateur
															.getIdDossier()
													+ File.separator
													+ typeExercice.toString(),
											pictogramme
													.getNomFichier()
													.concat("."
															+ FormatImage
																	.values()[i]
																	.toString())));
						}
						i++;
					}
				} catch (IOException e) {
					System.err
							.println(pictogramme.getNomFichier()
									+ "- Pictogramme non chargé malgré que sa présence soit détectée.");
					e.printStackTrace();
				}
			} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
				try {
					int i = 0;
					while (i < FormatImage.values().length) {
						if (TraitementSambaFichier.existeFichier(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								OptionsProgramme.getOptionsCourantes()
										.getCheminDistant()
										+ "/local/ressources/"
										+ utilisateur.getIdDossier()
										+ "/"
										+ typeExercice.toString(),
								pictogramme.getNomFichier().concat(
										"." + FormatImage.values()[i])))
							return ImageIO
									.read(TraitementSambaFichier.chargerFichier(
											OptionsProgramme
													.getOptionsCourantes()
													.getAuthentificateurSMB(),

											OptionsProgramme
													.getOptionsCourantes()
													.getCheminDistant()
													+ "/local/ressources/"
													+ utilisateur
															.getIdDossier()
													+ "/"
													+ typeExercice.toString(),

											pictogramme
													.getNomFichier()
													.concat("."
															+ FormatImage
																	.values()[i]),

											OptionsProgramme
													.getOptionsCourantes()
													.getCheminCourant()
													+ File.separator
													+ "ressources"
													+ File.separator
													+ utilisateur
															.getIdDossier()
													+ File.separator
													+ typeExercice.toString()));
						i++;
					}
				} catch (IOException e) {
					// EXISTE FICHIER SAMBA MAIS PROBLEME CHARGEMENT
					System.err
							.println(pictogramme.getNomFichier()
									+ "- Pictogramme non chargé malgré que sa présence soit détectée.");
					e.printStackTrace();
					return null;
				}

			} else {
				// AUTRE STATUT
				return null;
			}

		} else {
			// CHARGEMENT DES RESSOURCES DANS LE DEFAUT
			if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
				int i = 0;
				while (i < FormatImage.values().length) {
					if (TraitementFichier.existeFichier(
							"ressources" + File.separator + typeExercice
									+ File.separator + pictogramme.getGenre()
									+ File.separator,
							pictogramme.getNomFichier() + "."
									+ FormatImage.values()[i])) {
						try {
							return ImageIO.read(TraitementFichier
									.chargerFichier(
											"ressources" + File.separator
													+ typeExercice
													+ File.separator
													+ pictogramme.getGenre()
													+ File.separator,
											pictogramme.getNomFichier() + "."
													+ FormatImage.values()[i]));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							return null;
						} catch (IOException e) {
							e.printStackTrace();
							return null;
						}
					}

					i++;
				}
			} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
				int i = 0;
				try {
					while (i < FormatImage.values().length) {

						if (TraitementSambaFichier.existeFichier(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								OptionsProgramme.getOptionsCourantes()
										.getCheminDistant()
										+ "/ressources/"
										+ typeExercice
										+ "/"
										+ pictogramme.getGenre(),
								pictogramme.getNomFichier().concat(
										"." + FormatImage.values()[i]))) {
							return ImageIO
									.read(TraitementSambaFichier
											.chargerFichier(
													OptionsProgramme
															.getOptionsCourantes()
															.getAuthentificateurSMB(),
													OptionsProgramme
															.getOptionsCourantes()
															.getCheminDistant()
															+ "/ressources/"
															+ typeExercice
															+ "/" + pictogramme

															.getGenre(),
													pictogramme
															.getNomFichier()
															.concat("."
																	+ FormatImage
																			.values()[i]),

													"ressources"
															+ File.separator
															+ typeExercice
															+ File.separator
															+ pictogramme
																	.getGenre()));
						}
						i++;

					}
				} catch (IOException e) {
					System.err.println("erreur chargement Defaut Smb");
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param pictogramme
	 *            le pictogramme dont on veut le fichier sonore
	 * @param utilisateur
	 *            l'utilisateur courant
	 * @return une instance File du fichier sonore voulu
	 */
	public static File chargerSon(Concept pictogramme, Utilisateur utilisateur) {

		if (existeRessourceUtilisateur(TypeRessource.sons, pictogramme,
				utilisateur)) {

			if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
				try {
					return TraitementSambaFichier.chargerFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(),
							OptionsProgramme.getOptionsCourantes()
									.getCheminDistant()
									+ "/local/ressources/"
									+ utilisateur.getIdDossier()
									+ "/"
									+ TypeRessource.sons.toString(),

							pictogramme.getNomFichier() + ".mp3",

							OptionsProgramme.getOptionsCourantes()
									.getCheminCourant()
									+ File.separator
									+ "ressources"
									+ File.separator
									+ utilisateur.getIdDossier()
									+ File.separator
									+ TypeRessource.sons.toString());
				} catch (IOException e) {
					// ERREUR AU CHARGEMENT DE LA RESSOURCE PRIVEE DE SON
					e.printStackTrace();
					return null;
				}

			} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
				try {
					return TraitementFichier.chargerFichier(OptionsProgramme
							.getOptionsCourantes().getCheminCourant()
							+ File.separator
							+ "ressources"
							+ File.separator
							+ utilisateur.getIdDossier()
							+ File.separator
							+ TypeRessource.sons.toString(),
							pictogramme.getNomFichier() + ".mp3");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}

		}
		// RESSOURCE PAR DEFAUT
		try {

			if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

				return TraitementFichier.chargerFichier("ressources"
						+ File.separator + "voix" + File.separator
						+ utilisateur.getVoix().getNom() + File.separator,
						pictogramme.getNomFichier() + ".mp3");

			} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

				return TraitementSambaFichier.chargerFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()
								+ "/ressources/voix/"
								+ utilisateur.getVoix().getNom() + "/",
						pictogramme.getNomFichier() + ".mp3", "ressources"
								+ File.separator + "voix" + File.separator
								+ utilisateur.getVoix().getNom());

			} else {
				return null;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param utilisateur
	 *            l'utilisateur auquel on veut créer le dossier de ressources
	 * @throws IOException
	 */
	public static void creerDossiersRessourcesUtilisateur(
			Utilisateur utilisateur) throws IOException {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			// On cre
			TraitementFichier.creerDossier(OptionsProgramme
					.getOptionsCourantes().getCheminCourant()
					+ File.separator
					+ "ressources", "" + utilisateur.getIdDossier());

			for (int i = 0; i < TypeRessource.values().length; i++) {

				TraitementFichier.creerDossier(OptionsProgramme
						.getOptionsCourantes().getCheminCourant()
						+ File.separator
						+ "ressources"
						+ File.separator
						+ ""
						+ utilisateur.getIdDossier(), TypeRessource.values()[i]
						.toString());

			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			TraitementFichier.creerDossier(OptionsProgramme
					.getOptionsCourantes().getCheminCourant()
					+ File.separator
					+ "ressources", "" + utilisateur.getIdDossier());

			for (int i = 0; i < TypeRessource.values().length; i++) {

				TraitementFichier.creerDossier(OptionsProgramme
						.getOptionsCourantes().getCheminCourant()
						+ File.separator
						+ "ressources"
						+ File.separator
						+ ""
						+ utilisateur.getIdDossier(), TypeRessource.values()[i]
						.toString());

			}

			TraitementSambaFichier.copierLocalVersFichier(OptionsProgramme
					.getOptionsCourantes().getAuthentificateurSMB(),
					OptionsProgramme.getOptionsCourantes().getCheminCourant()
							+ File.separator + "ressources",

					"" + utilisateur.getIdDossier(), // Dossier

					OptionsProgramme.getOptionsCourantes().getCheminDistant()
							+ "/local/ressources/", null, false);

		}

	}

	/**
	 * 
	 * @param utilisateur
	 */
	public static void supDossiersRessourcesUtilisateur(Utilisateur utilisateur) {
		// SUPPRESSION LOCAL ET RESEAU
		// ORDRE DE SUPPRESSION AUX AUTRES DEPENDANTS DE LEUR LOCAL

		// SUPPRESSION EN LOCAL
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			try {
				TraitementFichier.retirerFichier(OptionsProgramme
						.getOptionsCourantes().getCheminCourant()
						+ File.separator + "ressources",
						"" + utilisateur.getIdDossier());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			try {

				TraitementSambaFichier.retirerFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant() + "/local/ressources/", utilisateur
								.getIdDossier());
				try {
					TraitementFichier.retirerFichier(OptionsProgramme
							.getOptionsCourantes().getCheminCourant()
							+ File.separator + "ressources",
							"" + utilisateur.getIdDossier());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SmbException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @param typeExercice
	 * @param pictogramme
	 * @param utilisateur
	 * @param fichier
	 */
	public static void addRessourceUtilisateur(TypeRessource typeExercice,
			Concept pictogramme, Utilisateur utilisateur, File fichier) {
		// MODIFIER LOCAL
		String end = null;
		if (typeExercice != TypeRessource.sons) {
			int i = 0;
			while (i < FormatImage.values().length) {
				if (fichier.getName().endsWith(
						FormatImage.values()[i].toString()))
					end = ".".concat(FormatImage.values()[i].toString());

				i++;
			}

		} else if (typeExercice == TypeRessource.sons)
			end = ".mp3";
		else
			end = "";

		if (end != null && !end.equals("")) {
			TraitementFichier.copierFichierVers(
					fichier.getParentFile().getAbsolutePath(),
					OptionsProgramme
							.getOptionsCourantes()
							.getCheminCourant()
							.concat(File.separator + "ressources"
									+ File.separator
									+ utilisateur.getIdDossier()
									+ File.separator + typeExercice), fichier
							.getName(), pictogramme.getNomFichier() + end,
					false);

			if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
				// MODIFIER SUR RESEAU
				try {
					TraitementSambaFichier.copierLocalVersFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(),
							OptionsProgramme
									.getOptionsCourantes()
									.getCheminCourant()
									.concat(File.separator + "ressources"
											+ File.separator
											+ utilisateur.getIdDossier()
											+ File.separator + typeExercice),
							pictogramme.getNomFichier() + end,
							OptionsProgramme
									.getOptionsCourantes()
									.getCheminDistant()
									.concat("/local/ressources/"
											+ utilisateur.getIdDossier() + "/"
											+ typeExercice.toString()), null,
							false);
				} catch (IOException e) {
					System.err
							.println("UtilisationFichier:addRessourceUtilisateur():IOException relevé a la recopie de la ressource sur le reseau Samba.");
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 
	 * @param typeExercice
	 * @param pictogramme
	 * @param utilisateur
	 * @param fichier
	 * 
	 */
	public static void modRessourceUtilisateur(TypeRessource typeExercice,
			Concept pictogramme, Utilisateur utilisateur, File fichier) {
		addRessourceUtilisateur(typeExercice, pictogramme, utilisateur, fichier);
	}

	/**
	 * 
	 * @param typeExercice
	 * @param pictogramme
	 * @param utilisateur
	 * @return True si la suppression c'est bien déroulé, sinon False.
	 */
	public static boolean supRessourceUtilisateur(TypeRessource typeExercice,
			Concept pictogramme, Utilisateur utilisateur) {
		// SUPPRESSION LOCAL ET RESEAU
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			try {
				if (typeExercice.equals(TypeRessource.sons)
						&& TraitementFichier
								.existeFichier(
										OptionsProgramme
												.getOptionsCourantes()
												.getCheminCourant()
												.concat(File.separator
														+ "ressources"
														+ File.separator
														+ utilisateur
																.getIdDossier()
														+ File.separator
														+ typeExercice),
										pictogramme.getNomFichier() + ".mp3")) {
					TraitementFichier.retirerFichier(
							OptionsProgramme
									.getOptionsCourantes()
									.getCheminCourant()
									.concat(File.separator + "ressources"
											+ File.separator
											+ utilisateur.getIdDossier()
											+ File.separator + typeExercice),
							pictogramme.getNomFichier() + ".mp3");
					return true;

				} else {
					int i = 0;
					while (i < FormatImage.values().length) {
						if (TraitementFichier
								.existeFichier(
										OptionsProgramme
												.getOptionsCourantes()
												.getCheminCourant()
												.concat(File.separator
														+ "ressources"
														+ File.separator
														+ utilisateur
																.getIdDossier()
														+ File.separator
														+ typeExercice),
										pictogramme.getNomFichier() + "."
												+ FormatImage.values()[i])) {
							TraitementFichier.retirerFichier(
									OptionsProgramme
											.getOptionsCourantes()
											.getCheminCourant()
											.concat(File.separator
													+ "ressources"
													+ File.separator
													+ utilisateur
															.getIdDossier()
													+ File.separator
													+ typeExercice),
									pictogramme.getNomFichier() + "."
											+ FormatImage.values()[i]);
							return true;
						}
						i++;
					}

					return false;

				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err
						.println("UtilisationFichier:supRessourceUtilisateur(): Suppresion du fichier en local impossible ");
				return false;
			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {
				String end = null;
				System.out.println(typeExercice + pictogramme.getNomFichier());
				if (typeExercice.equals(TypeRessource.sons)
						&& TraitementSambaFichier.existeFichier(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								OptionsProgramme
										.getOptionsCourantes()
										.getCheminDistant()
										.concat("/local/ressources/"

										+ utilisateur.getIdDossier() + "/"
												+ typeExercice),
								pictogramme.getNomFichier() + ".mp3"))
					end = ".mp3";
				else {
					int k = 0;
					while (k < FormatImage.values().length) {
						if (TraitementSambaFichier.existeFichier(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								OptionsProgramme
										.getOptionsCourantes()
										.getCheminDistant()
										.concat("/local/ressources/"

										+ utilisateur.getIdDossier() + "/"
												+ typeExercice),
								pictogramme.getNomFichier() + "."
										+ FormatImage.values()[k]))
							end = "." + FormatImage.values()[k];
						k++;
					}
				}
				if (end == null) {
					// N'existe pas de ressource privée pour ce pictogramme
					// avec une fin
					System.out.println("erreur");
					return false;
				} else {

					TraitementSambaFichier.retirerFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(),
							OptionsProgramme
									.getOptionsCourantes()
									.getCheminDistant()
									.concat("/local/ressources/"
											+ utilisateur.getIdDossier() + "/"
											+ typeExercice),
							pictogramme.getNomFichier() + end);
					return true;
				}
			} catch (MalformedURLException | SmbException e) {
				e.printStackTrace();
				System.err
						.println("UtilisationFichier:supRessourceUtilisateur(): Suppresion du fichier sur le réseau Samba impossible ");
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param type
	 * @param pictogramme
	 * @param utilisateur
	 * @return True si la ressource existe sur le reseau ou en local, sinon
	 *         False.
	 */
	public static boolean existeRessourceUtilisateur(TypeRessource type,
			Concept pictogramme, Utilisateur utilisateur) {
		// DEPENDANT RESEAU-SPE && LOCAL

		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			if (TypeRessource.sons.equals(type))
				return TraitementFichier.existeFichier(
						OptionsProgramme
								.getOptionsCourantes()
								.getCheminCourant()
								.concat(File.separator + "ressources"
										+ File.separator
										+ utilisateur.getIdDossier()
										+ File.separator + type),
						pictogramme.getNomFichier() + ".mp3");
			else {
				int i = 0;
				while (i < FormatImage.values().length) {
					if (TraitementFichier.existeFichier(
							OptionsProgramme
									.getOptionsCourantes()
									.getCheminCourant()
									.concat(File.separator + "ressources"
											+ File.separator
											+ utilisateur.getIdDossier()
											+ File.separator + type),
							pictogramme.getNomFichier() + "."
									+ FormatImage.values()[i]))
						return true;
					i++;
				}
				return false;
			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {
				if (TypeRessource.sons.equals(type))
					return TraitementSambaFichier.existeFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant()
									.concat("/local/ressources/"

									+ utilisateur.getIdDossier() + "/" + type),
							pictogramme.getNomFichier() + ".mp3");
				else {
					int i = 0;
					while (i < FormatImage.values().length) {

						if (TraitementSambaFichier.existeFichier(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								OptionsProgramme
										.getOptionsCourantes()
										.getCheminDistant()
										.concat("/local/ressources/"

										+ utilisateur.getIdDossier() + "/"
												+ type),
								pictogramme.getNomFichier() + "."
										+ FormatImage.values()[i]))
							return true;

						i++;
					}
					return false;
				}

			} catch (MalformedURLException | SmbException e) {
				e.printStackTrace();
				System.err
						.println("UtilisationFichier:existeRessourceUtilisateur: Erreur a la demande de présence du fichier sur le réseau Samba.");
				return false;
			}
		} else {
			// POUR AUTRES STATUTS FUTURS
			return false;
		}

	}

	/**
	 * 
	 * @return une liste des voix présentes dans les ressources
	 * @throws FichierVoixInvalideException
	 */
	public static List<Voix> chargerListeVoix()
			throws FichierVoixInvalideException {
		List<Voix> li = new ArrayList<Voix>();

		if (OptionsProgramme.getOptionsCourantes().getStatut()
				.equals(TypeStatut.Independant)) {
			File fl;
			try {
				fl = TraitementFichier.chargerFichier("ressources"
						+ File.separator + "voix", null);
			} catch (Exception e) {
				return li;
			}

			File fls[] = fl.listFiles();
			for (int i = 0; i < fls.length; i++) {

				if (TraitementFichier.existeFichier(fls[i].getAbsolutePath(),
						"Voix_" + fls[i].getName() + ".bin")) {
					try {
						li.add(UtilisationFichier.chargerVoix(
								fls[i].getAbsolutePath() + File.separator,
								"Voix_" + fls[i].getName() + ".bin"));
					} catch (ClassNotFoundException e) {
						System.err
								.println("UtilisationFichier:chargerListeVoix(): Existe mais Erreur à la verification de la voix se trouvant dans le dossier  : "
										+ fls[i].getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					throw new FichierVoixInvalideException(fls[i]);
				}
			}
		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			SmbFile s;
			try {
				s = TraitementSambaFichier.getFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant() + "/ressources/", "voix/");
				SmbFile[] l = s.listFiles();
				for (SmbFile sm : l) {
					int size = sm.getName().length();
					String name = sm.getName().substring(0, size - 1);
					if (TraitementSambaFichier.existeFichier(OptionsProgramme
							.getOptionsCourantes().getAuthentificateurSMB(), sm
							.getPath(), "Voix_" + name + ".bin")) {
						TraitementSambaFichier.copierFichierVersLocal(
								OptionsProgramme.getOptionsCourantes()
										.getAuthentificateurSMB(),
								sm.getPath(), "Voix_" + name + ".bin",
								"ressources" + File.separator + "voix"
										+ File.separator + name, "Voix_" + name
										+ ".bin", false);

						try {
							li.add(chargerVoix("ressources" + File.separator
									+ "voix" + File.separator + name, "Voix_"
									+ name + ".bin"));
						} catch (ClassNotFoundException e) {
							System.err
									.println("UtilisationFichier:chargerListeVoix(): Existe mais Erreur à la verification de la voix se trouvant dans le dossier (SmbFile)  : "
											+ sm.getURL().getPath());
							e.printStackTrace();
						}
					} else {
						throw new FichierVoixInvalideException(sm);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return li;
	}

	/**
	 * 
	 * @param path
	 *            le chemin du fichier voix
	 * @param namefile
	 *            le nom du fichier voix
	 * @return une instance Voix
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Voix chargerVoix(String path, String namefile)
			throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				TraitementFichier.chargerFichier(path, namefile)));
		Voix o = (Voix) ois.readObject();
		ois.close();
		return o;
	}

	/**
	 * 
	 * @param voix
	 * @param local
	 * @return <em><b>true</b></em> si le fichier spécial pour que les voix
	 *         soient comptabilisées est présent, sinon <em><b>false</b></em>.
	 */
	public static boolean existeSonPour(Voix voix, Concept local) {

		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			return TraitementFichier.existeFichier("ressources"
					+ File.separator + "voix" + File.separator + voix.getNom(),
					local.getNomFichier() + ".mp3");

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			try {
				return TraitementSambaFichier.existeFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()
								+ "/ressources"
								+ "/voix"
								+ "/" + voix.getNom(), local.getNomFichier()
								+ ".mp3");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SmbException e) {
				e.printStackTrace();
			}

		} else {
			// Autre statut
		}
		return false;
	}

	/**
	 * @param utilisateur
	 * @return <em><b>true</b></em> si le fichier contenant la liste des favoris
	 *         et des derniers utilisées existe, sinon <em><b>false</b></em>.
	 */
	public static boolean existeProfilListeConcepts(Utilisateur utilisateur) {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant)

			return TraitementFichier.existeFichier(
					OptionsProgramme.getOptionsCourantes().getCheminCourant()
							+ File.separator + "ressources" + File.separator
							+ utilisateur.getIdDossier(),
					ProfilListeConcept.getNameFile(utilisateur));

		else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			try {
				return TraitementSambaFichier.existeFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()

						+ "/local/ressources/" + utilisateur.getIdDossier(),
						ProfilListeConcept.getNameFile(utilisateur));
			} catch (MalformedURLException | SmbException e) {
				e.printStackTrace();
				return false;
			}

		}
		return false;
	}

	/**
	 * 
	 * @param utilisateur
	 * @return une instance de ProfilListeConcept
	 */
	public static ProfilListeConcept chargerProfilListeConcepts(
			Utilisateur utilisateur) {
		if (UtilisationFichier.existeProfilListeConcepts(utilisateur)) {
			if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
				try {
					File fl = TraitementFichier.chargerFichier(OptionsProgramme
							.getOptionsCourantes().getCheminCourant()
							+ File.separator
							+ "ressources"
							+ File.separator
							+ utilisateur.getIdDossier(), ProfilListeConcept
							.getNameFile(utilisateur));
					try {
						ObjectInputStream is = new ObjectInputStream(
								new FileInputStream(fl));

						try {
							ProfilListeConcept local = (ProfilListeConcept) is
									.readObject();
							is.close();
							return local;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							return null;
						} finally {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
				try {
					File fl = TraitementSambaFichier.chargerFichier(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant()

							+ "/local/ressources/" + utilisateur.getIdDossier(),
							ProfilListeConcept.getNameFile(utilisateur),
							OptionsProgramme.getOptionsCourantes()
									.getCheminCourant()
									+ File.separator
									+ "ressources"
									+ File.separator
									+ utilisateur.getIdDossier());
					try {
						ObjectInputStream is = new ObjectInputStream(
								new FileInputStream(fl));

						try {
							ProfilListeConcept local = (ProfilListeConcept) is
									.readObject();
							is.close();
							return local;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							return null;
						} finally {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		} else {
			return null;
		}
		return null;
	}

	/**
	 * @param utilisateur
	 * @param plc
	 */
	public static void enregistrerProfilListeConcept(Utilisateur utilisateur,
			ProfilListeConcept plc) {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			try {
				TraitementFichier.enregistrerFichier(OptionsProgramme
						.getOptionsCourantes().getCheminCourant()
						+ File.separator
						+ "ressources"
						+ File.separator
						+ utilisateur.getIdDossier(), ProfilListeConcept
						.getNameFile(utilisateur), plc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			try {
				TraitementSambaFichier.enregistrerFichier(
						OptionsProgramme.getOptionsCourantes()
								.getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()
								+ "/local/ressources/"
								+ utilisateur.getIdDossier(), OptionsProgramme
								.getOptionsCourantes().getCheminCourant()

								+ File.separator
								+ "ressources"
								+ File.separator
								+ utilisateur.getIdDossier(),
						ProfilListeConcept.getNameFile(utilisateur), plc);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// Autre Statut
		}

	}

	/**
	 * 
	 * @param utilisateur
	 * @return true si et seulement si l'enfant possède un graphe au format
	 *         .xgml .
	 * @throws SmbException
	 * @throws MalformedURLException
	 * 
	 */
	public static boolean existeGrapheUtilisateur(Utilisateur utilisateur) {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			return TraitementFichier.existeFichier(
					OptionsProgramme.getOptionsCourantes().getCheminCourant()
							+ File.separator + "ressources" + File.separator
							+ utilisateur.getIdDossier(), "graphe.xgml");

		} else if (OptionsProgramme.getOptionsCourantes().getStatut()
				.equals(TypeStatut.DependantAuReseauSmb)) {
			try {
				return TraitementSambaFichier
						.existeFichier(OptionsProgramme.getOptionsCourantes()
								.getAuthentificateurSMB(), OptionsProgramme
								.getOptionsCourantes().getCheminDistant()

						+ "/local/ressources/" + utilisateur.getIdDossier(), "graphe.xgml");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SmbException e) {
				e.printStackTrace();
			}

		} else {
			return false;
		}
		return false;
	}

	/**
	 * @param utilisateur
	 * @return un graphe orienté de concept.
	 * @throws JDOMException
	 * @throws IOException
	 * @throws GraphConversionException
	 */
	public static DiGraph<Concept> chargerGrapheUtilisateur(
			Utilisateur utilisateur) throws GraphConversionException {

		if (OptionsProgramme.getOptionsCourantes().getStatut()
				.equals(TypeStatut.Independant)) {

			try {
				return ConvertisseurXGMLGraphe.chargerGrapheConcept(
						TraitementFichier.chargerFichier(OptionsProgramme
								.getOptionsCourantes().getCheminCourant()
								+ File.separator
								+ "ressources"
								+ File.separator + utilisateur.getIdDossier(),
								"graphe.xgml"), chargerListeConcepts());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
				return null;

			}

		} else {
			if (OptionsProgramme.getOptionsCourantes().getStatut()
					.equals(TypeStatut.DependantAuReseauSmb)) {

				try {
					List<Concept> c= UtilisationFichier.chargerListeConcepts();
					return ConvertisseurXGMLGraphe.chargerGrapheConcept(
							TraitementSambaFichier.chargerFichier(
									OptionsProgramme.getOptionsCourantes()
											.getAuthentificateurSMB(),
									OptionsProgramme.getOptionsCourantes()
											.getCheminDistant() + "/local/ressources/"

									+ utilisateur.getIdDossier(),
									"graphe.xgml",
									OptionsProgramme.getOptionsCourantes()
											.getCheminCourant()
											+ File.separator
											+ "ressources"
											+ File.separator
											+ utilisateur.getIdDossier()),

							c);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return null;
		}
		return null;

	}

	/**
	 * @param utilisateur
	 * @param fl
	 * @return True si et seulement si l'enregistrement du graphe s'est bien
	 *         passée.
	 */
	public static boolean enregistrerGrapheVenantDe(Utilisateur utilisateur,
			File fl) {
		if (OptionsProgramme.getOptionsCourantes().getStatut()
				.equals(TypeStatut.Independant)) {
			TraitementFichier.copierFichierVers(fl.getParentFile()
					.getAbsolutePath(), OptionsProgramme.getOptionsCourantes()
					.getCheminCourant()
					+ File.separator
					+ "ressources"
					+ File.separator + utilisateur.getIdDossier(),
					fl.getName(), "graphe.xgml", false);
			return true;

		} else if (OptionsProgramme.getOptionsCourantes().getStatut()
				.equals(TypeStatut.DependantAuReseauSmb)) {
			try {
				TraitementSambaFichier.copierLocalVersFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(), fl
						.getParentFile().getAbsolutePath(), fl.getName(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()

						+ "/local/ressources/"

						+ utilisateur.getIdDossier(), "graphe.xgml", false);
				return true;
			} catch (IOException e) {

				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	/**
	 * @param utilisateur
	 * @param fl
	 * @return True si l'enregistrement s'est bien passé, sinon False.
	 */
	public static boolean enregistrerGrapheVers(Utilisateur utilisateur, File fl) {

		if (OptionsProgramme.getOptionsCourantes().getStatut()
				.equals(TypeStatut.Independant)) {

			TraitementFichier.copierFichierVers(
					OptionsProgramme.getOptionsCourantes().getCheminCourant()
							+ File.separator + "ressources" + File.separator
							+ utilisateur.getIdDossier(),

					fl.getParentFile().getAbsolutePath(),

					"graphe.xgml", fl.getName(), false);
			return true;

		} else {
			if (OptionsProgramme.getOptionsCourantes().getStatut()
					.equals(TypeStatut.DependantAuReseauSmb)) {
				try {
					TraitementSambaFichier.copierFichierVersLocal(
							OptionsProgramme.getOptionsCourantes()
									.getAuthentificateurSMB(), OptionsProgramme
									.getOptionsCourantes().getCheminDistant()
									+ "/local/ressources/" + utilisateur.getIdDossier(),
							"graphe.xgml",
							fl.getParentFile().getAbsolutePath(), fl.getName(),
							false);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * @param cpt
	 * @throws ConceptException
	 */
	public static void supConcept(Concept cpt) throws ConceptException {
		if (chargerListeConcepts().contains(cpt)) {

			List<Utilisateur> liU = chargerListeUtilisateur();
			for (Utilisateur ut : liU) {

				for (TypeRessource tr : TypeRessource.values()) {
					supRessourceUtilisateur(tr, cpt, ut);
				}

			}

			List<Voix> liV;
			try {
				liV = chargerListeVoix();
				for (Voix v : liV) {
					supSonParDefaut(v, cpt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<Concept> liC = chargerListeConcepts();
			liC.remove(cpt);
		} else {
			throw new ConceptException(
					"UtilisationFichier:supConcept: concept inexistant."
							+ cpt.toString());
		}

	}

	/**
	 * 
	 * @param v
	 * @param cpt
	 */
	private static boolean supSonParDefaut(Voix v, Concept cpt) {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			if (existeSonPour(v, cpt)) {
				try {
					TraitementFichier.retirerFichier(
							"ressources" + File.separator + "voix"
									+ File.separator + v.getNom(),
							cpt.getNomFichier() + ".mp3");
					return true;
				} catch (IOException e) {
					return false;
				}
			} else {
				return false;
			}
		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			if (existeSonPour(v, cpt)) {
				try {
					TraitementSambaFichier.retirerFichier(OptionsProgramme
							.getOptionsCourantes().getAuthentificateurSMB(),
							OptionsProgramme.getOptionsCourantes()
									.getCheminDistant()
									+ "/ressources/"
									+ "voix" + "/" + v.getNom(),
							cpt.getNomFichier() + ".mp3");
					return true;
				} catch (IOException e) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			// Autre Statut
		}
		return false;

	}

	/**
	 * @param te
	 * @param cpt
	 * @return True si la ressource te existe par défaut pour le concept cpt.
	 */
	public static boolean existeRessourceImageParDefaut(TypeRessource te,
			Concept cpt) {

		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			int i = 0;

			while (i < FormatImage.values().length) {

				if (TraitementFichier.existeFichier(
						"ressources" + File.separator + te + File.separator
								+ cpt.getGenre(), cpt.getNomFichier() + "."
								+ FormatImage.values()[i]))
					return true;

				i++;
			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			int i = 0;

			while (i < FormatImage.values().length) {

				try {
					if (TraitementSambaFichier.existeFichier(OptionsProgramme
							.getOptionsCourantes().getAuthentificateurSMB(),
							OptionsProgramme.getOptionsCourantes()
									.getCheminDistant()
									+ "/ressources/"
									+ te
									+ "/" + cpt.getGenre(), cpt.getNomFichier()
									+ "." + FormatImage.values()[i]))
						return true;
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (SmbException e) {
					e.printStackTrace();
				}

				i++;
			}
		}
		return false;

	}

	/**
	 * @param te
	 * @param cpt
	 * @param fl
	 * @throws IOException
	 */
	public static void addRessourceImageParDefaut(TypeRessource te,
			Concept cpt, File fl) throws IOException {
		String end = null;
		int i = 0;

		while (i < FormatImage.values().length) {
			if (fl.getName().endsWith(FormatImage.values()[i].toString()))
				end = "." + FormatImage.values()[i];
			i++;
		}
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			if (te != TypeRessource.sons) {
				TraitementFichier.copierFichierVers(fl.getParentFile()
						.getAbsolutePath(), OptionsProgramme
						.getOptionsCourantes().getCheminCourant(),
						fl.getName(), cpt.getNomFichier() + end, false);
			} else {
				throw new IOException(
						"Mauvais type de ressource dans cette méthode");
			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			if (te != TypeRessource.sons) {
				TraitementSambaFichier.copierLocalVersFichier(
						OptionsProgramme.getOptionsCourantes()
								.getAuthentificateurSMB(),
						fl.getParentFile().getAbsolutePath(),
						fl.getName(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()
								+ "/ressources/"
								+ te.toString() + "/" + cpt.getGenre(),
						cpt.getNomFichier() + end, false);
			} else {
				throw new IOException(
						"Mauvais type de ressource dans cette méthode");
			}
		}
	}

	/**
	 * @param v
	 * @param cpt
	 * @param fl
	 * @throws IOException
	 */
	public static void addRessourceSonParDefaut(Voix v, Concept cpt, File fl)
			throws IOException {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			TraitementFichier.copierFichierVers(fl.getParentFile()
					.getAbsolutePath(), "ressources" + File.separator + "voix"
					+ File.separator + v.getNom(), fl.getName(),
					cpt.getNomFichier() + ".mp3", false);

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			TraitementSambaFichier.copierLocalVersFichier(OptionsProgramme
					.getOptionsCourantes().getAuthentificateurSMB(), fl
					.getParentFile().getAbsolutePath(), fl.getName(),
					OptionsProgramme.getOptionsCourantes().getCheminDistant()
							+ "/ressources/voix/" + v.getNom() + "/",
					cpt.getNomFichier() + ".mp3", false);

		}
	}

	/**
	 * @param voix
	 * @param genre
	 * @throws IOException
	 */
	public static void modVoix(Voix voix, TypeGenre genre) throws IOException {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			TraitementFichier.enregistrerFichier("ressources" + File.separator
					+ "voix" + File.separator + voix.getNom(),
					"Voix_" + voix.getNom() + ".bin", new Voix(voix.getNom(),
							genre));

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			TraitementSambaFichier.enregistrerFichier(OptionsProgramme
					.getOptionsCourantes().getAuthentificateurSMB(),
					OptionsProgramme.getOptionsCourantes().getCheminDistant()
							+ "/ressources/voix/" + voix.getNom() + "/",
					OptionsProgramme.getOptionsCourantes().getCheminCourant(),
					"Voix_" + voix.getNom() + ".bin", new Voix(voix.getNom(),
							genre));

		}
	}

	/**
	 * @param voix
	 * @throws IOException
	 */
	public static void addVoix(Voix voix) throws IOException {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			TraitementFichier.creerDossier("ressources" + File.separator
					+ "voix" + File.separator, voix.getNom());

			TraitementFichier.enregistrerFichier("ressources" + File.separator
					+ "voix" + File.separator + voix.getNom(),
					"Voix_" + voix.getNom() + ".bin", voix);

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {

			SmbFile s = TraitementSambaFichier.getFichier(OptionsProgramme
					.getOptionsCourantes().getAuthentificateurSMB(),
					OptionsProgramme.getOptionsCourantes().getCheminDistant()
							+ "/ressources/voix/" + voix.getNom() + "/", null);

			if (!s.exists())
				s.mkdirs();

			TraitementSambaFichier.enregistrerFichier(OptionsProgramme
					.getOptionsCourantes().getAuthentificateurSMB(),
					OptionsProgramme.getOptionsCourantes().getCheminDistant()
							+ "/ressources/voix/" + voix.getNom() + "/",
					OptionsProgramme.getOptionsCourantes().getCheminCourant(),
					"Voix_" + voix.getNom() + ".bin", voix);

		}
	}

	/**
	 * @param name
	 * @return une instance File
	 * @throws IOException
	 *             ,FileNotFound
	 */
	public static File chargerGui(String name) throws IOException {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {
			try {
				return TraitementFichier.chargerFichier("ressources"
						+ File.separator + "gui" + File.separator, name);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
				throw e;
			}
		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {
				return TraitementSambaFichier.chargerFichier(OptionsProgramme
						.getOptionsCourantes().getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant() + "/ressources/gui/", name,
						"ressources" + File.separator + "gui");
			} catch (IOException e) {
				e.printStackTrace();
				throw e;

			}
		} else {
			// Autre statut
		}
		return null;

	}

	/**
	 * @param pictogramme
	 * @param v
	 * @return une instance de File.
	 */
	public static File chargerSon(Concept pictogramme, Voix v) {
		if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.Independant) {

			try {
				return TraitementFichier.chargerFichier("ressources"
						+ File.separator + "voix" + File.separator + v.getNom()
						+ File.separator, pictogramme.getNomFichier() + ".mp3");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}

		} else if (OptionsProgramme.getOptionsCourantes().getStatut() == TypeStatut.DependantAuReseauSmb) {
			try {
				return TraitementSambaFichier.chargerFichier(
						OptionsProgramme.getOptionsCourantes()
								.getAuthentificateurSMB(),
						OptionsProgramme.getOptionsCourantes()
								.getCheminDistant()
								+ "/ressources/voix/"
								+ v.getNom() + "/", pictogramme.getNomFichier()
								+ ".mp3", "ressources" + File.separator
								+ "voix" + File.separator + v.getNom());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			// Autre Statut
			return null;
		}

	}
}
