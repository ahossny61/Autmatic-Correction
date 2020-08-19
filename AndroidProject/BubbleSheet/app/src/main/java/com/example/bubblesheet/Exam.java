package com.example.bubblesheet;

public class Exam {
    private String answers,name;
    private int questionNumber;

    public  Exam(String n,String a,int qn){
        answers=a;
        name=n;
        questionNumber=qn;
    }
    public Exam(){

    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getAnswers() {
        return answers;
    }

    public String getName() {
        return name;
    }
}
