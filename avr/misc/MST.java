package com.avanseus.avr.misc;

public class MST {
	public static final double SEPARATOR_MULTIPLIER = 2;
	private Edge[] edges;
	private Node[] nodes;

	public Edge[] getEdges() {
		return edges;
	}

	public void setEdges(Edge[] edges) {
		this.edges = edges;
	}

	public Node[] getNodes() {
		return nodes;
	}

	public void setNodes(Node[] nodes) {
		this.nodes = nodes;
	}

	public MST(double[] distances){
		edges = new Edge[distances.length];
		nodes = new Node[distances.length + 1];

		for (int i=0; i<nodes.length; i++){
			nodes[i] = new Node((byte)(i+1));
		}

		int j=0;
		for (int k=0; k<distances.length; k++){
			edges[k] = new Edge(nodes[j], nodes[j+1], distances[k]);
			j = j+1;
		}
	}

	public void sortEdgeList(){
		for (int j=edges.length - 1; j>0; j--){
			for (int i=0; i<j; i++){
				if (edges[i+1].length < edges[i].length ){
					Edge temp = edges[i];
					edges[i] = edges[i+1];
					edges[i+1] = temp;
				}
			}
		}
	}

	public int[] filterComponents(){
		double edgeLengthSum = 0.0, edgeLengthAverage;
		sortEdgeList();

		Edge[] edges = getEdges();
		MultiSet multiSet = new MultiSet(edges.length + 1);

		Node[] nodes = getNodes();
		for (int i=0; i<nodes.length; i++){
			multiSet.addItem(i, nodes[i].nodeId);
		}

		for (int i=0; i<edges.length; i++){
			edgeLengthSum = edgeLengthSum + edges[i].length;
		}
		edgeLengthAverage = edgeLengthSum / edges.length;

		for (int i=0; i<edges.length && edges[i].length < SEPARATOR_MULTIPLIER * edgeLengthAverage ; i++){
			multiSet.lookupAndMergeSets(edges[i].fromNode.nodeId, edges[i].toNode.nodeId);
			//multiSet.printMultiSet();
			//System.out.println();
		}

		int[] chosenComponents = multiSet.denseSetElements();
		System.out.print("Chosen components: ");
		for (int i=0; i<chosenComponents.length; i++){
			System.out.print(chosenComponents[i] + " ");
		}
		System.out.println();

		return chosenComponents;
	}



	public static void main(String args[]){
		double startTime = System.currentTimeMillis();
		double[] testDistances = {1, -1.5, 0.5, 0.5, -1.5, 1.5, 0, 1};
                //{98.5, 95.5, 96, 88, 92.5, 86.5, 80, 84, 73};
				//{13, 325, 358.5, 392, 427.5, 471.5, 523, 559.5, 590, 652, 697, 892};
				//{312, 33.5, 33.5, 35.5, 44, 51.5, 36.5, 30.5, 62, 45, 195};
				//{29, 74, 136, 166.5, 203, 254.5, 298.5, 334, 367.5, 401, 713};
				//{45.0, 62.0, 30.5, 36.5, 51.5, 44.0, 35.5, 33.5, 33.5, 312.0};
				//{3, 5, 3.5, 2, 1.5, 5, -12, 16.5, 1, 59};
		        //{10, 15, 10, 10, 10, 10, 10, 10, 50};
		MST mst = new MST(testDistances);
		mst.filterComponents();
		double endTime = System.currentTimeMillis();
		System.out.println("Time taken: " + (endTime - startTime));
	}
}
