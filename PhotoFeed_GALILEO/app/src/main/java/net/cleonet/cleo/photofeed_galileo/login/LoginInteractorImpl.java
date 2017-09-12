package net.cleonet.cleo.photofeed_galileo.login;

/**
 * Created by Pepe on 10/13/2016.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    public LoginInteractorImpl() {
        //Log.d(TAG,"Constructor");
        this.loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void doSignUp(String email, String password) {
        //Log.d(TAG,"doSignUp");
        loginRepository.signUp(email, password);
    }

    @Override
    public void doSignIn(String email, String password) {
        //Log.d(TAG,"doSignIn");
        loginRepository.signIn(email, password);
    }
}
