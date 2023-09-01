package org.example.refactoringMiner.MinerUtils;

import org.example.util.objVersion01;

import java.util.ArrayList;

public class versionOriginCode {

    private ArrayList<objVersion01> versao01;

    public versionOriginCode() {
        this.versao01 = new ArrayList<>();
    }

    public ArrayList<objVersion01> getVersao01() {
        return versao01;
    }

    public void setVersao01(ArrayList<objVersion01> versao01) {
        this.versao01 = versao01;
    }

    public void addInArray(objVersion01 objV1){
        versao01.add(objV1);
    }

    @Override
    public String toString() {
        return "versionOriginCode{" +
                "versao01=" + versao01 +
                '}';
    }
}
