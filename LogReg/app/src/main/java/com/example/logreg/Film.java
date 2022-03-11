package com.example.logreg;

public class Film {
    String name;
    String category;
    public Film(){
    }
    public Film(String n, String c){
        this.name = n;
        this.category = c;
    }

    public String getName(){
        return this.name;
    }
    public String getCategory(){
        return this.category;
    }
    public void setName(String n){
        this.name = n;
    }
    public void setCategory(String c){
        this.category = c;
    }
}
