package de.tobi.tabusearch;

import java.util.List;
import java.util.Queue;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class TabuList implements Constants{
	
	private Queue<State> tabuList;

	public TabuList() {
		super();
		this.tabuList = new CircularFifoQueue<State>(TABU_TENURE);
	}
	
	public void add(State state) {
		this.tabuList.add(state);
	}
	
	public boolean contains(State state) {
		return this.tabuList.contains(state);		
	}

	public List<State> getTabuList() {
		return IteratorUtils.toList(this.tabuList.iterator());
	}
}
