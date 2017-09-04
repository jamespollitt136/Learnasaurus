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
 * {@link ComputingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComputingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComputingFragment extends Fragment implements OnClickListener{
    private OnFragmentInteractionListener mListener;

    private MainActivity main;

    private JavaFragment javaFragment;
    private FragmentManager fragmentManager;

    public ComputingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ComputingFragment.
     */
    public static ComputingFragment newInstance() {
        ComputingFragment fragment = new ComputingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        main = new MainActivity();
        javaFragment = new JavaFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.computing_fragment, container, false);
        setRetainInstance(true);
        getActivity().setTitle("Computing");

        ImageView csharpImage = (ImageView)view.findViewById(R.id.compCsharpImage);
        ImageView cssImage = (ImageView)view.findViewById(R.id.compCssImage);
        ImageView htmlImage = (ImageView)view.findViewById(R.id.compHtmlImage);
        ImageView javaImage = (ImageView)view.findViewById(R.id.compJavaImage);
        ImageView javascriptImage = (ImageView)view.findViewById(R.id.compJavascriptImage);
        ImageView phpImage = (ImageView)view.findViewById(R.id.compPhpImage);
        ImageView rubyImage = (ImageView)view.findViewById(R.id.compRubyImage);
        ImageView swiftImage = (ImageView)view.findViewById(R.id.compSwiftImage);

        csharpImage.setOnClickListener(this);
        cssImage.setOnClickListener(this);
        htmlImage.setOnClickListener(this);
        javaImage.setOnClickListener(this);
        javascriptImage.setOnClickListener(this);
        phpImage.setOnClickListener(this);
        rubyImage.setOnClickListener(this);
        swiftImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.compCsharpImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.compCssImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.compHtmlImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.compJavaImage:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, javaFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.compJavascriptImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.compPhpImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.compRubyImage:
                Toast.makeText(getActivity(), "Subject coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.compSwiftImage:
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
