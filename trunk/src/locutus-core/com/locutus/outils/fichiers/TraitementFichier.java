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
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 
 * @author Beugnon Sebastien
 * @version 1.0
 * 
 * 
 * 
 */
public class TraitementFichier {

	/**
	 * 
	 * @param path
	 *            le chemin du fichier a copier accessible par File.
	 * @param path2
	 *            le chemin du lieu de recopie du fichier accessible par File.
	 * @param namefile
	 *            le nom du fichier a recopier.
	 * @param namefile2
	 *            le nouveau nom du fichier a recopier (<em>null</em> pour
	 *            garder le même nom).
	 * @param append
	 *            si on écrie à la suite le fichier indique par le chemin path2
	 *            ou non.
	 * 
	 */
	public static void copierFichierVers(String path, String path2,
			String namefile, String namefile2, boolean append) {
		File f1;

		if (!path.endsWith(File.separator)) {
			f1 = new File(path.concat(File.separator.concat(namefile)));
		} else {
			f1 = new File(path.concat(namefile));
		}

		File f2;

		if (!path2.endsWith(File.separator)) {
			if (namefile2 != null)
				f2 = new File(path2.concat(File.separator.concat(namefile2)));
			else
				f2 = new File(path2.concat(File.separator.concat(namefile)));

		} else {
			if (namefile2 != null)
				f2 = new File(path2.concat(namefile2));
			else
				f2 = new File(path2.concat(namefile));

		}
		if (!f1.isDirectory()) {
			try {
				f2.getParentFile().mkdirs();
				f2.createNewFile();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			try {
				InputStream in = new FileInputStream(f1);
				OutputStream out = new FileOutputStream(f2, append);

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();

			} catch (FileNotFoundException ex) {
				System.out.println(ex.getMessage()
						+ " dans le dossier specifie.");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			f2.mkdirs();
			recopieRecursive(path2, f1, null);
		}

	}

	/**
	 * 
	 * @param path
	 * @param fl
	 * @param parentLointain
	 */
	private static void recopieRecursive(String path, File fl,
			File parentLointain) {

		File recopiedossier;
		if (fl.isDirectory()) {

			if (parentLointain != null)
				recopiedossier = new File(parentLointain.getAbsolutePath()
						+ File.separator + fl.getName());
			else
				recopiedossier = new File(path + File.separator + fl.getName());

			recopiedossier.mkdirs();

			File[] fils = fl.listFiles();
			for (int i = 0; i < fils.length; i++) {
				recopieRecursive(path, fils[i], recopiedossier);
			}
		} else {
			if (parentLointain == null)
				TraitementFichier.copierFichierVers(fl.getParentFile()
						.getAbsolutePath(), path + File.separator,
						fl.getName(), null, false);
			else
				TraitementFichier.copierFichierVers(fl.getParentFile()
						.getAbsolutePath(), parentLointain.getAbsolutePath(),
						fl.getName(), null, false);
		}

	}

	/**
	 * 
	 * @param path
	 *            le chemin du fichier ou dossier a verifier.
	 * @param namefile
	 *            le nom du fichier a recopier (<em>null</em> pour verifier
	 *            seulement le chemin).
	 * @return True si le fichier ou le chemin existe, sinon False.
	 */
	public static boolean existeFichier(String path, String namefile) {
		File fl;
		if (namefile != null)
			if (!path.endsWith(File.separator)) {
				fl = new File(path.concat(File.separator.concat(namefile)));
			} else {
				fl = new File(path.concat(namefile));
			}
		else
			fl = new File(path);
		return fl.exists();
	}

	/**
	 * 
	 * @param path
	 *            le chemin du dossier du fichier a charger.
	 * @param namefile
	 *            le nom du fichier a charger.
	 * @return une instance File du fichier a charger.
	 * @throws FileNotFoundException
	 *             Si le fichier est introuvable ou le chemin est incorrect.
	 */
	public static File chargerFichier(String path, String namefile)
			throws FileNotFoundException {
		File fl;
		if (namefile != null)
			if (!path.endsWith(File.separator)) {
				fl = new File(path.concat(File.separator.concat(namefile)));
			} else {
				fl = new File(path.concat(namefile));
			}
		else
			fl = new File(path);

		if (!TraitementFichier.existeFichier(path, namefile)) {
			throw new FileNotFoundException();
		} else
			return fl;
	}

	/**
	 * 
	 * @param path
	 *            le chemin du dossier du fichier a enregistrer.
	 * @param namefile
	 *            le nom du fichier a enregistrer.
	 * @param src
	 *            l'instance d'une classe serializable a enregistrer.
	 * @throws IOException
	 *             les exceptions que peuvent soulever cette manipulation.
	 */
	public static void enregistrerFichier(String path, String namefile,
			Serializable src) throws IOException {
		if (!existeFichier(path, null)) {
			creerDossier(path, null);
		}
		File fl;
		if (!path.endsWith(File.separator)) {
			fl = new File(path.concat(File.separator.concat(namefile)));
		} else {
			fl = new File(path.concat(namefile));
		}
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(fl));
		oos.writeObject(src);
		oos.close();
	}

	/**
	 * 
	 * @param path
	 * @param namefile
	 * @throws IOException
	 */
	public static void retirerFichier(String path, String namefile)
			throws IOException {
		// TODO Auto-generated method stub
		if (existeFichier(path, namefile)) {
			if (TraitementFichier.chargerFichier(path, namefile).delete())
				;
			else if (TraitementFichier.chargerFichier(path, namefile)
					.isDirectory()) {
				String[] fils = TraitementFichier
						.chargerFichier(path, namefile).list();
				for (int i = 0; i < fils.length; i++)
					retirerFichier(path + File.separator + namefile, fils[i]);

				TraitementFichier.chargerFichier(path, namefile).delete();
			}

		} else {
			throw new IOException(
					"TraitementFichier:retirerFichier(): Fichier Introuvable "
							+ path + File.separator + namefile);
		}
	}

	/**
	 * 
	 * @param path
	 * @param namefile
	 */
	public static void creerDossier(String path, String namefile) {
		File fl;
		if (namefile != null) {
			if (!path.endsWith(File.separator)) {
				fl = new File(path.concat(File.separator.concat(namefile)));
			} else {
				fl = new File(path.concat(namefile));
			}
		} else {
			fl = new File(path);
		}

		if (!fl.exists()) {
			fl.mkdirs();
		}

	}
}
