package edu.amherst.mwu22.puzzles;

import java.util.List;

public class NoDuplicationConstraint implements Constraint{

	private List<Object> values;

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public NoDuplicationConstraint() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean check(GridPanelStatus status) {
		if (values == null) {
			return true;
		}
		else {
			for(int i = 0; i < values.size()-1; i++) {
				Object obj = values.get(i);
				if (obj == null) {
					continue;
				}
				if (obj instanceof Integer && (Integer)obj < 0) {
					continue;
				}
				for (int j = i+1; j < values.size(); j++) {
					if (values.get(j) != null && values.get(j).equals(obj)) {
						return false;
					}
				}
			}
			return true;
		}
	}


}
