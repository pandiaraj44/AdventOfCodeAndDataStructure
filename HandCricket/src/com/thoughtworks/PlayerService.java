package com.thoughtworks;
/**
 * Interface to declare the play logics
 * @author ubuntu
 *
 */
public interface PlayerService {
	/**
	 *  To handle the play logic for the given players.
	 *  
	 * @param player1 A player1 object
	 *  @param player1 A player1 object
	 * @return Result - Return the game result status
	 */
	Result play(Player player1, Player player2);
}
