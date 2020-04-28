package edu.amherst.mwu22.puzzles;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.junit.Test;

public class Skyscraper extends MotherofPuzzles{

	List<SkyscraperConstraint> topConstraints;
	List<SkyscraperConstraint> leftConstraints;
	List<SkyscraperConstraint> rightConstraints;
	List<SkyscraperConstraint> bottomConstraints;
	NoDuplicationConstraint uniqueNumbers;
	Integer[] possibleValues = new Integer[] {1, 2, 3, 4, 5}; //do not use primitive
	//COUNT HOW MAMY TIMES THE MAXIMUM CHANGES

	@Override
	public Boolean correctChecker(int row, int column, GridPanelStatus status) {
		Boolean correct = true;
		//check for duplicate numbers in the row and column of the button
		List<Object> rowStates = status.getRowStates(row);
		uniqueNumbers.setValues(rowStates);
		correct = uniqueNumbers.check(status);
		if (!correct) return false;
		List<Object> colStates = status.getColumnStates(column);
		uniqueNumbers.setValues(colStates);
		correct = uniqueNumbers.check(status);
		if (!correct) return false;
		//check side totals
		SkyscraperConstraint top = topConstraints.get(column);
		top.setListOfValues(colStates);
		correct = top.check(status);
		if (!correct) return false;
		SkyscraperConstraint bottom = bottomConstraints.get(column);
		bottom.setListOfValues(reverseList(colStates));
		correct = bottom.check(status);
		if (!correct) return false;
		SkyscraperConstraint left = leftConstraints.get(row);
		left.setListOfValues(rowStates);
		correct = left.check(status);
		if (!correct) return false;
		SkyscraperConstraint right = rightConstraints.get(row);
		right.setListOfValues(reverseList(rowStates));
		correct = right.check(status);
		if (!correct) return false;
		return correct;
	}

	private List<Object> reverseList(List<Object> states){
		List<Object> stateCopy = new ArrayList<Object>(states); //copy states 
		Collections.reverse(stateCopy);
		return stateCopy;
	}

	@Override
	public void solve(GridPanelStatus status) {
		status.resetStatus();
		super.label(status, 0, 0, possibleValues);
	}


	@Override
	public String currentChecker(GridPanelStatus status) { //for check button
		String checkerMessage;	
		Boolean isFilled = true;
		for (int i = 0; i < status.getSize(); i++) {
			List<Object> rowStates = status.getRowStates(i);
			SkyscraperConstraint constraint = leftConstraints.get(i);
			constraint.setListOfValues(rowStates);
			if (!constraint.check(status)) {
				checkerMessage = "You have a mistake";
				return checkerMessage;
			}
			constraint = rightConstraints.get(i);
			constraint.setListOfValues(reverseList(rowStates));
			if (!constraint.check(status)) {
				checkerMessage = "You have a mistake";
				return checkerMessage;
			}
			uniqueNumbers.setValues(rowStates);
			if(!uniqueNumbers.check(status)){
				checkerMessage = "You have a mistake";
				return checkerMessage;
			}
			if (!status.isRowFilled(i)) {
				isFilled = false;
			}
		}
		for (int i = 0; i < status.getSize(); i++) {
			List<Object> colStates = status.getColumnStates(i);
			SkyscraperConstraint constraint = topConstraints.get(i);
			constraint.setListOfValues(colStates);
			if (!constraint.check(status)) {
				checkerMessage = "You have a mistake";
				return checkerMessage;
			}
			constraint = bottomConstraints.get(i);
			constraint.setListOfValues(reverseList(colStates));
			if (!constraint.check(status)) {
				checkerMessage = "You have a mistake";
				return checkerMessage;
			}
			uniqueNumbers.setValues(colStates);
			if(!uniqueNumbers.check(status)){
				checkerMessage = "You have a mistake";
				return checkerMessage;
			}
			if (!status.isColumnFilled(i)) {
				isFilled = false;
			}
		}
		if (isFilled) {
			checkerMessage = "You have finished the puzzle.";
			return checkerMessage;
		}
		else {
			checkerMessage = "Keep Going";
			return checkerMessage;
		}
	}

