package com.example.subjects;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.subjects.app.drawing.DrawingBoardActivity;
import com.example.subjects.app.home.HomeActivity;
import com.example.subjects.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int RC_SIGN_IN = 123;

    private ActivityMainBinding dataBinding;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Choose authentication providers
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        if (MainApplication.getSharedPref().getString(AppConstants.PREF_LOGGED_IN, "").equals("")){
            dataBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            });
        }else {
            HomeActivity.start(MainActivity.this);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            Log.d(TAG, "onActivityResult: "+response);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult: "+user);

                MainApplication.getSharedPref()
                        .edit()
                        .putString(AppConstants.PREF_LOGGED_IN, "true")
                        .commit();

                HomeActivity.start(MainActivity.this);
                finish();
            } else {
                Log.d(TAG, "onActivityResult: sign in failed");
                MainApplication.getSharedPref()
                        .edit()
                        .putString(AppConstants.PREF_LOGGED_IN, "")
                        .commit();

                makeToast("Login failed please try again");
            }
        }else {
            makeToast("Login failed please try again");
        }


    }


}
