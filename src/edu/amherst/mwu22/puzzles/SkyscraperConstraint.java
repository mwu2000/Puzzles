package edu.amherst.mwu22.puzzles;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SkyscraperConstraint implements Constraint {
	
	private int value; //value is the value of the constraint
	private List<Object> listOfValues; //the row or column in question

	public List<Object> getListOfValues() {
		return listOfValues;
	}

	public void setListOfValues(List<Object> listOfValues) {
		this.listOfValues = listOfValues;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public SkyscraperConstraint(){
		// TODO Auto-generated constructor stub
	}
	
	public Boolean check(GridPanelStatus status) {
		Integer maxValue = (Integer)listOfValues.get(0);
		if (maxValue == null) {
			return true;
		}
		int maxCtr = 1;
		for (int i = 0; i < listOfValues.size(); i++) {
			Integer current = (Integer)listOfValues.get(i);
			if (current == null) continue;
			if (maxValue < current) {
				maxCtr++;
				maxValue = current;
			}
		}
		//compare
		if (maxCtr > value) {
			return false;
		}
		if (isFilled() && maxCtr < value) { //less than and filled
			return false;
		}
		return true;
	}
	
	private Boolean isFilled() {
		Boolean isFilled = true;
		for (Object rowState : listOfValues) { 
			if (rowState == null) {
				isFilled = false;
				break;
			}
		}
		return isFilled;
	}
	
//	@Test
//	public void TestChecker() {
//		listOfValues = Arrays.asList(new Integer[]{2, 5, 1, 3, 4});
//		value = 3;
//		check(null);
//		
//	}


}
