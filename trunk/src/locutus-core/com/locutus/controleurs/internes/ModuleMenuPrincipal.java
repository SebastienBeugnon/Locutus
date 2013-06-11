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
 */
/**
 * Fichier : ModuleInterne.java<br/>
 * Email : Sébastien Beugnon - sebastien.beugnon.pro[at]gmail.com <br/>
 * Projet : Locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) Développé pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapés de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Pradès-Le-Lez 34730 <br/>
 * <br/>
 * Les modules internes consistent à gérer une scène particulière (un menu, un
 * défilement, etc). Ils seront donner au Module Principal qui s'occupera de les
 * afficher.<br/>
 * 
 * 
 * @author Copyright (C) 2013 - E.E.A.P. CosteRousse, Beugnon Sebastien (Chef de
 *         projet, Analyste, Développeur)
 */
package com.locutus.controleurs.internes;

import com.locutus.controleurs.ModuleInterne;
import com.locutus.controleurs.ModulePrincipal;
import com.locutus.vue.pc.VueMenuPrincipal;

/**
 * @author Sebastien Beugnon
 * 
 */
public abstract class ModuleMenuPrincipal extends ModuleInterne {

	/**
	 * 
	 * @param mp
	 */
	public ModuleMenuPrincipal(ModulePrincipal mp) {
		super(mp);

	}

	@Override
	public abstract void init();

	/**
	 * Accesseur
	 * 
	 * @return La Vue du menu principal.
	 */
	public VueMenuPrincipal getVue() {
		return ((VueMenuPrincipal) super.getVue());
	}

	/**
	 * 
	 */
	public void quitter() {
		System.exit(0);
	}

}
