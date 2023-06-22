package org.example.refactoringTypes;

import org.example.util.TempObject;
import org.example.util.objectOutputRefactMiner;

import java.util.ArrayList;

public class renameMethodObject {

    private ArrayList<TempObject> updatesMethodsList = new ArrayList<>();

    private ArrayList<TempObject> updateInvocationList = new ArrayList<>();

    private  TempObject updateMethodObj;

    private TempObject updateInvocationObj;

    public renameMethodObject(TempObject updateMethodObj, TempObject updateInvocationObj){
       this.updateMethodObj = updateMethodObj;
       this.updateInvocationObj = updateInvocationObj;
    }

    public TempObject getUpdateInvocationObj() {
        return updateInvocationObj;
    }


    public TempObject getUpdateMethodObj() {
        return updateMethodObj;
    }


    @Override
    public String toString() {
        return "Rename Method{" +
                "Update Method"+
                updateMethodObj+ '\'' +
                "Update Invocation"+
                updateInvocationObj+ '\'' +
                '}';
    }
}
