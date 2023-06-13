package org.example.util;


public class objectOutputRefactMiner {
    private String lineOrigin;
    private String nameMethodOrigin;
    private String lineDest;
    private String nameMethodDst;

    private String type;

    public objectOutputRefactMiner(String type,String lineOrigin, String nameMethodOrigin, String lineDest, String nameMethodDst) {
        this.type = type;
        this.lineOrigin = lineOrigin;
        this.nameMethodOrigin = nameMethodOrigin;
        this.lineDest = lineDest;
        this.nameMethodDst = nameMethodDst;
    }

    public String getLineDest() {
        return lineDest;
    }

    public String getLineOrigin() {
        return lineOrigin;
    }

    public String getNameMethodDst() {
        return nameMethodDst;
    }

    public String getNameMethodOrigin() {
        return nameMethodOrigin;
    }

    @Override
    public String toString() {
        return "Update Method{" +
                "lineOrigin='" + lineOrigin + '\'' +
                ", nameMethodOrigin='" + nameMethodOrigin + '\'' +
                ", lineDest='" + lineDest + '\'' +
                ", nameMethodDst='" + nameMethodDst + '\'' +
                '}';
    }
}