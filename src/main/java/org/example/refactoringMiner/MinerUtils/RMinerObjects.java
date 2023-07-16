package org.example.refactoringMiner.MinerUtils;

import org.example.util.objectOutputRefactMiner;

import java.util.ArrayList;

public class RMinerObjects {
    private ArrayList<objectOutputRefactMiner> objectsRefactoringMiner;

    public RMinerObjects(){
        this.objectsRefactoringMiner = new ArrayList<>();
    }

    public ArrayList<objectOutputRefactMiner> getObjectsRefactoringMiner() {
        return objectsRefactoringMiner;
    }

    public void setObjectsRefactoringMiner(ArrayList<objectOutputRefactMiner> objectsRefactoringMiner) {
        this.objectsRefactoringMiner = objectsRefactoringMiner;
    }

    public void addInArray(objectOutputRefactMiner outputRefactMiner){
        objectsRefactoringMiner.add(outputRefactMiner);
    }

    @Override
    public String toString() {
        return "ObjectsrefactoringMiner{" +
                 objectsRefactoringMiner +
                '}';
    }
}
