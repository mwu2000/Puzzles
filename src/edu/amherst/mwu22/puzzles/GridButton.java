package edu.amherst.mwu22.puzzles;

import javax.swing.JButton;

public class GridButton extends JButton {
	
	private int row;
	private int column;

	public GridButton(int x, int y) {
		this.row = x;
		this.column = y;
		
	}

	public int getRow() {
		return row;
	}

	public void setRow(int x) {
		this.row = x;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int y) {
		this.column = y;
	}
	
	
	

}
