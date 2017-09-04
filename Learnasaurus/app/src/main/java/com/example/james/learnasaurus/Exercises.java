package com.example.james.learnasaurus;

public class Exercises {

    private String uid;
    public String attempts;
    public String correct;
    public String correctPercentage;
    public String firstAttempt;
    public String firstGrade;
    public String highGrade;
    public String lastAttempt;

    public Exercises(){
        // required empty constructor
    }

    public Exercises(String attempts, String correct, String correctPercentage, String firstAttempt,
                     String firstGrade, String highGrade, String lastAttempt) {
        this.attempts = attempts;
        this.correct = correct;
        this.correctPercentage = correctPercentage;
        this.firstAttempt = firstAttempt;
        this.firstGrade = firstGrade;
        this.highGrade = highGrade;
        this.lastAttempt = lastAttempt;
    }
}
