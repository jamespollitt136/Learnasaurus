package com.example.james.learnasaurus;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
 * {@link JavaMethodsPlayground.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaMethodsPlayground#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaMethodsPlayground extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private TextView outputBox;

    private MediaPlayer helloSound;
    private MediaPlayer byeSound;
    private MediaPlayer wowSound;

    private MainActivity main;

    public JavaMethodsPlayground() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaMethodsPlayground.
     */
    public static JavaMethodsPlayground newInstance() {
        JavaMethodsPlayground fragment = new JavaMethodsPlayground();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_methodspg_fragment, container, false);

        outputBox = (TextView)view.findViewById(R.id.methPgOutputBox);

        Button helloBtn = (Button)view.findViewById(R.id.methPgHelloBtn);
        Button byeBtn = (Button)view.findViewById(R.id.methPgByeBtn);
        Button wowBtn = (Button)view.findViewById(R.id.methPgWowBtn);

        helloBtn.setOnClickListener(this);
        byeBtn.setOnClickListener(this);
        wowBtn.setOnClickListener(this);

        helloSound = MediaPlayer.create(getActivity(), R.raw.malehello);
        byeSound = MediaPlayer.create(getActivity(), R.raw.goodbye);
        wowSound = MediaPlayer.create(getActivity(), R.raw.wow);

        return view;
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
    public void onClick(View v) {
        main.unlockAchievement("CgkIrqu_x-kEEAIQCg"); // playing around
        main.incrementAchievements(10);
        main.updateLeaderboard(10);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());
        ValueEventListener methodsDatabaseListener = new ValueEventListener() {
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
        databaseReference.addListenerForSingleValueEvent(methodsDatabaseListener);
        switch(v.getId()){
            case R.id.methPgHelloBtn:
                helloSound.start(); // MediaPlayer sound
                outputBox.setText(Html.fromHtml(getString(R.string.java_methodspg_hello_output)));
                break;
            case R.id.methPgByeBtn:
                byeSound.start(); // MediaPlayer sound
                outputBox.setText(Html.fromHtml(getString(R.string.java_methodspg_bye_output)));
                break;
            case R.id.methPgWowBtn:
                wowSound.start(); // MediaPlayer sound
                outputBox.setText(Html.fromHtml(getString(R.string.java_methodspg_wow_output)));
                break;
        }
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
