package org.example.util;

public class objVersion02 {

    private String lineOrigin;

    private String nameMethodOrigin;

    private String type;

    public objVersion02(String type, String lineOrigin, String nameMethodOrigin) {
        this.type = type;
        this.lineOrigin = lineOrigin;
        this.nameMethodOrigin = nameMethodOrigin;
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
        return "objVersion02{" +
                "lineOrigin='" + lineOrigin + '\'' +
                ", nameMethodOrigin='" + nameMethodOrigin + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
