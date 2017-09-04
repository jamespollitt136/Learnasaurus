package com.example.james.learnasaurus;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * {@link JavaVarScopeExerciseContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaVarScopeExerciseContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaVarScopeExerciseContent extends Fragment {
    private OnFragmentInteractionListener mListener;

    private DialogController controller;

    private RadioButton classAns;
    private RadioButton localAns;
    private boolean answerCorrect = false;

    public JavaVarScopeExerciseContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaVarScopeExerciseContent.
     */
    public static JavaVarScopeExerciseContent newInstance() {
        JavaVarScopeExerciseContent fragment = new JavaVarScopeExerciseContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new DialogController(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.java_varex2content_fragment, container, false);

        TextView codeView = (TextView) view.findViewById(R.id.varEx2Code);
        codeView.setText(Html.fromHtml(getString(R.string.java_varex2_code)));

        RadioGroup rGroup = (RadioGroup)view.findViewById(R.id.varEx2RadioGroup);
        classAns = (RadioButton)view.findViewById(R.id.varExClassRadio);
        localAns = (RadioButton)view.findViewById(R.id.varExLocalRadio);

        rGroup.setOnCheckedChangeListener(checkedListener);

        return view;
    }

    RadioGroup.OnCheckedChangeListener checkedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            MainActivity main = (MainActivity)getActivity();
            if (classAns.isChecked()){
                controller.generateWrongDialog("Review the section on the keyword 'this'.");
                main.unlockAchievement("CgkIrqu_x-kEEAIQBw"); // dinosore
                classAns.setChecked(false);
            }
            else if(localAns.isChecked()){
                controller.generateCorrectDialog("\"Help me Obi-Wan Kenobi you're my only scope!\"\n\n"
                        + " Congratulations!");
                answerCorrect = true;
                localAns.setChecked(false);
                main.updateLeaderboard(10);
                main.unlockAchievement("CgkIrqu_x-kEEAIQAg"); // learnasaurus lex
                main.incrementAchievements(10);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(user.getUid());
                ValueEventListener scopeDatabaseListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int databaseCurrentScore;
                        User profile = dataSnapshot.getValue(User.class);
                        databaseCurrentScore = Integer.parseInt(profile.score);
                        int newScore = databaseCurrentScore + 10;
                        String newScoreString = Integer.toString(newScore);
                        databaseReference.child("score").setValue(newScoreString);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "updateScore:onCancelled", databaseError.toException());
                    }
                };
                databaseReference.addListenerForSingleValueEvent(scopeDatabaseListener);
            }
        }
    };

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
