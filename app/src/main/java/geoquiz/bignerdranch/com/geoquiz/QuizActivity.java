package geoquiz.bignerdranch.com.geoquiz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class QuizActivity extends Activity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private boolean mUserCheated;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;
    private int mCurrentQuestionIndex = 0;

    public final static String EXTRA_ANSWER="geoquiz.bignerdranch.com.geoquiz.answer";
    public final static String EXTRA_CHEATED="geoquiz.bignerdranch.com.geoquiz.cheated";

    private Question[] mQuestionList = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mCheatButton = (Button)findViewById(R.id.cheat_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this,CheatActivity.class);
                intent.putExtra(EXTRA_ANSWER,mQuestionList[mCurrentQuestionIndex].isAnswer());
                startActivityForResult(intent, 0);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentQuestionIndex = (mCurrentQuestionIndex+1)% mQuestionList.length;
                mUserCheated = false;
                updateQuestion();

            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentQuestionIndex = (mCurrentQuestionIndex+1)% mQuestionList.length;
                updateQuestion();

            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentQuestionIndex = (5+(mCurrentQuestionIndex-1))% mQuestionList.length;
                updateQuestion();

            }
        });
    }

    private void updateQuestion(){
        int question = mQuestionList[mCurrentQuestionIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userChoice){
        int answerResult = 0;
        if(mUserCheated){
            answerResult = R.string.judgment_toast;
        }
        else{
            if(userChoice == mQuestionList[mCurrentQuestionIndex].isAnswer()){
                answerResult = R.string.correct_toast;
            }else{
                answerResult = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this,answerResult,Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        mUserCheated = data.getBooleanExtra(EXTRA_CHEATED, false);
    }
}
