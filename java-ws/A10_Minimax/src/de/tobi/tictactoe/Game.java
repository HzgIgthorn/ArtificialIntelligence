package de.tobi.tictactoe;

public class Game implements Constants{
	
	private Board board;

	public Game(){
		super();
		init();
		display();
		makeFirstMove();
		playGame();
		checkStatus();
	}

	private void init() {
		this.board = new Board();
		this.board.setUpBoard();
	}

	private void display() {
		this.board.display();
	}

	private void makeFirstMove() {
		System.out.println("Wer darf beginnen? 1 für Computer und 2 für User");
		int choice = board.getScanner().nextInt();
		if(choice==1) {
			Cell cell = new Cell(RAND.nextInt(BOARD_SIZE), RAND.nextInt(BOARD_SIZE));
			board.move(cell, CellState.COMP);
			board.display();
		}
	}

	private void playGame() {
		while( board.isRunning()) {
			System.out.println("Der Spieler ist am Zug");
			Cell userCell = new Cell(board.getScanner().nextInt(), board.getScanner().nextInt());
			board.move(userCell, CellState.USER);
			board.display();
			if(! board.isRunning())
				break;
			board.callMinimax(0, CellState.COMP);
			for(Cell cell : board.getRootValues()) {
				System.out.println("Cellwerte: " + cell + " Minimaxwerte: "+ cell.getMinmax());
			}
			board.move(board.getBestMove(), CellState.COMP);
			board.display();
		}
	}

	private void checkStatus() {
		if(board.isWinning(CellState.COMP))
			System.out.println("Computer hat gewonnen!");
		else if(board.isWinning(CellState.USER)) {
			System.out.println("Der Spieler hat gewonnen");
		}else
			System.out.println("Das Spiel ist unentschieden");
	}
}
