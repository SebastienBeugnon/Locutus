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

/**
 * 
 * @author Beugnon
 * 
 * @param <E>
 */
public class Arc<E> implements Comparable<Arc<E>> {

	/**
	 * 
	 */
	private Node<E> v1;
	/**
	 * 
	 */
	private Node<E> v2;

	/**
	 * 
	 * @param v1
	 * @param v2
	 */
	public Arc(Node<E> v1, Node<E> v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	/**
	 * 
	 * @param next
	 */
	public Arc(Arc<E> next) {
		this.v1 = next.getFirstNode();
		this.v2 = next.getSecondNode();
	}

	/**
	 * 
	 * @return The arc's first node
	 */
	public Node<E> getFirstNode() {
		return this.v1;
	}

	/**
	 * 
	 * @return The arc's second node
	 */
	public Node<E> getSecondNode() {
		return this.v2;
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
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
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
		Arc<?> other = (Arc<?>) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		return true;
	}

	/**
	 * 
	 */
	public void showContent() {
		System.out.println("Arc - From : " + getFirstNode().get().toString()
				+ " To : " + getSecondNode().get().toString());

	}

	@Override
	public int compareTo(Arc<E> o) {
		if (this.getFirstNode().compareTo(o.getFirstNode()) < 0)
			return -1;
		else if (this.getFirstNode().compareTo(o.getFirstNode()) > 0)
			return 1;
		else {
			if (this.getSecondNode().compareTo(o.getSecondNode()) < 0)
				return -1;
			else if (this.getSecondNode().compareTo(o.getSecondNode()) > 0)
				return 1;
			else
				return 0;
		}
	}
}
