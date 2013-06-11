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
package com.locutus.outils.graphes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.locutus.exceptions.GraphConversionException;
import com.locutus.exceptions.modeles.GraphException;
import com.locutus.modeles.Concept;
import com.locutus.modeles.graphes.DiGraph;
import com.locutus.modeles.graphes.Node;

/**
 * 
 * @author Beugnon
 * 
 */
public class ConvertisseurXGMLGraphe {

	private SAXBuilder sxb;
	private Document document;
	private ArrayList<int[]> edges;
	private HashMap<int[], Node<String>> idNode;

	/**
	 * @param fl
	 * @param lcp
	 * @return un graphe/arborescence
	 * @throws GraphConversionException
	 * @throws FileNotFoundException
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static DiGraph<Concept> chargerGrapheConcept(File fl,
			List<Concept> lcp) throws GraphConversionException,
			FileNotFoundException, JDOMException, IOException {
		ConvertisseurXGMLGraphe cxg = new ConvertisseurXGMLGraphe(fl);
		DiGraph<String> dg = cxg.produceDiGraphByGXML(fl);
		if(dg!=null)
		return cxg.transpositionVersModele(dg, lcp);
		else
			throw new GraphConversionException("Impossible à créer");
	}

	/**
	 * 
	 * @param fl
	 */
	private ConvertisseurXGMLGraphe(File fl) {
		sxb = new SAXBuilder();
		document = null;
		try {
			document = sxb.build(new FileInputStream(fl));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		edges = new ArrayList<int[]>();
		idNode = new HashMap<int[], Node<String>>();

	}

	/**
	 * @param fl
	 * @throws GraphConversionException
	 */
	private DiGraph<String> produceDiGraphByGXML(File fl)
			throws GraphConversionException {

		DiGraph<String> dg = new DiGraph<String>();
		// Racine du document
		Element racine = document.getRootElement();

		// Itérateur des éléments du premier niveau
		Iterator<Element> it = racine.getChildren().iterator();

		// Element correspondant au graphe
		Element graph = null;
		while (it.hasNext() && graph == null) {

			Element local = it.next();

			// On récupère la section Graph
			if (local.getAttribute("name") != null
					&& local.getAttribute("name").getValue().equals("graph")) {

				// On a trouvé une section graphe
				graph = local;
			}
		}

		if (graph == null) {
			// Erreur car le fichier ne possède pas de section Graph au premier
			// niveaus
			throw new GraphConversionException(
					"Le fichier transmis n'est pas un graphe utilisable.");
		} else {
			this.traduireGraphe(dg, graph);
			if (idNode.size() > 0) {
				makeEdgesAndAdd(dg);

			} else {
				throw new GraphConversionException(
						"Le fichier ne contient aucun noeud.");
			}
		}

		return dg;
	}

	/**
	 * 
	 * @param graph
	 * @throws GraphConversionException
	 */
	private void traduireGraphe(DiGraph<String> dgc, Element graph)
			throws GraphConversionException {
		Iterator<Element> elemGraphIt = graph.getChildren().iterator();

		while (elemGraphIt.hasNext()) {

			// On va chercher dans cette catégorie les Nodes et les Edges
			Element local2 = elemGraphIt.next();

			if (local2.getAttribute("name") != null
					&& local2.getAttribute("name").getValue().equals("node")) {

				createNodeAndAdd(dgc, local2);

			} else if (local2.getAttribute("name") != null
					&& local2.getAttribute("name").getValue().equals("edge")) {

				createEdge(dgc, local2);

			}
		}
	}

	/**
	 * 
	 * @param dgc
	 * @param el
	 * @throws GraphConversionException
	 */
	private void createNodeAndAdd(DiGraph<String> dgc, Element el)
			throws GraphConversionException {
		// On crée un itérateur sur les informations du Node qu'on a
		// préalablement détecté.
		Iterator<Element> it3 = el.getChildren().iterator();
		// On prépare la récupération de l'id du node et du nomFichier du
		// Concept.
		int[] id = new int[1];
		String nomFichierConcept = null;

		while (it3.hasNext()) {

			Element local3 = it3.next();

			// On récupère l'id
			if (local3.getAttribute("key") != null
					&& local3.getAttribute("key").getValue().equals("id"))
				id[0] = Integer.parseInt(local3.getText());

			// On récupère le nom
			else if (local3.getAttribute("key") != null
					&& local3.getAttribute("key").getValue().equals("label"))
				nomFichierConcept = new String(local3.getText());

		}

		// Si le nom n'est pas vide
		if (nomFichierConcept != null) {
			idNode.put(id, new Node<String>(nomFichierConcept));
		} else {
			throw new GraphConversionException(
					"Il manque le nom fichier du concept pour le node en cours de traitement");
		}

	}

	/**
	 * 
	 * @param dgc
	 * @param local2
	 */
	private void createEdge(DiGraph<String> dgc, Element local2) {
		Iterator<Element> it3 = local2.getChildren().iterator();
		int[] arcId = new int[2];

		while (it3.hasNext()) {

			Element local3 = it3.next();

			if (local3.getAttribute("key") != null
					&& local3.getAttribute("key").getValue().equals("source"))
				arcId[0] = Integer.parseInt(local3.getText());
			else if (local3.getAttribute("key") != null
					&& local3.getAttribute("key").getValue().equals("target"))
				arcId[1] = Integer.parseInt(local3.getText());

		}
		edges.add(arcId);
	}

	/**
	 * 
	 * @param dg
	 */
	private void makeEdgesAndAdd(DiGraph<String> dg) {
		Iterator<int[]> idIt = idNode.keySet().iterator();
		while (idIt.hasNext()) {

			int[] local = idIt.next();
			try {
				dg.addNode(idNode.get(local));
			} catch (GraphException e) {
				e.printStackTrace();
			}
		}

		Iterator<int[]> edgesIt = edges.iterator();

		while (edgesIt.hasNext()) {

			int[] local = edgesIt.next();
			int[] t1 = new int[1];
			t1[0] = local[0];
			int[] t2 = new int[1];
			t2[0] = local[1];
			idIt = idNode.keySet().iterator();

			while (idIt.hasNext()) {

				int[] a = idIt.next();
				if (a[0] == t1[0]) {

					Node<String> n1 = idNode.get(a);
					Iterator<int[]> idIt2 = idNode.keySet().iterator();

					while (idIt2.hasNext()) {
						int[] b = idIt2.next();
						if (b[0] == t2[0]) {
							Node<String> n2 = idNode.get(b);
							try {
								dg.addArc(n1, n2);
							} catch (GraphException e) {
								System.out.println(e.getMessage());
							}
						}

					}

				}

			}

		}

	}

	/**
	 * 
	 * @param dg
	 * @param lcp
	 * @return
	 * @throws GraphConversionException
	 */
	private DiGraph<Concept> transpositionVersModele(DiGraph<String> dg,
			List<Concept> lcp) throws GraphConversionException {
		DiGraph<Concept> dgc = new DiGraph<Concept>();

		// NODES
		Iterator<Node<String>> it = dg.getNodes().iterator();
		while (it.hasNext()) {
			Node<String> nd = it.next();
			int i = 0;
			while (i < lcp.size()
					&& !lcp.get(i).getNomFichier().equals(nd.get())) {

				i++;
			}

			if (i < lcp.size()) {
				try {
					dgc.addNode(new Node<Concept>(lcp.get(i)));
				} catch (GraphException e) {
					e.printStackTrace();
					System.err.println(lcp.get(i) + "already exist");
				}
			}
		}

		// ARCS
		it = dg.getNodes().iterator();
		while (it.hasNext()) {
			Node<String> nd = it.next();
			int i = 0;
			while (i < lcp.size()
					&& !lcp.get(i).getNomFichier().equals(nd.get()))
				i++;

			if (i < lcp.size()) {
				try {
					Iterator<Node<String>> it2 = dg.getNodes().iterator();
					while (it2.hasNext()) {
						Node<String> nd2 = it2.next();
						int j = 0;
						while (j < lcp.size()
								&& !lcp.get(j).getNomFichier()
										.equals(nd2.get()))
							j++;

						if (j < lcp.size()) {
							if (dg.existArc(nd, nd2)) {
								dgc.addArc(dgc.getNodeByObject(lcp.get(i)),
										dgc.getNodeByObject(lcp.get(j)));
							}
						}

					}

				} catch (GraphException e) {
					e.printStackTrace();
					System.err.println(lcp.get(i) + "already exist");
				}
			}
		}

		Collections.sort(dgc.getNodes());
		Collections.sort(dgc.getArcs());
		Iterator<Node<Concept>> itNode = dgc.getNodes().iterator();
		while (itNode.hasNext()) {
			Node<Concept> local = itNode.next();
			Collections.sort(local.getStraightPredecessors());
			Collections.sort(local.getStraightSuccessors());
		}

		if (dgc.getNodeByObject(new Concept("origine", "origine (graphe)")) != null) {
			return dgc;
		} else {
			throw new GraphConversionException(
					"Ce graphe ne peut être utilisé comme arborescence de communication pour Locutus.");
		}
	}
}
