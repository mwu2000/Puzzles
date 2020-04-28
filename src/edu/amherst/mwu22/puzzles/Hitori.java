package edu.amherst.mwu22.puzzles;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Hitori extends MotherofPuzzles {

	private NoDuplicationConstraint uniqueNumbers;
	private HitoriShadeConstraint shadedConstraint;
	private HitoriUnshadedConstraint unshadedConstraint;
	private Object[] possibleValues;

	public Hitori() {
		// TODO Auto-generated constructor stub
	}

	public Boolean correctChecker(int row, int column, GridPanelStatus status) {
		List<Object> rowValues = status.getRowStates(row);
		uniqueNumbers.setValues(rowValues);
		if (!uniqueNumbers.check(status)) return false;
		List<Object> colValues = status.getColumnStates(column);
		uniqueNumbers.setValues(colValues);
		if (!uniqueNumbers.check(status)) return false;
		shadedConstraint.setColumn(column);
		shadedConstraint.setRow(row);
		if (!shadedConstraint.check(status)) return false;
		if (!unshadedConstraint.check(status)) return false;
		return true;
	}

	public void solve(GridPanelStatus status) {
		resetStatus(status);
		GridPanelStatus localStatus = new GridPanelStatus(status.getSize());
		super.label(localStatus, 0, 0, getPossibleValues(0, 0, null)); //possiblevalues current does not matter so pass in null
		//localStatus.checkStatus();
		copyStatus(localStatus, status);
	}
	

	private void copyStatus(GridPanelStatus local, GridPanelStatus status) {
		for (int i = 0; i < local.getSize(); i++) {
			for (int k = 0; k < local.getSize(); k++) {
				status.update(i, k, local.getState(i, k)); //update actual status with local status
			}
		}
	}

	public String currentChecker(GridPanelStatus status) {
		String message = "You are done";
		for (int i = 0; i < status.getSize(); i++) {
			List<Object> rowValues = status.getRowStates(i);
			uniqueNumbers.setValues(rowValues);
			if (!uniqueNumbers.check(status)) return "You have a mistake or you are not finished.";
		}
		for (int j = 0; j < status.getSize(); j++) {
			List<Object> colValues = status.getColumnStates(j);
			uniqueNumbers.setValues(colValues);
			if (!uniqueNumbers.check(status)) return "You have a mistake or you are not finished.";
		}
		for (int i = 0; i < status.getSize(); i++) {
			for (int j = 0; j < status.getSize(); j++) {
				shadedConstraint.setRow(i);
				shadedConstraint.setColumn(j);
				if (!shadedConstraint.check(status)) return "You have a mistake or you are not finished.";
			}
		}
		if(!unshadedConstraint.check(status)) return "You have a mistake.";
		return message;
	}

	public void setUpPuzzle() {
		GridPanelStatus status = getStatus();
		String[] lines = new String[] {"4,5,1,2,4", "5,4,3,2,1", "3,3,2,1,4",
				"1,4,5,3,2", "5,1,2,4,4"};
		List<List<Object>> states = new ArrayList<>();
		for (String line : lines) {
			String[] tokens = line.split(",");
			List<Object> list = new ArrayList<Object>();
			states.add(list);
			for (String token : tokens) {
				list.add(new Integer(token));
			}
		}
		status.setStates(states);
		uniqueNumbers = new NoDuplicationConstraint();
		shadedConstraint = new HitoriShadeConstraint();
		unshadedConstraint = new HitoriUnshadedConstraint();



	}


	public void setUpDisplay(GridPanel grid, List<GridButton> gridButtons) {
		GridPanelStatus status = getStatus();
		int size = status.getSize();
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GridPanelStatus status = getStatus();
				GridButton cell = (GridButton)e.getSource();
				int x = cell.getRow();
				int y = cell.getColumn();
				//correctChecker(x, y, status); 
				Integer numStatus = -(Integer)status.getState(x, y);
				if (numStatus == null) return; //not set yet
				status.update(x, y, numStatus); //update with the last number
				grid.setButtonStatus(numStatus, cell);
			}
		};
		grid.setLayout(new GridLayout(size, size));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				GridButton cell = new GridButton(i, j);
				grid.add(cell);
				Object numLabel = status.getState(i, j);
				cell.setText(numLabel.toString());
				cell.addActionListener(action);
				cell.setBackground(Color.ORANGE);
				cell.setOpaque(true);
				gridButtons.add(cell);
			}
		}
	}

	public int getGridSize() {
		return 0;
	}

	@Override
	public Object[] getPossibleValues(int row, int column, Object[] currentPossibleValues) {
		//the current possible values 
		Object[] cellValues = new Object[2];
		GridPanelStatus status= getStatus();
		int size = getStatus().getSize();
		if (row >= size || column >= size) //for when the recursion goes out of bounds
			return null;
		Integer originalValue = (Integer)status.getState(row, column);
		cellValues[0] = originalValue;
		Integer flippedValue = -(originalValue);
		cellValues[1] = flippedValue;
		return cellValues;		
	}
	
	public void resetStatus(GridPanelStatus status) {
		List<List<Object>> states = status.getStates();
		for (int i = 0; i < status.getSize(); i++) {
			List<Object> statesList = states.get(i);
			for (int j = 0; j < statesList.size(); j++) {
				Integer state = (Integer)statesList.get(j);
				if (state < 0) {
					status.update(i, j, -(state));
				}
			}
		}
		//status.checkStatus();
	}

}
