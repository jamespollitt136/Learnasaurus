package com.example.james.learnasaurus;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SocialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private ImageView twitterIcon;
    private TextView twitterPage;

    private TwitterFragment twitterFragment;
    private FragmentManager fragmentManager;

    public SocialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SocialFragment.
     */
    public static SocialFragment newInstance() {
        SocialFragment fragment = new SocialFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterFragment = new TwitterFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.social_fragment, container, false);

        twitterIcon = (ImageView)view.findViewById(R.id.socialTwitterLogo);
        twitterPage = (TextView)view.findViewById(R.id.socialTwitterText);

        twitterIcon.setOnClickListener(twitterListener);
        twitterPage.setOnClickListener(twitterListener);

        return view;
    }

    OnClickListener twitterListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, twitterFragment)
                    .addToBackStack(null)
                    .commit();
            }
    };

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
