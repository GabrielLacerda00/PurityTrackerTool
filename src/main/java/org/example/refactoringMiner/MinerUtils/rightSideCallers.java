package org.example.refactoringMiner.MinerUtils;

import org.example.util.callerWaited;

import java.util.ArrayList;

public class rightSideCallers {
    private ArrayList<callerWaited> rightSideCallers = new ArrayList<>();

    public rightSideCallers(ArrayList<callerWaited> rightSideCallers) {
        this.rightSideCallers = rightSideCallers;
    }

    public ArrayList<callerWaited> getRightSideCallers() {
        return rightSideCallers;
    }

    public void setRightSideCallers(ArrayList<callerWaited> rightSideCallers) {
        this.rightSideCallers = rightSideCallers;
    }

    public void addAllInArray(ArrayList<callerWaited> callers){
        rightSideCallers.addAll(callers);
    }

    @Override
    public String toString() {
        return "rightSideCallers{" +
                "rightSideCallers=" + rightSideCallers +
                '}';
    }
}
