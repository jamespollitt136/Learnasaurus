package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaObjectsContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaObjectsContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaObjectsContent extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private OnFragmentInteractionListener mListener;

    private boolean isSpeaking;
    private MainActivity main;
    private String introTts;
    private String deviceTts;
    private String behaviourTts;
    private String javaTts;

    public JavaObjectsContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaObjectsContent.
     */
    public static JavaObjectsContent newInstance() {
        JavaObjectsContent fragment = new JavaObjectsContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity)getActivity();
        isSpeaking = false;
        introTts = getResources().getString(R.string.java_objects_intro);
        deviceTts = getResources().getString(R.string.java_objects_techstate);
        behaviourTts = getResources().getString(R.string.java_objects_behaviours);
        javaTts = getResources().getString(R.string.java_objects_java_intro);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.java_objcontent_fragment, container, false);

        TextView deviceBrand = (TextView)view.findViewById(R.id.objBrandText);
        deviceBrand.setText("Brand: " + Build.BRAND);
        TextView deviceModel = (TextView)view.findViewById(R.id.objModelText);
        deviceModel.setText("Model: " + Build.MODEL);
        TextView deviceProduct = (TextView)view.findViewById(R.id.objProductText);
        deviceProduct.setText("Product: " + Build.PRODUCT);
        TextView deviceRelease = (TextView)view.findViewById(R.id.objReleaseText);
        deviceRelease.setText("Release: " + Build.VERSION.RELEASE);
        TextView deviceSerial = (TextView)view.findViewById(R.id.objSerialText);
        deviceSerial.setText("Serial: " + Build.SERIAL);

        ImageView ttsImg = (ImageView)view.findViewById(R.id.objTtsImage);
        ttsImg.setOnClickListener(this);
        ImageView phoneImg = (ImageView)view.findViewById(R.id.objPhoneImage);
        phoneImg.setOnClickListener(this);

        TextView objIntro = (TextView)view.findViewById(R.id.javaObjIntro);
        TextView objExample = (TextView)view.findViewById(R.id.javaObjExText);
        TextView objBehaviour = (TextView)view.findViewById(R.id.javaObjBehaviourText);
        TextView objJava = (TextView)view.findViewById(R.id.javaObjJavaText);

        objIntro.setOnLongClickListener(this);
        objExample.setOnLongClickListener(this);
        objBehaviour.setOnLongClickListener(this);
        objJava.setOnLongClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.objTtsImage:
                if(!isSpeaking){
                    isSpeaking = true;
                    main.speak(introTts); // pass the string to be spoken to the activity
                    main.speak(deviceTts);
                    main.speak(behaviourTts);
                    main.speak(javaTts);
                }
                else {
                    isSpeaking = false;
                    main.stopSpeech();
                }
                break;
            case R.id.objPhoneImage:
                //play sound
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch (v.getId()){
            case R.id.javaObjIntro:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(introTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaObjExText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(deviceTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaObjBehaviourText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(behaviourTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaObjJavaText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(javaTts);
                        return true;
                    }
                });
                popup.show();
                break;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
