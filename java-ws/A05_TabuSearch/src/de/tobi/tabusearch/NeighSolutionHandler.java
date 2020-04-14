package de.tobi.tabusearch;

import java.util.List;

public class NeighSolutionHandler {
	
	public State getBestNeigh(State[][] states, List<State> neighStates, List<State> tabuStates) {
		neighStates.removeAll(tabuStates);
		
		if(neighStates.size() == 0)
			return states[100][100];
		
		State best = null;
		
		for(State state : neighStates) {
			if(best == null || state.getZ() < best.getZ())
				best = state;
		}
		
		System.out.println("Beste Lösung ist: " + best);
		
		return best;
	}

}
