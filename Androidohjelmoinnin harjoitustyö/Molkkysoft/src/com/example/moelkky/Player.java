package com.example.moelkky;

/**
 * Stores information of single player.
 * Player stores name, last three throws and total score, also calculates if
 * player drops due three misses, accessed with getters and setters.
 * 
 * @author Joakim
 * 
 */
public class Player {
	/**
	 * The name of the player
	 */
	private String name;
	/**
	 * String input of the last throw player made
	 */
	private String lastThrow = "NA";
	/**
	 * String input of the second last throw player made
	 */
	private String secondLast = "NA";
	/**
	 * String input of third last throw player made
	 */
	private String thirdLast = "NA";
	/**
	 * Counter to keep in memory how many consecutive misses player has thrown
	 */
	private int misscounter = 0;
	/**
	 * The score player has
	 */
	private int score = 0;

	public Player(String name) {
		this.name = name;
	}

	public String getName() {

		return name;
	}

	public String getLast() {
		return lastThrow;
	}

	public String get2ndLast() {
		return secondLast;
	}

	public String get3rdLast() {
		return thirdLast;
	}

	public int getScore() {
		return score;
	}

	/**
	 * Tells if user is still in game. Method checks if user is still in game by
	 * making sure, that he has not made 3 misses in a row (misscounter < 3)
	 * 
	 * @see com.example.moelkky.Player#misscounter
	 * 
	 * @return boolean depending if player is still in game
	 */
	public boolean inGame() {
		if (misscounter < 3) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Updates object with result of last throw made.
	 * 
	 * Method updates score and three last throws with given result Integer It
	 * also checks if player goes over 50 points, and if so, sets score to 25
	 * Returns false by default, true if player wins with the throw made
	 * 
	 * @param Integer
	 *            value of last throw made (0-12)
	 * 
	 * @see com.example.moelkky.Player#misscounter
	 * @see com.example.moelkky.Player#score
	 * 
	 * @return true if player wins the game with the throw, false if not
	 */
	public boolean newThrow(int result) {
		String temp = "" + result;
		if (result > 0) {
			score += result;
			if (score > 50) {
				score = 25;
			}
			misscounter = 0;
		} else {
			temp = "-";
			misscounter++;
		}
		thirdLast = secondLast;
		secondLast = lastThrow;
		lastThrow = temp;
		if (score == 50) {
			return true;
		} else {
			return false;
		}
	}
}
