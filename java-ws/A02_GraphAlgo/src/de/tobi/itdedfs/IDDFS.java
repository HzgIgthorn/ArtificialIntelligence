package de.tobi.itdedfs;

import java.util.Stack;

public class IDDFS {
	
	private Node target;
	private volatile boolean isFound;
	
	public IDDFS(Node target) {
		this.target = target;
	}
	
	public void runDeepeningSearch(Node root) {
		int depth = 0;
		while(!isFound) {
			System.out.println();
			dfs(root, depth++);
		}
	}

	private void dfs(Node root, int i) {
		Stack<Node> stack = new Stack<Node>();
		root.setDepthLevel(0);
		stack.push(root);
		
		while(!stack.isEmpty()) {
			Node aktNode = stack.pop();
			System.out.print(aktNode+" ");
			if(aktNode.getName().equals(this.target.getName())) {
				System.out.print("Node has been found...");
				this.isFound = true;
				return;
			}
			if(aktNode.getDepthLevel() >= i) {
				continue;
			}
			
			for(Node node : aktNode.getAdjList()) {
				node.setDepthLevel(aktNode.getDepthLevel()+1);
				stack.push(node);
			}
		}
		
	}

}
