package com.example.james.learnasaurus;

import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaVarTypesContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaVarTypesContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaVarTypesContent extends Fragment implements View.OnClickListener, View.OnLongClickListener{
    private OnFragmentInteractionListener mListener;

    private boolean isSpeaking;
    MainActivity main;
    private String whatTts;
    private String dataTypesTts;
    private String primTypesTts;
    private String refTypesTts;
    private String characterTts;
    private String logicTts;
    private String numbersTts;

    public JavaVarTypesContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaVarTypesContent.
     */
    public static JavaVarTypesContent newInstance() {
        JavaVarTypesContent fragment = new JavaVarTypesContent();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSpeaking = false;
        main = (MainActivity)getActivity();
        whatTts = getResources().getString(R.string.java_var_what_tts);
        dataTypesTts = getResources().getString(R.string.java_var_datatypes_tts);
        primTypesTts = getResources().getString(R.string.java_var_primtypes_tts);
        refTypesTts = getResources().getString(R.string.java_var_reftypes_tts);
        characterTts = getResources().getString(R.string.java_var_charac_tts);
        logicTts = getResources().getString(R.string.java_var_logic_tts);
        numbersTts = getResources().getString(R.string.java_var_numeric_tts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.java_vartypescontent_fragment, container, false);

        ImageView typesTts = (ImageView)view.findViewById(R.id.typesTtsImage);
        typesTts.setOnClickListener(this);

        TextView introText = (TextView)view.findViewById(R.id.javaVarWhatAns);
        introText.setText(Html.fromHtml(getString(R.string.java_var_whata)));
        introText.setOnLongClickListener(this);

        TextView dataTypesText = (TextView)view.findViewById(R.id.javaVarDataTypesAns);
        dataTypesText.setText(Html.fromHtml(getString(R.string.java_var_primdata)));
        dataTypesText.setOnLongClickListener(this);

        TextView primTypesText = (TextView)view.findViewById(R.id.javaVarPrimText);
        primTypesText.setOnLongClickListener(this);

        TextView refTypesText = (TextView) view.findViewById(R.id.javaVarRefText);
        refTypesText.setText(Html.fromHtml(getString(R.string.java_var_refdata)));
        refTypesText.setOnLongClickListener(this);

        TextView charTypesText = (TextView)view.findViewById(R.id.javaVarCharTypesText);
        charTypesText.setText(Html.fromHtml(getString(R.string.java_var_chartypes)));
        charTypesText.setOnLongClickListener(this);

        TextView logicTypesText = (TextView)view.findViewById(R.id.javaVarLogTypesText);
        logicTypesText.setText(Html.fromHtml(getString(R.string.java_var_logtypes)));
        logicTypesText.setOnLongClickListener(this);

        TextView numTypesText = (TextView)view.findViewById(R.id.javaVarNumTypesText);
        numTypesText.setText(Html.fromHtml(getString(R.string.java_var_numtypes)));
        numTypesText.setOnLongClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.typesTtsImage:
                if(!isSpeaking){
                    isSpeaking = true;
                    main.speak(whatTts); // pass the string to be spoken to the activity
                    main.speak(dataTypesTts);
                    main.speak(primTypesTts);
                    main.speak(refTypesTts);
                    main.speak(characterTts);
                    main.speak(logicTts);
                    main.speak(numbersTts);
                }
                else {
                    isSpeaking = false;
                    main.stopSpeech();
                }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch(v.getId()){
            case R.id.javaVarWhatAns:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(whatTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarDataTypesAns:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(dataTypesTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarPrimText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(primTypesTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarRefText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(refTypesTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarCharTypesText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(characterTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarLogTypesText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(logicTts);
                        return true;
                    }
                });
                popup.show();
                break;
            case R.id.javaVarNumTypesText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(numbersTts);
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
