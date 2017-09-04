package com.example.james.learnasaurus;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseRefDeletion;
    private ValueEventListener mPostListener;
    private String userId;

    private boolean reAuthed;

    private View view;

    private MainActivity main;
    private DialogController dialogController;

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private ImageView profImage;
    private TextView profName;
    private TextView profEmail;
    private TextView profScore;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reAuthed = true;
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(user.getUid());
        databaseRefDeletion = FirebaseDatabase.getInstance().getReference();
        dialogController = new DialogController(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        main = new MainActivity();
        getActivity().setTitle("Profile");

        profImage = (ImageView)view.findViewById(R.id.profileImage);
        profImage.setOnClickListener(this);
        profName = (TextView)view.findViewById(R.id.profileName);
        profName.setText(user.getDisplayName());
        profEmail = (TextView)view.findViewById(R.id.profileEmail);
        profEmail.setText(user.getEmail());
        profScore = (TextView)view.findViewById(R.id.profileScore);

        Button changeName = (Button)view.findViewById(R.id.profileNameChange);
        Button changeEmail = (Button)view.findViewById(R.id.profileEmailChange);
        Button changePass = (Button)view.findViewById(R.id.profilePasswordChange);
        Button sync = (Button)view.findViewById(R.id.profileSync);
        Button delete = (Button)view.findViewById(R.id.profileDelete);

        changeName.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
        changePass.setOnClickListener(this);
        sync.setOnClickListener(this);
        delete.setOnClickListener(this);

        ValueEventListener profileListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User profile = dataSnapshot.getValue(User.class);
                TextView profScore = (TextView)view.findViewById(R.id.profileScore);
                profScore.setText(profile.score + " xp");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadProfile:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addListenerForSingleValueEvent(profileListener);
        mPostListener = profileListener;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPostListener != null) {
            databaseReference.removeEventListener(mPostListener);
        }
    }

    @Override
    public void onClick(View v) {
        final MainActivity main = new MainActivity();
        userId = user.getUid();
        Context context = getContext();
        if(reAuthed){
            switch (v.getId()){
                case R.id.profileImage:
                    Toast.makeText(getActivity(), "Profile picture selection coming soon", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.profileNameChange:
                    AlertDialog.Builder changeNameDialog = new AlertDialog.Builder(context);
                    changeNameDialog.setTitle("Change Name");
                    LinearLayout nameDialogLayout = new LinearLayout(context);
                    nameDialogLayout.setOrientation(LinearLayout.VERTICAL);
                    final EditText newFirstInput = new EditText(context);
                    newFirstInput.setHint("First Name");
                    nameDialogLayout.addView(newFirstInput);
                    final EditText newLastInput = new EditText(context);
                    newLastInput.setHint("Last Name");
                    nameDialogLayout.addView(newLastInput);
                    changeNameDialog.setView(nameDialogLayout);
                    changeNameDialog.setPositiveButton("Change Name", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String first = newFirstInput.getText().toString().trim();
                            String last = newLastInput.getText().toString().trim();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(first + " " + last).build();
                            user.updateProfile(profile);
                            databaseReference.child("name").setValue(first);
                            databaseReference.child("surname").setValue(last);
                            profName.setText(first + " " + last);
                            MainActivity mainact = (MainActivity)getActivity();
                            mainact.updateDrawer(first + " " + last, user.getEmail());
                        }
                    });
                    changeNameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    changeNameDialog.show();
                    break;
                case R.id.profileEmailChange:
                    AlertDialog.Builder changeEmailDialog = new AlertDialog.Builder(context);
                    changeEmailDialog.setTitle("Change Email");
                    changeEmailDialog.setMessage("Input your new email address:");
                    final EditText newEmailInput = new EditText(context);
                    newEmailInput.setHint("New Email Address");
                    changeEmailDialog.setView(newEmailInput);
                    changeEmailDialog.setPositiveButton("Change Email Address", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String emailInput = newEmailInput.getText().toString().trim();
                            user.updateEmail(emailInput)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                                databaseReference.child("email").setValue(emailInput);
                                                profEmail.setText(emailInput);
                                                MainActivity mainact = (MainActivity)getActivity();
                                                mainact.updateDrawer(user.getDisplayName(), emailInput);
                                            }
                                        }
                                    });
                        }
                    });
                    changeEmailDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    changeEmailDialog.show();
                    break;
                case R.id.profilePasswordChange:
                    AlertDialog.Builder changePassDialog = new AlertDialog.Builder(context);
                    changePassDialog.setTitle("Change Password");
                    LinearLayout passDialogLayout = new LinearLayout(context);
                    passDialogLayout.setOrientation(LinearLayout.VERTICAL);
                    final EditText currentPassInput = new EditText(context);
                    currentPassInput.setHint("Current Password");
                    currentPassInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passDialogLayout.addView(currentPassInput);
                    final EditText newPassInput = new EditText(context);
                    newPassInput.setHint("New Password");
                    newPassInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passDialogLayout.addView(newPassInput);
                    changePassDialog.setView(passDialogLayout);
                    changePassDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String oldPass = currentPassInput.getText().toString();
                            String newPass = newPassInput.getText().toString();
                            final MainActivity mainact = (MainActivity)getActivity();
                            if(oldPass.equals(mainact.password)){
                                if(newPass.length() >= 6 && !newPass.equals(oldPass)){
                                    user.updatePassword(newPass)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User password updated.");
                                                        Toast.makeText(mainact, "Password changed",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                                else{
                                    dialogController.generateErrorDialog("Your new password must be at " +
                                            "least 6 characters and not match your old password");
                                }
                            }
                            else{
                                dialogController.generateErrorDialog("The current password you entered " +
                                        "doesn't match our records.");
                            }
                        }
                    });
                    changePassDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    changePassDialog.show();
                    break;
                case R.id.profileSync:
                    ValueEventListener namingDatabaseListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User profile = dataSnapshot.getValue(User.class);
                            profName.setText(user.getDisplayName());
                            profEmail.setText(profile.email);
                            profScore.setText(profile.score + " xp");
                            //write anything that needs uploading to database below here
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadProfile:onCancelled", databaseError.toException());
                        }
                    };
                    databaseReference.addListenerForSingleValueEvent(namingDatabaseListener);
                    break;
                case R.id.profileDelete:
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
                    deleteDialog.setTitle("Delete Account");
                    deleteDialog.setMessage("Are you sure you want to delete your Learnasaurus account?\n\n" +
                     "This action cannot be reversed and you will lose all progress.");
                    deleteDialog.setPositiveButton("Delete My Account", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseRefDeletion.child("users").child(userId).removeValue();
                            // delete from authentication
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User account deleted.");
                                                MainActivity mainact = (MainActivity)getActivity();
                                                mainact.signOut();
                                            }
                                        }
                                    });
                        }
                    });
                    deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    deleteDialog.show();
                    break;
            }
        }
        else {
            // re-auth
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
