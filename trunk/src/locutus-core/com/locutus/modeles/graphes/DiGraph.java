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
package com.locutus.modeles.graphes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.locutus.exceptions.modeles.GraphException;

/**
 * 
 * @author Sebastien Beugnon
 * 
 * @param <E>
 *            The Type of the Graph, if we want to creat a graph of words (with
 *            String) or a graph of specific objects.
 */
public class DiGraph<E> {

	/**
	 * The graph's list of Nodes
	 */
	private List<Node<E>> nodes;
	/**
	 * The graph's list of Arcs.
	 */
	private List<Arc<E>> edges;

	/**
	 * Empty Constructor
	 */
	public DiGraph() {
		this.nodes = new ArrayList<Node<E>>();
		this.edges = new ArrayList<Arc<E>>();
	}

	/**
	 * 
	 * @param dg
	 * @throws GraphException
	 */
	public DiGraph(DiGraph<E> dg) throws GraphException {
		this();
		Iterator<Node<E>> it = dg.getNodes().iterator();
		while (it.hasNext())
			this.addNode(new Node<E>(it.next()));

		Iterator<Arc<E>> it2 = dg.getArcs().iterator();
		while (it2.hasNext())
			this.getArcs().add(new Arc<E>(it2.next()));

	}

	/**
	 * Method to add a node in the graph.
	 * 
	 * @param v1
	 *            The node which we want to add to the graph.
	 * @throws GraphException
	 *             if the node v1 is already in the graph's list of nodes.
	 */
	public void addNode(Node<E> v1) throws GraphException {
		if (!this.nodes.contains(v1))
			this.nodes.add(v1);
		else
			throw new GraphException(2, v1, true);
	}

	/**
	 * 
	 * @param v1
	 *            The node which we want to add a successor.
	 * @param v2
	 *            The node to add as a successor.
	 * @throws GraphException
	 *             if the node v1 isn't add to the graph's list of nodes.
	 */
	public void addSuccessorNode(Node<E> v1, Node<E> v2) throws GraphException {
		if (!this.getNodes().contains(v1))
			throw new GraphException(1, v1, true);
		else {
			if (!this.getNodes().contains(v2))
				addNode(v2);

			addArc(v1, v2);

		}
	}

