package com.example.james.learnasaurus;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener,
        SocialFragment.OnFragmentInteractionListener,
        TwitterFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener, ComputingFragment.OnFragmentInteractionListener,
        ComputingOopFragment.OnFragmentInteractionListener, JavaFragment.OnFragmentInteractionListener,
        JavaBasicsFragment.OnFragmentInteractionListener, JavaClassesFragment.OnFragmentInteractionListener,
        JavaMethodsFragment.OnFragmentInteractionListener, JavaMethodsContent.OnFragmentInteractionListener,
        JavaMethodsPlayground.OnFragmentInteractionListener, JavaNamingFragment.OnFragmentInteractionListener,
        JavaRelationshipsFragment.OnFragmentInteractionListener, JavaInheritanceFragment.OnFragmentInteractionListener,
        JavaStatementsFragment.OnFragmentInteractionListener, JavaIfElseFragment.OnFragmentInteractionListener,
        JavaIfContent.OnFragmentInteractionListener, JavaElseContent.OnFragmentInteractionListener,
        JavaIfExerciseContent.OnFragmentInteractionListener, JavaObjectsFragment.OnFragmentInteractionListener,
        JavaObjectsContent.OnFragmentInteractionListener, JavaObjectsPlayground.OnFragmentInteractionListener,
        JavaVariablesFragment.OnFragmentInteractionListener, JavaVarScopeContent.OnFragmentInteractionListener,
        JavaVarTypesContent.OnFragmentInteractionListener, JavaVarTypesExerciseContent.OnFragmentInteractionListener,
        JavaVarScopeExerciseContent.OnFragmentInteractionListener, JavaTestLandingFragment.OnFragmentInteractionListener,
        JavaTestFragment.OnFragmentInteractionListener, TestResultFragment.OnFragmentInteractionListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private TextToSpeech textToSpeech;
    private String language = "";
    private Bundle savedInstanceState;
    private String email;
    public String password;
    private int score;
    private int totalScore;
    private String displayName;
    // boolean variables for back press
    private boolean homeShown;
    private boolean aboutShown;
    private boolean socialShown;
    private boolean computingShown;
    private boolean javaShown;
    // fragment variables
    private HomeFragment homeFragment;
    // nav drawer fragments
    private AboutFragment aboutFragment;
    private ProfileFragment profileFragment;
    private SocialFragment socialFragment;
    // lesson related fragments
    private ComputingFragment computingFragment;
    private JavaFragment javaFragment;
    private FragmentManager fragmentManager;
    private TextView navEmail;
    private TextView navName;
    private String testCompleted;
    private String testGrade;
    private int testScore;
    // googleplay related
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    public MainActivity(){
        // empty constructor
    }

    public MainActivity(int score){
        this.score = score;
    }

    /**
     * This method is called on the creation of activity. This is used to initialise the required
     * variables/resources
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            this.displayName = extras.getString("displayName");
            email = extras.getString("email");
            password = extras.getString("password");
        }

        // Create the Google Api Client with access to the Play Games services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        // TextToSpeech set up. If language has not been changed via settings, the default is UK.
        if(language.equals("")){
            language = "UK"; // The language variable will be used to set different Locale's in TTS.
        }
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.UK); // default
                    if (language.equals("German")){
                        result = textToSpeech.setLanguage(Locale.GERMAN);
                    }
                    else if (language.equals("French")){
                        result = textToSpeech.setLanguage(Locale.FRENCH);
                    }
                    if(result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("NamingTTS", "Language not supported");
                    }
                }
            }
        });

        homeFragment = new HomeFragment();
        socialFragment = new SocialFragment();
        aboutFragment = new AboutFragment();
        profileFragment = new ProfileFragment();
        computingFragment = new ComputingFragment();
        javaFragment = new JavaFragment();
        fragmentManager = getSupportFragmentManager();
        // instead of replacing the content with fragment straight away, call the method to do so
        // allowing control over the title settings
        openHome();

        homeShown = true;
        aboutShown = false;
        socialShown = false;
        computingShown = false;
        javaShown = false;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayName = firebaseUser.getDisplayName();

        ImageView navAccount = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.nav_account_image);
        navAccount.setOnClickListener(profileListener);
        navName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_name);
        navName.setText(displayName);
        navName.setOnClickListener(profileListener);
        navEmail = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        navEmail.setText(email);
        navEmail.setOnClickListener(profileListener);

        setTotalScore();
    }

    boolean mExplicitSignOut = false;
    boolean mInSignInFlow = false; // set to true when you're in the middle of the
    // sign in flow, to know you should not attempt
    // to connect in onStart()

    @Override
    protected void onStart() {
        super.onStart();
        if (!mInSignInFlow && !mExplicitSignOut) {
            // auto sign in
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    public void updateDrawer(String name, String email){
        navName.setText(name);
        navEmail.setText(email);
    }

   View.OnClickListener profileListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           fragmentManager.beginTransaction()
                   .replace(R.id.content_main, profileFragment)
                   .addToBackStack(null)
                   .commit();
       }
   };

    /**
     * This method is called from external classes to pass the required string resource for use by
     * TextToSpeech (what the user hears).
     * @param toSpeak
     */
    public void speak(String toSpeak){
        // Check the API level
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_ADD, null, null);
        }
        else {
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);
        }
        unlockAchievement("CgkIrqu_x-kEEAIQBQ"); // teacher speaks
    }

    /*
    * This method will be called if the user clicks on the TextToSpeech symbol within a fragment to
    * stop the sound from playing. Currently stops the text completely rather than pause.
    */
    public void stopSpeech(){
        if(textToSpeech.isSpeaking()){
            textToSpeech.stop();
        }
    }

    /*
    * This method will be used when the app is closed. The code inside frees the resources used by
    * TextToSpeech.
    */
    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown(); // free the resources
        }
        super.onDestroy();
    }

    /*
    * Method which is controlled by user pressing the back button on bottom navigation bar
    */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // if the navigation drawer is open, close it
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            // pop the last fragment from the stack
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Method for selecting an item from the menu in the app bar (top right).
     * @param item
     * @return menu item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // open the settings fragment here
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) { // home fragment containing the course buttons
            openHome();
        } else if (id == R.id.nav_accomplishments) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient), 0);
        } else if (id == R.id.nav_leaderboard) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.leaderboard_id)), 0);
        } else if (id == R.id.nav_social) { // social fragment containing the relevant links to facebook and twitter
            openSocial();
        } else if (id == R.id.nav_about) { // about fragment containing info about the app
            openAbout();
        } else if (id == R.id.nav_settings) { // settings fragment containing the settings for the app
            //openSettings();
        } else if (id == R.id.nav_signout) {
            homeShown = false;
            signOut();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method implemented from HomeFragment
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     *  These methods are for controlling the back buttons operations in a tidy format. not optimal
     *  for managing a stack but it will do for now.
     */
    public void openHome(){
        setTitle("Home");
        homeShown = true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, homeFragment)
                .addToBackStack(null)
                .commit();
    }

    public void openAbout(){
        setTitle("About");
        homeShown = false;
        aboutShown = true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, aboutFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openSocial(){
        setTitle("Social");
        homeShown = false;
        socialShown = true;
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, socialFragment)
                .addToBackStack(null)
                .commit();
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void setFragmentVariables(boolean home, boolean about, boolean social, boolean computing,
                                     boolean java){
        homeShown = home;
        aboutShown = about;
        socialShown = social;
        computingShown = computing;
        javaShown = java;
    }

    public void setHomeFragVar(boolean shown){
        homeShown = shown;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String checkPassword(){
        return password;
    }

    public void setTestVariables(String test, String grade, int score){
        testCompleted = test;
        testGrade = grade;
        testScore = score;
    }

    public String getTestCompleted(){
        return testCompleted;
    }

    public int getTestScore(){
        return testScore;
    }

    public String getTestGrade(){
        return testGrade;
    }

    public void setTotalScore(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(firebaseUser.getUid());
        ValueEventListener startUpListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User profile = dataSnapshot.getValue(User.class);
                int databaseCurrentScore = Integer.parseInt(profile.score);
                totalScore = databaseCurrentScore;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "updateScore:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addListenerForSingleValueEvent(startUpListener);
    }

    public void updateTotalScore(int updateValue){
        totalScore = totalScore + updateValue;
    }

    /*
    GooglePlayGamesServices/GoogleAPIClient implemented methods
    */
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled, launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "Sign In Failed")) {
                mResolvingConnectionFailure = false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in failed.
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.sign_in_failed);
            }
        }
    }

    // Achievements unlock and Leaderboards methods.
    public void updateLeaderboard(int score){
        totalScore += score;
        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_id), totalScore);
    }

    public void unlockAchievement(String achievementId){
        Games.Achievements.unlock(mGoogleApiClient, achievementId);
    }

    public void incrementAchievements(int increment){
        String prettyHistoricId = "CgkIrqu_x-kEEAIQCA";
        String masterOfMyProfessionId = "CgkIrqu_x-kEEAIQCw";
        Games.Achievements.increment(mGoogleApiClient, prettyHistoricId, increment);
        Games.Achievements.increment(mGoogleApiClient, masterOfMyProfessionId, increment);
    }
}