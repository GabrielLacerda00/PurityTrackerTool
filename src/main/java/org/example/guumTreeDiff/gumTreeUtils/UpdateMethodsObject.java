package org.example.guumTreeDiff.gumTreeUtils;

import org.example.util.TempObject;

import java.util.ArrayList;

public class UpdateMethodsObject {
    private ArrayList<TempObject> updateMethodsObjects;

    public UpdateMethodsObject(){
        this.updateMethodsObjects = new ArrayList<TempObject>();
    }

    public ArrayList<TempObject> getUpdateMethodsObjects() {
        return updateMethodsObjects;
    }

    public void setUpdateMethodsObjects(ArrayList<TempObject> updateMethodsObjects) {
        this.updateMethodsObjects = updateMethodsObjects;
    }

    public void addInArray(TempObject obj){
        updateMethodsObjects.add(obj);
    }

    @Override
    public String toString() {
        return "UpdateMethodsObject{" +
                "updateMethodsObjects=" + updateMethodsObjects +
                '}';
    }
}
