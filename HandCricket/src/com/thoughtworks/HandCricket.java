package com.thoughtworks;

/**
 * Main class for the HandCricket Game
 * 
 * @author ubuntu
 *
 */
public class HandCricket {

	public static void main(String[] args) {
		HandCricket handCricket = new HandCricket();
		handCricket.playGame();
		
	}
	
	void playGame() {
		Game game = new Game();
		game.setId(1);
		
		Player playerA = new Player();
		playerA.setId(1);
		playerA.setName("Player A");
		
		Player playerB = new Player();
		playerB.setId(2);
		playerB.setName("Player B");
		
		// Assign players
		game.setPlayer1(playerA);
		game.setPlayer2(playerB);
		
		
		Result result = game.play(playerA, playerB);
		
		if (null != result.getPlayer()) {
			System.out.print("Result: " + result.getPlayer().getName() + " -> " + result.getStatus());
		} else {
			System.out.print("Result: " + result.getStatus());
		}
		
		
	}

}
