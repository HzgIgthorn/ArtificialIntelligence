package de.tobi.tabusearch;

import java.util.List;

public class TabuSearch implements Constants{
	
	private State[][] states;
	private TabuList tabuList;
	private NeighSolutionHandler handler;
	
	public TabuSearch(State[][] states) {
		super();
		this.states = states;
		this.tabuList = new TabuList();
		this.handler = new NeighSolutionHandler();
	}
	
	public State solve(State initSol) {
		State best = initSol;
		State curr = initSol;
		
		int iCount = 0;
		
		while(iCount < NUM_ITER) {
			List<State> candiNeigh = curr.getNeigh();
			List<State> solutTabu = tabuList.getTabuList();
			
			State bestNeight = handler.getBestNeigh(states, candiNeigh, solutTabu);
					
			if(bestNeight.getZ() < best.getZ()) {
				best = bestNeight;
			}
			
			tabuList.add(bestNeight);
			
			curr = bestNeight;
			
			iCount++;
		}
		
		return best;
	}

}
