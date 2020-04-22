package de.tobi.hopfield;

public class HopfieldApp {

	public static void main(String[] args) {
		HopfieldNet net = new HopfieldNet(4);
		net.train(new double[] {1, 0, 1, 0});
		net.train(new double[] {1, 1, 1, 1});
		net.recal(new double[] {1, 0, 1, 0});
		net.recal(new double[] {0, 0, 0, 1});
		
		double[][] test = new double[3][4];
		test[2][2] = 1;
		test[2][1] = 1;
		test = Matrix.clearDiagonal(test);
		System.out.println(Matrix.toString(test));
	}
}
