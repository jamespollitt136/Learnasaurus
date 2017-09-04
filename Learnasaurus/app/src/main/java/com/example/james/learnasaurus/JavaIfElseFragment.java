package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaIfElseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaIfElseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaIfElseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public JavaIfElseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaIfElseFragment.
     */
    public static JavaIfElseFragment newInstance() {
        JavaIfElseFragment fragment = new JavaIfElseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_ifelse_fragment, container, false);

        FragmentTabHost tabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        tabHost.addTab(tabHost.newTabSpec("if").setIndicator("IF"),
                JavaIfContent.class, null);
        tabHost.addTab(tabHost.newTabSpec("ifelse").setIndicator("IF THEN ELSE"),
                JavaElseContent.class, null);
        tabHost.addTab(tabHost.newTabSpec("ifexercise").setIndicator("EXERCISE"),
                JavaIfExerciseContent.class, null);

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
