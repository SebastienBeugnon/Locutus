/**
 * Copyright (C) 2013
	S�bastien Beugnon <sebastien.beugnon.pro[at]gmail.com>,
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
package com.locutus.exceptions.modeles;

import com.locutus.modeles.Concept;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public class ProfilListeConceptException extends Exception {

	int err;
	Concept pic;

	/**
	 * @param i
	 * @param pic
	 */
	public ProfilListeConceptException(int i, Concept pic) {
		err = i;
		if (i == 1) {
			// Err 1 : D�j� pr�sent

		}

	}

	@Override
	public String getMessage() {
		if (err == 1)
			return super.getMessage() + "Existe Deja Dans Des Derniers Ajouts";
		else
			return super.getMessage();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
