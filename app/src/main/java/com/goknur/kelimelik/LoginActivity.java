package com.goknur.kelimelik;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_login );

        // Views
        mStatusTextView = (TextView) findViewById( R.id.title_text );


        // Button listeners
        findViewById( R.id.sign_in_button ).setOnClickListener( this );
        //l findViewById( R.id.sign_out_button ).setOnClickListener( this );
        // findViewById( R.id.disconnect_button ).setOnClickListener( this );


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken( getString( R.string.default_web_client_id ) )
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder( this )
                .enableAutoManage( this /* FragmentActivity */, this /* OnConnectionFailedListener */ )
                .addApi( Auth.GOOGLE_SIGN_IN_API, gso )
                .build();

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //signOut();
        updateUI( currentUser );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            if (result.isSuccess()) {

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle( account );
                //intent.putExtra( "isim", mStatusTextView.getText().toString() );


            } else {

                updateUI( null );

            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d( TAG, "kelimelik:" + acct.getId() );

        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential( acct.getIdToken(), null );
        mAuth.signInWithCredential( credential )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( TAG, "signInWithCredential:success" );
                            FirebaseUser user = mAuth.getCurrentUser();

                          /*  Intent intent = new Intent( getApplicationContext(), MainActivity.class );


                            Bundle bundle = new Bundle();
                            bundle.putString( "Deneme", user.getEmail() );

                            intent.putExtras( bundle );
                            startActivity( intent );
 */
                            updateUI( user );
                        } else {
                            // If sign in fails, display activity_sıralama message to the user.
                            Log.w( TAG, "signInWithCredential:failure", task.getException() );
                            Toast.makeText( LoginActivity.this, "Kimlik doğrulama başarısız oldu.",
                                    Toast.LENGTH_SHORT ).show();
                            updateUI( null );
                        }


                        hideProgressDialog();

                    }
                } );
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent( mGoogleApiClient );
        startActivityForResult( signInIntent, RC_SIGN_IN );
    }


    private void signOut() {

        mAuth.signOut();


        Auth.GoogleSignInApi.signOut( mGoogleApiClient ).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI( null );
                    }
                } );
    }

    private void revokeAccess() {

        mAuth.signOut();


        Auth.GoogleSignInApi.revokeAccess( mGoogleApiClient ).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI( null );
                    }
                } );
    }

    private void updateUI(FirebaseUser user) {

        // TODO: 12.07.2017 LoginActivty den MainActivity e data pasla

        hideProgressDialog();


        if (user != null) {
            Intent intent = new Intent( getApplicationContext(), MainActivity.class );
            Bundle bundle = new Bundle();
            bundle.putString( MainActivity.ARG_LOGIN_EMAIL, user.getEmail() );

            intent.putExtras( bundle );
            startActivity( intent );
        }

        /*if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);


            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        } */
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d( TAG, "onConnectionFailed:" + connectionResult );
        Toast.makeText( this, "Google Play Services error.", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {

            signIn();

        } else if (i == R.id.sign_out_button) {
            signOut();
        } //else if (i == R.id.disconnect_button) {
        revokeAccess();
    }
}

