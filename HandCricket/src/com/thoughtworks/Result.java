package com.thoughtworks;

/**
 * Model calss for the result information
 * @author ubuntu
 *
 */
public class Result {
	
	private Status status;
	
	private Player player;

	public Status getStatus() {
		return status;
	}

	public Player getPlayer() {
		return player;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	

}
