package de.tobi.astar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarAlgo implements Constants{

	private Node[][] searchSpace;
	private Node root;
	private Node target;
	private List<Node> closedSet;
	private Queue<Node> openSet;
	
	public AStarAlgo() {
		this.searchSpace = new Node[NUM_ROWS][NUM_COLS];
		this.openSet = new PriorityQueue<Node> (new NodeComparator());
		this.closedSet = new ArrayList<>();
		init();
	}

	private void init() {
		for(int r = 0; r<NUM_ROWS; r++) {
			for(int c = 0; c<NUM_COLS; c++) {
				Node node = new Node(r,c);
				this.searchSpace[r][c]=node;
			}
		}
		
		this.searchSpace[1][7].setBlocked(true);
		this.searchSpace[2][3].setBlocked(true);
		this.searchSpace[2][4].setBlocked(true);
		this.searchSpace[2][5].setBlocked(true);
		this.searchSpace[2][6].setBlocked(true);
		this.searchSpace[2][7].setBlocked(true);
		
		this.root = this.searchSpace[3][3];
		this.target = this.searchSpace[1][6];
	}
	
	public List<Node> getAllNeigh(Node node) {
		List<Node> res = new ArrayList<Node>(4);
		int row = node.getrI();
		int col = node.getcI();
		List<Node> normList = new ArrayList<Node>(4);
		List<Node> diagList = new ArrayList<Node>(4);
		
		if(row-1>=0 && !this.searchSpace[row-1][col].isBlocked()) {
			normList.add(searchSpace[row-1][col]);
		}
		if(row+1<NUM_ROWS && !this.searchSpace[row+1][col].isBlocked()) {
			normList.add(searchSpace[row+1][col]);
		}
		if(col-1>=0 && !this.searchSpace[row][col-1].isBlocked()) {
			normList.add(searchSpace[row][col-1]);
		}
		if(col+1<NUM_COLS && !this.searchSpace[row][col+1].isBlocked()) {
			normList.add(searchSpace[row][col+1]);
		}
		int plusNorm = node.getG()+NORM_COST;
		normList.stream().
			filter(n -> n.getG() == 0 || plusNorm < n.getG()).
			peek(n -> n.setG(plusNorm)).
			peek(n -> n.setH(manhattenHeuristic(n, target))).
			forEach(n -> res.add(n));
		
		if(row-1>=0 && col-1>=0  && !this.searchSpace[row-1][col-1].isBlocked()) {
			diagList.add(searchSpace[row-1][col-1]);
		}
		if(row+1<NUM_ROWS && col-1>=0  && !this.searchSpace[row+1][col-1].isBlocked()) {
			diagList.add(searchSpace[row+1][col-1]);
		}
		if(row-1>=0 && col+1<NUM_COLS && !this.searchSpace[row-1][col+1].isBlocked()) {
			diagList.add(searchSpace[row-1][col+1]);
		}
		if(row+1<NUM_ROWS && col+1<NUM_COLS && !this.searchSpace[row+1][col+1].isBlocked()) {
			diagList.add(searchSpace[row+1][col+1]);
		}
		int plusDiag = node.getG()+DIAG_COST;
		diagList.stream().
		filter(n -> n.getG() == 0 || plusDiag < n.getG()).
		peek(n -> n.setG(plusDiag)).
		peek(n -> n.setH(manhattenHeuristic(n, target))).
		forEach(n -> res.add(n));
		
		return res;
	}
	
	public int manhattenHeuristic(Node n1, Node n2) {
		return (NORM_COST*(Math.abs(n1.getrI()-n2.getrI())+Math.abs(n1.getcI()-n2.getcI())));
	}

	public void showPath() {
		System.out.println("Kürzester Weg mit A*-Search");
		Node node = this.target;
		while(node!=null) {
			System.out.println(node);
			node = node.getParent();
		}
	}
	
	public void search() {
		root.setH(manhattenHeuristic(root, target));
		openSet.add(root);
		while(!openSet.isEmpty()) {
			Node aktNode = openSet.poll();
			System.out.println("aktNode ist: "+aktNode+" Parent is "+aktNode.getParent());
			if(aktNode.equals(target))
				return;
			((PriorityQueue<Node>)openSet).remove(aktNode);
			printQueue(new LinkedList<Node>(openSet));
			closedSet.add(aktNode);
			for(Node node: getAllNeigh(aktNode)) {
				if(closedSet.contains(node))
					continue;
				if(!openSet.contains(node))
					openSet.add(node);
				node.setParent(aktNode);
			}
		}
	}

	private void printQueue(LinkedList<Node> linkedList) {
		if(!linkedList.isEmpty()) {
			StringBuilder builder = new StringBuilder("In openSet: "+linkedList.remove().toString());
			while(!linkedList.isEmpty()) {
				builder.append(", "+linkedList.remove().toString());
			}
			System.out.println(builder.toString());
		}

		
	}

}
