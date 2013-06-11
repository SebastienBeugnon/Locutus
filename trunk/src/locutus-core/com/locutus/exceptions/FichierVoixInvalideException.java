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
package com.locutus.exceptions;

import java.io.File;

import jcifs.smb.SmbFile;

/**
 * @author Sebastien Beugnon
 * 
 */
public class FichierVoixInvalideException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private File directory;

	/**
	 * 
	 * @param fl
	 */
	public FichierVoixInvalideException(File fl) {
		this.directory = fl;
	}

	/**
	 * @param sm
	 */
	public FichierVoixInvalideException(SmbFile sm) {
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return "Dossier : " + directory.getName()
				+ " - Fichier Voix introuvable.";
	}

	/**
	 * @return une instance File du dossier dont le fichier est introuvable
	 */
	public File getDirectory() {
		return this.directory;
	}

}
