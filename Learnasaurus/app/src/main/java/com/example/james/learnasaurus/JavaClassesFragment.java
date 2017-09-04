package com.example.james.learnasaurus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.VideoView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JavaClassesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JavaClassesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavaClassesFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    private OnFragmentInteractionListener mListener;
    private boolean isSpeaking;
    private MainActivity main;
    private String introTts;

    public JavaClassesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JavaClassesFragment.
     */
    public static JavaClassesFragment newInstance() {
        JavaClassesFragment fragment = new JavaClassesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity)getActivity();
        isSpeaking = false;
        introTts = getResources().getString(R.string.java_classes_intro);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.java_classes_fragment, container, false);

        ImageView classTtsImg = (ImageView)view.findViewById(R.id.classTtsImage);
        classTtsImg.setOnClickListener(this);

        TextView introText = (TextView)view.findViewById(R.id.javaClassIntro);
        introText.setOnLongClickListener(this);

        final VideoView video = (VideoView)view.findViewById(R.id.javaClassVideo);
        // Video belongs to WebDevMentors from their Youtube channel: https://www.youtube.com/channel/UCMqC6THcgjvDMbdxa7TaZ7w
        // video link: https://www.youtube.com/watch?v=pa3X5fTnBGA
        Uri videoPath = Uri.parse("android.resource://com.example.james.learnasaurus/raw/classvid");
        video.setVideoURI(videoPath);
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.classTtsImage:
                if(!isSpeaking){
                    isSpeaking = true;
                    main.speak(introTts); // pass the string to be spoken to the activity
                    // other tts
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
            case R.id.javaClassIntro:
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
            // other menus
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
