package edu.amherst.mwu22.puzzles;

import java.util.ArrayList;
import java.util.List;

public class HitoriUnshadedConstraint implements Constraint{

	public HitoriUnshadedConstraint() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean check(GridPanelStatus status) {
		if (isNotFilled(status)) return true; //if not filled then you can't check it
		List<String> allUnshadedCells = new ArrayList<String>();
		int[] unshadedIndex = collectUnshadedCells(status, allUnshadedCells); //this is the last unshaded cell's index
		List<String> unshadedRecursive = checkHelper(unshadedIndex[0], unshadedIndex[1], status);
		return unshadedRecursive.size() == allUnshadedCells.size();
	}
	
	private Boolean isNotFilled(GridPanelStatus status) {
		for (int i = 0; i < status.getSize(); i++) {
			for (int j = 0; j < status.getSize(); j++) {
				if (status.getState(i, j) == null) {
					return true;
				}
			}
		}
		return false;
	}

	private int[] collectUnshadedCells(GridPanelStatus status, List<String> allUnshadedCells){
		int[] unshadedIndexes = new int[2];
		List<List<Object>> states = status.getStates();
		for (int i = 0; i < states.size(); i++) {
			List<Object> list = states.get(i);
			for (int j = 0 ; j < list.size(); j++) {
				Object obj = list.get(j);
				if ((Integer)obj > 0) {
					allUnshadedCells.add(i + "," + j);
					unshadedIndexes[0] = i;
					unshadedIndexes[1] = j;
				}
			}
		}
		return unshadedIndexes;
	}

	private List<String> checkHelper(int x, int y, GridPanelStatus status) {
		List<String> unshaded = new ArrayList<String>();
		checkHelperRecursion(x, y, unshaded, status);
		return unshaded;
	}

	private void checkHelperRecursion(int x, int y, List<String> unshaded, GridPanelStatus status) {
		Integer state = (Integer)status.getState(x, y);
		if (state < 0) return; //means it's shaded
		if (unshaded.contains(x + "," + y)){
			return;
		}
		unshaded.add(x + "," + y);
		if (y > 0) { //only check sides if there is a side
			checkHelperRecursion(x, y-1, unshaded, status);
		}
		if (x < status.getSize()-1) { //right
			checkHelperRecursion(x+1, y, unshaded, status);
		}
		if (y < status.getSize()-1) {//bottom
			checkHelperRecursion(x, y+1, unshaded, status);
		}
		if (x > 0) {
			checkHelperRecursion(x-1, y, unshaded, status); //left
		}
	}

}


