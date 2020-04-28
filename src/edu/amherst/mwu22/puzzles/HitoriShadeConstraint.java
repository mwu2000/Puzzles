package edu.amherst.mwu22.puzzles;

public class HitoriShadeConstraint implements Constraint{


	private int row;
	private int column;

	public HitoriShadeConstraint() {
		// TODO Auto-generated constructor stub
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public Boolean check(GridPanelStatus status) {
		Integer numberStatus = (Integer)status.getState(row, column);
		if (numberStatus > 0) return true; //means unshaded
		if (column > 0) { //only check sides if there is a side
			Integer up = (Integer)status.getState(row, column -1);
			if (up != null && up < 0) return false;
		}
		if (row < status.getSize()-1) {
			Integer right = (Integer)status.getState(row+1, column);
			if (right != null && right < 0) return false;
		}
		if (column < status.getSize()-1) {
			Integer down = (Integer)status.getState(row, column + 1);
			if (down != null && down < 0) return false;
		}
		if (row > 0) {
			Integer left = (Integer)status.getState(row -1, column);
			if (left != null && left < 0) return false;
		}
		return true;
	}

}
