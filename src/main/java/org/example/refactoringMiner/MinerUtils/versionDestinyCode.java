package org.example.refactoringMiner.MinerUtils;

import org.example.util.objVersion01;
import org.example.util.objVersion02;

import java.util.ArrayList;

public class versionDestinyCode {

    private ArrayList<objVersion02> versao02;

    public versionDestinyCode() {
        this.versao02 = new ArrayList<>();
    }

    public ArrayList<objVersion02> getVersao02() {
        return versao02;
    }

    public void setVersao02(ArrayList<objVersion02> versao02) {
        this.versao02 = versao02;
    }

    public void addInArray(objVersion02 objV2){
        versao02.add(objV2);
    }


    @Override
    public String toString() {
        return "versionDestinyCode{" +
                "versao02=" + versao02 +
                '}';
    }
}
