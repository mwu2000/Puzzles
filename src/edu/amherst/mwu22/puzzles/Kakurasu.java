package edu.amherst.mwu22.puzzles;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

public class Kakurasu extends MotherofPuzzles{

	private List<KakurasuConstraint> rowConstraintList;
	private List<KakurasuConstraint> colConstraintList;
	private Boolean[] possibleValues = new Boolean[]{true, false};

	public Kakurasu() {
	}

	public void setRowConstraints(int[] rowSums) {
		rowConstraintList = new ArrayList<KakurasuConstraint>();
		setupConstraints(rowSums, rowConstraintList);
		for (KakurasuConstraint constraint : rowConstraintList) {
			constraint.setIsForRow(true);
		}
	}

	public void setColumnConstraints(int[] colSums) {
		colConstraintList = new ArrayList<KakurasuConstraint>();
		setupConstraints(colSums, colConstraintList);
		for (KakurasuConstraint constraint : colConstraintList) {
			constraint.setIsForRow(false);
		}
	}

	public void setUpPuzzle() {
		int[] rowSums = new int[] {15, 10, 11, 2, 3};
		int colSums[] = new int[] {1, 10, 8, 4, 6};
		this.setRowConstraints(rowSums);
		this.setColumnConstraints(colSums);
	}

	public void setUpDisplay(GridPanel grid, List<GridButton> gridButtons) {
		int size = rowConstraintList.size();
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GridPanelStatus status = getStatus();
				GridButton cell = (GridButton)e.getSource();
				int x = cell.getRow();
				int y = cell.getColumn();
				correctChecker(x, y, status); 
				if (status.getState(x, y) != null && status.getState(x, y).equals(Boolean.TRUE)) {
					status.update(x, y, null);
					grid.setButtonStatus(null, cell);
				}
				else {
					grid.setButtonStatus(true, cell);
					status.update(x, y, true);
				}
			}
		};
		grid.setLayout(new GridLayout(size+2, size+2));
		for (int i = 0; i < size+2; i++) {
			for (int j = 0; j < size+2; j++) {
				GridButton cell = new GridButton(i-1, j-1);
				cell.setBackground(Color.PINK);
				cell.setOpaque(true);
				grid.add(cell);
				if (i == 0 || i == size + 1 || j == 0 || j == size + 1) {
					cell.setEnabled(false);
					if (i == size + 1 && (j > 0 && j <= size)) { //set up the column constraints (the bottom row)
						KakurasuConstraint constraint = colConstraintList.get(j-1);
						cell.setText(constraint.getSum() + "");
					}
					if (j == size + 1 && (i > 0 && i <= size)) { //set up the row constraints (the right column)
						KakurasuConstraint constraint = rowConstraintList.get(i-1);
						cell.setText(constraint.getSum() + "");
					}
					int[] nums = new int[] {1,2,3,4,5};
					if (i == 0 && (j > 0 && j <= size)) {
						cell.setText(nums[j-1] + "");
					}
					if (j == 0 && (i > 0 && i <= size)) {
						cell.setText(nums[i-1] + "");
					}
				}
				else { //now, add the buttons that the user can click to a list
					cell.addActionListener(action);
					gridButtons.add(cell);
					cell.setBackground(Color.ORANGE);
					cell.setOpaque(true);
				}
			}
		}
	}

	public int getGridSize() {
		return rowConstraintList.size();
	}

	//for checking button
	public String currentChecker(GridPanelStatus status) { //checks the entire puzzle
		String checkerMessage;	
		Boolean isSolved = true;
		for (int i = 0; i < status.getSize(); i++) {
			int currentRowSum = getRowStatus(i, status);
			KakurasuConstraint rowConstraint = rowConstraintList.get(i);
			int rowSum = rowConstraint.getSum();
			if (currentRowSum > rowSum) {
				checkerMessage = "You have a mistake.";
				return checkerMessage;
			}
			if (currentRowSum < rowSum) {
				isSolved = false;
			}
		}
		for (int i = 0; i < status.getSize(); i++) {
			int currentColSum = getColStatus(i, status);
			KakurasuConstraint colConstraint = colConstraintList.get(i);
			int colSum = colConstraint.getSum();
			if (currentColSum > colSum) {
				checkerMessage = "You have a mistake.";
				return checkerMessage;
			}
			if (currentColSum < colSum) {
				isSolved = false;
			}
		}
		if (isSolved) {
			checkerMessage = "You have finished the puzzle.";
			return checkerMessage;
		}
		else {
			checkerMessage = "Keep Going";
			return checkerMessage;
		}
	}

	//for solving button
	public Boolean correctChecker(int row, int column, GridPanelStatus status) {
		KakurasuConstraint rowConstraint = rowConstraintList.get(row);
		if(!rowConstraint.check(status)) { //if the constraint is false, then it is wrong and return
			return false;
		}
		KakurasuConstraint colConstraint = colConstraintList.get(column);
		if(!colConstraint.check(status)) {
			return false;
		}
		return true;
	}


	private void setupConstraints(int[] sumArray, List<KakurasuConstraint> list) {
		for (int i = 0; i < sumArray.length; i++) {
			int sum = sumArray[i];
			KakurasuConstraint constraint = new KakurasuConstraint();
			constraint.setGame(this);
			constraint.setSum(sum);
			constraint.setIndex(i);
			list.add(constraint);
		}
	}

	public int getRowStatus(int x, GridPanelStatus status) {
		int current = 0;
		List<Object> row = status.getRowStates(x);
		for(int i = 0; i < row.size(); i++) {
			if(row.get(i) != null && row.get(i).equals(Boolean.TRUE)) {
				current = current + (i+1);	
			}
		}
		return current;
	}

	public int getColStatus(int y, GridPanelStatus status) {
		int current = 0;
		List<Object> row = status.getColumnStates(y);
		for(int i = 0; i < row.size(); i++) {
			if(row.get(i) != null && row.get(i).equals(Boolean.TRUE)) {
				current = current + (i+1);	
			}
		}
		return current;
	}

	public void solve(GridPanelStatus status) {
		status.resetStatus();
		label(status, 0, 0);
		//status.checkStatus();
	}

	public Boolean label(GridPanelStatus status, int row, int column) { //checks all possible combinations and finds the correct one
		return super.label(status, row, column, possibleValues);
	}
	
	public void resetStatus(GridPanelStatus status) {
		status.resetStatus();
	}
	









}
