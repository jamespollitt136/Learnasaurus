package com.example.james.learnasaurus;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaIfContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaIfContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaIfContent extends Fragment implements View.OnLongClickListener, View.OnClickListener {

    private MediaPlayer robotSound;
    private MediaPlayer birthdaySound;

    private OnFragmentInteractionListener mListener;
    private boolean isSpeaking;
    private MainActivity main;
    private String introTts;
    private String realWorldTts;
    private String methodsTts;
    private String containsTts;
    private String equalsTts;
    private String robotTts;

    public JavaIfContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaIfContent.
     */
    public static JavaIfContent newInstance() {
        JavaIfContent fragment = new JavaIfContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity)getActivity();
        isSpeaking = false;
        introTts = getResources().getString(R.string.java_if_intro_tts);
        realWorldTts = getResources().getString(R.string.java_if_realworld_tts);
        methodsTts = getResources().getString(R.string.java_if_methods_text);
        containsTts = getResources().getString(R.string.java_if_contains_tts);
        equalsTts = getResources().getString(R.string.java_if_equals_tts);
        robotTts = getResources().getString(R.string.java_if_robot_text);

        birthdaySound = MediaPlayer.create(main, R.raw.birthday);
        robotSound = MediaPlayer.create(main, R.raw.robot);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_ifcontent_fragment, container, false);

        ImageView ifTtsImg = (ImageView)view.findViewById(R.id.ifTtsImage);
        ifTtsImg.setOnClickListener(this);

        TextView introText = (TextView)view.findViewById(R.id.ifIntroText);
        introText.setOnLongClickListener(this);

        TextView realWorldText = (TextView)view.findViewById(R.id.ifRealText);
        realWorldText.setText(Html.fromHtml(getString(R.string.java_if_realworld_text)));
        realWorldText.setOnLongClickListener(this);

        TextView methodsText = (TextView)view.findViewById(R.id.ifMethodsText);
        methodsText.setOnLongClickListener(this);

        TextView containsText = (TextView)view.findViewById(R.id.ifContainsText);
        containsText.setText(Html.fromHtml(getString(R.string.java_if_contains_text)));
        containsText.setOnClickListener(this);

        TextView equalsText = (TextView)view.findViewById(R.id.ifEqualsText);
        equalsText.setText(Html.fromHtml(getString(R.string.java_if_equals_text)));
        equalsText.setOnLongClickListener(this);

        ImageView birthdayImg = (ImageView)view.findViewById(R.id.ifBirthdayImage);
        birthdayImg.setOnClickListener(this);

        TextView robotText = (TextView)view.findViewById(R.id.ifRobotText);
        robotText.setOnLongClickListener(this);
        ImageView robotImg = (ImageView)view.findViewById(R.id.ifRobotImage);
        robotImg.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ifTtsImage:
                if(!isSpeaking){
                    isSpeaking = true;
                    main.speak(introTts); // pass the string to be spoken to the activity
                    main.speak(realWorldTts);
                    main.speak(methodsTts);
                    main.speak(containsTts);
                    main.speak(equalsTts);
                    main.speak(robotTts);
                }
                else {
                    isSpeaking = false;
                    main.stopSpeech();
                }
                break;
            case R.id.ifBirthdayImage:
                birthdaySound.start();
                break;
            case R.id.ifRobotImage:
                robotSound.start();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch(v.getId()){
            case R.id.ifIntroText:
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
            case R.id.ifRealText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(realWorldTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.ifMethodsText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(methodsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.ifContainsText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(containsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.ifEqualsText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(equalsTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.ifRobotText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(robotTts);
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
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
