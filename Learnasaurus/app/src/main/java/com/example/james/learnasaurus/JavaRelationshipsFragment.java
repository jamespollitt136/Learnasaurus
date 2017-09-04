package com.example.james.learnasaurus;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaRelationshipsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaRelationshipsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaRelationshipsFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private FragmentManager fragmentManager;
    private JavaInheritanceFragment inheritanceFragment;
    //private JavaRealisationFragment realisationFragment;

    public JavaRelationshipsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaRelationshipsFragment.
     */

    public static JavaRelationshipsFragment newInstance() {
        JavaRelationshipsFragment fragment = new JavaRelationshipsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inheritanceFragment = new JavaInheritanceFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_relationships_fragment, container, false);

        getActivity().setTitle("Relationships");

        ImageView inheritanceImg = (ImageView)view.findViewById(R.id.relInheritanceImage);
        ImageView realisationImg = (ImageView)view.findViewById(R.id.relRealisationImage);

        inheritanceImg.setOnClickListener(this);
        realisationImg.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relInheritanceImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, inheritanceFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.relRealisationImage:
                Toast.makeText(getActivity(), "Lesson not available", Toast.LENGTH_SHORT).show();
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
