package de.tobi.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board implements Constants{
	
	private List<Cell> emptyCells;
	private Scanner scanner;
	private CellState[][] board;
	private List<Cell> rootValues;
	
	public Board() {
		init();
	}

	private void init() {
		this.rootValues = new ArrayList<>(9);
		this.scanner = new Scanner(System.in);
		this.board = new CellState[BOARD_SIZE][BOARD_SIZE];
	}
	
	public boolean isRunning() {
		if(isWinning(CellState.COMP) || isWinning(CellState.USER))
				return false;
		if(getEmptyCells().isEmpty())
			return false;
		return true;
	}

	public boolean isWinning(CellState state) {
		if(board[0][0] == state && board[1][1] == state && board[2][2] == state)
			return true;
		if(board[0][2] == state && board[1][1] == state && board[2][0] == state)
			return true;
		for(int i = 0; i < BOARD_SIZE; i++) {
			if(board[0][i] == state && board[1][i] == state && board[2][i] == state)
				return true;
			if(board[i][0] == state && board[i][1] == state && board[i][2] == state)
				return true;
		}
		return false;
	}

	private List<Cell> getEmptyCells() {
		emptyCells = new ArrayList<>(9);
		for(int i = 0; i < BOARD_SIZE; i++)
			for(int j = 0; j < BOARD_SIZE; j++) {
				if(board[i][j]==CellState.EMPT)
					emptyCells.add(new Cell(i,j));
			}
		return emptyCells;
	}

	public void move(Cell userCell, CellState user) {
		this.board[userCell.getX()][userCell.getY()] = user;
	}

	public void display() {
		System.out.println("");
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j =0; j < BOARD_SIZE; j++) 
				System.out.print(this.board[i][j].getSign()+" ");
			System.out.println("");
		}
	}
	
	public void makeUserInput() {
		System.out.println("Dein Zug!");
		int x = scanner.nextInt();
		int y = scanner.nextInt();
		Cell cell = new Cell(x,y);
		move(cell, CellState.USER);
	}
	
	public int getMin(List<Integer> list) {
		int min = Integer.MAX_VALUE;
		int index = Integer.MIN_VALUE;
		
		for(int i= 0; i < list.size(); i++) {
			if(list.get(i) < min) {
				min = list.get(i);
				index = i;
			}
		}
		return list.get(index);
	}
	
	public int getMax(List<Integer> list) {
		int max = Integer.MIN_VALUE;
		int index = Integer.MIN_VALUE;
		
		for(int i= 0; i < list.size(); i++) {
			if(list.get(i) > max) {
				max = list.get(i);
				index = i;
			}
		}
		return list.get(index);
	}

	public Cell getBestMove() {
		int max = Integer.MIN_VALUE;
		int best = Integer.MIN_VALUE;
		
		for(int i = 0; i < rootValues.size(); i++) {
			if( max < rootValues.get(i).getMinmax()) {
				max = rootValues.get(i).getMinmax();
				best = i;
			}
		}
		return rootValues.get(best);
	}

	public void setUpBoard() {
		for(int i = 0; i < BOARD_SIZE; i++)
			for(int j =0; j < BOARD_SIZE; j++) {
				board[i][j] = CellState.EMPT;
			}
	}

	public Scanner getScanner() {
		return scanner;
	}

	public List<Cell> getRootValues() {
		return rootValues;
	}

	public void callMinimax(int depth, CellState state) {
		rootValues.clear();
		miniMax(depth, state);
	}

	private int miniMax(int depth, CellState state) {
		if(isWinning(CellState.COMP)) return 1;
		if(isWinning(CellState.USER)) return -1;
		
		List<Cell> available = getEmptyCells();
		if(available.isEmpty()) return 0;
		
		List<Integer> scores = new ArrayList<>(9);
		for(int i = 0; i < available.size(); i++) {
			Cell currCell = available.get(i);
			if(state == CellState.COMP) {
				move(currCell, CellState.COMP);
				int currScore = miniMax(depth + 1, CellState.USER);
				scores.add(currScore);
				if(depth == 0) {
					currCell.setMinmax(currScore);
					rootValues.add(currCell);
				}
			}else if(state == CellState.USER){
				move(currCell, CellState.USER);
				scores.add(miniMax(depth+1, CellState.COMP));
			}
			board[currCell.getX()][currCell.getY()] = CellState.EMPT;
		}
		if(state == CellState.COMP) {
			return getMax(scores);
		}
		return getMin(scores);
	}
}
