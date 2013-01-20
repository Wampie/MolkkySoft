package com.example.moelkky;

import java.util.ArrayList;

import com.example.moelkky.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableRow.LayoutParams;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Scoreboard shows the scoretable from ongoing game. Shows how much was
 * players total score after throw or - if throw was miss, has button for closing
 * 
 * @author Joakim
 * 
 */
public class Scoreboard extends Activity {
	/**
	 * Back button initiates in OnCreate and calls finish() on current activity
	 * 
	 * @see com.example.moelkky.Scoreboard#finish()
	 */
	ImageButton back;
	/**
	 * Initiates to the lower TableLayout in XML layout file, this is the one in
	 * ScrollView where all the scores are added
	 */
	TableLayout scoretable;
	/**
	 * ArrayList to store all player names, inherited from Intent that user
	 * created while calling for Scoreboard activity
	 */
	ArrayList<String> players;
	/**
	 * ArrayList to store all player scores, inherited from Intent that user
	 * created while calling for Scoreboard activity
	 */
	ArrayList<Integer> scores;
	/**
	 * Simple counter that is used to remember how many scores are added per
	 * row, so that new rows will be created at right times
	 */
	int scorecounter = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.scoreboard);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		players = this.getIntent().getStringArrayListExtra("playerlist");
		scores = this.getIntent().getIntegerArrayListExtra("scores");
		scoretable = (TableLayout) findViewById(R.id.scores);

		TableRow row = (TableRow) findViewById(R.id.players);
		for (int i = 0; i < players.size(); i++) {
			row.addView(addPlayer(players.get(i)));
		}
		TableRow tr = newRow();

		for (int i = 0; i < scores.size(); i++) {
			tr.addView(addScore(scores.get(i)));
			scorecounter++;
			if (scorecounter == players.size()) {
				scoretable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				tr = newRow();
				scorecounter = 0;
			}
		}
		scoretable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		this.back = (ImageButton) this.findViewById(R.id.back);
		this.back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * Method creates a TextView with correct attributes from String variable
	 * 
	 * @param player
	 *            next unused player name found on players list
	 * 
	 * @return a optimized TextView
	 */
	private TextView addPlayer(String player) {
		TextView temp = new TextView(this);
		if (player.length() > 6) {
			player = player.substring(0, 6);
		}
		temp.setText(player);
		temp.setTextSize(18);
		temp.setTextColor(android.graphics.Color.WHITE);
		temp.setTypeface(null, Typeface.BOLD);
		return temp;
	}

	/**
	 * Method creates a TextView with correct attributes from Integer variable
	 * 
	 * @param score
	 *            next unused integer from scores list
	 * 
	 * @return a optimized TextView
	 */

	private TextView addScore(int score) {

		TextView tempscore = new TextView(this);
		tempscore.setTextSize(14);
		tempscore.setTextColor(android.graphics.Color.WHITE);
		tempscore.setTypeface(null, Typeface.BOLD);
		tempscore.setText("" + score);
		if (score == 0) {
			tempscore.setText("-");
		}
		return tempscore;
	}

	/**
	 * Creates new empty TableRow to TableLayout holding scores
	 * 
	 * Method creates a new TableRow, gives it same attributes as last ones and
	 * adds empty TextView (in XML layout, some parameters require that
	 * TableRows always has child)
	 * 
	 * @return an optimized, seemingly empty TableView
	 */
	private TableRow newRow() {
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		TextView temp = new TextView(this);
		temp.setText("");
		tr.addView(temp);
		return tr;
	}
}
