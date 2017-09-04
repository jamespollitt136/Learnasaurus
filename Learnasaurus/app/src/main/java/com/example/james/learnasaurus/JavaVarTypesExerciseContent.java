package com.example.james.learnasaurus;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
 * {@link JavaVarTypesExerciseContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaVarTypesExerciseContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaVarTypesExerciseContent extends Fragment implements View.OnClickListener, View.OnDragListener, View.OnTouchListener {
    private OnFragmentInteractionListener mListener;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userId;
    private int correct;
    private MainActivity main;

    public JavaVarTypesExerciseContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaVarTypesExerciseContent.
     */
    public static JavaVarTypesExerciseContent newInstance() {
        JavaVarTypesExerciseContent fragment = new JavaVarTypesExerciseContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = 0;
        userId = user.getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_varex1content_fragment, container, false);

        ImageView booleanType = (ImageView)view.findViewById(R.id.booleanTypeImage);
        ImageView booleanVal = (ImageView)view.findViewById(R.id.booleanValueImage);
        ImageView charType = (ImageView)view.findViewById(R.id.charTypeImage);
        ImageView charVal = (ImageView)view.findViewById(R.id.charValueImage);
        ImageView doubleType = (ImageView)view.findViewById(R.id.doubleTypeImage);
        ImageView doubleVal = (ImageView)view.findViewById(R.id.doubleValueImage);
        ImageView floatType = (ImageView)view.findViewById(R.id.floatTypeImage);
        ImageView floatVal = (ImageView)view.findViewById(R.id.floatValueImage);
        ImageView intType = (ImageView)view.findViewById(R.id.intTypeImage);
        ImageView intVal = (ImageView)view.findViewById(R.id.intValueImage);
        ImageView stringType = (ImageView)view.findViewById(R.id.stringTypeImage);
        ImageView stringVal = (ImageView)view.findViewById(R.id.stringValueImage);

        booleanType.setOnDragListener(this);
        charType.setOnDragListener(this);
        doubleType.setOnDragListener(this);
        floatType.setOnDragListener(this);
        intType.setOnDragListener(this);
        stringType.setOnDragListener(this);

        booleanVal.setOnTouchListener(this);
        charVal.setOnTouchListener(this);
        doubleVal.setOnTouchListener(this);
        floatVal.setOnTouchListener(this);
        intVal.setOnTouchListener(this);
        stringVal.setOnTouchListener(this);

        Button checkBtn = (Button)view.findViewById(R.id.varEx1Btn);
        checkBtn.setOnClickListener(this);

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
        main = (MainActivity)getActivity();
        switch(v.getId()){
            case R.id.varEx1Btn:
                if(correct == 6){
                    DialogController controller = new DialogController(getActivity());
                    controller.generateCorrectDialog("You're the (data)type to get them all right!");
                    main.unlockAchievement("CgkIrqu_x-kEEAIQAg"); // learnasaurus lex
                    main.unlockAchievement("CgkIrqu_x-kEEAIQBg"); // dragon drop
                    main.updateLeaderboard(50);
                    main.incrementAchievements(50);
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("users").child(userId);
                    ValueEventListener typesDatabaseListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User profile = dataSnapshot.getValue(User.class);
                            int databaseCurrentScore = Integer.parseInt(profile.score);
                            int newScore = databaseCurrentScore + 50;
                            String newScoreString = Integer.toString(newScore);
                            databaseReference.child("score").setValue(newScoreString);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "updateScore:onCancelled", databaseError.toException());
                        }
                    };
                    databaseReference.addListenerForSingleValueEvent(typesDatabaseListener);
                }
                else{
                    Toast.makeText(getActivity(), "There is more to complete", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch(event.getAction()){
            case DragEvent.ACTION_DROP:
                View view = (View)event.getLocalState();
                ImageView target = (ImageView) v;
                ImageView mover = (ImageView) view;
                if(target.getTag() == mover.getTag()) {
                    view.setVisibility(View.VISIBLE);
                    target.setColorFilter(Color.GRAY);
                    mover.setColorFilter(Color.GRAY);
                    correct += 1;
                    target.setOnDragListener(null);
                    mover.setOnDragListener(null);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }
        else {
            return false;
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
