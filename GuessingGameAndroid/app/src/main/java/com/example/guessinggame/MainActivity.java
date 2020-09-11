package com.example.guessinggame;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtGuess;
    private Button btnGuess;
    private TextView lblOutput;
    private int theNumber;
    private int numberOfTries;
    private int range;
    private TextView lblRange;

    public void newGame() {
        numberOfTries = 0;
        theNumber = (int)(Math.random() * range + 1);
        lblRange.setText("Enter a number between 1 and " + range);
        txtGuess.setText("" + range/2);
        txtGuess.requestFocus();
        txtGuess.selectAll();
    }

    public void checkGuess(){
        String guessText = txtGuess.getText().toString();
        String message = "";
        numberOfTries++;
        try {
            int guess = Integer.parseInt(guessText);

            if (guess < theNumber)
                message = guess + " is too low. Try again.";
            else if (guess > theNumber)
                message = guess + " is too high. Try again.";
            else {
                int maxNumberOfTries = (int) (Math.log(range)/ Math.log(2) + 1);
                SharedPreferences preferences =
                        PreferenceManager.getDefaultSharedPreferences(this);

                SharedPreferences.Editor editor = preferences.edit();

                if (numberOfTries > maxNumberOfTries){
                    message = guess + " is correct. But you after " + numberOfTries + " tries, " +
                            "it's high. Try again. #Max=" + maxNumberOfTries;}
                else {
                    message = guess + " is correct. You win after " + numberOfTries + " tries";
                    int gamesWon = preferences.getInt("gamesWon", 0) + 1;
                    editor.putInt("gamesWon", gamesWon);
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                int gamesPlay = preferences.getInt("gamesPlay", 0) + 1;
                editor.putInt("gamesPlay", gamesPlay);
                editor.apply();

                newGame();
            }
        } catch (Exception e) {
            message = "Enter a wholy number between 1 and " + range;
        } finally {
            lblOutput.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGuess = (EditText) findViewById(R.id.txtGuess);
        btnGuess = (Button) findViewById(R.id.btnGuess);
        lblOutput = (TextView) findViewById(R.id.lblOutput);
        lblRange = (TextView) findViewById(R.id.textView6);
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        range = preferences.getInt("range", 100);
        newGame();

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
        txtGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                checkGuess();
                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                final CharSequence[] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select the range");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                range = 10;
                                storeRange(range);
                                newGame();
                                break;
                            case 1:
                                range = 100;
                                storeRange(range);
                                newGame();
                                break;
                            case 2:
                                range = 1000;
                                storeRange(range);
                                newGame();
                                break;
                        }
                        dialog.dismiss();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.action_newgame:
                newGame();
                return true;
            case R.id.action_gamestats:

                SharedPreferences preferences =
                        PreferenceManager.getDefaultSharedPreferences(this);

                int gamesWon = preferences.getInt("gamesWon", 0);
                int gamesPlay = preferences.getInt("gamesPlay", 0);
                int percent;
                if (gamesPlay != 0) {
                    percent = gamesWon  * 100 / gamesPlay;
                }
                else {
                    percent = 0;}

                AlertDialog startDialog = new AlertDialog.Builder(MainActivity.this).create();
                startDialog.setTitle("Guessing Game Stats");
                startDialog.setMessage("You have won " + gamesWon + " out of " + gamesPlay +
                        " games, " + percent + "%. Nice");
                startDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                startDialog.show();
                return true;

            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("About Guessing Game");
                aboutDialog.setMessage("(c) 2020 Hromovuc Olexii");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
        public void storeRange(int newRange) {
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("range", newRange);
            editor.apply();

    }
}
