package com.example.mypersistencyapp;

//import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mypersistencyapp.model.User;

public class MainActivity extends FragmentActivity implements LoginFragment.Listener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attachFragmentToActivity();
    }

    private void attachFragmentToActivity() {
        Fragment currentFragment = getSupportFragmentManager().
                findFragmentById(R.id.fragment_container);
        if (currentFragment == null) {
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.fragment_container, loginFragment).
                    commit();
        }
    }

    @Override
    public void checkRegistration(User user) {
        DAO.getInstance().getUserList().add(user);
        Fragment currentFragment = getSupportFragmentManager().
                findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            nextFragment(user, currentFragment);
        }
    }

    private void nextFragment(User user, Fragment currentFragment) {
        /*
         *   Normally when an User is exited
         * the user variable is empty meaning the session argument
         * attached to fragment is blank.
         * */
        if (user.fullname().length() > 0) {
            PersonalInfoFragment personalInfoFragment = PersonalInfoFragment.
                    newInstance(user);
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container, personalInfoFragment).
                    commit();
        }
        if (user.fullname().trim().isEmpty()) {
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container, loginFragment).
                    commit();
        }
    }

}