package org.example.refactoringTypes;

import org.example.util.TempObject;
import org.example.util.objectOutputRefactMiner;

public class renameMethodObject {

    private TempObject updateMethodObj;

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
