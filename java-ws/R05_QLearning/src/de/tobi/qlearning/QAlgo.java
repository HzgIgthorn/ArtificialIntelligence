package de.tobi.qlearning;

import java.util.ArrayList;
import java.util.List;

public class QAlgo implements Constants{
	
	private double[][] R;
	
	private double[][] Q;
	
	public double[][] getR() {
		return R;
	}

	public void setR(double[][] r) {
		R = r;
		Q = new double[r.length][r.length];
	}

	public void run() {
		//Eine Episode ist wenn ein Agent in einem zufälligen Knoten startet und den Terminalknoten findet
		for(int i = 0; i < NUM_Of_ITERATIONS; i++) {
			int state = RAND.nextInt(NUM_STATES);
			//wir wollen allerdings nicht im Terminalknoten starten
			if(state==5)
				continue;
			simulate(state);
		}
		
	}

	public void showResult() {
		for(int r=0; r < this.Q.length; r++) {
			StringBuilder builder = new StringBuilder();
			for(int c=0; c < this.Q.length; c++) {
				builder.append(DDF.format(this.Q[r][c])+"  ");
			}
			System.out.println(builder.toString());
		}
		
	}

	public void showPolicy() {
		for(int i = 0; i < NUM_STATES; i++) {
			int state = i;
			StringBuilder builder = new StringBuilder("pi: " + state);
			while(state != 5) {
				int maxQState = 0;
				double maxQ = 0;
				
				for(int j=0; j < NUM_STATES; j++) {
					if(Q[state][j] > maxQ) {
						maxQ = Q[state][j];
						maxQState = j;
					}
				}
				builder.append(" -> " + maxQState);
				state = maxQState;
			}
			System.out.println(builder.toString());
		}
	}
	
	private void simulate(int state) {
		do {
			//Eine einzelne Episode: der Agent findet eine Pfad zum Terminalkonten
			List<Integer> possible = availableStates(state);
			//Wähle einen zufälligen nächsten Schritt
			int next = possible.get(RAND.nextInt(possible.size()));
			
			//maximaler Q Wert für den nächsten Schritt
			double maxQ = findMaxQ(next);
			
			//Q-Lerning Gleichung: Q[s][a] = Q[s][a] + alpha ( R[s][a] + gamma (max Q[s'][a'] - Q[s][a]))
			Q[state][next] = Q[state][next] + ALPHA*(R[state][next]+GAMMA*maxQ-Q[state][next]);
			
			//Agent geht in den nächsten Knoten
			state = next;
		}while(state != 5);

	}

	private double findMaxQ(int next) {
		double res = MIN_VALUE;
		for(int c = 0; c < this.Q.length; c++) {
			if(this.Q[next][c] > res)
				res = this.Q[next][c];
		}
		return res;
	}

	private List<Integer> availableStates(int state) {
		List<Integer> res = new ArrayList<>(NUM_STATES);
		//alle erreichbaren Punkte in die Liste res packen
		for(int c = 0; c < this.R.length; c++) {
			if(this.R[state][c] > MIN_VALUE) {
				res.add(c);
			}
		}
		return res;
	}
}
