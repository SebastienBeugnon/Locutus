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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

/**
 * 
 * @author Beugnon Sebastien
 * @version 1.0
 * 
 */
public class TraitementSambaFichier {

	/**
	 * 
	 * @param npa
	 *            l'authentificateur sur le réseau Samba.
	 * @param pathSmb
	 *            le chemin du dossier dans le réseau Samba.
	 * @param namefileSmb
	 *            le nom du fichier sur le réseau Samba.
	 * @param pathlocal
	 *            le chemin du dossier du fichier sur l'ordinateur.
	 * @param namefilelocal
	 *            le nom du fichier sur l'ordinateur. (<em>null</em>, si on
	 *            garde le même nom que le fichier smb)
	 * @param append
	 *            si on écrie à la suite le fichier indique par le chemin path2
	 *            ou non.
	 * @throws IOException
	 */
	public static void copierFichierVersLocal(NtlmPasswordAuthentication npa,
			String pathSmb, String namefileSmb, String pathlocal,
			String namefilelocal, boolean append) throws IOException {

		// FLUX/STREAMS
		SmbFileInputStream in = null; // canal d'entrée
		FileOutputStream out = null; // canal de sortie
		SmbFile destination = null;

		if (!pathSmb.endsWith("/"))
			destination = new SmbFile(pathSmb.concat("/".concat(namefileSmb)),
					npa);
		else
			destination = new SmbFile(pathSmb.concat("".concat(namefileSmb)),
					npa);

		// USELESS
		if (pathSmb == null || pathSmb.length() == 0) {
			System.out.println("Erreur path fichier destination");
		}

		if (namefileSmb == null || namefileSmb.length() == 0) {
			System.out.println("Erreur nom fichier destination");
		}
		//

		File file;
		if (namefilelocal != null) {
			if (pathlocal.endsWith(File.separator))
				file = new File(pathlocal.concat(namefilelocal));
			else
				file = new File(pathlocal.concat(File.separator
						.concat(namefilelocal)));
		} else {
			if (pathlocal.endsWith(File.separator))
				file = new File(pathlocal.concat(namefileSmb));
			else
				file = new File(pathlocal.concat(File.separator
						.concat(namefileSmb)));
		}
		if (destination.isDirectory()) {
			recopieFichierversLocalRecursif(npa,
					new SmbFile(destination.getPath() + "/", npa), file);
		} else {

			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			// Init
			in = new SmbFileInputStream(destination);
			out = new FileOutputStream(file);

			byte buffer[] = new byte[1024];
			int nbLecture;
			while ((nbLecture = in.read(buffer)) != -1) {
				out.write(buffer, 0, nbLecture);
			}

			// FERMETURE DES FLUX/STREAMS
			in.close();
			out.close();
		}
	}

	/**
	 * 
	 * @param npa
	 *            l'authentificateur sur le réseau Samba.
	 * @param pathlocal
	 *            le chemin du dossier du fichier sur l'ordinateur.
	 * @param namefilelocal
	 *            le nom du fichier sur l'ordinateur.
	 * @param pathSmb
	 *            le chemin du dossier dans le réseau Samba.
	 * @param namefileSmb
	 *            le nom du fichier dans le réseau Samba (<em>null</em> pour
	 *            garder le même nom).
	 * @param append
	 *            si on écrie à la suite le fichier indique par le chemin path2
	 *            ou non.
	 * @throws IOException
	 */
	public static void copierLocalVersFichier(NtlmPasswordAuthentication npa,
			String pathlocal, String namefilelocal, String pathSmb,
			String namefileSmb, boolean append) throws IOException {

		SmbFileOutputStream out = null; // canal d'entrée
		FileInputStream in = null; // canal de sortie
		SmbFile destination = null;

		if (namefileSmb != null) {
			if (!pathSmb.endsWith(File.separator))
				destination = new SmbFile(pathSmb.concat("/"
						.concat(namefileSmb)), npa);
			else
				destination = new SmbFile(pathSmb.concat("/"
						.concat(namefileSmb)), npa);
		} else {
			if (!pathSmb.endsWith(File.separator))
				destination = new SmbFile(pathSmb.concat("/"
						.concat(namefilelocal)), npa);
			else
				destination = new SmbFile(pathSmb.concat("/"
						.concat(namefilelocal)), npa);
		}

		File file;
		if (pathlocal.endsWith(File.separator))
			file = new File(pathlocal.concat(namefilelocal));
		else
			file = new File(pathlocal.concat(File.separator + (namefilelocal)));

		if (file.isDirectory()) {
			recopieLocalVersFichierRecursif(npa, file, destination);
		} else {

			if (!TraitementSambaFichier
					.existeFichier(npa, pathSmb, namefileSmb)) {
				destination.createNewFile();
			}
			// Init
			in = new FileInputStream(file);
			out = new SmbFileOutputStream(destination);

			byte buffer[] = new byte[1024];
			int nbLecture;
			while ((nbLecture = in.read(buffer)) != -1) {
				out.write(buffer, 0, nbLecture);
			}
			in.close();
			out.close();
		}
	}

