package org.example.refactoringTypes;

import it.unimi.dsi.fastutil.Arrays;
import org.example.util.TempObject;
import org.example.util.objectOutputRefactMiner;

import java.util.ArrayList;
import java.util.Objects;

public class renameMethodObject {

    private ArrayList<TempObject> updateInvocationList = new ArrayList<>();

    private  TempObject updateMethodObj;


    public renameMethodObject(TempObject updateMethodObj){
       this.updateMethodObj = updateMethodObj;
    }

    public void setUpdateInvocationList(TempObject updateInvocationObj) {
        this.updateInvocationList.add(updateInvocationObj);
    }

    public ArrayList<TempObject> getUpdateInvocationInList() {
        return updateInvocationList;
    }

    public TempObject getUpdateMethodObj() {
        return updateMethodObj;
    }


    @Override
    public String toString() {
        return "\nRename Method{" +
                "Update Method"+
                updateMethodObj+ '\'' +
                "Update Invocation"+
                updateInvocationList + '\'' +
                '}';
    }
}
