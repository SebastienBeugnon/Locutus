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
 * @author Beugnon
 * 
 * @param <E>
 */
public class Node<E> implements Comparable<Node<E>> {

	/**
	 * 
	 */
	private E box;
	/**
	 * 
	 */
	private List<Node<E>> successors;
	/**
	 * 
	 */
	private List<Node<E>> predecessors;

	/**
	 * 
	 * @param ob
	 */
	public Node(E ob) {
		box = ob;
		successors = new ArrayList<Node<E>>();
		predecessors = new ArrayList<Node<E>>();
	}

	/**
	 * @param nod
	 */
	public Node(Node<E> nod) {
		box = nod.get();
		successors = new ArrayList<Node<E>>();
		predecessors = new ArrayList<Node<E>>();
		successors.addAll(nod.getStraightSuccessors());
		predecessors.addAll(nod.getStraightPredecessors());
	}

	/**
	 * 
	 * @param ob
	 */
	public void set(E ob) {
		box = ob;
	}

	/**
	 * 
	 * @return the Element.
	 */
	public E get() {
		return box;
	}

	/**
	 * 
	 * @return The list of this's direct successor nodes.
	 */

	public List<Node<E>> getStraightSuccessors() {
		return this.successors;
	}

	/**
	 * 
	 * @param successor
	 * @throws GraphException
	 */
	protected void addSuccessor(Node<E> successor) throws GraphException {
		if (!this.getStraightSuccessors().contains(successor))
			this.getStraightSuccessors().add(successor);
		else
			throw new GraphException(4, this, successor, true);
	}

	/**
	 * 
	 * @param successor
	 * @throws GraphException
	 */
	protected void removeSuccessor(Node<E> successor) throws GraphException {
		if (this.getStraightSuccessors().contains(successor))
			this.getStraightSuccessors().remove(successor);
		else
			throw new GraphException(5, this, successor, true);
	}

	/**
	 * 
	 * @return The list of this's direct predecessor nodes.
	 */
	public List<Node<E>> getStraightPredecessors() {
		return this.predecessors;
	}

	/**
	 * 
	 * @param node
	 * @throws GraphException
	 */
	protected void addPredecessor(Node<E> node) throws GraphException {
		if (!this.getStraightPredecessors().contains(node))
			this.getStraightPredecessors().add(node);
		else
			throw new GraphException(4, node, this, true);

	}

	/**
	 * 
	 * @param node
	 * @throws GraphException
	 */
	protected void removePredecessor(Node<E> node) throws GraphException {
		if (this.getStraightPredecessors().contains(node))
			this.getStraightPredecessors().remove(node);
		else
			throw new GraphException(5, node, this, true);
	}

	/**
	 * 
	 */
	public void showContent() {
		System.out.println("box : " + get());
		System.out.print("successors : [");
		Iterator<Node<E>> it = getStraightSuccessors().iterator();
		while (it.hasNext()) {
			System.out.print(it.next().get());
			if (it.hasNext())
				System.out.print(",");
		}
		System.out.println("]");

		System.out.print("predecessors : [");
		it = getStraightPredecessors().iterator();
		while (it.hasNext()) {
			System.out.print(it.next().get());
			if (it.hasNext())
				System.out.print(",");
		}
		System.out.println("]");
	}

	/**
	 * 
	 * @param node
	 * @return true if the node is a direct predecessor of this's.
	 */
	public boolean isStraightPredecessorOf(Node<E> node) {
		return node.getStraightPredecessors().contains(this);
	}

	/**
	 * 
	 * @param node
	 * @return true if the node is a direct successor of this's.
	 */
	public boolean isStraightSuccessorOf(Node<E> node) {
		return node.getStraightSuccessors().contains(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return get().hashCode();
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
		Node<?> other = (Node<?>) obj;
		if (box == null) {
			if (other.box != null)
				return false;
		} else if (!box.equals(other.box))
			return false;
		if (predecessors == null) {
			if (other.predecessors != null)
				return false;
		} else if (!predecessors.equals(other.predecessors))
			return false;
		if (successors == null) {
			if (other.successors != null)
				return false;
		} else if (!successors.equals(other.successors))
			return false;
		return true;
	}

	/**
	 * 
	 * @param v
	 * @return true if the node are similar.
	 */
	public boolean equals(Node<E> v) {
		return v.get().equals(this.get());
	}

	/**
	 * @return The list of neighbour's nodes
	 */
	public List<Node<E>> getNeighbour() {
		List<Node<E>> neighbours = new ArrayList<Node<E>>();

		neighbours.addAll(getStraightSuccessors());
		neighbours.addAll(getStraightPredecessors());
		return neighbours;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Node<E> arg0) {
		return ((Comparable<E>) this.get()).compareTo((E) arg0.get());
	}

}
