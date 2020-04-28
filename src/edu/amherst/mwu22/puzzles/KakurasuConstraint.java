package edu.amherst.mwu22.puzzles;

public class KakurasuConstraint implements Constraint {

	private int sum;
	private int index;
	private Boolean isForRow; //checks whether this constraint is used for the row or column
	private Kakurasu game;
	
	public Kakurasu getGame() {
		return game;
	}

	public void setGame(Kakurasu game) {
		this.game = game;
	}

	public Boolean getIsForRow() {
		return isForRow;
	}

	public void setIsForRow(Boolean isForRow) {
		this.isForRow = isForRow;
	}

	public KakurasuConstraint() {

	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Boolean check(GridPanelStatus status) {
		Boolean correct = true;
		if(isForRow) {
			int currentRowSum = game.getRowStatus(index, status);
			if ((currentRowSum > this.getSum()) ||
					(status.isRowFilled(index) && currentRowSum != this.getSum())){ //busted greater than total or the entire row is filled out and it is less than the total sum
				correct = false;
				//System.out.println("WRONG ROW SUM");
			}
		}
		else {
			int currentColSum = game.getColStatus(index, status);
			if((currentColSum > this.getSum()) ||
					(status.isColumnFilled(index) && currentColSum != this.getSum())) {
				correct = false;
			}
		}
		return correct;
	}


}
