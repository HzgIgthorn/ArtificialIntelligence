package de.tobi.markovdecision;

public class MarkovProcessValueIteration implements Constants{
	
	//Definition der V-Funktion
	private double v[][];
	//Update der V-Funktion
	private double vNext[][];
	//Speicher für den errechneten Nutzen
	private double use[][];
	//Speicher für die Entscheidungen
	private char pi[][];
	//Wir speichern den Fehlerterm
	private double delta = 0;
	//Anzahl der durchgeführten Iterationen
	private int n;
	public MarkovProcessValueIteration() {
		super();
		//Entscheidungsfunktion, die am Ende die Werte U, D, L und R haben kann
		pi=new char[NUM_ROWS][NUM_COLS];
		//Initialisiere die V-Funkiton mit den Werten 0
		v=new double[NUM_ROWS][NUM_COLS];
		//Initialisiere die V'-Funktion mit den Werten 0
		vNext= new double[NUM_ROWS][NUM_COLS];
		//Nutzenfunktion R(s',s)
		use = new double[NUM_ROWS][NUM_COLS];
		
		init();
	}
	
	private void init() {
		for(int r = 0; r < NUM_ROWS; r++) {
			for(int c = 0; c < NUM_COLS; c++) {
				use[r][c] = STATE_REWARD;
			}
		}
		use[0][3] = +1;
		use[1][3] = -1;
		use[1][1] = 0;
	}
	
	public void run() {
		do {
			copyArray(vNext, v);
			n++;
			delta = 0;
			
			for(int r = 0; r < NUM_ROWS; r++) {
				for(int c = 0; c < NUM_COLS; c++) {
					//Update der vNext Werte
					update(r,c);
					double error = Math.abs(vNext[r][c] - v[r][c]);
					//berechne der maximalen Fehlerterm in der Makrov-Matrix
					if(error > delta)
						delta = error;
				}
			}
		}while(delta > EPSILON && n < NUM_Of_ITERATIONS);
		
		printResults();
	}
	
	//Bellman-Gleichung für die Updates
	public void update(int row, int col) {
		double[] actions = new double[4];
		
		//+1, -1 oder Hindernisswert als Nutzen
		if((row==0 && col == 3) || (row == 1 && col == 3) || (row == 1 && col == 1)) {
			vNext[row][col] = use[row][col];
		}else {
			//wir berechnen die P(s'|s,a)*V(s') Werte
			actions[0] = ACTION_PROB*goUp(row, col) + ACTION_MISS_PROB*(goLeft(row, col)+goRight(row, col));
			actions[1] = ACTION_PROB*goDown(row, col) + ACTION_MISS_PROB*(goLeft(row, col)+goRight(row, col));
			actions[2] = ACTION_PROB*goLeft(row, col) + ACTION_MISS_PROB*(goUp(row, col)+goDown(row, col));
			actions[3] = ACTION_PROB*goRight(row, col) + ACTION_MISS_PROB*(goUp(row, col)+goDown(row, col));
		}
		
		int best = findMaxIndex(actions);
		
		vNext[row][col] = use[row][col]+GAMMA*actions[best];
		
		switch(best) {
			case 0:{
				pi[row][col] = 'U';
				break;
			}
			case 1:{
				pi[row][col] = 'D';
				break;
			}
			case 2:{
				pi[row][col] = 'L';
				break;
			}
			case 3:{
				pi[row][col] = 'R';
				break;
			}
		}
	}

	//einfache lineare Suche nach der besten Aktion für jede Zelle
	public int findMaxIndex(double[] actions) {
		int res = 0;
		for(int i = 1; i < actions.length; i++) {
			if(actions[i] > actions[res])
				res = i;
		}
		return res;
	}
	
	public double goUp(int row, int col) {
		//prüfe ob es möglich ist nach oben zu gehen, wenn nicht, gebe die aktuelle Zelle wieder
		if((row==0) || (row==2 && col==1))
			return v[row][col];
		return v[row-1][col];
	}
	
	public double goDown(int row, int col) {
		//prüfe ob es möglich ist nach unten zu gehen, wenn nicht, gebe die aktuelle Zelle wieder
		if((row==NUM_ROWS-1) || (row==0 && col==1))
			return v[row][col];
		return v[row+1][col];
	}
	
	public double goLeft(int row, int col) {
		//prüfe ob es möglich ist nach links zu gehen, wenn nicht, gebe die aktuelle Zelle wieder
		if((col==0) || (row==1 && col==2))
			return v[row][col];
		return v[row][col-1];
	}
	
	public double goRight(int row, int col) {
		//prüfe ob es möglich ist nach rechts zu gehen, wenn nicht, gebe die aktuelle Zelle wieder
		if((col==NUM_COLS-1) || (row==1 && col==0))
			return v[row][col];
		return v[row][col+1];
	}
	
	//Kopiere ein Array
	public void copyArray(double[][] sourArray, double[][] destArray) {
		for(int i = 0; i < sourArray.length; i++) {
			for(int j = 0; j < sourArray[i].length; j++) {
				destArray[i][j] = sourArray[i][j];
			}
		}
	}
	
	private void printResults() {
		StringBuilder builder = new StringBuilder("V(s) Werte nach "+ n +" Wiederholungen:\r\n");
		for(int r =0; r < NUM_ROWS; r++) {
			for(int c = 0; c < NUM_COLS; c++) {
				builder.append(DDF.format(v[r][c])+"\t");
			}
			builder.append("\r\n");
		}
		
		pi[0][3] = '+';
		pi[1][3] = '-';
		pi[1][1] = '@';
		
		builder.append("\r\n beste Strategie: \r\n");
		for(int r = 0; r < NUM_ROWS; r++) {
			for(int c = 0; c < NUM_COLS; c++) {
				builder.append(pi[r][c]+"   ");
			}
			builder.append("\r\n");
		}
		System.out.println(builder.toString());
	}
}