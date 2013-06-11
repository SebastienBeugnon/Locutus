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
package com.locutus.outils.son;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * 
 * @author Beugnon
 * 
 */
public class Son implements Runnable {
	/**
	 * 
	 */
	private Player player;

	/**
	 * 
	 * @param fichier
	 */
	public Son(File fichier) {
		try {
			this.player = new Player(new FileInputStream(fichier));
		} catch (FileNotFoundException | JavaLayerException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	@Override
	public void run() {

		try {
			this.player.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

}
