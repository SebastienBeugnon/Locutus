/**
 * Copyright (C) 2013
	Sebastien Beugnon <sebastien.beugnon.pro[at]gmail.com>,
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
package com.locutus.controleurs;

import com.locutus.vue.pc.VueAbstraite;

/**
 * 
 * Fichier : ModuleAbstrait.java<br/>
 * Email : Sebastien Beugnon - sebastien.beugnon.pro[at]gmail.com <br/>
 * Projet : Locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) Developpe pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapes de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Prades-Le-Lez 34730 <br/>
 * <br/>
 * La classe VueAbstraite aux classes (ou les differents modules) qui en
 * heritent de stocker leur vue, la changer. Ainsi que la methode init qui sert
 * a l'initialisation des modules.
 * 
 * @author Beugnon Sebastien - Chef de projet, Analyste, Developpeur
 * 
 */

public abstract class ModuleAbstrait {
	/**
	 * Instance d'une vue de module.
	 */
	private VueAbstraite av;

	/**
	 * Methode abstraite redefinie au niveau des modules crees, cette methode
	 * doit etre inseree dans les constructeurs.
	 */
	public abstract void init();

	/**
	 * Methode servant a modifier la vue appartenant au module.
	 * 
	 * @param av
	 *            la nouvelle vue du module.
	 */
	public void setVue(VueAbstraite av) {
		this.av = av;
	}

	/**
	 * Methode servant a acceder a la vue du module courant.
	 * 
	 * @return la vue courante du module.
	 */
	public VueAbstraite getVue() {
		return this.av;
	}
}
