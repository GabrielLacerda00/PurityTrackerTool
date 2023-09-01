package org.example.util;

import java.util.ArrayList;

public class objVersion02 {

    private String lineDst;

    private String nameMethodDst;

    private String type;

    private static ArrayList<callerWaited> callerWaitedsMethod = new ArrayList<>();

    public objVersion02(String type, String lineDst, String nameMethodDst,ArrayList<callerWaited> callerWaitedMethod) {
        this.type = type;
        this.lineDst = lineDst;
        this.nameMethodDst = nameMethodDst;
        this.callerWaitedsMethod = callerWaitedMethod;
    }

    public String getLineDst() {
        return lineDst;
    }

    public String getNameMethodDst() {
        return nameMethodDst;
    }

    public String getType() {return type;}

    public  ArrayList<callerWaited> getCallerWaitedsMethod() {
        return callerWaitedsMethod;
    }

    @Override
    public String toString() {
        return "objVersion02{" +
                "lineOrigin='" + lineDst + '\'' +
                ", nameMethodOrigin='" + nameMethodDst + '\'' +
                ", type='" + type + '\'' +
                ", method calls=" + callerWaitedsMethod+ '\''+
                '}';
    }

}
