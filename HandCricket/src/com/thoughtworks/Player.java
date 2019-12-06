package com.thoughtworks;

import java.util.Scanner;

/**
 * Model class for the player infromation.
 * @author ubuntu
 *
 */
public class Player {

	// A Name of the player
	private String name;
	
	// An Id of the player
	private int id;
	
	// An score earned by the player
	private int score;
	
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

	/**
	 * To perform the throw action
	 * @return score to return the score
 	 */
	int performThorw() {
		int score = 0;
		System.out.println("--");
		System.out.println("Enter a score of ---" + this.name + " \n");
		score = new Scanner(System.in).nextInt();
		System.out.println("--");
		
//		if (!Rule.RUNS_ARRAY cont score) {
//			System.out.println("You have entered invalid score"); 
//			score = 0;
//		}
		return score;
	}
	
}