	@Override
	public void setUpPuzzle() {
		topConstraints = new ArrayList<SkyscraperConstraint>();
		leftConstraints = new ArrayList<SkyscraperConstraint>();
		rightConstraints = new ArrayList<SkyscraperConstraint>();
		bottomConstraints = new ArrayList<SkyscraperConstraint>();
		uniqueNumbers = new NoDuplicationConstraint();
		File file = new File("SkyscraperLoader");
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				String[] tokens = line.split(" ");
				if(tokens[tokens.length-1].equals("top")) {
					setUpConstraints(topConstraints, tokens);
				}
				else if(tokens[tokens.length-1].equals("left")) {
					setUpConstraints(leftConstraints, tokens);
				}
				else if(tokens[tokens.length-1].equals("right")) {
					setUpConstraints(rightConstraints, tokens);
				}
				else {
					setUpConstraints(bottomConstraints, tokens);
				}
			}
			sc.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setUpConstraints(List<SkyscraperConstraint> constraintList, String[] tokens) {
		for (int i = 0; i < tokens.length-1; i++) {
			SkyscraperConstraint constraint = new SkyscraperConstraint();
			constraint.setValue(new Integer(tokens[i])); //got to switch to ints
			constraintList.add(constraint);
		}
	}


	@Test
	public void testPuzzle() {
		setUpPuzzle();
		for (SkyscraperConstraint constraint : topConstraints) {
			int value = constraint.getValue();
			//System.out.println(value);
		}
	}

	public void setUpDisplay(GridPanel grid, List<GridButton> gridButtons) {
		int size = topConstraints.size();
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GridPanelStatus status = getStatus();
				GridButton cell = (GridButton)e.getSource();
				int x = cell.getRow();
				int y = cell.getColumn();
				//correctChecker(x, y, status); 
				Object number = JOptionPane.showInputDialog(grid, "Please Choose a Number", "Input Number",
						JOptionPane.QUESTION_MESSAGE, null, possibleValues, 1);
				if (number == null) return; //means user pressed cancel
				status.update(x, y, number); //update with the last number
				grid.setButtonStatus(number, cell);
			}
		};
		grid.setLayout(new GridLayout(size+2, size+2));
		for (int i = 0; i < size+2; i++) {
			for (int j = 0; j < size+2; j++) {
				GridButton cell = new GridButton(i-1, j-1);
				grid.add(cell);
				if (i == 0 || i == size + 1 || j == 0 || j == size + 1) {
					cell.setEnabled(false);
					if (i == size + 1 && (j > 0 && j <= size)) { //set up the column constraints (the bottom row)
						SkyscraperConstraint constraint = bottomConstraints.get(j-1);
						cell.setText(constraint.getValue() + "");
					}
					if (j == size + 1 && (i > 0 && i <= size)) { //set up the row constraints (the right column)
						SkyscraperConstraint constraint = rightConstraints.get(i-1);
						cell.setText(constraint.getValue() + "");
					}
					if (i == 0 && (j > 0 && j <= size)) {
						SkyscraperConstraint constraint = topConstraints.get(j-1);
						cell.setText(constraint.getValue() + "");
					}
					if (j == 0 && (i > 0 && i <= size)) {
						SkyscraperConstraint constraint = leftConstraints.get(i-1);
						cell.setText(constraint.getValue() + "");
					}
				}
				else { //now, add the buttons that the user can click to a list
					cell.addActionListener(action);
					cell.setBackground(Color.ORANGE);
					cell.setOpaque(true);
					gridButtons.add(cell);
					
				}
			}
		}
	}

	@Override
	public int getGridSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void resetStatus(GridPanelStatus status) {
		status.resetStatus();
	}



}
