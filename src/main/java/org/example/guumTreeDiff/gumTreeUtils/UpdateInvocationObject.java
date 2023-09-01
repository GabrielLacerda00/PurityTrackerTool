package org.example.guumTreeDiff.gumTreeUtils;

import org.example.util.TempObject;

import java.util.ArrayList;

public class UpdateInvocationObject {
    private ArrayList<TempObject> updateInvocationObjects;

    public UpdateInvocationObject(){
        this.updateInvocationObjects = new ArrayList<TempObject>();
    }

    public ArrayList<TempObject> getUpdateInvocationObjects() {
        return updateInvocationObjects;
    }

    public void setUpdateInvocationObjects(ArrayList<TempObject> updateInvocationObjects) {
        this.updateInvocationObjects = updateInvocationObjects;
    }

    public void addInArray(TempObject obj){
        updateInvocationObjects.add(obj);
    }

    @Override
    public String toString() {
        return "UpdateInvocationObject{" +
                "updateInvocationObjects=" + updateInvocationObjects +
                '}';
    }
}
