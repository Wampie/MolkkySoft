package com.example.moelkky;

import java.util.ArrayList;

import com.example.moelkky.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * NewGame screen gives options to add players and start playing. The players
 * are added with dialog prompting user input, added players are showing on
 * toprow of the screen, user can't start game with less than two players and
 * players name must contain at least three letters
 * 
 * @author Joakim
 * 
 */
public class NewGame extends Activity {
	/**
	 * AddPlayer button initiates in OnCreate and calls addplayer() when pressed
	 * 
	 * @see com.example.moelkky.NewGame#addplayer():
	 */
	private ImageButton addbutton;
	/**
	 * Button for adding new players
	 * 
	 * AddPlayer button initiates in OnCreate, on press it checks if user has
	 * add players, and either starts GameScreen, or prompts up alert dialog for
	 * too few players
	 * 
	 * @see com.example.moelkky.NewGame#tooFewPlayersAlert()
	 * @see com.example.moelkky.NewGame#startGame()
	 */
	private ImageButton startbutton;

	/**
	 * Top row of the XML layout
	 * 
	 * Stored TableRow object that is attached to XML layout in onCreate, adding
	 * TextViews to this rows attaches them to top of layout, so user can track
	 * added players
	 */
	TableRow row;

	/**
	 * ArrayList storing player names
	 * 
	 * ArrayList to store all player names, it will be passed to GameScreen when
	 * user decides to start game.
	 */
	private ArrayList<String> players = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newgame);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		row = (TableRow) findViewById(R.id.playerrow);

		this.addbutton = (ImageButton) this.findViewById(R.id.add);
		this.addbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addplayer();
			}
		});
		this.startbutton = (ImageButton) this.findViewById(R.id.start);
		this.startbutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (players.size() < 2) {
					tooFewPlayersAlert();
				} else {
					startGame();
				}
			}
		});

	}

	/**
	 * Calls GameScreen activity
	 * 
	 * Method creates new Intent, adds the list of players to it and starts
	 * activity from GameScreen, it also applies, that when GameScreens activity
	 * finishes, also this activity finishes.
	 * 
	 * @see com.example.moelkky.GameScreen
	 */
	private void startGame() {
		Intent myIntent = new Intent(this, GameScreen.class);
		myIntent.putStringArrayListExtra("playerlist", players);
		startActivity(myIntent);
		finishFromChild(null);
	}

	/**
	 * Dialog asking user input for players name
	 * 
	 * Method creates new AlertDialog that checks if there is still room for new
	 * players (max 10) and if so, calls newPlayer(). If there is no more room,
	 * will call playerLimitReachedAlert():
	 * 
	 * @see com.example.moelkky.NewGame#playerLimitReachedAlert()
	 * 
	 * @see com.example.moelkky.NewGame#newPlayer(String)
	 */
	private void addplayer() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Lisää pelaaja");
		alert.setMessage("nimi:");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (players.size() < 10) {
					String tempPlayer = input.getText().toString();
					newPlayer(tempPlayer);
				} else {
					playerLimitReachedAlert();
				}
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
					}
				});
		alert.show();
	}

	/**
	 * Method for generating TextView object from String variable
	 * 
	 * Method takes user input and creates an TextView object with correct
	 * attributes and adds it to TableRow row, if user input is too short, will
	 * call playerNameTooShortAlert()
	 * 
	 * @param player
	 *            user input for players name
	 * 
	 * @see com.example.moelkky.NewGame#playerNameTooShortAlert()
	 * 
	 * @see com.example.moelkky.NewGame#row
	 */
	private void newPlayer(String player) {
		if (player.length() < 3) {
			playerNameTooShortAlert();
			return;
		}
		players.add(player);
		if (player.length() >= 3) {
			player = player.substring(0, 3);
		}
		TextView temp = new TextView(this);
		temp.setText(player);
		temp.setTextSize(18);
		temp.setTypeface(null, Typeface.BOLD);
		row.addView(temp);
	}

	/**
	 * Dialog telling user cant start game with less than 2 players
	 * 
	 * Method creates AlertDialog to stop user from starting the game with less
	 * than 2 players.
	 */
	private void tooFewPlayersAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Hanki kavereita, Mölkkyyn tarvitaan ainakin kaksi pelaajaa!")
				.setTitle("VIRHE!").setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Dialog telling user playerlimit is reached
	 * 
	 * Method creates AlertDialog that tells user that player limit has been
	 * reached and offers start button for calling startGame()
	 * 
	 * @see com.example.moelkky.NewGame#startGame()
	 */
	private void playerLimitReachedAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Valitettavasti peli on täynnä")
				.setTitle("Huomio!")
				.setCancelable(false)
				.setPositiveButton("Aloita peli!",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								startGame();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Dialog telling user that input was too short
	 * 
	 * Method creates AlertDialog to stop user from adding players that have
	 * less than three letters in their names.
	 */

	private void playerNameTooShortAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Annathan nimeen ainakin kolme merkkiä")
				.setTitle("VIRHE!").setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}