	/**
	 * @return a new oriented graph without th nodes without Arcs.
	 * 
	 */
	public DiGraph<E> removeNodesWithoutArcs() {
		Iterator<Node<E>> it = this.getNodes().iterator();
		DiGraph<E> dgc;
		try {
			dgc = new DiGraph<E>(this);
			while (it.hasNext()) {
				Node<E> local = it.next();
				if (outgoingDegreeOf(local) == 0
						&& incomingDegreeOf(local) == 0)
					try {
						dgc.removeNode(local);
					} catch (GraphException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return dgc;
		} catch (GraphException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * @param object
	 * @return the Node with the object
	 */
	public Node<E> getNodeByObject(E object) {
		Iterator<Node<E>> it = this.getNodes().iterator();
		while (it.hasNext()) {
			Node<E> local = it.next();

			if (local.get().equals(object))
				return local;

		}
		return null;
	}

	/**
	 * 
	 * @param v1
	 * @param v2
	 * @throws GraphException
	 */
	public void addPredecessorNode(Node<E> v1, Node<E> v2)
			throws GraphException {
		if (!this.getNodes().contains(v2))
			addNode(v2);
		addSuccessorNode(v2, v1);
	}

	/**
	 * 
	 * @param v1
	 * @param v2
	 * @throws GraphException
	 */
	public void addArc(Node<E> v1, Node<E> v2) throws GraphException {
		if (!this.nodes.contains(v1))
			throw new GraphException(1, v1, true);
		if (!this.nodes.contains(v2))
			throw new GraphException(1, v2, true);

		Arc<E> ed = new Arc<E>(v1, v2);

		if (this.getArcs().contains(ed))
			throw new GraphException(3, v1, v2, true);
		else {
			this.getArcs().add(ed);
			v1.addSuccessor(v2);
			v2.addPredecessor(v1);

		}

	}

	/**
	 * 
	 * @return the list ofGraph's arcs.
	 */
	public List<Arc<E>> getArcs() {
		return this.edges;
	}

	/**
	 * 
	 * @return the list of Graph's nodes.
	 */
	public List<Node<E>> getNodes() {
		return this.nodes;
	}

	/**
	 * 
	 * @param v1
	 * @throws GraphException
	 */
	public void removeNode(Node<E> v1) throws GraphException {
		if (!this.getNodes().contains(v1)) {
			throw new GraphException(1, v1, true);
		} else {
			Iterator<Node<E>> stPrIt = v1.getStraightPredecessors().iterator();
			while (stPrIt.hasNext()) {
				stPrIt.next().removeSuccessor(v1);
			}

			Iterator<Node<E>> stSuIt = v1.getStraightSuccessors().iterator();
			while (stSuIt.hasNext()) {
				stSuIt.next().removePredecessor(v1);
			}

			this.edges = removeAllArcWith(v1);

			this.getNodes().remove(v1);
		}
	}

	/**
	 * 
	 * @param v1
	 * @return
	 * @throws GraphException
	 */
	private List<Arc<E>> removeAllArcWith(Node<E> v1) throws GraphException {
		if (!this.getNodes().contains(v1)) {
			throw new GraphException(1, v1, true);
		} else {
			List<Arc<E>> arcs = new ArrayList<Arc<E>>();
			Iterator<Arc<E>> it = this.getArcs().iterator();
			while (it.hasNext()) {
				Arc<E> arc = it.next();
				if (!(arc.getFirstNode().equals(v1) || arc.getSecondNode()
						.equals(v1))) {
					arcs.add(arc);
				}
			}
			return arcs;
		}
	}

	/**
	 * 
	 * @param v1
	 * @return the list of all predecessor nodes for node v1
	 * @throws GraphException
	 */
	public List<Node<E>> getAllPredecessorNodes(Node<E> v1)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			List<Node<E>> set = new ArrayList<Node<E>>();
			Iterator<Node<E>> it = v1.getStraightPredecessors().iterator();
			while (it.hasNext()) {
				Node<E> local = it.next();
				if (!set.contains(local)) {
					set.add(local);
					set.addAll(getAllPredecessorNodes(local, set));
				}
			}
			return (List<Node<E>>) onlyOneOccurrenceNode(set);
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v1
	 * @param set
	 * @return The list of all predecessor nodes for node v1
	 * @throws GraphException
	 */
	private List<Node<E>> getAllPredecessorNodes(Node<E> v1, List<Node<E>> set)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			Iterator<Node<E>> it = v1.getStraightPredecessors().iterator();
			while (it.hasNext()) {
				Node<E> local = it.next();
				if (!set.contains(local)) {
					set.add(local);
					getAllPredecessorNodes(local, set);
				}

			}

			return set;
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v1
	 * @return The list of all successor nodes for node v1
	 * @throws GraphException
	 */
	public List<Node<E>> getAllSuccessorNodes(Node<E> v1) throws GraphException {
		if (this.getNodes().contains(v1)) {
			List<Node<E>> set = new ArrayList<Node<E>>();
			Iterator<Node<E>> it = v1.getStraightSuccessors().iterator();
			while (it.hasNext()) {
				Node<E> local = it.next();
				if (!set.contains(local)) {
					set.add(local);
					set.addAll(getAllSuccessorNodes(local, set));
				}
			}
			return (List<Node<E>>) onlyOneOccurrenceNode(set);
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v1
	 * @param set
	 * @return The list of all successor nodes for node v1
	 * @throws GraphException
	 */
	private List<Node<E>> getAllSuccessorNodes(Node<E> v1, List<Node<E>> set)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			Iterator<Node<E>> it = v1.getStraightSuccessors().iterator();
			while (it.hasNext()) {
				Node<E> local = it.next();
				if (!set.contains(local)) {
					set.add(local);
					getAllSuccessorNodes(local, set);
				}

			}

			return set;
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v1
	 * @return The list of straight successor nodes for node v1
	 * @throws GraphException
	 */
	public List<Node<E>> getStraightPredecessorNodes(Node<E> v1)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			return v1.getStraightPredecessors();
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v1
	 * @return The list of straight predecessor nodes for node v1 by Arc
	 *         technique
	 * @throws GraphException
	 */
	public List<Node<E>> getStraightPredecessorNodesByArc(Node<E> v1)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			Iterator<Arc<E>> it = this.getArcs().iterator();
			List<Node<E>> set = new ArrayList<Node<E>>();
			while (it.hasNext()) {
				Arc<E> local = it.next();
				if (local.getSecondNode().equals(v1)) {
					set.add(local.getFirstNode());
				}
			}
			return set;
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v1
	 * @return The list of straight predecessor nodes for node v1
	 * @throws GraphException
	 */
	public List<Node<E>> getStraightSuccessorNodes(Node<E> v1)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			return v1.getStraightSuccessors();
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v2
	 * @return The list of straight predecessor nodes for node v1 by Arc
	 *         technique
	 * @throws GraphException
	 */
	public List<Node<E>> getStraightSuccessorNodesByArc(Node<E> v2)
			throws GraphException {
		if (this.getNodes().contains(v2)) {
			Iterator<Arc<E>> it = this.getArcs().iterator();
			List<Node<E>> set = new ArrayList<Node<E>>();
			while (it.hasNext()) {
				Arc<E> local = it.next();
				if (local.getFirstNode().equals(v2)) {
					set.add(local.getSecondNode());
				}
			}
			return set;
		} else
			throw new GraphException(1, v2, true);
	}

	/**
	 * 
	 * @param v1
	 * @return The list of All Arcs from node v1 by Arc Technique.
	 * @throws GraphException
	 */
	public List<Arc<E>> getAllArcsFromByArc(Node<E> v1) throws GraphException {
		if (this.getNodes().contains(v1)) {
			Iterator<Arc<E>> it = this.getArcs().iterator();
			List<Arc<E>> set = new ArrayList<Arc<E>>();
			while (it.hasNext()) {
				Arc<E> local = it.next();
				if (local.getFirstNode().equals(v1)
						|| local.getSecondNode().equals(v1)) {
					set.add(local);
				}
			}
			return set;
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * @param v1
	 * @return The list of all Arc from/to the node v1.
	 * @throws GraphException
	 */
	public List<Arc<E>> getAllArcsFrom(Node<E> v1) throws GraphException {
		if (this.getNodes().contains(v1)) {
			Iterator<Node<E>> it = v1.getStraightPredecessors().iterator();
			List<Arc<E>> set = new ArrayList<Arc<E>>();
			while (it.hasNext()) {
				set.add(new Arc<E>(it.next(), v1));
			}
			it = v1.getStraightSuccessors().iterator();
			while (it.hasNext()) {
				set.add(new Arc<E>(v1, it.next()));
			}
			return set;
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * @param v1
	 * @return The list of All incoming Arcs to node v1 by Arc technique
	 * @throws GraphException
	 */
	public List<Arc<E>> getAllIncomingArcsFromByArc(Node<E> v1)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			Iterator<Arc<E>> it = this.getArcs().iterator();
			List<Arc<E>> set = new ArrayList<Arc<E>>();
			while (it.hasNext()) {
				Arc<E> local = it.next();
				if (local.getSecondNode().equals(v1)) {
					set.add(local);
				}
			}
			return set;
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * @param v1
	 * @return The list of All outgoing Arcs from node v1 by Arc technique
	 * @throws GraphException
	 */
	public List<Arc<E>> getAllOutgoingArcsFromByArc(Node<E> v1)
			throws GraphException {
		if (this.getNodes().contains(v1)) {
			Iterator<Arc<E>> it = this.getArcs().iterator();
			List<Arc<E>> set = new ArrayList<Arc<E>>();
			while (it.hasNext()) {
				Arc<E> local = it.next();
				if (local.getFirstNode().equals(v1)) {
					set.add(local);
				}
			}
			return set;
		} else
			throw new GraphException(1, v1, true);
	}

	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return true if the Arc with the node v1 and v2 exists, else false.
	 * @throws GraphException
	 */
	public boolean existArc(Node<E> v1, Node<E> v2) throws GraphException {
		if (!this.getNodes().contains(v1))
			throw new GraphException(1, v1, true);
		if (!this.getNodes().contains(v2))
			throw new GraphException(1, v2, true);

		return this.getArcs().contains(new Arc<E>(v1, v2));

	}

	/**
	 * 
	 * @param arc
	 * @return true if the Arc arc exists, else false.
	 * @throws GraphException
	 */
	public boolean existArc(Arc<E> arc) throws GraphException {
		if (!this.getNodes().contains(arc.getFirstNode()))
			throw new GraphException(1, arc.getFirstNode(), true);
		if (!this.getNodes().contains(arc.getSecondNode()))
			throw new GraphException(1, arc.getSecondNode(), true);
		if (arc.getFirstNode().isStraightPredecessorOf(arc.getSecondNode())
				&& arc.getSecondNode()
						.isStraightSuccessorOf(arc.getFirstNode())) {
			return true;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * @return The Graphs's order.
	 */
	public int orderOfGraph() {
		return this.getNodes().size();
	}

	/**
	 * 
	 * @return The Graph's size.
	 */
	public int sizeOfGraph() {
		return this.getArcs().size();
	}

	/**
	 * 
	 * @param node
	 * @return The outgoing degree's node.
	 */
	public int outgoingDegreeOf(Node<E> node) {
		return node.getStraightPredecessors().size();

	}

	/**
	 * 
	 * @param node
	 * @return The incoming degree's node
	 */
	public int incomingDegreeOf(Node<E> node) {
		return node.getStraightPredecessors().size();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiGraph<?> other = (DiGraph<?>) obj;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	/**
	 * 
	 */
	public void showContent() {
		System.out.println("Nodes : ");
		Iterator<Node<E>> it = this.getNodes().iterator();

		while (it.hasNext()) {
			it.next().showContent();
			System.out.println("--- --- --- ---");
		}

		System.out.println("Arcs :");
		Iterator<Arc<E>> it2 = this.getArcs().iterator();
		while (it2.hasNext()) {
			it2.next().showContent();
			System.out.println("--- --- --- ---");
		}

	}

	/**
	 * 
	 * @param set
	 * @return
	 */
	private List<Node<E>> onlyOneOccurrenceNode(List<Node<E>> set) {
		List<Node<E>> l = new ArrayList<>();
		Iterator<Node<E>> it = set.iterator();
		while (it.hasNext()) {
			Node<E> local = it.next();
			if (!l.contains(local)) {
				l.add(local);
			}
		}
		return l;

	}

}
