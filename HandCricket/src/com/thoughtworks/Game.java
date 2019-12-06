package com.thoughtworks;

import java.lang.annotation.Inherited;
import java.util.Scanner;

/**
 * This class used to play the game
 * @author ubuntu
 *
 */
public class Game implements PlayerService {
	private int id;
	private Player player1;
	private Player player2;
	

	/**
	 * {@link Inherited}
	 */
	@Override
	public Result play(Player player1, Player player2) {
		Result result = new Result();		

		System.out.print("Player 1 is batting");
		
		Round round1 = new Round();
		round1.setId(1);
		player1.setScore(round1.getScore(player1, player2, null));
		
		System.out.print("Player 2 is batting");
		
		Round round2 = new Round();
		round2.setId(2);
		player2.setScore(round2.getScore(player2, player1, player1.getScore()));
		
		
		if (player1.getScore() > player2.getScore()) {
			result.setStatus(Status.WIN);
			result.setPlayer(player1);
		} else if (player1.getScore() < player2.getScore()) {
			result.setStatus(Status.WIN);
			result.setPlayer(player2);
		} else {
			result.setStatus(Status.TIE);
			result.setPlayer(null);
		}
		
		
		
		return result;
	}

	public int getId() {
		return id;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	
	
	
	
}
