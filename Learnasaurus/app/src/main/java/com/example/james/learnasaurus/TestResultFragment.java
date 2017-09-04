package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestResultFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private String test;
    private String grade;
    private int score;

    private MainActivity main;

    private JavaFragment javaFragment;
    private FragmentManager fragmentManager;

    public TestResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TestResultFragment.
     */
    public static TestResultFragment newInstance() {
        TestResultFragment fragment = new TestResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity)getActivity();
        this.test = main.getTestCompleted();
        this.grade = main.getTestGrade();
        this.score = main.getTestScore();
        javaFragment = new JavaFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.test_result_fragment, container, false);

        TextView heading = (TextView)view.findViewById(R.id.testResultHeading);
        heading.setText("You have completed the " + test + " test");

        JavaTestQuestions questions = new JavaTestQuestions();

        TextView score = (TextView)view.findViewById(R.id.testScore);
        score.setText(this.score + "/" + questions.numberOfQuestions());
        TextView grade = (TextView)view.findViewById(R.id.testGrade);
        grade.setText(this.grade);

        if(this.grade.equals("A")){
            main.unlockAchievement("CgkIrqu_x-kEEAIQAw"); // top of the class
        }
        else if(this.score == 10){
            main.unlockAchievement("CgkIrqu_x-kEEAIQBA"); // perfect pupil
        }
        else if(this.score == 5){
            main.unlockAchievement("CgkIrqu_x-kEEAIQCQ"); // im half way there
        }

        Button continueBtn = (Button)view.findViewById(R.id.testResultContinueBtn);
        continueBtn.setText("Continue");
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, javaFragment)
                        .commit();
            }
        });
        return view;
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
