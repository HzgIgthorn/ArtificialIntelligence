package de.tobi.hopfield;

class Matrix {
	public static double[] matrixMulti(double[][] A, double[] x) {
		if(A[0].length != x.length)
			throw new IllegalArgumentException("Matrix hat "+A[0].length+" und Vektor hat "+x.length+" Zeilen");
		double[]res = new double[A.length];
		
		for(int r = 0; r < A.length; r++) {
			double sum = 0;
			for(int c = 0; c < x.length; c++) {
				sum += A[r][c]*x[c];
			}
			res[r]=sum;
		}
		return res;
	}
	
	public static double[][] createMatrix(int rows, int cols){
		return new double[rows][cols];
	}
	
	public static double[][] outerProduct(double[] x) {
		double[][] res = new double[x.length][x.length];
		for(int r=0; r < x.length; r++) {
			for(int c=0; c < x.length;c++) {
				res[r][c]=x[r]*x[c];
			}
		}
		return res;
	}
	
	public static double[][] clearDiagonal(double[][]A){
		double[][] res = new double [A.length][A[0].length];
		for(int r = 0; r < A.length; r++) {
			for(int c = 0; c < A[0].length; c++) {
				if(c==r)
					res[r][c]=0;
				else
					res[r][c]=A[r][c];
			}
		}
		return res;
	}
	
	public static double[][] addMatrix(double[][]A, double[][]B){
		double[][] res =  new double[A[0].length][A.length];
		
		for(int r=0; r<A[0].length; r++) {
			for(int c=0; c<A.length; c++) {
				res[r][c]=A[r][c]+B[r][c];
			}
		}
		return res;
	}
	
	public static String toString(double[][]A) {
		StringBuilder builder = new StringBuilder();
		for(int r = 0; r < A.length; r++) {
			for(int c = 0; c < A[0].length; c++) {
				if(c==0)
					builder.append(A[r][c]);
				else
					builder.append(", "+ A[r][c]);
			}
			builder.append("\r\n");
		}
		return builder.toString();
	}
}
