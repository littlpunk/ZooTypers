package com.example.zootypers.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

/**
 * Testing to see if the login feature works correctly by logging into
 * an existing user, registering a new user, and also entering a password
 * for recovering information.
 * 
 * (Black box testing to make sure that the login UI works.)
 * 
 * @author dyxliang
 *
 */
public class LoginTest extends ActivityInstrumentationTestCase2<TitlePage> {

    private Solo solo;
    private static final int TIMEOUT = 10000;
    private Button multiButton;

    public LoginTest() {
        super(TitlePage.class);
    }

    /**
     * create a new solo class to use robotium
     * @throws Exception if activity isn't instantiated
     */
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        multiButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.multiplayer_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiButton.performClick();
            }
        });
        solo.sleep(1000);
    }

    /**
     * Goes to Multiplayer activity, click Register and putting in record that is already used
     */
    @Test(timeout = TIMEOUT)
    public void testingRegisteringForAccountInUse() {
        final TextView joinNow = (TextView) solo.getView(com.example.zootypers.R.id.join_now);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                joinNow.performClick();
            }
        });
        solo.sleep(1000);
        EditText username = (EditText) solo.getView(R.id.username_register_input);
        solo.enterText(username, "David");
        EditText password = (EditText) solo.getView(R.id.password_input_register);
        solo.enterText(password, "12345");
        EditText password2 = (EditText) solo.getView(R.id.confirm_password_input_register);
        solo.enterText(password2, "12345");
        EditText email = (EditText) solo.getView(R.id.email_input_register);
        solo.enterText(email, "davidqwe123@hotmail.com");
        final Button submitButton = (Button) solo.getView(com.example.zootypers.R.id.submit_register);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                submitButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.searchText("Username is already in use.");
    }

    /**
     * Goes to Multiplayer activity and login with an active account then logout.
     */
    @Test(timeout = TIMEOUT)
    public void testingLoginToExistingUserAndLoggingOut() {
        EditText username = (EditText) solo.getView(R.id.username_login_input);
        solo.enterText(username, "David");
        EditText password = (EditText) solo.getView(R.id.password_login_input);
        solo.enterText(password, "1234567");
        final Button loginButton = (Button) solo.getView(com.example.zootypers.R.id.login_button);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginButton.performClick();
            }
        });
        final Button logoutButton = (Button) solo.getView(com.example.zootypers.R.id.logout_button);
        solo.sleep(1000);
        solo.searchText("You are logged in as David");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logoutButton.performClick();
            }
        });
        solo.searchText("You have successfully logged out!");
    }

    /**
     * Goes to Multiplayer activity and request an invalid email for a password reset.
     */
    @Test(timeout = TIMEOUT)
    public void testingForgotPasswordInputFailure() {
        final TextView forgotButton = (TextView) solo.getView(com.example.zootypers.R.id.forgot_pw);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                forgotButton.performClick();
            }
        });
        EditText email = (EditText) solo.getView(R.id.email_forgot_password_input);
        solo.enterText(email, "davidqwe123@hotmail.com");
        solo.sleep(1000);
        final Button resetButton = (Button) solo.getView(com.example.zootypers.R.id.reset_password_button);
        solo.sleep(1000);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                resetButton.performClick();
//            }
//        });
    }

    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
