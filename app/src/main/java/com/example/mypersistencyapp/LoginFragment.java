package com.example.mypersistencyapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mypersistencyapp.model.User;
import com.example.mypersistencyapp.model.utils.FilePersistence;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // Decoupling Fragment from Activity using Interface
    public interface Listener {
        void checkRegistration(User user);
    }

    private LoginFragment.Listener listener;

    // Windget used by UI
    private AppCompatEditText firstnameText;
    private AppCompatEditText lastnameText;
    private AppCompatButton registerButton;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        bindingWidgetFromUIToLocalInstance();
        setUpButtonListener();
    }


    /*@Override
    public void onCreateOptionsMenu (Menu menu,
                                     MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_app, menu);
    }
*/

    private void setUpButtonListener() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setFname(firstnameText.getText().toString());
                user.setLname(lastnameText.getText().toString());
                writeUserParamsFromFile(getContext(), user);
                listener.checkRegistration(user);
            }
        });
    }

    private void writeUserParamsFromFile(Context context, User user) {
        if(FilePersistence.writeToFile("userList",getContext(),user)){
            Toast.makeText(context,"Successfully saved",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Saving Process Failed",Toast.LENGTH_SHORT).show();
        }
    }


    private void bindingWidgetFromUIToLocalInstance() {
        firstnameText = getView().findViewById(R.id.edit_fname);
        lastnameText = getView().findViewById(R.id.edit_lname);
        registerButton = getView().findViewById(R.id.button_add);
    }

}