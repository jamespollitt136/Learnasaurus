package com.example.james.learnasaurus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaNamingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaNamingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class JavaNamingFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private OnFragmentInteractionListener mListener;

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private EditText classInput;
    private EditText variableInput;
    private EditText methodInput;

    private DialogController controller;

    private MediaPlayer personSound;

    private MainActivity main;
    private boolean isSpeaking;
    private String conventionsTts;
    private String camelTts;
    private String namingTts;
    private String exerciseTts;

    public JavaNamingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaNamingFragment.
     */
    public static JavaNamingFragment newInstance() {
        JavaNamingFragment fragment = new JavaNamingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new DialogController(getActivity());

        main = (MainActivity)getActivity();
        isSpeaking = false;
        conventionsTts = getResources().getString(R.string.java_naming_sec1_tts);
        camelTts = getResources().getString(R.string.java_naming_sec2_tts);
        namingTts = getResources().getString(R.string.java_naming_sec3_tts);
        exerciseTts = getResources().getString(R.string.java_naming_sec4_tts);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_naming_fragment, container, false);

        ImageView ttsImg = (ImageView)view.findViewById(R.id.namingTtsImage);
        ttsImg.setOnClickListener(this);

        TextView conventionsText = (TextView)view.findViewById(R.id.namingConvAText);
        conventionsText.setOnLongClickListener(this);

        TextView camelText = (TextView)view.findViewById(R.id.camelCaseText);
        camelText.setOnLongClickListener(this);
        ImageView camelImg = (ImageView)view.findViewById(R.id.camelImage);
        camelImg.setOnClickListener(this);
        TextView camelExamplesText = (TextView)view.findViewById(R.id.namingCamelFamousText);
        camelExamplesText.setOnLongClickListener(this);

        TextView namingExamplesText = (TextView)view.findViewById(R.id.namingExamplesText);
        namingExamplesText.setOnLongClickListener(this);

        // Exercise widgets
        TextView exerciseText = (TextView)view.findViewById(R.id.namingExerciseIntro);
        exerciseText.setOnLongClickListener(this);
        ImageView personImg = (ImageView)view.findViewById(R.id.namingPersonImage);
        personImg.setOnClickListener(this);
        personSound = MediaPlayer.create(getActivity(), R.raw.malehello);
        classInput = (EditText)view.findViewById(R.id.namingClassInput);
        variableInput = (EditText)view.findViewById(R.id.namingVariablesInput);
        methodInput = (EditText)view.findViewById(R.id.namingMethodsInput);
        Button checkBtn = (Button)view.findViewById(R.id.namingCheckButton);
        checkBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public boolean onLongClick(View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch (v.getId()){
            case R.id.namingConvAText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(conventionsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.camelCaseText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(camelTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.namingCamelFamousText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(camelTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.namingExamplesText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(namingTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.namingExerciseIntro:
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.namingTtsImage: // speaker (tts) image
                if(!isSpeaking){
                    isSpeaking = true;
                    // pass the string to be spoken to the activity
                    main.speak(conventionsTts);
                    main.speak(camelTts);
                    main.speak(namingTts);
                    main.speak(exerciseTts);
                }
                else {
                    isSpeaking = false;
                    main.stopSpeech();
                }
                break;
            case R.id.camelImage: // click on camel image
                //play a sound
                break;
            case R.id.namingPersonImage: // click on person image
                personSound.start(); // MediaPlayer sound
                break;
            case R.id.namingCheckButton: // check user answers
                String classAns = classInput.getText().toString();
                String varAns = variableInput.getText().toString();
                String methAns = methodInput.getText().toString();
                if(classAns.equals("JohnSmith") && varAns.equals("eyeColour") && methAns.equals("sayHello()")){
                    controller.generateCorrectDialog("You'll be an expert in no time!");
                    main.unlockAchievement("CgkIrqu_x-kEEAIQAg"); // learnasaurus lex
                    main.incrementAchievements(15);
                    main.updateLeaderboard(15);
                    ValueEventListener namingDatabaseListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int databaseCurrentScore;
                            User profile = dataSnapshot.getValue(User.class);
                            databaseCurrentScore = Integer.parseInt(profile.score);
                            int newScore = databaseCurrentScore + 15;
                            String newScoreString = Integer.toString(newScore);
                            databaseReference.child("score").setValue(newScoreString);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "updateScore:onCancelled", databaseError.toException());
                        }
                    };
                    databaseReference.addListenerForSingleValueEvent(namingDatabaseListener);
                    classInput.setText("");
                    variableInput.setText("");
                    methodInput.setText("");
                }
                else{ // wrong answer section
                    main.unlockAchievement("CgkIrqu_x-kEEAIQBw"); // dinosore
                    if(classAns.equals("") || varAns.equals("") || methAns.equals("")){
                        controller.generateWrongDialog("There are some missing fields");
                    }
                    else if(!classAns.equals("JohnSmith")){
                        classInput.setText("");
                        controller.generateWrongDialog("There is a problem with your class name");
                    }
                    else if (!varAns.equals("eyeColour")){
                        variableInput.setText("");
                        controller.generateWrongDialog("There is a problem with your variable name");
                    }
                    else if (!methAns.equals("sayHello()")){
                        if (methAns.equals("sayHello")){
                            controller.generateWrongDialog("Don't forget the () for a method with no parameters!");
                        }
                        else{
                            controller.generateWrongDialog("Check your method answer.");
                        }
                    }
                }
                break;
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
