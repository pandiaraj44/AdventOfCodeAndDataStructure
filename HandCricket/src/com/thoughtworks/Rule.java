package com.thoughtworks;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * This calss used to define the rules and properties required by the game.
 * @author ubuntu
 *
 */
public class Rule {

	// List of supported score values
	public static final Integer[] RUNS_ARRAY = {0, 1, 2, 3, 4, 6};
	
	public static final Integer MAXIMUM_NUMBER_OF_THROWS = 6;
	
	public static boolean checkIfPlayerHasDissmissed(int score1, int score2, int numberOfThrows) {
		return (score1 == score2 || numberOfThrows > MAXIMUM_NUMBER_OF_THROWS );
	}
	
	
}