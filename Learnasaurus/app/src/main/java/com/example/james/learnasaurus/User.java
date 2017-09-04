package com.example.james.learnasaurus;

public class User {
    private String uid;
    private String displayName;
    public String name;
    public String email;
    public String score;
    public String surname;

    public User(){
        // required for firebase
    }

    public User(String name, String email, String score, String surname){
        this.name = name;
        this.email = email;
        this.score = score;
        this.surname = surname;
    }

    public String getUid() {
        return uid;
    }

    public String getDisplayName(){
        return displayName;
    }

    public String getEmail(){
        return email;
    }

    public String getScore(){
        return score;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void updateScore(int score){
        this.score += score;
    }
}
