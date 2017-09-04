package com.example.james.learnasaurus;

/**
 * Created by James on 29/03/2017.
 */

public class JavaTestQuestions {

    // array holding all questions for the Java test
    private String questions[] = {
            "What is a variable?",
            "What keyword appears in the class header of a subclass?",
            "What is the naming convention used in Java called?",
            "Which of the following values is a double?",
            "What actions can a subclass take from its parent?",
            "Which of the following is not a primitive data type?",
            "Inheritance is also known as...",
            "What data type should be used for a variable named: isOpen?",
            "When comparing two String values, what operator or method should we use?",
            "What is an access modifier?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"a small piece of memory which holds a value", "a java keyword",
                    "the behaviour of an object", "a subprogram"},
            {"int", "if", "return", "extends"},
            {"Smalltalk", "Positional", "CamelCase", "None"},
            {"10.0f", "50.0", "4", "'g'"},
            {"inherit from more than one parent", "no actions", "method overriding",
                    "inherit all features of superclass regardless of access modifier"},
            {"boolean", "long", "char", "String"},
            {"Specialisation", "Aggregation", "Composition", "Realisation"},
            {"String", "int", "boolean", "char"},
            {"==", "equals()", "=", "+"},
            {"It controls allocation of memory", "Used for exception handling", "The name of an item",
                    "They determine what properties can be used by external classes"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "a small piece of memory which holds a value", "extends", "CamelCase", "50.0",
            "method overriding", "String", "Specialisation", "boolean", "equals()",
            "They determine what properties can be used by external classes"
    };

    public int numberOfQuestions(){
        return questions.length;
    }

    public String pullQuestion(int i){
        String question = questions[i];
        return question;
    }

    public String pullOptionOne(int i){
        String option1 = options[i][0];
        return option1;
    }
    public String pullOptionTwo(int i){
        String option2 = options[i][1];
        return option2;
    }
    public String pullOptionThree(int i){
        String option3 = options[i][2];
        return option3;
    }
    public String pullOptionFour(int i){
        String option4 = options[i][3];
        return option4;
    }

    public String pullCorrect(int i){
        String correctAnswer = correct[i];
        return correctAnswer;
    }

}
