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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaBasicsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaBasicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaBasicsFragment extends Fragment implements OnClickListener{

    private OnFragmentInteractionListener mListener;
    private FragmentManager fragmentManager;
    private ComputingOopFragment oopFragment;
    private JavaClassesFragment classesFragment;
    private JavaMethodsFragment methodsFragment;
    private JavaNamingFragment namingFragment;
    private JavaObjectsFragment objectsFragment;
    private JavaVariablesFragment variablesFragment;

    public JavaBasicsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaBasicsFragment.
     */
    public static JavaBasicsFragment newInstance() {
        JavaBasicsFragment fragment = new JavaBasicsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oopFragment = new ComputingOopFragment();
        classesFragment = new JavaClassesFragment();
        methodsFragment = new JavaMethodsFragment();
        namingFragment = new JavaNamingFragment();
        objectsFragment = new JavaObjectsFragment();
        variablesFragment = new JavaVariablesFragment();
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_basics_fragment, container, false);

        getActivity().setTitle("Basics");

        ImageView oopImage = (ImageView)view.findViewById(R.id.javaOopImage);
        ImageView objectsImage = (ImageView)view.findViewById(R.id.javaObjectsImage);
        ImageView classesImage = (ImageView)view.findViewById(R.id.javaClassesImage);
        ImageView namingImage = (ImageView)view.findViewById(R.id.javaNamingImage);
        ImageView variablesImage = (ImageView)view.findViewById(R.id.javaVariablesImage);
        ImageView methodsImage = (ImageView)view.findViewById(R.id.javaMethodsImage);

        oopImage.setOnClickListener(this);
        objectsImage.setOnClickListener(this);
        classesImage.setOnClickListener(this);
        namingImage.setOnClickListener(this);
        variablesImage.setOnClickListener(this);
        methodsImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.javaOopImage:
                replaceFragment(oopFragment);
                break;
            case R.id.javaObjectsImage:
                replaceFragment(objectsFragment);
                break;
            case R.id.javaClassesImage:
                replaceFragment(classesFragment);
                break;
            case R.id.javaNamingImage:
                replaceFragment(namingFragment);
                break;
            case R.id.javaVariablesImage:
                replaceFragment(variablesFragment);
                break;
            case R.id.javaMethodsImage:
                replaceFragment(methodsFragment);
                break;
        }
    }

    public void replaceFragment(Fragment fragment){
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .addToBackStack(null)
                .commit();
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
