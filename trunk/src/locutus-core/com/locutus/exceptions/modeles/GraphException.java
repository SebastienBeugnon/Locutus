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
package com.locutus.exceptions.modeles;

import java.util.Date;

import com.locutus.modeles.graphes.Node;

/**
 * 
 * @author Sebastien Beugnon
 * 
 */
public class GraphException extends Exception {
	/**
	 * 
	 */
	public static final int NODE_NOT_EXIST = 1;
	/**
	 * 
	 */
	public static final int NODE_ALREADY_EXIST = 2;
	/**
	 * 
	 */
	public static final int ARC_ALREADY_EXIST = 3;
	/**
	 * 
	 */
	public static final int NODE_N1_ALREADY_SUCCESSOR_OF_N2 = 4;
	/**
	 * 
	 */
	public static final int NODE_N1_NOT_SUCCESSOR_OF_N2 = 5;

	private final int numErr;

	private final Node<?> v1;

	private final Node<?> v2;

	private final boolean fastLog;

	/**
	 * 
	 * @param i
	 * @param v2
	 * @param fastLog
	 */
	public GraphException(int i, Node<?> v2, boolean fastLog) {
		// TODO Auto-generated constructor stub
		// err 1 : Verticle Doesn't exist
		// err 2 : Verticle Already exist
		this.numErr = i;
		this.v1 = v2;
		this.v2 = null;
		this.fastLog = fastLog;
	}

	/**
	 * 
	 * @param i
	 * @param v1
	 * @param v2
	 * @param fastLog
	 */
	public GraphException(int i, Node<?> v1, Node<?> v2, boolean fastLog) {
		// TODO Auto-generated constructor stub
		// err 3 : Edge Already exist
		// err 4 : Node v2 Already exist in list of v1
		// err 5 : Node v2 doesn't exist in list of v1
		this.numErr = i;
		this.fastLog = fastLog;
		this.v1 = v1;
		this.v2 = v2;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();

		Date date = new Date();
		sb.append(date.toString());
		if (numErr == NODE_NOT_EXIST) {
			if (!fastLog) {

				sb.append("\n" + "GraphException relevée :");
				sb.append("Le noeud contenant l'objet "
						+ v1.get().toString()
						+ " a été utilisé dans une méthode d'un graphe sans exister dans l'ensemble de noeuds"
						+ " de ce dernier, relevant ainsi une erreur.");
			} else {
				sb.append(" - Noeud:non existant");
			}

		} else if (numErr == NODE_ALREADY_EXIST) {
			if (!fastLog) {
				sb.append("\n" + "GraphException relevée :");
				sb.append("Le noeud contenant l'objet "
						+ v1.get().toString()
						+ " a été rajouté à l'ensemble de noeuds du graphe une nouvelle fois"
						+ ", relevant ainsi une erreur.");
			} else {
				sb.append(" - Noeud:déjà existant");
			}
		} else if (numErr == NODE_ALREADY_EXIST) {
			if (!fastLog) {
				sb.append("\n" + "GraphException relevée :");
				sb.append("Le noeud contenant l'objet "
						+ v1.get().toString()
						+ " a été rajouté à l'ensemble de noeuds du graphe une nouvelle fois"
						+ ", relevant ainsi une erreur.");
			} else {
				sb.append(" - Noeud:déjà existant");
			}
		} else if (numErr == ARC_ALREADY_EXIST) {
			if (!fastLog) {
				sb.append("\n" + "GraphException relevée :");
				sb.append("L'arc contenant les noeuds aux objets respectifs "
						+ v1.get().toString()
						+ " et "
						+ v2.get().toString()
						+ " a été rajouté à l'ensemble des arcs du graphe une nouvelle fois"
						+ ", relevant ainsi une erreur.");
			} else {
				sb.append(" - Arc:déjà existant");
			}
		} else if (numErr == NODE_N1_ALREADY_SUCCESSOR_OF_N2) {
			if (!fastLog) {
				sb.append("\n" + "GraphException relevée :");
				sb.append("Le noeud contenant l'objet "
						+ v1.get().toString()
						+ " a subit une tentative pour rajouter en successeur le noeud contenant l'objet "
						+ v2.get().toString()
						+ " cependant ce dernier existe déjà dans la liste des successeurs"
						+ ", relevant ainsi une erreur.");
			} else {
				sb.append(" - Node:node2 déjà successeur node1");
			}
		} else if (numErr == NODE_N1_ALREADY_SUCCESSOR_OF_N2) {
			if (!fastLog) {
				sb.append("\n" + "GraphException relevée :");
				sb.append("Le noeud contenant l'objet "
						+ v1.get().toString()
						+ " a subit une tentative pour rajouter en successeur le noeud contenant l'objet "
						+ v2.get().toString()
						+ " cependant ce dernier existe déjà dans la liste des successeurs"
						+ ", relevant ainsi une erreur.");
			} else {
				sb.append(" - Node:node2 déjà successeur node1");
			}
		} else if (numErr == NODE_N1_NOT_SUCCESSOR_OF_N2) {
			if (!fastLog) {
				sb.append("\n" + "GraphException relevée :");
				sb.append("Le noeud contenant l'objet "
						+ v1.get().toString()
						+ " a subit une tentative pour rajouter en successeur le noeud contenant l'objet "
						+ v2.get().toString()
						+ " cependant ce dernier existe déjà dans la liste des successeurs"
						+ ", relevant ainsi une erreur.");
			} else {
				sb.append(" - Node:node2 déjà successeur node1");
			}
		}
		return sb.toString();

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
