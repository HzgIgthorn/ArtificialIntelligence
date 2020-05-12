package de.tobi.qlearning;

public class QApp implements Constants{
	
	private static final double[][]R = {
			/*0*/		/*1*/		/*2*/		/*3*/		/*4*/		/*5*/
	/*0*/	{MIN_VALUE, MIN_VALUE, MIN_VALUE, MIN_VALUE, STATE_REWARD, MIN_VALUE},
	/*1*/	{MIN_VALUE, MIN_VALUE, MIN_VALUE, STATE_REWARD, MIN_VALUE, EXIT_REWARD},
	/*2*/	{MIN_VALUE, MIN_VALUE, MIN_VALUE, STATE_REWARD, MIN_VALUE, MIN_VALUE},
	/*3*/	{MIN_VALUE, STATE_REWARD, STATE_REWARD, MIN_VALUE, STATE_REWARD, MIN_VALUE},
	/*4*/	{STATE_REWARD, MIN_VALUE, MIN_VALUE, STATE_REWARD, MIN_VALUE, EXIT_REWARD},
	/*5*/	{MIN_VALUE, MIN_VALUE, MIN_VALUE, MIN_VALUE, MIN_VALUE, EXIT_REWARD}
	};

	public static void main(String[] args) {
		QAlgo algo = new QAlgo();
		algo.setR(R);
		algo.run();
		algo.showResult();
		algo.showPolicy();

	}

}
