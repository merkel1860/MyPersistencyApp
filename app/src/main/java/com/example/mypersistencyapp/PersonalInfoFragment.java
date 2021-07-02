package com.example.mypersistencyapp;

import android.content.Context;
import android.os.Bundle;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mypersistencyapp.model.User;
import com.example.mypersistencyapp.model.utils.FilePersistence;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PersonalInfoFragment extends Fragment {
    private LinearLayoutCompat parentContainer;
    private final static String USER_SESSION = "FIRST_SESSION";
    private User userDetails;

    private TextView userTextView;
    private UUID userTextViewId;

    private AppCompatButton previousButton;
    private UUID previousBtnId;

    private LoginFragment.Listener listener;

    public PersonalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userDetails = (User) getArguments().getSerializable(USER_SESSION);
            System.out.println("Arguments successfully retrieved");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    public static PersonalInfoFragment newInstance(User anUser) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_SESSION, anUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        parentContainer = getView().findViewById(R.id.fragment_personal_details);
        userTextViewId = UUID.randomUUID();
        previousBtnId = UUID.randomUUID();
        updateUIFragment();
        FilePersistence.readFromFile("userList.txt",getContext(), new ArrayList<User>());
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.listener = (LoginFragment.Listener)context;

    }

    private TextView makeTextView(String msg, Context context) {
        TextView textView = new TextView(context);
        textView.setText(msg);
        // creating a TextView Id based on randomized UUID value by extracting
        // the significant bits.
        textView.setId((int) userTextViewId.getMostSignificantBits());
        return textView;
    }

    private AppCompatButton makeButton(String title, Context context){
        AppCompatButton aButton = new AppCompatButton(context);
        aButton.setText(title);
        aButton.setId((int)previousBtnId.getMostSignificantBits());
        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User myUser = new User();
                myUser.setLname("");
                myUser.setFname("");
                listener.checkRegistration(myUser);
            }
        });
        return aButton;
    }
    private void updateUIFragment() {
        LinearLayoutCompat linearLayout = new LinearLayoutCompat(getContext());
        userTextView = makeTextView(userDetails.fullname(), getContext());
        linearLayout.addView(userTextView, makeLayoutParams(50,
                5, 5, 5, 5));
        previousButton = makeButton("Back to Login",getContext());
        linearLayout.addView(previousButton,makeLayoutParams(50,5,
                5,5,5));
        parentContainer.addView(linearLayout);
    }

    private void bindingWidgetFromUIToLocalInstance() {
        Toast.makeText(getContext(),
                userDetails.fullname()
                , Toast.LENGTH_SHORT).show();
    }

    // Create a LayoutParams
    private LinearLayoutCompat.LayoutParams makeLayoutParams(int weight,
                                                             int rightMargin, int leftMargin,
                                                             int topMarging, int bottomMarging) {
        LinearLayoutCompat.LayoutParams buttonLayoutParams = new LinearLayoutCompat.
                LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.weight = weight;
        buttonLayoutParams.rightMargin = rightMargin;
        buttonLayoutParams.leftMargin = leftMargin;
        buttonLayoutParams.topMargin = topMarging;
        buttonLayoutParams.bottomMargin = bottomMarging;
        return buttonLayoutParams;
    }
}