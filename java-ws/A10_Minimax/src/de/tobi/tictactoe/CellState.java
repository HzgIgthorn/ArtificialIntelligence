package de.tobi.tictactoe;

public enum CellState {
	COMP("X"), USER("O"),EMPT("-");

	String sign;
	
	CellState(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return sign;
	}
}
