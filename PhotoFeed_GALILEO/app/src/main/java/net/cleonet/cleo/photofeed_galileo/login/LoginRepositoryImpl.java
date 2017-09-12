package net.cleonet.cleo.photofeed_galileo.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import net.cleonet.cleo.photofeed_galileo.domain.FirebaseAPI;
import net.cleonet.cleo.photofeed_galileo.entities.User;
import net.cleonet.cleo.photofeed_galileo.lib.GreenRobotEventBus;
import net.cleonet.cleo.photofeed_galileo.lib.base.EventBus;
import net.cleonet.cleo.photofeed_galileo.login.events.LoginEvent;
//import com.technologies.cleo.cleomove.domain.FirebaseHelper;
//import com.technologies.cleo.cleomove.entities.User;
//import com.technologies.cleo.cleomove.lib.EventBus;
//import com.technologies.cleo.cleomove.lib.GreenRobotEventBus;
//import com.technologies.cleo.cleomove.login.events.LoginEvent;

/**
 * Created by Pepe on 10/13/2016.
 */

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseAPI helper;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginRepositoryImpl";

    public LoginRepositoryImpl() {
        Log.d(TAG, "Constructor");
        this.helper = FirebaseAPI.getInstance();
        this.mDatabase = helper.getDataReference();
        this.mAuth = helper.getMyAuth();
        this.mAuthListener = helper.getMyAuthListener();
        this.mAuth.addAuthStateListener(this.mAuthListener);
    }

    @Override
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                    if (task.isSuccessful()) {
                        initSignIn();
                    } else {
                        Exception exception = task.getException();
                        postEvent(LoginEvent.onSignInError, exception.getMessage());
                    }
                }
            });
    }

    @Override
    public void signUp(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signUp:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            postEvent(LoginEvent.onSignUpSuccess);
                            signIn(email, password);
                        } else {
                            Exception exception = task.getException();
                            postEvent(LoginEvent.onSignUpError, exception.getMessage());
                        }
                    }
                });
    }

    private void initSignIn() {
        mDatabase = helper.getDataReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                if (currentUser == null) {
                    registerNewUser();
                }
                helper.changeUserConnectionStatus(User.ONLINE);
                postEvent(LoginEvent.onSignInSuccess);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void registerNewUser() {
        String email = helper.getAuthUserEmail();
        if (email != null) {
            User currentUser = new User();
            currentUser.setEmail(email);
            mDatabase.setValue(currentUser);
        }
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            Log.d(TAG, errorMessage);
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        Log.d(TAG, String.valueOf(type));
        postEvent(type, null);
    }
}
