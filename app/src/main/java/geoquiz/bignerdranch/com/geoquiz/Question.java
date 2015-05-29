package geoquiz.bignerdranch.com.geoquiz;

/**
 * Created by mzf6js on 21/05/2015.
 */
public class Question {

    private int mQuestion;
    private boolean mAnswer;

    public Question(int question, boolean answer) {
        mQuestion = question;
        mAnswer=answer;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
