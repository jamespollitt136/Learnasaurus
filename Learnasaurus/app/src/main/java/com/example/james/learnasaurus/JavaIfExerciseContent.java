package com.example.james.learnasaurus;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaIfExerciseContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaIfExerciseContent#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("NewApi")
public class JavaIfExerciseContent extends Fragment implements View.OnClickListener, View.OnDragListener, View.OnTouchListener {

    private OnFragmentInteractionListener mListener;

    private TextView weightDec, allowDec, ifText, allowPlaceholder, ifWeightPlaceholder,
            ifAllowPlaceholder, ifMessagePlaceholder, allowMover, weightMover, messageMover, valueMover;
    private int correct;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userId;
    private MainActivity main;

    public JavaIfExerciseContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaIfExerciseContent.
     */
    public static JavaIfExerciseContent newInstance() {
        JavaIfExerciseContent fragment = new JavaIfExerciseContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = 0;
        userId = user.getUid();
        main = (MainActivity)getActivity();
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_ifexcontent_fragment, container, false);

        TextView exerciseOneView = (TextView)view.findViewById(R.id.javaIfEx1Text);
        exerciseOneView.setText(Html.fromHtml(getString(R.string.java_statements_ifelse_ex1)));

        // actual textviews
        weightDec = (TextView)view.findViewById(R.id.ifExWeightDec);
        allowDec = (TextView)view.findViewById(R.id.ifExAllowDec);
        ifText = (TextView)view.findViewById(R.id.javaIfExIfText);

        // position placeholders - targets
        allowPlaceholder = (TextView)view.findViewById(R.id.ifExVarValueInput);
        ifWeightPlaceholder = (TextView)view.findViewById(R.id.varOneInputArea);
        ifAllowPlaceholder = (TextView)view.findViewById(R.id.varTwoInputArea);
        ifMessagePlaceholder = (TextView)view.findViewById(R.id.ifExMessageOne);

        allowPlaceholder.setOnDragListener(this);
        ifWeightPlaceholder.setOnDragListener(this);
        ifAllowPlaceholder.setOnDragListener(this);
        ifMessagePlaceholder.setOnDragListener(this);

        // tabled textviews - movers
        weightMover = (TextView)view.findViewById(R.id.weightMover);
        allowMover = (TextView)view.findViewById(R.id.allowMover);
        messageMover = (TextView)view.findViewById(R.id.messageMover);
        valueMover = (TextView)view.findViewById(R.id.valueMover);

        weightMover.setOnTouchListener(this);
        allowMover.setOnTouchListener(this);
        messageMover.setOnTouchListener(this);
        valueMover.setOnTouchListener(this);

        Button ifCheckButton = (Button)view.findViewById(R.id.ifEx1Check);
        ifCheckButton.setOnClickListener(this);

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
        switch(v.getId()){
            case R.id.ifEx1Check:
                if(correct == 4){
                    DialogController controller = new DialogController(getActivity());
                    controller.generateCorrectDialog("You are going to be a pro(grammer) in no time!");
                    main.unlockAchievement("CgkIrqu_x-kEEAIQAg"); // learnasaurus lex
                    main.unlockAchievement("CgkIrqu_x-kEEAIQBg"); // dragon drop
                    main.incrementAchievements(20);
                    main.updateLeaderboard(20);
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("users").child(userId);
                    ValueEventListener ifDatabaseListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User profile = dataSnapshot.getValue(User.class);
                            int databaseCurrentScore = Integer.parseInt(profile.score);
                            int newScore = databaseCurrentScore + 20;
                            String newScoreString = Integer.toString(newScore);
                            databaseReference.child("score").setValue(newScoreString);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "updateScore:onCancelled", databaseError.toException());
                        }
                    };
                    databaseReference.addListenerForSingleValueEvent(ifDatabaseListener);
                }
                else{
                    Toast.makeText(getActivity(), "There is more to complete", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch(event.getAction()){
            case DragEvent.ACTION_DROP:
                View view = (View)event.getLocalState();
                TextView target = (TextView) v;
                TextView mover = (TextView) view;
                if(target.getTag() == mover.getTag()) {
                    view.setVisibility(View.INVISIBLE);
                    target.setText(mover.getText().toString());
                    target.setTypeface(Typeface.DEFAULT_BOLD);
                    int textColour = ContextCompat.getColor(getActivity(), R.color.textGreen);
                    target.setTextColor(textColour);
                    correct += 1;
                    target.setOnDragListener(null);
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