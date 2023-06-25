package org.example.util;

public class objVersion01 {
    private String lineOrigin;
    private String nameMethodOrigin;
    private String type;

    public objVersion01(String type, String lineOrigin, String nameMethodOrigin) {
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
}
