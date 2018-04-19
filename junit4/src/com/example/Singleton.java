package com.example;

public class Singleton {
    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    private String name = "noname";

    public void setName(String name) {
        this.name = name;
    }

    public String greeting() {
        return "Hello, " + name;
    }
}
