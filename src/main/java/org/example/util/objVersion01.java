package org.example.util;

import java.util.ArrayList;

public class objVersion01 {
    private String lineOrigin;
    private String nameMethodOrigin;
    private String type;

    private static ArrayList<callerWaited> callerWaitedsMethod = new ArrayList<>();

    public objVersion01(String type, String lineOrigin, String nameMethodOrigin, ArrayList<callerWaited> callerWaitedsMethod) {
        this.type = type;
        this.lineOrigin = lineOrigin;
        this.nameMethodOrigin = nameMethodOrigin;
        this.callerWaitedsMethod = callerWaitedsMethod;
    }

    public  ArrayList<callerWaited> getCallMethods(){
        return callerWaitedsMethod;
    }

    public String getLineOrigin() {
        return lineOrigin;
    }

    public String getNameMethodOrigin() {
        return nameMethodOrigin;
    }

    public String getType() {return type;}

    @Override
    public String toString() {
        return "objVersion01{" +
                "lineOrigin='" + lineOrigin + '\'' +
                ", nameMethodOrigin='" + nameMethodOrigin + '\'' +
                ", type='" + type + '\'' +
                " method calls= " + callerWaitedsMethod+ '\''+
                '}';
    }
}
