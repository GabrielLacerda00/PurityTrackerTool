package org.example.util;

import java.util.ArrayList;

public class objVersion02 {

    private String lineDst;

    private String nameMethodDst;

    private String type;

    public objVersion02(String type, String lineDst, String nameMethodDst) {
        this.type = type;
        this.lineDst = lineDst;
        this.nameMethodDst = nameMethodDst;
    }

    public String getLineDst() {
        return lineDst;
    }

    public String getNameMethodDst() {
        return nameMethodDst;
    }

    public String getType() {return type;}


    @Override
    public String toString() {
        return "objVersion02{" +
                "lineOrigin='" + lineDst + '\'' +
                ", nameMethodOrigin='" + nameMethodDst + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
