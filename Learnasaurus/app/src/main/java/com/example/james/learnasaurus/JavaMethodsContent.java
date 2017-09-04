package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaMethodsContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaMethodsContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaMethodsContent extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private OnFragmentInteractionListener mListener;

    public JavaMethodsContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaMethodsContent.
     */
    public static JavaMethodsContent newInstance() {
        JavaMethodsContent fragment = new JavaMethodsContent();
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
        View view = inflater.inflate(R.layout.java_methodscontent_fragment, container, false);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){

        }
        return true;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
