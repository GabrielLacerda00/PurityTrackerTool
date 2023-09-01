package org.example.guumTreeDiff.gumTreeUtils;

import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;

import java.util.HashMap;

public class RenameMethodsObj {

    private HashMap<TempObject, renameMethodObject> renameMethodObjects;

    public RenameMethodsObj(){
        this.renameMethodObjects = new HashMap<>();
    }

    public HashMap<TempObject, renameMethodObject> getRenameMethodObjects() {
        return renameMethodObjects;
    }

    public void setRenameMethodObjects(HashMap<TempObject, renameMethodObject> renameMethodObjects) {
        this.renameMethodObjects = renameMethodObjects;
    }

    public void putInMap(TempObject key, renameMethodObject obj){
        renameMethodObjects.put(key,obj);
    }

    public void getKey(TempObject key){
        renameMethodObjects.get(key);
    }

    @Override
    public String toString() {
        return "RenameMethodObject{" +
                "renameMethodObjects=" + renameMethodObjects +
                '}';
    }
}
