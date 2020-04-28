package edu.amherst.mwu22.puzzles;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ControlPanel extends JPanel implements ActionListener{

	private JButton reset;
	private JButton undo;
	private JButton solve;
	private JButton check;
	private JComboBox switchPuzzle;
	private GridPanel grid;
	//private JLabel title;


	public ControlPanel() { 
//		String nameMessage = "Welcome to Kakurasu (Default)";
//		title = new JLabel(nameMessage);
//		this.add(title);
		reset = new JButton("Reset");
		reset.addActionListener(this);
		this.add(reset);
		solve = new JButton("Solve");
		solve.addActionListener(this);
		this.add(solve);
		check = new JButton("Check");
		check.addActionListener(this); 
		this.add(check);
		String[] puzzleTitles = new String[]{"Kakurasu", "Skyscraper", "Hitori"};
		switchPuzzle = new JComboBox(puzzleTitles);
		switchPuzzle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String title = (String) switchPuzzle.getSelectedItem();
				if (title.equals("Kakurasu")) {
					Kakurasu Kakurasu = new Kakurasu();
					grid.reset();
					grid.setGame(Kakurasu);
				}
				if (title.equals("Skyscraper")) {
					Skyscraper Skyscraper = new Skyscraper();
					grid.reset();
					grid.setGame(Skyscraper);
				}
				if (title.equals("Hitori")) {
					Hitori Hitori = new Hitori();
					grid.reset();
					grid.setGame(Hitori);
				}
				
			}
		});
		this.add(switchPuzzle);


	}

	public GridPanel getGrid() {
		return grid;
	}

	public void setGrid(GridPanel grid) {
		this.grid = grid;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == reset) {
			grid.reset();
		}
		if (e.getSource() == solve) {
			grid.solve();
		}
		if (e.getSource() == check) {
			grid.check();
		}
	}





}








