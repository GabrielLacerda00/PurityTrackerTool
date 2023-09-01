package org.example.util;


public class TempObjectMiner {
    private String lineOrigin;
    private String nameMethodOrigin;
    private String lineDest;
    private String nameMethodDst;

    private String type;

    public TempObjectMiner(String type, String lineOrigin, String nameMethodOrigin, String lineDest, String nameMethodDst) {
        this.type = type;
        this.lineOrigin = lineOrigin;
        this.nameMethodOrigin = nameMethodOrigin;
        this.lineDest = lineDest;
        this.nameMethodDst = nameMethodDst;
    }

    public TempObjectMiner(String type) {
        this.type = type;
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

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return
                "{type='" + type + '\''+
                        ",lineOrigin='" + lineOrigin + '\'' +
                        ", nameMethodOrigin='" + nameMethodOrigin + '\'' +
                        ", lineDest='" + lineDest + '\'' +
                        ", nameMethodDst='" + nameMethodDst + '\'' +
                        '}';
    }

    public String toStringInvocation() {
        return "Update Invocation{" +
                toString();
    }

    public String toStringMethod(){
        return "Update Method{"+ toString();
    }
}