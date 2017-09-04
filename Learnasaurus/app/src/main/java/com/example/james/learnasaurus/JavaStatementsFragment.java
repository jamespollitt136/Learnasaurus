package com.example.james.learnasaurus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaStatementsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaStatementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaStatementsFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private FragmentManager fragmentManager;
    private JavaIfElseFragment ifElseFragment;

    public JavaStatementsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaStatementsFragment.
     */
    public static JavaStatementsFragment newInstance() {
        JavaStatementsFragment fragment = new JavaStatementsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ifElseFragment = new JavaIfElseFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_statements_fragment, container, false);

        getActivity().setTitle("Statements");

        ImageView ifelseImg = (ImageView)view.findViewById(R.id.ifElseImage);
        ImageView switchImg = (ImageView)view.findViewById(R.id.switchImage);

        ifelseImg.setOnClickListener(this);
        switchImg.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ifElseImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, ifElseFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.switchImage:
                //open switch fragment
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
