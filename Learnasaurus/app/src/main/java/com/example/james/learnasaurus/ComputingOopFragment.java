package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComputingOopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComputingOopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComputingOopFragment extends Fragment implements OnClickListener, OnLongClickListener {
    private OnFragmentInteractionListener mListener;

    private boolean isSpeaking;

    private MainActivity main;
    private String firstToSpeak;
    private String secToSpeak;
    private String thirdToSpeak;
    private String fourthToSpeak;

    public ComputingOopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ComputingOopFragment.
     */
    public static ComputingOopFragment newInstance() {
        ComputingOopFragment fragment = new ComputingOopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSpeaking = false;
        main = (MainActivity)getActivity();
        firstToSpeak = getResources().getString(R.string.java_basics_oop_intro);
        secToSpeak = getResources().getString(R.string.java_basics_oop_adv_tts);
        thirdToSpeak = getResources().getString(R.string.java_oop_disadv_tts);
        fourthToSpeak = getResources().getString(R.string.java_oop_langs_tts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.computing_oop_fragment, container, false);

        ImageView oopTts = (ImageView)view.findViewById(R.id.oopTtsImage);
        oopTts.setOnClickListener(this);

        TextView paragraph1 = (TextView)view.findViewById(R.id.compOopWhatExpText);
        TextView paragraph2 = (TextView)view.findViewById(R.id.compOopAdvAText);
        TextView paragraph3 = (TextView)view.findViewById(R.id.compOopDisAText);
        TableLayout paragraph4 = (TableLayout)view.findViewById(R.id.oopUsesTable);
        paragraph1.setOnLongClickListener(this);
        paragraph2.setOnLongClickListener(this);
        paragraph3.setOnLongClickListener(this);
        paragraph4.setOnLongClickListener(this);

        return view;
    }

    /*
    * onLongClick is implemented from OnLongClickListener. This method controls the popup menu's seen
    * on long click's on a TextView. The switch statement checks which TextView has been told to speak
    * and passes the relevant argument to a method for TextToSpeecht to start.
    * */
    @Override
    public boolean onLongClick(View v) {
        Context contextWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuStyle);
        PopupMenu popup = new PopupMenu(contextWrapper, v);
        popup.getMenu().add("Speak");
        switch(v.getId()){
            case R.id.compOopWhatExpText:
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
            case R.id.compOopAdvAText:
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
            case R.id.compOopDisAText:
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
            case R.id.compOopUsesText:
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        isSpeaking = false;
                        main.stopSpeech();
                        main.speak(fourthToSpeak);
                        return true;
                    }
                });
                popup.show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.oopTtsImage: // speaker (tts) image
                if(isSpeaking){ // if currently speaking turn off
                    isSpeaking = false;
                    main.stopSpeech();
                }
                else { // if not currently speaking
                    isSpeaking = true;
                    main.speak(firstToSpeak);
                    main.speak(secToSpeak);
                    main.speak(thirdToSpeak);
                    main.speak(fourthToSpeak);
                }
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
