package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mPrevButton;
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
	    new TrueFalse(R.string.question_oceans, true),
	    new TrueFalse(R.string.question_mideast, false),
	    new TrueFalse(R.string.question_africa, false),
	    new TrueFalse(R.string.question_americas, true),
	    new TrueFalse(R.string.question_asia, true),
	};
	
	private int mCurrentIndex = 0;

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
		mQuestionTextView.setOnClickListener(nextListener);
		
		mPrevButton = (Button)findViewById(R.id.prev_button);
    mPrevButton.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        incrementQuestion(-1);
      }
    });
		
		updateQuestion();
	}

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
	  if(mQuestionBank[mCurrentIndex].isTrueQuestion() == userPressedTrue){
	    toastTextId = R.string.correct_toast;
	  } else {
	    toastTextId = R.string.incorrect_toast;
	  }
	  
	  Toast.makeText(this, toastTextId, Toast.LENGTH_LONG).show();
	}
	
	private void incrementQuestion(int numToInc){
	  while(numToInc < 0){ numToInc += mQuestionBank.length;}
	  mCurrentIndex = (mCurrentIndex + numToInc) % mQuestionBank.length;
    int question = mQuestionBank[mCurrentIndex].getQuestion();
    mQuestionTextView.setText(question);
	}
	
	private class NextListener implements View.OnClickListener {
	  @Override
    public void onClick(View v) {
      incrementQuestion(1);
    }
	}
}
