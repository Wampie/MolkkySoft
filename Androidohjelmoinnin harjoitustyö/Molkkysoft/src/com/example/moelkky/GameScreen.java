package com.example.moelkky;

import java.util.ArrayList;
import java.util.Random;

import com.example.moelkky.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * GameScreen holds all the logic for a game of Mölkky. Starts by showing the
 * randomized order of players, after that it shows player whose turn it
 * currently is, with buttons to add result of throw made, and optional button
 * to show current scores in new activity. A throw button must be pressed for
 * next players turn to begin. Ending game with back button prompts user
 * confirmation
 * 
 * @author Aleksi Majander
 * 
 */
public class GameScreen extends Activity {

	/**
	 * ArrayList on players that have been added to the game
	 */
	private ArrayList<String> players = new ArrayList<String>();
	/**
	 * String array of player objects in the order that they will throw
	 */
	private Player[] gameOrder;
	/**
	 * ArrayList holding all scores that have been updated during throws
	 */
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	/**
	 * Integer showing which players turn it is
	 */
	private int currentPlayer = 0;
	/**
	 * number of players Integer telling how many players are added to game,
	 * both those who are in game and those who have dropped
	 */
	private int playercount;
	/**
	 * Integer telling how many players are still in game
	 */
	private int playersStillInGame;

