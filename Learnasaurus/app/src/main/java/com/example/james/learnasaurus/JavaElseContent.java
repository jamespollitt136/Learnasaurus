package com.example.james.learnasaurus;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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
 * {@link JavaElseContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaElseContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaElseContent extends Fragment implements View.OnLongClickListener {
    private OnFragmentInteractionListener mListener;

    private ImageView elseTts;
    private boolean isSpeaking;
    private MainActivity main;
    private String firstToSpeak;
    private String secToSpeak;
    private String thirdToSpeak;

    public JavaElseContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaElseContent.
     */
    public static JavaElseContent newInstance() {
        JavaElseContent fragment = new JavaElseContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSpeaking = false;
        main = (MainActivity)getActivity();
        firstToSpeak = getResources().getString(R.string.java_statements_else_p1);
        secToSpeak = getResources().getString(R.string.java_statements_else_code_tts);
        thirdToSpeak = getResources().getString(R.string.java_statements_else_p2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_elsecontent_fragment, container, false);

        elseTts = (ImageView) view.findViewById(R.id.elseTtsImage);
        elseTts.setOnClickListener(ttsListener);

        TextView elseIntroView = (TextView)view.findViewById(R.id.javaElseIntro);
        elseIntroView.setOnLongClickListener(this);

        TextView elseCodeView = (TextView) view.findViewById(R.id.javaElseCode);
        elseCodeView.setText(Html.fromHtml(getString(R.string.java_statements_else_code)));
        elseCodeView.setOnLongClickListener(this);

        TextView elseNextView = (TextView)view.findViewById(R.id.javaElseP2);
        elseNextView.setOnLongClickListener(this);

        return view;
    }

    @Override
    public boolean onLongClick(View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch(v.getId()){
            case R.id.javaElseIntro:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(firstToSpeak);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaElseCode:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(secToSpeak);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaElseP2:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(thirdToSpeak);
                        return true;
                    }
                });
                popup.show();
                break;
        }
      return true;
    }

    View.OnClickListener ttsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity main = (MainActivity)getActivity();
            if(!isSpeaking){
                isSpeaking = true;
                main.speak(firstToSpeak); // pass the string to be spoken to the activity
                main.speak(secToSpeak);
                main.speak(thirdToSpeak);
            }
            else {
                isSpeaking = false;
                main.stopSpeech();
            }
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
