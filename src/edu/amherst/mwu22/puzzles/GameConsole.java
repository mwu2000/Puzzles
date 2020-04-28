package edu.amherst.mwu22.puzzles;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameConsole extends JFrame{
	
	public static void main(String[] args) {
		GameConsole puzzles = new GameConsole();
	}

	public GameConsole() {
		String title = "Puzzle Play Time";
		this.setTitle(title);
		GridPanel grid = new GridPanel();
		this.getContentPane().add(grid, BorderLayout.CENTER);
		ControlPanel controls = new ControlPanel();
		controls.setGrid(grid);
		this.getContentPane().add(controls, BorderLayout.EAST);
		this.setSize(1000, 800); //number of pixels of the GUI window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
