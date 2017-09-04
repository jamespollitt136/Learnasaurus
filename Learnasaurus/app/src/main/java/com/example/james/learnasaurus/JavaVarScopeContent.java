package com.example.james.learnasaurus;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaVarScopeContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaVarScopeContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaVarScopeContent extends Fragment implements View.OnLongClickListener{
    private boolean isSpeaking;
    private boolean answerShown;

    private OnFragmentInteractionListener mListener;

    private MainActivity main;
    private String firstTts;
    private String secTts;
    private String thirdTts;

    private TextView thisExpText;

    public JavaVarScopeContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaVarScopeContent.
     */
    public static JavaVarScopeContent newInstance() {
        JavaVarScopeContent fragment = new JavaVarScopeContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSpeaking = false;
        answerShown = false;
        main = (MainActivity)getActivity();
        firstTts = getResources().getString(R.string.java_var_scope_tts);
        secTts = getResources().getString(R.string.java_var_this1_tts);
        thirdTts = getResources().getString(R.string.java_var_this2_tts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_varscopecontent_fragment, container, false);

        ImageView scopeTts = (ImageView)view.findViewById(R.id.scopeTtsImage);
        scopeTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSpeaking){
                    isSpeaking = true;
                    main.speak(firstTts); // pass the string to be spoken to the activity
                    main.speak(secTts);
                    // give the user time to view the image before speaking the next section
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            speakThird();
                        }
                    }, 60000);
                }
                else {
                    isSpeaking = false;
                    main.stopSpeech();
                }
            }
        });

        TextView typesText = (TextView)view.findViewById(R.id.javaVarTypesAns);
        typesText.setText(Html.fromHtml(getString(R.string.java_var_typesa)));
        typesText.setOnLongClickListener(this);

        TextView thisText = (TextView)view.findViewById(R.id.javaVarThisText);
        thisText.setText(Html.fromHtml(getString(R.string.java_var_thistext)));
        thisText.setOnLongClickListener(this);

        thisExpText = (TextView)view.findViewById(R.id.javaVarThisExp);
        thisExpText.setOnLongClickListener(this);
        thisExpText.setOnClickListener(showAnswerListener);

        return view;
    }

    @Override
    public boolean onLongClick(final View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch(v.getId()){
            case R.id.javaVarTypesAns:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(firstTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarThisText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(secTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarThisExp:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(!answerShown){
                            showAnswerListener.onClick(v);
                        }
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(thirdTts);
                        return true;
                    }
                });
                popup.show();
                break;
        }
        return true;
    }

    // if the user clicks on the screen to display the text answer for this keyword section
    View.OnClickListener showAnswerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            thisExpText.setTextColor(getResources().getColor(R.color.black));
            thisExpText.setTextSize(16.0f);
            thisExpText.setText(Html.fromHtml(getString(R.string.java_var_this_expl)));
            if(isSpeaking){
                main.stopSpeech(); // stop the initial speech process
            }
            isSpeaking = false;
            answerShown = true;
        }
    };


    private void speakThird(){
        main.speak(thirdTts);
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
