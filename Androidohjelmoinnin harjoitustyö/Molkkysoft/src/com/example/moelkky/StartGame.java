package com.example.moelkky;

import com.example.moelkky.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
/**
 * The TitleScreen of the application. Has buttons for starting a new game and exiting application
 * @author Joakim
 *
 */
public class StartGame extends Activity {

	/**
	 * Close button initiates in OnCreate and calls finish() on current activity
	 * and therefore terminates application
	 * 
	 * @see com.example.moelkky.StartGame#finish()
	 */
	private ImageButton closebutton;
	/**
	 * NewGame button initiates in OnCreate and calls handleNewGameCall when
	 * pressed
	 * 
	 * @see com.example.moelkky.StartGame#handleNewGameCall()
	 */
	private ImageButton newgamebutton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.closebutton = (ImageButton) this.findViewById(R.id.close);
		this.closebutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		this.newgamebutton = (ImageButton) this.findViewById(R.id.newgame);
		this.newgamebutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				handleNewGameCall();
			}
		});
	}

	/**
	 * Method creates new Intent and starts activity from NewGame
	 * 
	 * @see com.example.moelkky.NewGame
	 */
	private void handleNewGameCall() {
		startActivity(new Intent(this, NewGame.class));
	}
	/**
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
}