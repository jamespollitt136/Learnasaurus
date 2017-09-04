package com.example.james.learnasaurus;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DialogController {

    private Context context;

    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;

    public DialogController(Context context){
        this.context = context;
        correctSound = MediaPlayer.create(context, R.raw.correct);
        wrongSound = MediaPlayer.create(context, R.raw.wrong);
    }

    public void generateCorrectDialog(String message){
        final Dialog correctDialog = new Dialog(context);
        correctDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        correctDialog.setCancelable(false);
        correctDialog.setContentView(R.layout.correct_dialog);
        TextView dialogMsg = (TextView)correctDialog.findViewById(R.id.correctMessage);
        dialogMsg.setText(message);
        Button dialogButton = (Button)correctDialog.findViewById(R.id.correctButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctDialog.dismiss();
            }
        });
        correctDialog.show();
        correctSound.start();
    }

    public void generateWrongDialog(String message){
        final Dialog wrongDialog = new Dialog(context);
        wrongDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        wrongDialog.setCancelable(false);
        wrongDialog.setContentView(R.layout.wrong_dialog);
        TextView dialogMsg = (TextView)wrongDialog.findViewById(R.id.wrongMessage);
        dialogMsg.setText(message);
        Button dialogButton = (Button)wrongDialog.findViewById(R.id.wrongButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongDialog.dismiss();
            }
        });
        wrongDialog.show();
        wrongSound.start();
    }

    public void generateErrorDialog(String message){
        final Dialog errorDialog = new Dialog(context);
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errorDialog.setCancelable(false);
        errorDialog.setContentView(R.layout.error_dialog);
        TextView dialogMsg = (TextView)errorDialog.findViewById(R.id.errorMessage);
        dialogMsg.setText(message);
        Button dialogButton = (Button)errorDialog.findViewById(R.id.errorButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDialog.dismiss();
            }
        });
        errorDialog.show();
    }
}
