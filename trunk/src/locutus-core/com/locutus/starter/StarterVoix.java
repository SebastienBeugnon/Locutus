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
package com.locutus.starter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.locutus.modeles.Voix;
import com.locutus.modeles.enums.TypeGenre;

/**
 * @author Sebastien Beugnon
 * 
 */
public class StarterVoix {

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void starter() throws FileNotFoundException, IOException {

		File fl = new File("ressources" + File.separator + "voix"
				+ File.separator + "femme1" + File.separator
				+ "Voix_femme1.bin");
		fl.getParentFile().mkdirs();
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(fl));
		oos.writeObject(new Voix("femme1", TypeGenre.FEMME));
		oos.flush();
		oos.close();

		fl = new File("ressources" + File.separator + "voix" + File.separator
				+ "homme1" + File.separator + "Voix_homme1.bin");
		fl.getParentFile().mkdirs();
		oos = new ObjectOutputStream(new FileOutputStream(fl));
		oos.writeObject(new Voix("homme1", TypeGenre.HOMME));
		oos.flush();
		oos.close();
	}

}
