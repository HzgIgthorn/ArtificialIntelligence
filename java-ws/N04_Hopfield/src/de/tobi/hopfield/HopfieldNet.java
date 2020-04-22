package de.tobi.hopfield;

public class HopfieldNet {
	
	private double[][] weightMatrix;

	public HopfieldNet(int dim) {
		this.weightMatrix = new double[dim][dim];
	}
	
	public void train(double[] pattern) {
		double[] patBi = Utils.transform(pattern);
		
		double[][] patMat = Matrix.createMatrix(patBi.length, patBi.length);
		patMat = Matrix.outerProduct(patBi);
		patMat = Matrix.clearDiagonal(patMat);
		
		this.weightMatrix = Matrix.addMatrix(this.weightMatrix, patMat);
	}
	
	public void recal(double[] pattern) {
		StringBuilder builder = new StringBuilder("Muster (");
		boolean hf = false;
		for(double d : pattern) {
			if(hf)
				builder.append(", "+d);
			else {
				builder.append(""+d);
				hf=true;
			}
		}
		double[] patBi = Utils.transform(pattern);
		double[] res = Matrix.matrixMulti(weightMatrix, patBi);
		for(int i=0; i< patBi.length; i++) {
			res[i] = ActivationFkt.stepFkt(res[i]);
		}
		for(int i=0; i < patBi.length; i++) {
			if(patBi[i] != res[i]) {
				builder.append(") wurde nicht erkannt!");
				System.out.println(builder.toString());
				System.out.println(Matrix.toString(weightMatrix));
				return;
			}
		}
		builder.append(") wurde erkannt!");
		System.out.println(builder);
		System.out.println(Matrix.toString(weightMatrix));
	}

}
