package com.bignerdranch.android.geoquiz;

public class TrueFalse {
	
	private int mQuestion;
	
	private boolean mTrueQuestion;
	
	/**
	 * Creates a True/False question object.
	 * @param question Resource ID of the question's string resource
	 * @param trueQuestion the truth value of the question's answer
	 */
	public TrueFalse(int question, boolean trueQuestion){
		mQuestion = question;
		mTrueQuestion = trueQuestion;
	}

	/**
	 * @return question's text resource ID
	 */
	public int getQuestion() {
		return mQuestion;
	}

	/**
	 * @param question set resource ID of question's text
	 */
	public void setQuestion(int question) {
		mQuestion = question;
	}

	/**
	 * @return whether the statement is true or false
	 */
	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}

	/**
	 * @param trueQuestion set whether the statement is true or false
	 */
	public void setTrueQuestion(boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}
}