	/**
	 * 
	 * @param npa
	 *            l'authenficateur sur le réseau Samba.
	 * @param pathSmb
	 *            le chemin du dossier du fichier sur le réseau Samba.
	 * @param namefileSmb
	 *            le nom du fichier sur le réseau Samba (<em>null</em> si on
	 *            veut vérifier le dossier).
	 * @return True si le fichier existe sur le réseau Samba, sinon False.
	 * @throws MalformedURLException
	 * @throws SmbException
	 */
	public static boolean existeFichier(NtlmPasswordAuthentication npa,
			String pathSmb, String namefileSmb) throws MalformedURLException,
			SmbException {
		SmbFile fi;
		if (namefileSmb != null) {
			if (pathSmb.endsWith("/"))
				fi = new SmbFile(pathSmb.concat(namefileSmb), npa);
			else
				fi = new SmbFile(pathSmb.concat("/".concat(namefileSmb)), npa);
		} else
			fi = new SmbFile(pathSmb, npa);
		return fi.exists();
	}

	/**
	 * @param npa
	 *            l'authenficateur sur le réseau Samba.
	 * @param pathSmb
	 *            le chemin du fichier sur le réseau Samba.
	 * @param namefileSmb
	 *            le nom du fichier sur le réseau Samba.
	 * @return un instance de SmbFile
	 * @throws IOException
	 */
	public static SmbFile getFichier(NtlmPasswordAuthentication npa,
			String pathSmb, String namefileSmb) throws IOException {
		if (TraitementSambaFichier.existeFichier(npa, pathSmb, namefileSmb)) {
			SmbFile fi;
			if (namefileSmb != null) {
				if (pathSmb.endsWith("/"))
					fi = new SmbFile(pathSmb.concat(namefileSmb), npa);
				else
					fi = new SmbFile(pathSmb.concat("/".concat(namefileSmb)),
							npa);
			} else {
				fi = new SmbFile(pathSmb, npa);
			}
			return fi;
		} else {
			throw new FileNotFoundException(
					"TraitementSambaFichier:getFichier:" + pathSmb
							+ namefileSmb + ":Fichier Smb Inexistant");
		}
	}

	/**
	 * 
	 * @param npa
	 *            l'authenficateur sur le réseau Samba.
	 * @param pathSmb
	 *            le chemin du dossier dans le réseau Samba
	 * @param namefileSmb
	 *            le nom du fichier sur le réseau Samba.
	 * @param pathlocal
	 *            le chemin du dossier du fichier sur l'ordinateur.
	 * @return une instance File utilisable
	 * @throws IOException
	 */
	public static File chargerFichier(NtlmPasswordAuthentication npa,
			String pathSmb, String namefileSmb, String pathlocal)
			throws IOException {
		if (TraitementSambaFichier.existeFichier(npa, pathSmb, namefileSmb)) {
			TraitementSambaFichier.copierFichierVersLocal(npa, pathSmb,
					namefileSmb, pathlocal, null, false);
			return TraitementFichier.chargerFichier(pathlocal, namefileSmb);

		} else {
			throw new SmbException(SmbException.ERROR_NO_DATA, true);
		}

	}

	/**
	 * 
	 * @param npa
	 *            l'authenficateur sur le réseau Samba.
	 * @param pathSmb
	 *            le chemin du fichier sur le réseau Samba.
	 * @param pathlocal
	 *            le chemin du dossier du fichier sur l'ordinateur.
	 * @param namefile
	 *            le nom du fichier.
	 * @param src
	 * @throws IOException
	 */
	public static void enregistrerFichier(NtlmPasswordAuthentication npa,
			String pathSmb, String pathlocal, String namefile, Serializable src)
			throws IOException {
		TraitementFichier.enregistrerFichier(pathlocal, namefile, src);

		TraitementSambaFichier.copierLocalVersFichier(npa, pathlocal, namefile,
				pathSmb, null, false);
	}

	/**
	 * @param npa
	 * @param pathSmb
	 * @param namefileSmb
	 * @throws SmbException
	 * @throws MalformedURLException
	 */
	public static void retirerFichier(NtlmPasswordAuthentication npa,
			String pathSmb, String namefileSmb) throws MalformedURLException,
			SmbException {
		if (TraitementSambaFichier.existeFichier(npa, pathSmb, namefileSmb)) {
			SmbFile fi;
			if (pathSmb.endsWith("/"))
				fi = new SmbFile(pathSmb.concat(namefileSmb), npa);
			else
				fi = new SmbFile(pathSmb.concat("/".concat(namefileSmb)), npa);

			fi.delete();
		}
	}

	private static void recopieLocalVersFichierRecursif(
			NtlmPasswordAuthentication npa, File file, SmbFile parent)
			throws IOException {
		if (!parent.exists())
			parent.mkdirs();

		for (File fls : file.listFiles()) {
			if (fls.isDirectory()) {
				SmbFile son = new SmbFile(parent.getPath() + "/"
						+ fls.getName(), npa);
				recopieLocalVersFichierRecursif(npa, fls, son);
			} else
				copierLocalVersFichier(npa, file.getAbsolutePath(),
						fls.getName(), parent.getPath(), null, false);
		}

	}

	private static void recopieFichierversLocalRecursif(
			NtlmPasswordAuthentication npa, SmbFile smbFile, File parent)
			throws SmbException, IOException {
		if (!parent.exists())
			parent.mkdirs();

		for (SmbFile sm : smbFile.listFiles()) {
			if (sm.isDirectory()) {
				File son = new File(parent.getAbsolutePath() + File.separator
						+ sm.getName());
				son.mkdirs();
				recopieFichierversLocalRecursif(npa, sm, son);
			} else {
				copierFichierVersLocal(npa, smbFile.getPath(), sm.getName(),
						parent.getAbsolutePath(), null, false);
			}
		}

	}
}
