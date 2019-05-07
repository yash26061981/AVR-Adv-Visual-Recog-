package com.avanseus.avr.misc;

public class Edge implements Comparable{
	public Node fromNode;
	public Node toNode;
	public double length;

	public Edge (Node fromNode, Node toNode, double length){
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.length = length;
	}

	@Override
	public int compareTo(Object edge) {
		if (this.length > ((Edge) edge).length){
			return 1;
		}
		else if (this.length < ((Edge) edge).length){
			return -1;
		}
		else{
			return 0;
		}
	}
}