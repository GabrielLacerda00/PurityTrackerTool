package org.example.util;


import java.util.ArrayList;

public class objectOutputRefactMiner {

   private objVersion01 version01;

   private objVersion02 version02;


    public objectOutputRefactMiner(objVersion01 version01, objVersion02 version02) {
        this.version01 = version01;
        this.version02 = version02;
    }

    public objVersion01 getVersion01() {
        return version01;
    }

    public objVersion02 getVersion02() {
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