package com.example.bubblesheet;


import com.google.firebase.firestore.Exclude;

public class User {
    private String email,username,password,id;

    public User(){

    }
    public User(String e,String u,String p){
        email=e;
        username=u;
        password=p;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}

