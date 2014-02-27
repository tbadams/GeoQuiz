package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An activity consisting of a quiz on various geography questions.  Users are informed by toasts
 * whether there answers are correct, and buttons allow them to move forward and backwards through
 * the questions.
 * @author Tadams
 */
public class QuizActivity extends Activity {
  
  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";
	
  /*
   *  Screen elements.
   */
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	
	/*
	 * Array containing questions to be asked by activity, in the forms of TrueFalse objects.
	 */
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
	    new TrueFalse(R.string.question_oceans, true),
	    new TrueFalse(R.string.question_mideast, false),
	    new TrueFalse(R.string.question_africa, false),
	    new TrueFalse(R.string.question_americas, true),
	    new TrueFalse(R.string.question_asia, true),
	};
	
	private int mCurrentIndex = 0;
	
	private boolean mIsCheater;

	/**
	 * Creates the activity, setting up the initial view and the behavior of the Buttons.
	 * 
	 * @param savedInstanceState instance information used to restore activity to its previous state. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
		
		mTrueButton = (Button)findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		
		mFalseButton = (Button)findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		NextListener nextListener = new NextListener();
		mNextButton = (Button)findViewById(R.id.next_button);
		mNextButton.setOnClickListener(nextListener);
		
		mCheatButton = (Button)findViewById(R.id.cheatButton);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Intent i = new Intent(QuizActivity.this, CheatActivity.class);
        i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, 
            mQuestionBank[mCurrentIndex].isTrueQuestion());
        startActivityForResult(i, 0);
      }
    });
		
		if(savedInstanceState != null){
		  mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		}
		
		updateQuestion();
	}

	/**
	 * Inflates the options menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}
	
	private void updateQuestion(){
	  int question = mQuestionBank[mCurrentIndex].getQuestion();
      mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
	  int toastTextId = 0;
	  if(mIsCheater) {
	    toastTextId = R.string.judgment_toast;
	  } else {
  	  if(mQuestionBank[mCurrentIndex].isTrueQuestion() == userPressedTrue){
  	    toastTextId = R.string.correct_toast;
  	  } else {
  	    toastTextId = R.string.incorrect_toast;
  	  }
	  }
	  Toast.makeText(this, toastTextId, Toast.LENGTH_LONG).show();
	}
	
	private void incrementQuestion(int numToInc){
	  while(numToInc < 0){ numToInc += mQuestionBank.length;}
	  mCurrentIndex = (mCurrentIndex + numToInc) % mQuestionBank.length;
	  mIsCheater = false;
    updateQuestion();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState){
	  super.onSaveInstanceState(savedInstanceState);
	  Log.i(TAG, "onSaveInstanceState");
	  savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (data == null) {
	    return;
	  }
	  mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
	}
	
	private class NextListener implements View.OnClickListener {
	  @Override
    public void onClick(View v) {
      incrementQuestion(1);
    }
	}
}
