package com.example.mypersistencyapp;

import com.example.mypersistencyapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class DAO {
    private static DAO instance = new DAO();
    private List<User> userList;

    private DAO(){
        userList = new ArrayList<>();
    }

    public static DAO getInstance(){
        return instance;
    }

    public List<User> getUserList() {
        return userList;
    }
}
