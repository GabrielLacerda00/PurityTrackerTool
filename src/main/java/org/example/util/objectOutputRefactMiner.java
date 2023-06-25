package org.example.util;


import java.util.ArrayList;

public class objectOutputRefactMiner {

   private ArrayList<Object> version01;

   private ArrayList<Object> version02;


    public objectOutputRefactMiner(ArrayList<Object> version01, ArrayList<Object> version02) {
        this.version01 = version01;
        this.version02 = version02;
    }

    public ArrayList<Object> getVersion01() {
        return version01;
    }

    public ArrayList<Object> getVersion02() {
        return version02;
    }

    @Override
    public String toString() {
        return "Edições Esperadas{" +
                "version01=" + version01 +
                ", version02=" + version02 +
                '}';
    }
}