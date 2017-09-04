package com.example.james.learnasaurus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
 * {@link JavaObjectsPlayground.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaObjectsPlayground#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaObjectsPlayground extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private TextView colourText;
    private TextView posText;
    private TextView sizeText;
    private ImageView circle;
    private int size;
    private String colour;
    private String position;

    private MainActivity main;

    public JavaObjectsPlayground() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaObjectsPlayground.
     */
    public static JavaObjectsPlayground newInstance() {
        JavaObjectsPlayground fragment = new JavaObjectsPlayground();
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
        View view = inflater.inflate(R.layout.java_objpg_fragment, container, false);

        circle = (ImageView)view.findViewById(R.id.objPgCircle);
        colour = "black";
        size = 150;
        position = "middle";
        colourText = (TextView)view.findViewById(R.id.circColourText);
        colourText.setText("colour: " + colour);
        posText = (TextView)view.findViewById(R.id.circPosText);
        posText.setText("position: " + position);
        sizeText = (TextView)view.findViewById(R.id.circSizeText);
        sizeText.setText("size: " + size);

        Button blackBtn = (Button)view.findViewById(R.id.objPgBlkBtn);
        Button blueBtn = (Button)view.findViewById(R.id.objPgBluBtn);
        Button orangeBtn = (Button)view.findViewById(R.id.objPgOraBtn);

        Button smallBtn = (Button)view.findViewById(R.id.objPgCircSmall);
        Button medBtn = (Button)view.findViewById(R.id.objPgCircMed);
        Button bigBtn = (Button)view.findViewById(R.id.objPgCircBig);

        Button leftBtn = (Button)view.findViewById(R.id.objPgCircLeft);
        Button rightBtn = (Button)view.findViewById(R.id.objPgCircRight);

        blackBtn.setOnClickListener(this);
        blueBtn.setOnClickListener(this);
        orangeBtn.setOnClickListener(this);
        smallBtn.setOnClickListener(this);
        medBtn.setOnClickListener(this);
        bigBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // get user display size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        //int height = screenSize.y;
        int width = screenSize.x;

        main.unlockAchievement("CgkIrqu_x-kEEAIQCg"); // playing around
        main.incrementAchievements(10);
        main.updateLeaderboard(10);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());
        ValueEventListener objectsDatabaseListener = new ValueEventListener() {
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
        databaseReference.addListenerForSingleValueEvent(objectsDatabaseListener);
        ViewGroup.LayoutParams layoutParams = circle.getLayoutParams();
        switch(v.getId()){
            case R.id.objPgBlkBtn:
                circle.setImageResource(R.drawable.circle_blk);
                colour = "black";
                colourText.setText("colour: " + colour);
                break;
            case R.id.objPgBluBtn:
                circle.setImageResource(R.drawable.circle_blu);
                colour = "blue";
                colourText.setText("colour: " + colour);
                break;
            case R.id.objPgOraBtn:
                circle.setImageResource(R.drawable.circle_ora);
                colour = "orange";
                colourText.setText("colour: " + colour);
                break;
            case R.id.objPgCircSmall:
                layoutParams.width = 50;
                layoutParams.height = 50;
                circle.setLayoutParams(layoutParams);
                size = 50;
                sizeText.setText("size: " + size);
                break;
            case R.id.objPgCircMed:
                layoutParams.width = 500;
                layoutParams.height = 500;
                circle.setLayoutParams(layoutParams);
                size = 500;
                sizeText.setText("size: " + size);
                break;
            case R.id.objPgCircBig:
                layoutParams.width = 1000;
                layoutParams.height = 1000;
                circle.setLayoutParams(layoutParams);
                size = 1000;
                sizeText.setText("size: " + size);
                break;
            case R.id.objPgCircLeft:
                circle.setX(0);
                position = "left";
                posText.setText("position: " + position);
                break;
            case R.id.objPgCircRight:
                circle.setX(width - 700); //set the image to the far right of device display
                position = "right";
                posText.setText("position: " + position);
                break;
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
