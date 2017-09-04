package com.example.james.learnasaurus;

import android.animation.Animator;
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
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnClickListener{
    private OnFragmentInteractionListener mListener;
    private MainActivity main;
    private ComputingFragment computingFragment;
    private FragmentManager fragmentManager;

    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        main = new MainActivity();
        computingFragment = new ComputingFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_fragment, container, false);
        // widget binding and setting event handlers for image clicks
        ImageView compImage = (ImageView)view.findViewById(R.id.homeCompImage);
        ImageView englishImage = (ImageView)view.findViewById(R.id.homeEnglishImage);
        ImageView geographyImage = (ImageView)view.findViewById(R.id.homeGeographyImage);
        ImageView historyImage = (ImageView)view.findViewById(R.id.homeHistoryImage);
        ImageView mathsImage = (ImageView)view.findViewById(R.id.homeMathsImage);
        ImageView scienceImage = (ImageView)view.findViewById(R.id.homeScienceImage);

        compImage.setOnClickListener(this);
        englishImage.setOnClickListener(this);
        geographyImage.setOnClickListener(this);
        historyImage.setOnClickListener(this);
        mathsImage.setOnClickListener(this);
        scienceImage.setOnClickListener(this);

        return view;
    }

    // Implemented method. Handles the clicks on images on the UI.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeCompImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, computingFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.homeEnglishImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeGeographyImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeHistoryImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeMathsImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.homeScienceImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
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
