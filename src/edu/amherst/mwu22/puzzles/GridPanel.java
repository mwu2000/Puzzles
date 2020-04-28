package edu.amherst.mwu22.puzzles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/*
 * This is the main panel to set up the button grid
 */
public class GridPanel extends JPanel{

	private GridPanelStatus status;
	private MotherofPuzzles game;
	private List<GridButton> gridButtons;
	private final Color DEFAULT_BACKGROUND = Color.ORANGE;
	private final Color CLICKED_COLOR = Color.MAGENTA;

	public GridPanel(){
		game = new Kakurasu(); //by default
		//game = new Skyscraper();
		gridButtons = new ArrayList<GridButton>();
		game.setUpPuzzle();
		game.setUpDisplay(this, this.gridButtons);
		status = new GridPanelStatus(game.getGridSize());
		game.setStatus(status);
	}

	public MotherofPuzzles getGame() {
		return game;
	}

	public void setGame(MotherofPuzzles game) {
		status.resetStatus();
		gridButtons.clear();
		this.removeAll();
		this.game = game;
		game.setStatus(status);
		game.setUpPuzzle();
		game.setUpDisplay(this, gridButtons);
		this.invalidate();
		this.repaint();
	}

	public void setButtonStatus(Object checked, GridButton cell) {
		if (checked != null && checked.equals(Boolean.TRUE)) {
			cell.setBackground(CLICKED_COLOR);
			cell.setText("*");
			Icon ButtonFace = new ImageIcon("Pika.jpg");
			cell.setIcon(ButtonFace);
			cell.setOpaque(true);
		}
		else if (checked != null && checked instanceof Integer) {
			Integer checkInt = (Integer)checked; 
			if (checkInt > 0) { //for Skyscraper and unclicked Hitoris
				cell.setIcon(null);
				cell.setBackground(CLICKED_COLOR);
				cell.setText(checked.toString());
				cell.setOpaque(true);
				//System.out.println("goodbye");
			}
			else { //for Hitori
				cell.setBackground(CLICKED_COLOR);
				cell.setText("*");
				Icon ButtonFace = new ImageIcon("Pika.jpg");
				cell.setIcon(ButtonFace);
				cell.setOpaque(true);
				//System.out.println("hello");
			}
		}
		else {
			cell.setBackground(DEFAULT_BACKGROUND);
			cell.setIcon(null);
			cell.setText("");
		}
	}



	public void reset() {
		//status.resetStatus();
		game.resetStatus(status);
		int buttonIndex = 0;
		for (int i =0; i < status.getSize(); i++) { //resets all the statuses
			for (int j =0; j < status.getSize(); j++) {
				Object state = status.getState(i, j);
				GridButton button = gridButtons.get(buttonIndex);
				setButtonStatus(state, button);
				buttonIndex++;
			}
		}
		//		this.removeAll();
		//		game.setUpDisplay(this, gridButtons);
		//		this.invalidate();
		//		this.repaint();
	}

	public void solve() {
		game.solve(status);
		int listButtonindex = 0;
		List<List<Object>> correctStatus = status.getStates();
		for (int i = 0; i < correctStatus.size(); i++) {
			List<Object> list = correctStatus.get(i);
			for (int j = 0; j < list.size(); j++) {
				GridButton cell = gridButtons.get(listButtonindex);
				setButtonStatus(list.get(j), cell); //depending on the status, we will fill in the button or not
				listButtonindex++;
			}
		}
		this.invalidate();
		this.repaint();
	}

	public void check() {
		String message = game.currentChecker(status);
		JOptionPane.showMessageDialog(this, message);
	}

	public void switchPuzzle(MotherofPuzzles puzzle) {
		//constructs and reloads grid
	}


}
