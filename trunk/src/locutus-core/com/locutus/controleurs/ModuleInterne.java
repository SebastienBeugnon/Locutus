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

import com.locutus.vue.pc.VuePanneau;

/**
 * Fichier : ModuleInterne.java<br/>
 * Email : Sebastien Beugnon - sebastien.beugnon.pro[at]gmail.com <br/>
 * Projet : Locutus (Logiciel d'Apprentissage des Pictogrammes par les Images et
 * le Son.) Developpe pour l'Etablissement Educatif pour Adolescents
 * Poly-handicapes de Coste Rousse.<br/>
 * Adresse : 93 Avenue des Baronnes - Prades-Le-Lez 34730 <br/>
 * <br/>
 * Les modules internes consistent a gerer une scene particuliere (un menu, un
 * defilement, etc). Ils seront donner au Module Principal qui s'occupera de les
 * afficher.<br/>
 * 
 * 
 * @author Beugnon Sebastien - Chef de projet, Analyste, Developpeur
 */
public abstract class ModuleInterne extends ModuleAbstrait {

	/**
	 * explication variable
	 */
	private ModulePrincipal mp;

	/**
	 * Constructeur a partir du ModulePrincipal.
	 * 
	 * @param mp
	 */
	public ModuleInterne(ModulePrincipal mp) {
		this.mp = mp;
	}

	/**
	 * Methode servant a acceder au module principal.
	 * 
	 * @return le module principal du programme.
	 */
	public ModulePrincipal getModulePrincipal() {
		return this.mp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.controleurs.ModuleAbstrait#getVue()
	 */
	@Override
	public VuePanneau getVue() {
		return (VuePanneau) super.getVue();
	}

}
