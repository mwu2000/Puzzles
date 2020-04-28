package edu.amherst.mwu22.puzzles;

import java.util.List;

public class MotherofPuzzles {
	
	private GridPanelStatus status;
	
	public MotherofPuzzles() {
		// TODO Auto-generated constructor stub
	}
	

	public GridPanelStatus getStatus() {
		return status;
	}


	public void setStatus(GridPanelStatus status) {
		this.status = status;
	}
	
	
	public Boolean correctChecker(int row, int column, GridPanelStatus status) {
		return false;
	}
	
	public void solve(GridPanelStatus status) {
		
	}
	
	public String currentChecker(GridPanelStatus status) {
		return "";
	}
	
	public void setUpPuzzle() {
		
	}
	
	public void setUpDisplay(GridPanel grid, List<GridButton> gridButtons) {
	}
	
	public int getGridSize() {
		return 0;
	}
	
	public void resetStatus(GridPanelStatus status) {
	}
	
	public Boolean label(GridPanelStatus status, int row, int column, Object[] possibleValues) { //checks all possible combinations and finds the correct one
		if (row == status.getSize()) { //base case, means all of the buttons are correctly filled in
			return true;
		}
		if (status.getState(row, column) != null) { //if the button has been labeled, go to the next button
			int newcolumn = column + 1;
			int newrow = row;
			if (newcolumn == status.getSize()) {
				newcolumn = 0;
				newrow = row + 1;
			}
			if(label(status, newrow, newcolumn, getPossibleValues(newrow, newcolumn, possibleValues))) return true;
			else return false; 
		}
		else { //if the button has not been labeled
			for (Object value : possibleValues) {
				status.update(row, column, value);
				Boolean check = correctChecker(row, column, status); //if value is true, it will
				//see whether the button should actually be true. if the value is false/null, it will see whether the button should actually be so.
				//polymorphism: will call the correctChecker of the subclass
				if (check) { //if the check is true, then go to the next button
					int newcolumn = column + 1;
					int newrow = row;
					if (newcolumn == status.getSize()) {
						newcolumn = 0;
						newrow = row + 1;
					}
					if(label(status, newrow, newcolumn, getPossibleValues(newrow, newcolumn, possibleValues))) { //means all cells before satisfy the current solution with the new button
						return true;
					}
				}
			}
			status.update(row, column, null); //means previous solution is not correct, so it goes back into the for loop
			return false;
		}
	}
	
	//extra step in the hitori subclass
	public Object[] getPossibleValues(int row, int column, Object[] currentPossibleValues) {
		return currentPossibleValues;
	}
	

}
