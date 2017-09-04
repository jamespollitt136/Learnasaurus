package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaInheritanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaInheritanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaInheritanceFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private OnFragmentInteractionListener mListener;

    private boolean isSpeaking;
    private MainActivity main;
    private String specialisationTts;
    private String inheritanceTts;
    private String parentsTts;
    private String extendsTts;
    private String exerciseTts;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userId;
    private final DatabaseReference exerciseReference = FirebaseDatabase.getInstance().getReference()
            .child("exercises").child("computing").child("java").child("relationships")
            .child("inheritance").child(user.getUid());
    private int attempts;
    private int correct;
    private int gradeNumber;
    private String date;
    private String grade;
    private int updatedScore;

    private EditText extendsInput;

    private DialogController controller;

    public JavaInheritanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaInheritanceFragment.
     */
    public static JavaInheritanceFragment newInstance() {
        JavaInheritanceFragment fragment = new JavaInheritanceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = user.getUid();

        isSpeaking = false;
        main = (MainActivity)getActivity();
        specialisationTts = getResources().getString(R.string.java_inheritance_intro);
        inheritanceTts = getResources().getString(R.string.java_inheritance_whata);
        parentsTts = getResources().getString(R.string.java_inheritance_parents_tts);
        extendsTts = getResources().getString(R.string.java_inheritance_extends_tts);
        exerciseTts = getResources().getString(R.string.java_inheritance_extends_exer);
        controller = new DialogController(getActivity());

        attempts = 0;
        correct = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_inheritance_fragment, container, false);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        date = year +"/"+ (month+1) +"/"+day;

        ImageView inherTts = (ImageView)view.findViewById(R.id.inherTtsImage);
        inherTts.setOnClickListener(this);

        TextView specialistionText = (TextView)view.findViewById(R.id.javaInherIntro);
        specialistionText.setOnLongClickListener(this);

        TextView inheritanceText = (TextView)view.findViewById(R.id.javaInherWhatAns);
        inheritanceText.setOnLongClickListener(this);

        TextView inJavaIntroText = (TextView)view.findViewById(R.id.javaInherFamily);
        inJavaIntroText.setOnLongClickListener(this);
        TextView inJavaParentsText = (TextView)view.findViewById(R.id.JavaInherDif);
        inJavaParentsText.setOnLongClickListener(this);
        TextView inJavaSubclassesText = (TextView)view.findViewById(R.id.javaInherSub);
        inJavaSubclassesText.setOnLongClickListener(this);
        TextView inJavaFeatures = (TextView)view.findViewById(R.id.javaInherFeatures);
        inJavaFeatures.setOnLongClickListener(this);

        TextView extendsIntroText = (TextView)view.findViewById(R.id.inherExtendsIntro);
        extendsIntroText.setOnLongClickListener(this);
        TextView extendsCodeView = (TextView)view.findViewById(R.id.javaInherExtendsCode);
        extendsCodeView.setText(Html.fromHtml(getString(R.string.java_inheritance_extends_codea)));
        extendsCodeView.setOnLongClickListener(this);
        TextView extendsCodeExp = (TextView)view.findViewById(R.id.javaInherExtEx);
        extendsCodeExp.setOnLongClickListener(this);

        TextView exerciseText = (TextView)view.findViewById(R.id.inherExerciseText);
        exerciseText.setOnLongClickListener(this);

        extendsInput = (EditText)view.findViewById(R.id.extendsExerciseInput);
        Button check = (Button)view.findViewById(R.id.inherExtCheckBtn);
        check.setOnClickListener(checkButtonListener);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.inherTtsImage:
                if(isSpeaking){ // if currently speaking turn off
                    isSpeaking = false;
                    main.stopSpeech();
                }
                else { // if not currently speaking
                    isSpeaking = true;
                    main.speak(specialisationTts);
                    main.speak(inheritanceTts);
                    main.speak(parentsTts);
                    main.speak(extendsTts);
                    main.speak(exerciseTts);
                }
                break;
        }
    }

    private int calculateGrade(){
        gradeNumber = 0;
        if(attempts == 1){
            grade = "A";
            gradeNumber = 1;
        } else if(attempts == 2){
            grade = "B";
            gradeNumber = 2;
        } else if(attempts == 3 ){
            grade = "C";
            gradeNumber = 3;
        } else if(attempts == 4){
            grade = "D";
            gradeNumber = 4;
        }
        return gradeNumber;
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

    @Override
    public boolean onLongClick(View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch(v.getId()){
            case R.id.javaInherIntro:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(specialisationTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaInherWhatAns:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(inheritanceTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaInherFamily:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(parentsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.JavaInherDif:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(parentsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaInherSub:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(parentsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaInherFeatures:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(parentsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.inherExtendsIntro:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(extendsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaInherExtendsCode:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(extendsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaInherExtEx:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(extendsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.inherExerciseText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(exerciseTts);
                        return true;
                    }
                });
                popup.show();
                break;
        }
        return true;
    }

    View.OnClickListener checkButtonListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            attempts += 1;
            if(extendsInput.getText().toString().equals("public class Car extends Vehicle") ||
                    extendsInput.getText().toString().equals("public class Car extends Vehicle {")){
                correct += 1;
                calculateGrade();
                controller.generateCorrectDialog("\"Muuum, my phone keeps calling me a subclass!\"\n\n" +
                "Well done!");
                main.unlockAchievement("CgkIrqu_x-kEEAIQAg"); // learnasaurus lex
                main.incrementAchievements(5);
                main.updateLeaderboard(5);
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(userId);
                ValueEventListener extendsDatabaseListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User profile = dataSnapshot.getValue(User.class);
                        int databaseCurrentScore = Integer.parseInt(profile.score);
                        int newScore = databaseCurrentScore + 5;
                        String newScoreString = Integer.toString(newScore);
                        databaseReference.child("score").setValue(newScoreString);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "updateScore:onCancelled", databaseError.toException());
                    }
                };
                databaseReference.addListenerForSingleValueEvent(extendsDatabaseListener);
                ValueEventListener extendsExerciseListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(userId)){
                            Exercises exercises = dataSnapshot.getValue(Exercises.class);
                            int existingAttempts = Integer.parseInt(exercises.attempts);
                            String totalAttempts = Integer.toString(existingAttempts + attempts);
                            exerciseReference.child("attempts").setValue(totalAttempts);

                            int existingCorrect = Integer.parseInt(exercises.correct);
                            String totalCorrect = Integer.toString(existingCorrect + correct);
                            updatedScore = Integer.parseInt(totalCorrect);
                            exerciseReference.child("correct").setValue(totalCorrect);

                            double workPercentage = (Double.parseDouble(totalCorrect)*100) / Double.parseDouble(totalAttempts);
                            String newPercentage = Double.toString(workPercentage);
                            exerciseReference.child("correctPercentage").setValue(newPercentage);

                            // set users last attempt
                            exerciseReference.child("lastAttempt").setValue(date);
                            exerciseReference.child("lastGrade").setValue(grade);
                            // check and update the high grade
                            if(exercises.highGrade.equals("B") && gradeNumber == 1){
                                exerciseReference.child("highGrade").setValue("A");
                            } else if(exercises.highGrade.equals("C") && gradeNumber < 3){
                                exerciseReference.child("highGrade").setValue(grade);
                            } else if(exercises.highGrade.equals("D") && gradeNumber < 4){
                                exerciseReference.child("highGrade").setValue(grade);
                            }
                        }
                        else{ // exercise does not exist in firebase for user
                            setUp(userId, attempts, correct, date, grade);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "updateExercise:onCancelled", databaseError.toException());
                    }
                };
                exerciseReference.addListenerForSingleValueEvent(extendsExerciseListener);
            }
            else {
                main.unlockAchievement("CgkIrqu_x-kEEAIQBw"); // dinosore
                if(extendsInput.getText().toString().equals("public class Vehicle extends Car") ||
                        extendsInput.getText().toString().equals("public class Vehicle extends Car {")){
                    controller.generateWrongDialog("Check your logic!" +
                            " Have you put your classes in the correct order?");
                    extendsInput.setText("");
                }
                else {
                    controller.generateWrongDialog("Not quite! Try again.");
                    extendsInput.setText("");
                }
                ValueEventListener wrongListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(userId)){ // exercise already exists in Firebase
                            Exercises exercises = dataSnapshot.getValue(Exercises.class);
                            int existingAttempts = Integer.parseInt(exercises.attempts);
                            String totalAttempts = Integer.toString(existingAttempts + attempts);
                            exerciseReference.child("attempts").setValue(totalAttempts);

                            int existingCorrect = Integer.parseInt(exercises.correct);
                            String totalCorrect = Integer.toString(existingCorrect + correct);
                            exerciseReference.child("correct").setValue(totalCorrect);

                            double workPercentage = (Double.parseDouble(totalAttempts)*100) / Double.parseDouble(totalCorrect);
                            String newPercentage = Double.toString(workPercentage);
                            exerciseReference.child("correctPercentage").setValue(newPercentage);

                            exerciseReference.child("lastAttempt").setValue(date);
                            exerciseReference.child("lastGrade").setValue("F");
                        }
                        else{ // exercise does not exist in firebase for user
                            setUp(userId, attempts, correct, date, "F");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "updateExercise:onCancelled", databaseError.toException());
                    }
                };
                exerciseReference.addListenerForSingleValueEvent(wrongListener);
            }
        }
    };

    private void setUp(String userId, int att, int corr, String date, String grade){
        String percentage = Double.toString((corr * 100)/att);
        String attempts = Integer.toString(att);
        String correct = Integer.toString(this.correct);

        // configures Firebase for Inheritance lesson
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("attempts").setValue(attempts);
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("correct").setValue(correct);
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("correctPercentage").setValue(percentage);
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("firstAttempt").setValue(date);
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("firstGrade").setValue(grade);
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("highGrade").setValue(grade);
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("lastAttempt").setValue(date);
        databaseReference.child("exercises").child("computing").child("java").child("relationships")
                .child("inheritance").child(userId).child("lastGrade").setValue(grade);
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicate to the activity and potentially other fragments
     * contained in that activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
