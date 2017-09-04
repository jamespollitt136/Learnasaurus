package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaFragment extends Fragment implements OnClickListener {

    private OnFragmentInteractionListener mListener;
    private FragmentManager fragmentManager;
    private JavaBasicsFragment javaBasicsFragment;
    private JavaStatementsFragment javaStatementsFragment;
    private JavaRelationshipsFragment javaRelationshipsFragment;
    private JavaTestLandingFragment javaTestLandingFragment;

    public JavaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaFragment.
     */
    public static JavaFragment newInstance() {
        JavaFragment fragment = new JavaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        javaBasicsFragment = new JavaBasicsFragment();
        javaStatementsFragment = new JavaStatementsFragment();
        javaRelationshipsFragment = new JavaRelationshipsFragment();
        javaTestLandingFragment = new JavaTestLandingFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_fragment, container, false);

        getActivity().setTitle("Java");

        ImageView basicsImage = (ImageView)view.findViewById(R.id.javaBasicsImage);
        ImageView statementsImage = (ImageView)view.findViewById(R.id.javaStatementsImage);
        ImageView collectionsImage = (ImageView)view.findViewById(R.id.javaCollectionsImage);
        ImageView loopsImage = (ImageView)view.findViewById(R.id.javaLoopsImage);
        ImageView relationshipsImage = (ImageView)view.findViewById(R.id.javaRelationshipsImage);
        ImageView testImage = (ImageView)view.findViewById(R.id.javaTestImage);

        basicsImage.setOnClickListener(this);
        statementsImage.setOnClickListener(this);
        collectionsImage.setOnClickListener(this);
        loopsImage.setOnClickListener(this);
        relationshipsImage.setOnClickListener(this);
        testImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.javaBasicsImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, javaBasicsFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.javaStatementsImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, javaStatementsFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.javaCollectionsImage:
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.javaLoopsImage:
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.javaRelationshipsImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, javaRelationshipsFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.javaTestImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, javaTestLandingFragment)
                        .addToBackStack(null)
                        .commit();
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
