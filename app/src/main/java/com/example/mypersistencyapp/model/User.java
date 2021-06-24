package com.example.mypersistencyapp.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class User implements Serializable {
    private String fname;
    private String lname;
    private Login credentials;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String fullname(){
        return fname+" "+lname;
    }
    private class Login {
        private  String username;
        private  String password;
        private List<Login> oldLogins;

        public Login(String username, String password) {
            this.username = username;
            this.password = encodingPass(password);
        }

        private String encodingPass(String pass){
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            md.update(pass.getBytes());
            byte[] digest = md.digest();
            BigInteger bi = new BigInteger(digest);
            String hashtext = bi.toString(16);

            return hashtext;
        }

        private boolean checkPass(String pass){
            return 0 == password.compareToIgnoreCase(encodingPass(pass));
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = encodingPass(password);
        }
    }
}
