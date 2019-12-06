package com.thoughtworks;

public class Round {
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getScore(Player battingPlayer, Player bowlingPlayer, Integer earnedScore) {
		int battingScore = 0;
		for (int numberOfThrows= 1; numberOfThrows <= Rule.MAXIMUM_NUMBER_OF_THROWS; numberOfThrows++) {
			int battingPlayerScore = battingPlayer.performThorw();
			int bowlingPlayerScore = bowlingPlayer.performThorw();
			if (Rule.checkIfPlayerHasDissmissed(battingPlayerScore, bowlingPlayerScore, numberOfThrows)) {
				break;
			} else {
				battingScore = battingScore + battingPlayerScore;
			}
			
			if (null != earnedScore && battingScore > earnedScore) {
				break;
			}
		}
		return battingScore;
	}
	
	
	
}
