package com.android.itechsuite.tictactoe;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FiveViews extends AppCompatActivity {
    // Represents the internal state of the game
    private TicTacToeFiveBoard mGame;

    // Buttons making up the board
    private Button mBoardButtons[];
    private Button mNewGame;

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    // Counters for the wins and ties
    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;

    // Bools needed to see if player one goes first
    // if the gameType is to be single or local multiplayer
    // if it is player one's turn
    // and if the game is over
    private boolean mPlayerOneFirst = true;
    private boolean mIsSinglePlayer = false;
    private boolean mIsPlayerOneTurn = true;
    private boolean mGameOver = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_fiveviews);


        boolean mGameType = getIntent().getExtras().getBoolean("gameTypes");
        // Initialize the buttons
        // Log.d("BoardSize", "Board size is initialized"+ mGame.getBOARDS_SIZE());
        // mBoardButtons = new Button[mGame.getBOARDS_SIZE()];
        mBoardButtons = new Button[25];
        mBoardButtons[0] = (Button) findViewById(R.id.fOne);
        mBoardButtons[1] = (Button) findViewById(R.id.fTwo);
        mBoardButtons[2] = (Button) findViewById(R.id.fThree);
        mBoardButtons[3] = (Button) findViewById(R.id.fFour);
        mBoardButtons[4] = (Button) findViewById(R.id.fFive);
        mBoardButtons[5] = (Button) findViewById(R.id.fsix);
        mBoardButtons[6] = (Button) findViewById(R.id.fSeven);
        mBoardButtons[7] = (Button) findViewById(R.id.fEight);
        mBoardButtons[8] = (Button) findViewById(R.id.fNine);
        mBoardButtons[9] = (Button) findViewById(R.id.fTen);
        mBoardButtons[10] = (Button) findViewById(R.id.fEleven);
        mBoardButtons[11] = (Button) findViewById(R.id.fTwelve);
        mBoardButtons[12] = (Button) findViewById(R.id.fThirteen);
        mBoardButtons[13] = (Button) findViewById(R.id.fFourteen);
        mBoardButtons[14] = (Button) findViewById(R.id.fFifteen);
        mBoardButtons[15] = (Button) findViewById(R.id.fSixteen);
        mBoardButtons[16] = (Button) findViewById(R.id.fSeventeen);
        mBoardButtons[17] = (Button) findViewById(R.id.fEighteen);
        mBoardButtons[18] = (Button) findViewById(R.id.fNineteen);
        mBoardButtons[19] = (Button) findViewById(R.id.fTwenty);
        mBoardButtons[20] = (Button) findViewById(R.id.fTwentyOne);
        mBoardButtons[21] = (Button) findViewById(R.id.fTwentyTwo);
        mBoardButtons[22] = (Button) findViewById(R.id.fTwentyThree);
        mBoardButtons[23] = (Button) findViewById(R.id.fTwentyFour);
        mBoardButtons[24] = (Button) findViewById(R.id.fTwentyFive);
        addListenerOnButton();


        // setup the textviews
        mInfoTextView = (TextView) findViewById(R.id.information);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mPlayerTwoText = (TextView) findViewById(R.id.android);

        // set the initial counter display values
        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));

        // create a new game object
        mGame = new TicTacToeFiveBoard();

        // start a new game
        startNewGame(mGameType);
    }

    public void addListenerOnButton() {

        mNewGame = (Button) findViewById(R.id.NewGame);

        mNewGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startNewGame(mIsSinglePlayer);
            }
        });
    }


    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        if (player == mGame.PLAYER_ONE)
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.x));
        else
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.o));
    }

    //start a new game
    // clears the board and resets all buttons / text
    // sets game over to be false
    private void startNewGame(boolean isSingle) {

        this.mIsSinglePlayer = isSingle;

        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new FiveViews.ButtonClickListener(i));
            mBoardButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));
        }


        if (mIsSinglePlayer) {
            mPlayerOneText.setText("Human:");
            mPlayerTwoText.setText("Android:");

            if (mPlayerOneFirst) {
                mInfoTextView.setText(R.string.first_human);
                mPlayerOneFirst = false;
            } else {
                mInfoTextView.setText(R.string.turn_computer);
                int move = mGame.getComputerMove();
                setMove(mGame.PLAYER_TWO, move);
                mPlayerOneFirst = true;
            }
        } else {
            mPlayerOneText.setText("Player One:");
            mPlayerTwoText.setText("Player Two:");

            if (mPlayerOneFirst) {
                mInfoTextView.setText(R.string.turn_player_one);
                mPlayerOneFirst = false;
            } else {
                mInfoTextView.setText(R.string.turn_player_two);
                mPlayerOneFirst = true;
            }
        }

        mGameOver = false;
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!mGameOver) {
                if (mBoardButtons[location].isEnabled()) {
                    if (mIsSinglePlayer) {
                        setMove(mGame.PLAYER_ONE, location);

                        int winner = mGame.checkForWinner();

                        if (winner == 0) {
                            mInfoTextView.setText(R.string.turn_computer);
                            int move = mGame.getComputerMove();
                            setMove(mGame.PLAYER_TWO, move);
                            winner = mGame.checkForWinner();
                        }

                        if (winner == 0)
                            mInfoTextView.setText(R.string.turn_human);
                        else if (winner == 1) {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (winner == 2) {
                            mInfoTextView.setText(R.string.result_human_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                        } else {
                            mInfoTextView.setText(R.string.result_android_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                        }
                    } else {
                        if (mIsPlayerOneTurn)
                            setMove(mGame.PLAYER_ONE, location);
                        else
                            setMove(mGame.PLAYER_TWO, location);

                        int winner = mGame.checkForWinner();

                        if (winner == 0) {
                            if (mIsPlayerOneTurn) {
                                mInfoTextView.setText(R.string.turn_player_two);
                                mIsPlayerOneTurn = false;
                            } else {
                                mInfoTextView.setText(R.string.turn_player_one);
                                mIsPlayerOneTurn = true;
                            }
                        } else if (winner == 1) {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (winner == 2) {
                            mInfoTextView.setText(R.string.result_player_one_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                            mIsPlayerOneTurn = false;
                        } else {
                            mInfoTextView.setText(R.string.result_player_two_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                            mIsPlayerOneTurn = true;
                        }
                    }
                }
            }
        }
    }

}