package com.example.james.learnasaurus;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaTestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaTestFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private MainActivity main;

    private TextView header;
    private TextView textQuestion;
    private Button option1, option2, option3, option4;
    private int score = 0;
    private int endScore = 0;
    private int correct = 0;
    private String grade;
    private JavaTestQuestions questions = new JavaTestQuestions();
    private int questionNumber = 0;
    private String answer;
    private Timer changeTimer;
    private Timer questionTimer;

    private FragmentManager fragmentManager;
    private TestResultFragment testResultFragment;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    public JavaTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaTestFragment.
     */
    public static JavaTestFragment newInstance() {
        JavaTestFragment fragment = new JavaTestFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity)getActivity();
        changeTimer = new Timer();
        questionTimer = new Timer();
        fragmentManager = getFragmentManager();
        testResultFragment = new TestResultFragment();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_test_fragment, container, false);

        header = (TextView)view.findViewById(R.id.javaTestHeader);
        header.setText(R.string.java_test_question_header + Integer.toString(questionNumber + 1));
        textQuestion = (TextView)view.findViewById(R.id.javaTestTextQuestion);

        option1 = (Button)view.findViewById(R.id.javaTestOptionOne);
        option2 = (Button)view.findViewById(R.id.javaTestOptionTwo);
        option3 = (Button)view.findViewById(R.id.javaTestOptionThree);
        option4 = (Button)view.findViewById(R.id.javaTestOptionFour);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        changeQuestion();

        return view;
    }

    private void changeQuestion(){
        if(questionNumber < questions.numberOfQuestions()){
            header.setText(R.string.java_test_question_header);
            textQuestion.setText(questions.pullQuestion(questionNumber));
            option1.setText(questions.pullOptionOne(questionNumber));
            option2.setText(questions.pullOptionTwo(questionNumber));
            option3.setText(questions.pullOptionThree(questionNumber));
            option4.setText(questions.pullOptionFour(questionNumber));
            changeColour("change", option1);
            changeColour("change", option2);
            changeColour("change", option3);
            changeColour("change", option4);
            answer = questions.pullCorrect(questionNumber);
            questionNumber += 1;
        } else {
            calculateScore();
            ValueEventListener javaTestDatabaseListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int databaseCurrentScore;
                    User profile = dataSnapshot.getValue(User.class);
                    databaseCurrentScore = Integer.parseInt(profile.score);
                    int newScore = databaseCurrentScore + endScore;
                    String newScoreString = Integer.toString(newScore);
                    databaseReference.child("score").setValue(newScoreString);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "updateScore:onCancelled", databaseError.toException());
                }
            };
            databaseReference.addListenerForSingleValueEvent(javaTestDatabaseListener);
            MainActivity main = (MainActivity)getActivity();
            main.setTestVariables("Java", grade, score);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, testResultFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.javaTestOptionOne:
                if(option1.getText() == answer){
                    score += 1;
                    correct += 1;
                    changeColour("correct", option1);
                } else{
                    changeColour("wrong", option1);
                }
                changeQuestion();
                break;
            case R.id.javaTestOptionTwo:
                if(option2.getText() == answer){
                    score += 1;
                    correct += 1;
                    changeColour("correct", option2);
                } else{
                    changeColour("wrong", option2);
                }
                changeQuestion();
                break;
            case R.id.javaTestOptionThree:
                if(option3.getText() == answer){
                    score += 1;
                    correct += 1;
                    changeColour("correct", option3);
                } else{
                    changeColour("wrong", option3);
                }
                changeQuestion();
                break;
            case R.id.javaTestOptionFour:
                if(option4.getText() == answer){
                    score += 1;
                    correct += 1;
                    changeColour("correct", option4);
                } else{
                    changeColour("wrong", option4);
                }
                changeQuestion();
                break;
        }
    }

    private void calculateScore(){
        if(score == 10){
            endScore = 100;
            grade = "A*";
        } else if(score == 9){
            endScore = 90;
            grade = "A+";
        } else if(score == 8){
            endScore = 80;
            grade = "A";
        } else if(score == 7){
            endScore = 70;
            grade = "A-";
        } else if(score == 6){
            endScore = 60;
            grade = "B";
        } else if(score == 5){
            endScore = 50;
            grade = "C";
        } else if(score == 4){
            endScore = 40;
            grade = "D";
        } else if(score == 3){
            endScore = 30;
            grade = "F";
        } else if(score == 2){
            endScore = 20;
            grade = "F";
        } else if(score == 1){
            endScore = 10;
            grade = "F";
        } else {
            endScore = 0;
            grade = "U";
        }
        main.incrementAchievements(endScore);
        main.updateLeaderboard(endScore);
    }

    private void changeColour(String correct, Button button){
        if(correct.equals("correct")){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                button.setBackgroundColor(getActivity().getResources().getColor(R.color.buttonGreen, getActivity().getTheme()));
            }
            else {
                button.setBackgroundColor(getActivity().getResources().getColor(R.color.buttonGreen));
            }
        }
        else if (correct.equals("wrong")){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                button.setBackgroundColor(getActivity().getResources().getColor(R.color.red, getActivity().getTheme()));
            }
            else {
                button.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
            }
        }
        else if(correct.equals("change")){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                button.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corners,
                        getActivity().getTheme()));
            }
            else {
                button.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.rounded_corners));
            }
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
