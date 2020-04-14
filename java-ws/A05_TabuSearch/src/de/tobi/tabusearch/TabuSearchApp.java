package de.tobi.tabusearch;

public class TabuSearchApp implements Constants{

	public static void main(String[] args) {
		State[][] states = new State[NUM_VALUES][NUM_VALUES];
		
		int row = 0;
		int col = 0;
		
		for(double x = -10; x < 9.9; x+=0.1) {
			for(double y = -10; y < 9.9; y+=0.1) {
				states[row][col]=new State(x,y,CostFunktion.f(x, y));
				col++;
			}
			col = 0;
			row++;
		}
		
		//set neighbours for the first and last column
		for(int i = 0; i < NUM_VALUES; i++) {
			states[i][0].addNeigh(states[i][1]);
			states[i][NUM_VALUES-1].addNeigh(states[i][NUM_VALUES-2]);
		}
		
		//set neighbours for the first and last row
		for(int i = 0; i < NUM_VALUES; i++) {
			states[0][i].addNeigh(states[1][i]);
			states[NUM_VALUES-1][i].addNeigh(states[NUM_VALUES-2][i]);
		}
		
		//set neighbours for the middle nodes
		for(int i=1; i < NUM_VALUES-1; i++) {
			for(int j = 1; j < NUM_VALUES-1; j++) {
				states[i][j].addNeigh(states[i-1][j]);
				states[i][j].addNeigh(states[i+1][j]);
				states[i][j].addNeigh(states[i][j-1]);
				states[i][j].addNeigh(states[i][j+1]);
			}
		}
		
		TabuSearch tabuSearch = new TabuSearch(states);
		System.out.println(tabuSearch.solve(states[100][100]));

	}

}