	private TextView current;
	private TextView lastThrow;
	private TextView secondLast;
	private TextView thirdLast;
	private ImageButton but1;
	private ImageButton but2;
	private ImageButton but3;
	private ImageButton but4;
	private ImageButton but5;
	private ImageButton but6;
	private ImageButton but7;
	private ImageButton but8;
	private ImageButton but9;
	private ImageButton but10;
	private ImageButton but11;
	private ImageButton but12;
	private ImageButton but0;
	private ImageButton butScore;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.gamescreen);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		current = (TextView) findViewById(R.id.current);
		lastThrow = (TextView) findViewById(R.id.lastThrow);
		secondLast = (TextView) findViewById(R.id.secondLast);
		thirdLast = (TextView) findViewById(R.id.thirdLast);
		players = this.getIntent().getStringArrayListExtra("playerlist");
		playercount = players.size();
		playersStillInGame = playercount;
		gameOrder = new Player[playercount];
		for (int i = 0; i < playercount; i++) {
			Player temp = new Player(players.get(i));
			gameOrder[i] = temp;
		}
		gameOrder = randomGameOrder(gameOrder);
		showGameOrder();
		updateUI();
		players = new ArrayList<String>();
		for (int i = 0; i < gameOrder.length; i++) {
			players.add(gameOrder[i].getName());
		}

		this.but1 = (ImageButton) this.findViewById(R.id.button1);
		this.but1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(1);
			}
		});
		this.but2 = (ImageButton) this.findViewById(R.id.but2);
		this.but2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(2);
			}
		});
		this.but3 = (ImageButton) this.findViewById(R.id.but3);
		this.but3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(3);
			}
		});
		this.but4 = (ImageButton) this.findViewById(R.id.but4);
		this.but4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(4);
			}
		});
		this.but5 = (ImageButton) this.findViewById(R.id.but5);
		this.but5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(5);
			}
		});
		this.but6 = (ImageButton) this.findViewById(R.id.but6);
		this.but6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(6);
			}
		});
		this.but7 = (ImageButton) this.findViewById(R.id.but7);
		this.but7.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(7);
			}
		});
		this.but8 = (ImageButton) this.findViewById(R.id.but8);
		this.but8.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(8);
			}
		});
		this.but9 = (ImageButton) this.findViewById(R.id.but9);
		this.but9.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(9);
			}
		});
		this.but10 = (ImageButton) this.findViewById(R.id.but10);
		this.but10.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(10);
			}
		});
		this.but11 = (ImageButton) this.findViewById(R.id.but11);
		this.but11.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(11);
			}
		});
		this.but12 = (ImageButton) this.findViewById(R.id.but12);
		this.but12.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(12);
			}
		});
		this.but0 = (ImageButton) this.findViewById(R.id.but0);
		this.but0.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newThrow(0);
			}
		});
		this.butScore = (ImageButton) this.findViewById(R.id.scoreButton);
		this.butScore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				callScoreScreen();
			}
		});
	}

	/**
	 * Method updates UI to correspond with the player whose turn it is to throw
	 * 
	 * @see com.example.moelkky.GameScreen#currentPlayer
	 * @see com.example.moelkky.GameScreen#gameOrder
	 */
	private void updateUI() {
		String nowPlaying = gameOrder[currentPlayer].getName() + ": "
				+ gameOrder[currentPlayer].getScore();
		current.setText(nowPlaying);
		nowPlaying = gameOrder[currentPlayer].get3rdLast();
		thirdLast.setText(nowPlaying);
		nowPlaying = gameOrder[currentPlayer].get2ndLast();
		secondLast.setText(nowPlaying);
		nowPlaying = gameOrder[currentPlayer].getLast();
		lastThrow.setText(nowPlaying);
	}

	/**
	 * Updates current player with result of throw made
	 * 
	 * Method is called when player presses the button corresponding his last
	 * throws result. It adds score to scores list and checks if player wins
	 * with the throw and either calls gameWon() or nextPlayer()
	 * 
	 * @param result
	 *            Integer value of last throw player made
	 * 
	 * @see com.example.moelkky.GameScreen#gameWon()
	 * @see com.example.moelkky.GameScreen#nextPlayer()
	 * @see com.example.moelkky.GameScreen#scores
	 * @see com.example.moelkky.GameScreen#currentPlayer
	 * @see com.example.moelkky.Player#newThrow(int)
	 */
	private void newThrow(int result) {

		boolean didPlayerWin = gameOrder[currentPlayer].newThrow(result);
		if (!gameOrder[currentPlayer].getLast().equals("-")) {
			scores.add(gameOrder[currentPlayer].getScore());
		} else {
			scores.add(0);
			if (!gameOrder[currentPlayer].inGame()) {
				playersStillInGame--;
			}
		}
		if (didPlayerWin) {
			gameWon();

		} else {
			nextPlayer();
		}
	}

	/**
	 * Method changes the next player in game to be the current player
	 * 
	 * Method is called when one player finishes his turn, it checks if there
	 * are still other players in game, if so it goes recursively trough the
	 * gameOrder list to find next player still in game and updates UI to
	 * correspond with the player in question
	 * 
	 * @see com.example.moelkky.GameScreen#updateUI()
	 * @see com.example.moelkky.GameScreen#gameWon()
	 * @see com.example.moelkky.GameScreen#playercount
	 * @see com.example.moelkky.GameScreen#currentPlayer
	 * @see com.example.moelkky.GameScreen#gameOrder
	 * 
	 */
	private void nextPlayer() {
		if (playersStillInGame == 1) {
			gameWon();
		}
		if (currentPlayer == playercount - 1) {
			currentPlayer = 0;
			if (gameOrder[currentPlayer].inGame()) {
				updateUI();
			} else {
				nextPlayer();
			}
		} else {
			currentPlayer++;
			if (gameOrder[currentPlayer].inGame()) {
				updateUI();
			} else {
				scores.add(0);
				nextPlayer();
			}
		}
	}

	/**
	 * Adds confirmation to back key press
	 * 
	 * Method overrides the default BACK key press to prompt alert dialog
	 * requiring user confirmation for exit
	 * 
	 */
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Haluatko varmasti lopettaa?")
				.setTitle("Huomio!")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Calls ScoreScreen activity
	 * 
	 * Method creates new Intent, adds the list of players and scores and starts
	 * activity activity from ScoreBoard.
	 * 
	 * @see com.example.moelkky.Scoreboard
	 */
	private void callScoreScreen() {
		Intent myIntent = new Intent(this, Scoreboard.class);
		myIntent.putStringArrayListExtra("playerlist", players);
		myIntent.putIntegerArrayListExtra("scores", scores);
		startActivity(myIntent);
	}

	/**
	 * Method randomizes the order in given Player[] array
	 * 
	 * @param currentOrder
	 *            Array of Players
	 * @return Array of Players with random order
	 */
	private Player[] randomGameOrder(Player[] currentOrder) {
		Random randomizer = new Random();
		for (int i = currentOrder.length - 1; i > 0; i--) {
			int s = randomizer.nextInt(i);
			Player help = currentOrder[s];
			currentOrder[s] = currentOrder[i];
			currentOrder[i] = help;
		}
		return currentOrder;
	}

	/**
	 * Dialog showing winning player
	 * 
	 * Method creates AlertDialog that tells the name of winning Player and
	 * gives choices to either show final scores, or end the game
	 * 
	 * @see com.example.moelkky.GameScreen#callScoreScreen()
	 */
	private void gameWon() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Onnea " + gameOrder[currentPlayer].getName()
						+ " Voitit pelin!")
				.setTitle("Peli ohi:")
				.setCancelable(false)
				.setPositiveButton("Lopputulos",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								callScoreScreen();
								finish();
							}
						})
				.setNegativeButton("Lopeta",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								finish();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Dialog showing the order of players
	 * 
	 * Method creates AlertDialog to show the generated order in which players
	 * will play the game
	 */

	private void showGameOrder() {
		String order = "";
		for (int i = 0; i < gameOrder.length; i++) {
			order += i + 1 + ". " + gameOrder[i].getName() + "\n";
		}
		order = order.trim();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(order).setTitle("Heittojärjestys:")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
