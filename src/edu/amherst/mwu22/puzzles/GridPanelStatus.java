package edu.amherst.mwu22.puzzles;

import java.util.ArrayList;
import java.util.List;

public class GridPanelStatus{

	private List<List<Object>> states;
	private int size;

	public List<List<Object>> getStates() {
		return states;
	}

	public void setStates(List<List<Object>> states) {
		this.states = states;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public GridPanelStatus(int size) {
		this.size = size;
		states = new ArrayList<>();
		for (int i = 0; i < size; i++) { 
			List<Object> list = new ArrayList<Object>();
			states.add(list);
			for ( int j = 0; j < size; j++) {//initialize the states of the status
				list.add(null); //means it has not been marked by solver
			}
		}
	}

	public void update(int x, int y, Object state) { //set state
		List<Object> row = states.get(x);
		row.set(y, state);
		//System.out.println("UPDATE SUCCESSFUL" + "" + x + " " + y);
	}

	public Object getState(int x, int y) {
		List<Object> row = states.get(x);
		return row.get(y);
	}

	public List<Object> getRowStates(int x){ //gets the states of the row the button is in
		return states.get(x);
	}

	public List<Object> getColumnStates(int column){ //gets the states of the column the button is in
		List<Object> rtn = new ArrayList<Object>();
		for (int row = 0; row < size; row++) { 
			List<Object> rowStates = states.get(row);
			Object rowState = rowStates.get(column);
			rtn.add(rowState);
		}
		return rtn;
	}

	public Boolean isRowFilled(int row) {
		List<Object> rowStates = states.get(row);
		Boolean isFilled = true;
		for (Object rowState : rowStates) { 
			if (rowState == null) {
				isFilled = false;
				break;
			}
		}
		return isFilled;
	}

	public Boolean isColumnFilled(int column) {
		Boolean isFilled = true;
		for (int i = 0; i < states.size(); i++) {
			List<Object> rowStates = states.get(i);
			Object colState = rowStates.get(column);
			if (colState == null) {
				isFilled = false;
				break;
			}
		}
		return isFilled;
	}

	public void resetStatus() {
		for (int i = 0; i < size; i++) {
			List<Object> statusList = states.get(i);
			for (int j = 0; j < statusList.size(); j++) {
				statusList.set(j, null);
			}
		}
	}

	public void checkStatus() {
		StringBuilder builder = new StringBuilder();
		for (int i =0; i < states.size(); i++ ) {
			List<Object> rowStates = states.get(i);
			for (int j = 0; j < rowStates.size(); j++) {
				builder.append(rowStates.get(j)).append(",");
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}



}
