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
 * {@link JavaVariablesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaVariablesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaVariablesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public JavaVariablesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaVariablesFragment.
     */
    public static JavaVariablesFragment newInstance() {
        JavaVariablesFragment fragment = new JavaVariablesFragment();
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
        View view = inflater.inflate(R.layout.java_variables_fragment, container, false);

        FragmentTabHost mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("varDataTypes").setIndicator("DATA TYPES"),
                JavaVarTypesContent.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("varTypesExercise").setIndicator("EXERCISE 1"),
                JavaVarTypesExerciseContent.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("varScope").setIndicator("SCOPE"),
                JavaVarScopeContent.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("varScopeExercise").setIndicator("EXERCISE 2"),
                JavaVarScopeExerciseContent.class, null);

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
